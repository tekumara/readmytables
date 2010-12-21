package org.omancode.rmt.tablereader.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.omancode.rmt.cellreader.CellReader;
import org.omancode.rmt.tablereader.AbstractTableReader;
import org.omancode.rmt.tablereader.Column;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCSVException;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;

/**
 * Delimited file reader.
 * 
 * @author Oliver Mannion
 * @version $Revision: 72 $
 */
public class DelimitedFileReader extends AbstractTableReader {

	private final String filename;
	private final String[] headerRow;
	private final String[] headerRowColumnsRead;
	private final File file;
	private final DelimiterSettings delimSettings;
	private final CellProcessor[] cellProcessors;

	/**
	 * Convenience constructor loading all columns with default cell readers.
	 * Calls {@link #DelimitedFileReader(File, String[])} with
	 * {@code columnsToRead} = {@code null}.
	 * 
	 * @param file
	 *            file to read
	 * @throws IOException
	 *             if problem opening {@code file} or if a column specified does
	 *             not exist.
	 */
	public DelimitedFileReader(File file) throws IOException {
		this(file, null);
	}

	/**
	 * Convenience constructor loading specified columns with default cell
	 * readers. Calls
	 * {@link #DelimitedFileReader(File, String[], CellReader[], DelimiterSettings)}
	 * with {@code cellReaders} = {@code null} and {@code settings} =
	 * {@code null}.
	 * 
	 * @param file
	 *            file to read
	 * @param columnsToRead
	 *            if {@code null}, all columns are read. If specified, then only
	 *            these columns will be read.
	 * @throws IOException
	 *             if problem opening {@code file} or if a column specified does
	 *             not exist.
	 */
	public DelimitedFileReader(File file, String[] columnsToRead)
			throws IOException {
		this(file, columnsToRead, null, null);
	}

	/**
	 * Construct DelimitedFileReader.
	 * 
	 * @param file
	 *            file
	 * @param columnsToRead
	 *            if {@code null}, all columns are read. If specified, then only
	 *            these columns will be read.
	 * @param cellReaders
	 *            cell readers. If {@code null} then the default {@link Column}
	 *            cell reader is used for all columns.
	 * @param settings
	 *            delimiter settings. If {@code null} will use default (
	 *            {@link DelimiterSettings#WINDOWS_CSV}).
	 * @throws IOException
	 *             if problem loading file
	 */
	public DelimitedFileReader(File file, String[] columnsToRead,
			CellReader<?>[] cellReaders, DelimiterSettings settings)
			throws IOException {

		this.delimSettings = settings == null ? DelimiterSettings.WINDOWS_CSV
				: settings;
		this.file = file;
		this.filename = file.getCanonicalPath();

		// Load header
		headerRow = getNewCsvMapReader().getCSVHeader(true);

		if (headerRow == null) {
			throw new IOException(getName() + "is empty.");
		}

		initializeColumns(columnsToRead, cellReaders);
		Map<String, Column> mappedColumns = Column.mappedColumns(columns);

		headerRowColumnsRead = initializeHeaderRowColumnsRead(headerRow,
				mappedColumns);

		cellProcessors = createCellProcessors(headerRowColumnsRead,
				mappedColumns);
	}

	/**
	 * Return {@code header} with all the columns that are not being read (ie:
	 * do not appear in {@code mappedColumns}) set to {@code null}. This is so
	 * that {@link ICsvMapReader#read(String...)} will ignore the columns we
	 * don't need.
	 * 
	 * @param header
	 * @param mappedColumns
	 * @return
	 */
	private String[] initializeHeaderRowColumnsRead(String[] header,
			Map<String, Column> mappedColumns) {

		if (mappedColumns.isEmpty()) {
			return null;
		}

		String[] headerRowColumnsRead = new String[header.length];

		for (int i = 0; i < header.length; i++) {
			headerRowColumnsRead[i] = mappedColumns.containsKey(header[i]) ? header[i]
					: null; // NOPMD
		}
		return headerRowColumnsRead;
	}

	/**
	 * Returns a new CSV map reader each time. We need a new reader for each
	 * iterator we create.
	 * 
	 * @return ICsvMapReader
	 * @throws FileNotFoundException
	 */
	private ICsvMapReader getNewCsvMapReader() throws FileNotFoundException {
		return new CsvMapReader(new FileReader(file),
				delimSettings.getCsvPreference());
	}

	@Override
	public final String getName() {
		return filename + ": ";
	}

	@Override
	public String[] getHeaderRow() throws IOException {
		return headerRow;
	}

	@Override
	public Iterator<Object[]> iterator() {
		return new CSVRowIterator();
	}

	/**
	 * Create a CellProcessor array from the file columns. The array must be the
	 * same size of the header array, so columns that are not being read will
	 * have a {@code null} in that location in the returned array.
	 * 
	 * @param header
	 *            array of column names in the file
	 * @param mappedColumns
	 *            map of columns in the file to process
	 * @return an array of CellProcessors that will parse each column, or
	 *         {@code null} if {@code columns} is {@code null}.
	 */
	public static CellProcessor[] createCellProcessors(String[] header,
			Map<String, Column> mappedColumns) {
		if (mappedColumns.isEmpty()) {
			return null;
		}

		CellProcessor[] cellProcessors = new CellProcessor[header.length];

		for (int i = 0; i < header.length; i++) {
			Column column = mappedColumns.get(header[i]);

			if (column == null) {
				cellProcessors[i] = null; // NOPMD
			} else {
				cellProcessors[i] = new CellProcessorAdaptor(
						column.getCellReader());
			}

		}
		return cellProcessors;
	}

	/**
	 * Iterator that returns Object arrays of each line in the delimited file.
	 * Starts of line 2, ie: after the header.
	 * 
	 * @author Oliver Mannion
	 * 
	 */
	private class CSVRowIterator implements Iterator<Object[]> {

		private final ICsvMapReader reader;
		private Object[] nextLine;

		CSVRowIterator() {

			try {
				reader = getNewCsvMapReader();

				// skip past header
				reader.getCSVHeader(true);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			nextLine = readNextLine();

		}

		@Override
		public boolean hasNext() {
			return nextLine != null;
		}

		@Override
		public Object[] next() {
			Object[] thisLine = nextLine;
			nextLine = readNextLine();

			return thisLine;
		}

		/**
		 * Read next line from the CsvReader as a map. Return it as an Object[].
		 * 
		 * @return object array
		 */
		private Object[] readNextLine() {
			Map<String, ?> map;
			try {
				map = reader.read(headerRowColumnsRead, cellProcessors);
			} catch (SuperCSVException e) {
				throw new RuntimeException(getName() + e);
			} catch (IOException e) {
				throw new RuntimeException(getName() + e);
			}

			if (map == null) {
				return null;
			}

			// create an Object[] from the map
			Object[] row = new Object[columns.length];

			for (int i = 0; i < row.length; i++) {
				row[i] = map.get(columns[i].getName());
			}

			return row;
		}

		@Override
		public void remove() {
			throw new NotImplementedException();
		}

	}
}

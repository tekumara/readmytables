package org.omancode.rmt.tablereader;

import java.io.IOException;

import org.omancode.rmt.cellreader.CellReader;
import org.omancode.rmt.cellreader.CellReaders;
import org.omancode.util.ArrayUtil;

/**
 * Represents a table containing a header and cells (i.e. rows consisting of
 * columns). Each column contains values of the same type.
 * 
 * Column names may be specified (allowing reading of only certain columns from
 * the table) or unspecified (in which case columns names do not need to be
 * known in advance and all columns are loaded).
 * 
 * Types of columns may be specified or unspecified (in which case the widest
 * possible type is used for loading values, eg: Object or String).
 * 
 * Missing values are supported via the use of optional cell readers, eg:
 * {@link CellReaders#OPTIONAL_DOUBLE}.
 * 
 * <p>
 * Returns an iterator that starts at the first row after the header. Subclasses
 * are responsible for making sure their iterator calls the column's
 * {@link CellReader} on the values it reads.
 * 
 * @author Oliver Mannion
 * @version $Revision: 72 $
 */
public abstract class AbstractTableReader implements Iterable<Object[]> {

	/**
	 * Columns read from the table. Used by subclasses.
	 */
	protected Column[] columns;

	/**
	 * Sets up {@link #columns}. Must be called by the subclass during its
	 * construction.
	 * 
	 * @param columnsToRead
	 *            if {@code null}, all columns are read. If specified, then only
	 *            these columns will be read.
	 * @param cellReaders
	 *            cell readers. If {@code null} then the default {@link Column}
	 *            cell reader is used for all columns.
	 * @throws IOException
	 *             if problem reading header during initialisation or problem
	 *             with {@code columnsToRead} or {@code cellReaders}.
	 */
	protected void initializeColumns(String[] columnsToRead,
			CellReader<?>[] cellReaders) throws IOException {
		this.columns = loadCellReadersIntoColumns(initColumns(columnsToRead),
				cellReaders);
	}

	/**
	 * Load header and return columns with {@code expectedColumns} or all
	 * columns if none expected.
	 * 
	 * @param expectedColumns
	 *            columns names to read
	 * @return array of columns
	 * @throws IOException
	 *             if column in {@code expectColumns} does not exist in the
	 *             header.
	 */
	private Column[] initColumns(String[] expectedColumns) throws IOException {

		Column[] cols;

		String[] headerRow = getHeaderRow();

		checkHeader(headerRow);

		if (expectedColumns == null) {
			// no specified columns,
			// so do set up for reading all columns

			cols = new Column[headerRow.length];

			for (int i = 0; i < headerRow.length; i++) {
				cols[i] = new Column(headerRow[i], i); // NOPMD
			}

		} else {
			// determine index of specified columns

			cols = new Column[expectedColumns.length];

			for (int i = 0; i < expectedColumns.length; i++) {

				String colToRead = expectedColumns[i];

				// determine the array index of the
				// column that will be read
				int positionOfColToRead = ArrayUtil.indexOfString(headerRow,
						colToRead, false, 0);

				if (positionOfColToRead == ArrayUtil.INDEX_NOT_FOUND) {
					throw new IOException(getName()
							+ " does not contain a column with the header \""
							+ colToRead + "\"");
				}

				cols[i] = new Column(colToRead, positionOfColToRead); // NOPMD
			}

		}

		return cols;
	}

	/**
	 * Make sure the header exists and does not contain any empty column
	 * headings.
	 * 
	 * @param headerRow
	 *            header row
	 * @throws IOException
	 *             if header doesn't exist or contains an empty heading
	 */
	private void checkHeader(String[] headerRow) throws IOException {
		if (headerRow == null) {
			throw new IOException(getName() + " header is empty.");
		}

		for (int i = 0; i < headerRow.length; i++) {
			// if header column is null or the empty string
			if (headerRow[i] == null || "".equals(headerRow[i])) {
				throw new IOException(getName() + " header row, column "
						+ (i + 1) + " is empty");
			}
		}

	}

	/**
	 * Set the cell reader for each column, if specified in {@code cellReaders}.
	 * 
	 * @param cols
	 *            columns array
	 * @param cellReaders
	 *            cell readers array. If {@code null} do nothing
	 * @throws IOException
	 *             if {@code cellReaders.length != cols.length}.
	 */
	private static Column[] loadCellReadersIntoColumns(Column[] cols,
			CellReader<?>[] cellReaders) throws IOException {

		if (cellReaders != null) {

			if (cellReaders.length != cols.length) {
				throw new IOException("The number of cell readers ("
						+ cellReaders.length
						+ ") does not match the number of " + "columns ("
						+ cols.length + ").");
			}

			for (int i = 0; i < cols.length; i++) {
				cols[i].setCellReader(cellReaders[i]);
			}

		}

		return cols;
	}

	/**
	 * Generates column types from the cell readers.
	 * 
	 * @return column types
	 */
	public Class<?>[] getColumnTypes() {
		Class<?>[] columnTypes = new Class[columns.length];

		for (int i = 0; i < columns.length; i++) {
			columnTypes[i] = columns[i].getCellReader().getResultType();
		}

		return columnTypes;
	}

	/**
	 * Return list of column names that this instance is reading. This will be
	 * all columns defined in the header if no columns were specified in
	 * the constructor. Otherwise, it will be those columns specified in the
	 * constructor.
	 * 
	 * @return columns read
	 */
	public String[] getColumnsRead() {
		String[] columnNames = new String[columns.length];

		for (int i = 0; i < columns.length; i++) {
			columnNames[i] = columns[i].getName();
		}

		return columnNames;
	}

	/**
	 * Get the table name.
	 * 
	 * @return table name
	 */
	public abstract String getName();

	/**
	 * Reads and returns the first row of the current sheet as a String array.
	 * 
	 * @return header row
	 * @throws IOException
	 *             if any of the cells are empty.
	 */
	public abstract String[] getHeaderRow() throws IOException;

}
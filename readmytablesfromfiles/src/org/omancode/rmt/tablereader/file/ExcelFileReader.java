package org.omancode.rmt.tablereader.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.omancode.rmt.cellreader.CellReader;
import org.omancode.rmt.tablereader.AbstractTableReader;
import org.omancode.rmt.tablereader.Column;

/**
 * Excel XLS/XLSX file reader.
 * 
 * @author Oliver Mannion
 * @version $Revision: 54 $
 */
public class ExcelFileReader extends AbstractTableReader {

	private final Sheet currentSheet;
	private final String filename;

	/**
	 * Convenience constructor loading all columns with default cell readers.
	 * Calls {@link #ExcelFileReader(File, String[])} with {@code columnsToRead}
	 * = {@code null}.
	 * 
	 * @param file
	 *            file to read
	 * @throws IOException
	 *             if problem opening {@code file} or if a column specified does
	 *             not exist.
	 */
	public ExcelFileReader(File file) throws IOException {
		this(file, null);
	}

	/**
	 * Convenience constructor loading specified columns with default cell
	 * readers. Calls {@link #ExcelFileReader(File, String[], CellReader[])}
	 * with {@code cellReaders} = {@code null}.
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
	public ExcelFileReader(File file, String[] columnsToRead)
			throws IOException {
		this(file, columnsToRead, null);
	}

	/**
	 * Construct ExcelFileReader.
	 * 
	 * @param file
	 *            file
	 * @param columnsToRead
	 *            if {@code null}, all columns are read. If specified, then only
	 *            these columns will be read.
	 * @param cellReaders
	 *            cell readers. If {@code null} then the default
	 *            {@link Column} cell reader is used for all columns.
	 * @throws IOException
	 *             if problem loading file
	 */
	public ExcelFileReader(File file, String[] columnsToRead,
			CellReader<?>[] cellReaders) throws IOException {

		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(new FileInputStream(file));
			this.filename = file.getCanonicalPath();
		} catch (InvalidFormatException e) {
			throw new IOException(e);
		}

		// set current sheet to the first sheet
		currentSheet = workbook.getSheetAt(0);

		// check sheet has 2 or more rows
		if (currentSheet.getPhysicalNumberOfRows() < 2) {
			throw new IOException(getName() + " contains no rows.");
		}

		initializeColumns(columnsToRead, cellReaders);

	}

	@Override
	public final String getName() {
		return filename + ": Sheet \"" + currentSheet.getSheetName() + "\"";
	}

	@Override
	public String[] getHeaderRow() throws IOException {
		if (currentSheet.getPhysicalNumberOfRows() < 1) {
			throw new IOException(getName() + " is empty.");
		}

		Iterator<Row> rowIterator = currentSheet.rowIterator();
		Row firstRow = rowIterator.next();

		short rowLength = firstRow.getLastCellNum();
		String[] result = new String[rowLength];

		for (int i = 0; i < rowLength; i++) {
			Cell cell = firstRow.getCell(i);

			Object cellValue = getExcelCellValue(cell);

			if (cellValue == null) {
				throw new IOException(getName() + " header row, column "
						+ (i + 1) + " is empty");
			}

			result[i] = cellValue.toString();
		}

		return result;
	}

	/**
	 * Returns the value in a Excel cell as a Java object. Returns empty String
	 * if the cell is blank, and returns an Error string if the cell has an
	 * error.
	 * 
	 * @param cell
	 *            Excel cell
	 * @return empty String if blank, String if text, Boolean if boolean, Double
	 *         if numeric.
	 */
	public static Object getExcelCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		Object result;

		int cellType = cell.getCellType();

		if (cellType == Cell.CELL_TYPE_FORMULA) {
			cellType = cell.getCachedFormulaResultType();
		}

		if (cellType == Cell.CELL_TYPE_BLANK) {
			result = "";
		} else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
			result = Boolean.valueOf(cell.getBooleanCellValue());
		} else if (cellType == Cell.CELL_TYPE_ERROR) {
			result = "[Error " + cell.getErrorCellValue() + "]";
		} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
			result = Double.valueOf(cell.getNumericCellValue());
		} else if (cellType == Cell.CELL_TYPE_STRING) {
			// NB: cell.getStringCellValue() is the same as
			// cell.getRichStringCellValue().getString()
			result = cell.getStringCellValue();
		} else {
			throw new IllegalArgumentException("Unknown excel cell type: "
					+ cellType);
		}

		return result;
	}

	/**
	 * Return Excel ALPHA-26 cell based name.
	 * 
	 * @param row
	 *            row number, 0 based. eg: 1
	 * @param col
	 *            column number, 0 based. eg: 1
	 * @return cell name, eg: B2
	 */
	public static String cellName(int row, int col) {
		return "Cell " + CellReference.convertNumToColString(col) + (row + 1);
	}

	@Override
	public Iterator<Object[]> iterator() {
		return new XLSRowIterator();
	}

	/**
	 * Iterator that returns Object arrays of each line in the Excel file.
	 * Starts of line 2, ie: after the header.
	 * 
	 * @author Oliver Mannion
	 * 
	 */
	private class XLSRowIterator implements Iterator<Object[]> {

		private final Iterator<Row> iterator;

		XLSRowIterator() {
			iterator = currentSheet.rowIterator();
			iterator.next();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Object[] next() {
			Row row = iterator.next();

			Object[] result = new Object[columns.length];
			int i = 0;

			// loop through the selected columns
			// and get the corresponding cell
			// from the row.
			// Parse the cell result through the
			// cell reader
			for (Column column : columns) {
				Cell cell = row.getCell(column.getIndex());

				try {
					result[i++] = column.getCellReader().call(
							getExcelCellValue(cell));
				} catch (RuntimeException e) {
					throw new RuntimeException(getName() + " "
							+ cellName(row.getRowNum(), column.getIndex())
							+ ": " + e.getMessage(), e);

				}
			}

			return result;
		}

		@Override
		public void remove() {
			iterator.remove();
		}

	}
}

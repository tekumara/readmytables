package org.omancode.rmt.tablereader;

import java.util.HashMap;
import java.util.Map;

import org.omancode.rmt.cellreader.CellReader;
import org.omancode.rmt.cellreader.CellReaders;

/**
 * Represents a column in a table.
 * 
 * @author Oliver Mannion
 * @version $Revision: -1 $
 */
public class Column {

	private final String name;

	private final int index;

	private CellReader<?> cellReader;

	/**
	 * Construct a {@link Column} with the default cell reader
	 * {@link CellReaders#IDENTITY}.
	 * 
	 * @param name
	 *            column name
	 * @param index
	 *            column position, zero based (ie: first column is 0).
	 */
	public Column(String name, int index) {
		this(name, index, null);
	}

	/**
	 * Construct a {@link Column}.
	 * 
	 * @param name
	 *            column name
	 * @param index
	 *            column position, zero based (ie: first column is 0).
	 * @param cellReader
	 *            cell reader used to read cells from this column. If
	 *            {@code null} then uses the default cell reader
	 *            {@link CellReaders#IDENTITY}.
	 */
	public Column(String name, int index, CellReader<?> cellReader) {
		this.name = name;
		this.index = index;
		this.cellReader = cellReader == null ? CellReaders.IDENTITY
				: cellReader;
	}

	/**
	 * Get column name.
	 * 
	 * @return column name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get column index.
	 * 
	 * @return index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Get column's {@link CellReader}.
	 * 
	 * @return column's CellReader
	 */
	public CellReader<?> getCellReader() {
		return cellReader;
	}

	/**
	 * Set column's {@link CellReader}.
	 * 
	 * @param cellReader
	 *            a CellReader.
	 */
	public void setCellReader(CellReader<?> cellReader) {
		this.cellReader = cellReader;
	}

	/**
	 * Returns a map of {@link Column}s, keyed by name.
	 * 
	 * @param columns
	 *            columns array
	 * @return map
	 */
	public static Map<String, Column> mappedColumns(Column[] columns) {

		Map<String, Column> result = new HashMap<String, Column>();

		for (int i = 0; i < columns.length; i++) {
			result.put(columns[i].getName(), columns[i]);
		}

		return result;
	}

}

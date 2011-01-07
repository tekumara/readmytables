package org.omancode.rmt.tablereader.file;

import org.omancode.rmt.cellreader.CellReader;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCSVException;
import org.supercsv.util.CSVContext;

/**
 * Converts a CellReader to a CellProcessor for use with SuperCSV.
 * 
 * @author Oliver Mannion
 * @version $Revision: 54 $
 */
public class CellProcessorAdaptor implements CellProcessor {

	private final CellReader<?> reader;

	/**
	 * Construct CellProcessor from {@code reader}.
	 * 
	 * @param reader
	 *            cell reader
	 */
	public CellProcessorAdaptor(CellReader<?> reader) {
		this.reader = reader;
	}

	@Override
	public Object execute(Object value, CSVContext context) {
		try {
			return reader.call(value);

		} catch (RuntimeException e) {
			throw new SuperCSVException(e.getMessage(), context, this, e);
		}
	}

	@Override
	public String toString() {
		return "CellProcessorAdaptor for " + reader.toString();
	}
}
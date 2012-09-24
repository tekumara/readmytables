package org.omancode.rmt.cellreader;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Converts an object to a Date without loss of fidelity. Throws
 * {@link CellReaderException} if the object cannot be converted without loss of
 * fidelity.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class DateReader implements CellReader<Date> {

	private final DateFormat dateFormat;

	/**
	 * Construct {@link DateReader} with default formatting style for the
	 * default locale.
	 */
	public DateReader() {
		this(DateFormat.getDateInstance());
	}

	public DateReader(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * Convert an object to a Date without loss of fidelity.
	 * 
	 * @param value
	 * 
	 *            value to convert
	 * @return result
	 */
	public Date objectToDate(Object value) {
		if (value == null) {
			throw new CellReaderException(
					"Null object cannot be converted to Date.");
		}

		if (!(value instanceof String)) {
			throw new CellReaderException("Object [" + value.toString()
					+ "] of type " + value.getClass().toString()
					+ " cannot be converted to Date.");
		}

		try {
			return dateFormat.parse((String) value);
		} catch (ParseException e) {
			throw new CellReaderException("Cannot convert \"" // NOPMD
					+ value.toString() + "\" to date");
		}

	}

	@Override
	public Class<Date> getResultType() {
		return Date.class;
	}

	@Override
	public Date call(Object value) {
		return objectToDate(value);
	}

}

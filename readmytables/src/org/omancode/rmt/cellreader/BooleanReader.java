package org.omancode.rmt.cellreader;

import org.omancode.util.ArrayUtil;

/**
 * Converts an object to a Boolean without loss of fidelity. Throws
 * {@link CellReaderException} if the object cannot be converted without loss of
 * fidelity.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class BooleanReader implements CellReader<Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2542403746336985674L;

	private static final String[] DEFAULT_TRUE_VALUES = new String[] { "1",
			"1.0", "true", "t", "yes", "y" };

	private static final String[] DEFAULT_FALSE_VALUES = new String[] { "0",
			"0.0", "false", "f", "no", "n" };

	private final String[] trueValues;
	private final String[] falseValues;

	/**
	 * Construct with default true and false values.
	 */
	public BooleanReader() {
		this(DEFAULT_TRUE_VALUES, DEFAULT_FALSE_VALUES);
	}

	/**
	 * Construct with a single String true and single String false value.
	 * 
	 * @param trueValue
	 *            string true value
	 * @param falseValue
	 *            string false value
	 */
	public BooleanReader(final String trueValue, final String falseValue) {
		super();
		this.trueValues = new String[] { trueValue };
		this.falseValues = new String[] { falseValue };
	}

	/**
	 * Construct with specified true and false values.
	 * 
	 * @param trueValues
	 *            set of true Strings
	 * @param falseValues
	 *            set of false Strings
	 */
	public BooleanReader(final String[] trueValues, final String[] falseValues) {
		super();
		this.trueValues = trueValues.clone();
		this.falseValues = falseValues.clone();
	}

	/**
	 * Convert String to Boolean.
	 * 
	 * @param value
	 *            string to convert.
	 * @return Boolean
	 */
	public Boolean stringToBoolean(String value) {

		if (ArrayUtil.indexOfString(falseValues, value) != ArrayUtil.INDEX_NOT_FOUND) {
			return false;
		} else if (ArrayUtil.indexOfString(trueValues, value) != ArrayUtil.INDEX_NOT_FOUND) {
			return true;
		}

		throw new CellReaderException("String [" + value
				+ "] is not a boolean value (ie: "
				+ ArrayUtil.toString(falseValues) + ", "
				+ ArrayUtil.toString(trueValues) + ")");
	}

	/**
	 * Convert Number to Boolean.
	 * 
	 * @param value
	 *            Number to convert.
	 * @return Boolean
	 */
	public Boolean numberToBoolean(Number value) {
		String valueAsString = value.toString();

		if ("0".equals(valueAsString) || "0.0".equals(valueAsString)) {
			return false;
		} else if ("1".equals(valueAsString) || "1.0".equals(valueAsString)) {
			return true;
		}

		throw new CellReaderException("Number [" + valueAsString
				+ "] is not 0 or 1 and so cannot be converted to boolean");
	}

	/**
	 * Convert Object to Boolean.
	 * 
	 * @param value
	 *            Number to convert.
	 * @return Boolean
	 */
	public Boolean objectToBoolean(Object value) {

		if (value == null) {
			throw new CellReaderException(
					"Null object cannot be converted to Boolean.");
		}
		
		if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		}

		if (value instanceof Number) {
			return numberToBoolean((Number) value);
		}

		if (value instanceof String) {
			return stringToBoolean(value.toString().trim());
		}

		if (value instanceof Character) {
			return stringToBoolean(value.toString());
		}

		throw new CellReaderException("Object [" + value.toString()
				+ "] of type " + value.getClass()
				+ " cannot be converted to Boolean");
	}

	@Override
	public Class<Boolean> getResultType() {
		return Boolean.class;
	}

	@Override
	public Boolean call(Object value) {
		return objectToBoolean(value);
	}

}
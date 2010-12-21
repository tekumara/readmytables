package org.omancode.rmt.cellreader;

/**
 * Converts an object to an Integer without loss of fidelity. Throws
 * {@link CellReaderException} if the object cannot be converted without loss of
 * fidelity.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class IntegerReader implements CellReader<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2542403746336945674L;

	/**
	 * Convert String to Integer. If fidelity will be lost, throws
	 * {@link CellReaderException}.
	 * 
	 * @param value
	 *            string to convert.
	 * @return Integer
	 */
	public Integer stringToInteger(String value) {
		try {
			// try converting String to Double - the
			// widest of all number types (can contain
			// all other types including max & min long)
			Double convertedDouble = Double.parseDouble(value);

			// see if the converted Double can be an
			// Integer
			return doubleToInteger(convertedDouble);

		} catch (NumberFormatException e) {
			throw new CellReaderException("Cannot convert \"" + value // NOPMD
					+ "\" to integer");
		}
	}

	/**
	 * Convert Long to Integer. If fidelity will be lost, throws
	 * {@link CellReaderException}.
	 * 
	 * @param arg
	 *            Long to convert.
	 * @return Integer
	 */
	public Integer longToInteger(Long arg) {
		long value = arg.longValue();

		if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
			return (int) value;
		}

		throw new CellReaderException("long value [" + value
				+ "] too big to be an integer.");
	}

	/**
	 * Convert Float to Integer. If fidelity will be lost, throws
	 * {@link CellReaderException}.
	 * 
	 * @param arg
	 *            Float to convert.
	 * @return Integer
	 */
	public Integer floatToInteger(Float arg) {
		float value = arg.floatValue();

		// if no fractional part, return
		if (value % 1 == 0) {
			return longToInteger(arg.longValue());
		}

		throw new CellReaderException("float value [" + value
				+ "] has a fractional part and cannot be "
				+ "converted to integer.");
	}

	/**
	 * Convert Double to Integer. If fidelity will be lost, throws
	 * {@link CellReaderException}.
	 * 
	 * @param arg
	 *            Double to convert.
	 * @return Integer
	 */
	public Integer doubleToInteger(Double arg) {
		double value = arg.doubleValue();

		// if no fractional part, return
		if (value % 1 == 0) {
			return longToInteger(arg.longValue());
		}

		throw new CellReaderException("double value [" + value
				+ "] has a fractional part and cannot be "
				+ "converted to integer.");
	}

	/**
	 * Convert Object to Integer. If fidelity will be lost, throws
	 * {@link CellReaderException}.
	 * 
	 * @param value
	 *            Object to convert.
	 * @return Integer
	 */
	public Integer objectToInteger(Object value) {
		if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue() ? 1 : 0;
		} else if (value instanceof Byte || value instanceof Short
				|| value instanceof Integer) {
			return ((Number) value).intValue();
		} else if (value instanceof Long) {
			return longToInteger((Long) value);
		} else if (value instanceof Float) {
			return floatToInteger((Float) value);
		} else if (value instanceof Double) {
			return doubleToInteger((Double) value);
		} else if (value instanceof String || value instanceof Character) {
			return stringToInteger(value.toString());
		}

		throw new CellReaderException("Object [" + value.toString()
				+ "] of type " + value.getClass().toString()
				+ " cannot be converted to Integer.");
	}

	@Override
	public Class<Integer> getResultType() {
		return Integer.class;
	}

	@Override
	public Integer call(Object value) {
		return objectToInteger(value);
	}

}
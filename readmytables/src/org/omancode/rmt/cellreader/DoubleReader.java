package org.omancode.rmt.cellreader;

/**
 * Converts an object to a Double without loss of fidelity. Throws
 * {@link CellReaderException} if the object cannot be converted without loss of
 * fidelity.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class DoubleReader implements CellReader<Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8349049119392596402L;

	/**
	 * Convert an object to a Double without loss of fidelity.
	 * 
	 * @param value
	 *            value to convert
	 * @return result
	 */
	public Double objectToDouble(Object value) {
		if (!(value instanceof Number || value instanceof String || value instanceof Character)) {
			throw new CellReaderException("Object [" + value.toString()
					+ "] of type " + value.getClass().toString()
					+ " cannot be converted to Double.");
		}

		try {
			return Double.parseDouble(value.toString());
		} catch (NumberFormatException e) {
			throw new CellReaderException("Cannot convert \"" // NOPMD
					+ value.toString() + "\" to double");
		}
	}

	@Override
	public Class<Double> getResultType() {
		return Double.class;
	}

	@Override
	public Double call(Object value) {
		return objectToDouble(value);
	}

}

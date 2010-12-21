package org.omancode.rmt.cellreader;


/**
 * Converts an object to a String without loss of fidelity. Throws
 * {@link CellReaderException} if the object cannot be converted without loss of
 * fidelity.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class StringReader implements
		CellReader<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8349049119312596406L;

	@Override
	public Class<String> getResultType() {
		return String.class;
	}

	@Override
	public String call(Object value) {
		return objectToString(value);
	}

	/**
	 * Convert an object to a String without loss of fidelity. Converts Boolean,
	 * Number, Character, String to String, otherwise throws a runtime
	 * exception.
	 * 
	 * @param value
	 *            value to convert
	 * @return result
	 */
	public String objectToString(Object value) {
		if (value instanceof Boolean || value instanceof Number
				|| value instanceof Character || value instanceof String) {
			return value.toString();
		}

		throw new CellReaderException("Object [" + value.toString()
				+ "] of type " + value.getClass()
				+ "cannot be converted to String");
	}

}

package org.omancode.rmt.cellreader;


/**
 * Converts an object to a Character without loss of fidelity. Throws
 * {@link CellReaderException} if the object cannot be converted without loss of
 * fidelity.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class CharacterReader implements
		CellReader<Character> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8349039119392596406L;

	/**
	 * Convert an object to a Character without loss of fidelity.
	 * 
	 * @param value
	 *            value to convert
	 * @return result
	 */
	public Character objectToCharacter(Object value) {
		if (value instanceof Character) {
			return (Character) value;
		}

		if (value instanceof String) {
			String valueAsString = (String) value;
			// if String of length 1, can be char
			if (valueAsString.length() == 1) {
				return Character.valueOf(valueAsString.charAt(0));
			}
		}

		throw new CellReaderException("Object [" + value.toString()
				+ "] of type " + value.getClass()
				+ " cannot be converted to Character");
	}

	@Override
	public Class<Character> getResultType() {
		return Character.class;
	}

	@Override
	public Character call(Object value) {
		return objectToCharacter(value);
	}

}

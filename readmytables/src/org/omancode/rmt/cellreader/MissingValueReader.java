package org.omancode.rmt.cellreader;

/**
 * Reader that checks the read value and returns a different value if the read
 * value is missing, ie: {@code null} or empty string.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class MissingValueReader implements CellReader<Object> {

	private final Object returnValue;

	/**
	 * Construct a {@link MissingValueReader} that returns {@code returnValue}
	 * if it encounters missing value.
	 * 
	 * @param returnValue
	 *            return value to return instead of missing value
	 */
	public MissingValueReader(Object returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * If {@code value} is missing (ie: {@code null} or empty string), return
	 * {@link #returnValue} else return {@code value}.
	 * 
	 * @param value
	 *            value to check
	 * @return result {@code value} or {@link #returnValue}
	 */
	public Object iifMissing(Object value) {
		if (value == null) {
			return returnValue;
		}
		
		return "".equals(value) ? returnValue : value;
	}

	@Override
	public Class<Object> getResultType() {
		return Object.class;
	}

	@Override
	public Object call(Object value) {
		return iifMissing(value);
	}

}

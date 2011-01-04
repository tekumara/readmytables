package org.omancode.rmt.cellreader;

/**
 * Reader that checks the read value and returns a different value if matches a
 * token. Matching is done using the Object equals method. For example, a
 * {@link TokenReader} can be used to return a different value for empty string.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class TokenReader implements CellReader<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8589533835413230489L;

	private final Object returnValue;
	private final Object token;

	/**
	 * Construct a token reader.
	 * 
	 * @param token
	 *            value to check for
	 * @param returnValue
	 *            value to return if token found
	 */
	public TokenReader(Object token, Object returnValue) {
		this.returnValue = returnValue;
		this.token = token;
	}

	/**
	 * If {@code value} is token, return {@link #returnValue} else return
	 * {@code value}.
	 * 
	 * @param value
	 *            value to check
	 * @return result {@code value} or {@link #returnValue}
	 */
	public Object iifToken(Object value) {
		return token.equals(value) ? returnValue : value;
	}

	@Override
	public Class<Object> getResultType() {
		return Object.class;
	}

	@Override
	public Object call(Object value) {
		return iifToken(value);
	}

}

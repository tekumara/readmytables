package org.omancode.rmt.cellreader.narrow;

/**
 * Exception during type testing/narrowing.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 * 
 */
public class NarrowException extends Exception {

	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -7171935382264183199L;

	public NarrowException(String message) {
		super(message);
	}

	public NarrowException(Throwable cause) {
		super(cause);
	}

	public NarrowException(String message, Throwable cause) {
		super(message, cause);
	}

}

package org.omancode.rmt.cellreader;

/**
 * Runtime exception during cell reading.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 * 
 */
public class CellReaderException extends RuntimeException {

	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -7171935382264183199L;

	/**
	 * Throw exception with message / reason.
	 * 
	 * @param message
	 *            The detail message (which is saved for later retrieval by the
	 *            {@link #getMessage()} method)
	 */
	public CellReaderException(String message) {
		super(message);
	}
	/**
	 * Constructs an exception with the specified cause and a detail message of
	 * {@code (cause==null ? null : cause.toString())} (which typically contains
	 * the class and detail message of {@code cause}). This constructor is
	 * useful for IO exceptions that are little more than wrappers for other
	 * throwables.
	 * 
	 * @param cause
	 *            The cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public CellReaderException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Throw exception with previous (chained) exception.
	 * 
	 * <p>
	 * Note that the detail message associated with {@code cause} is <i>not</i>
	 * automatically incorporated into this exception's detail message.
	 * 
	 * @param message
	 *            The detail message (which is saved for later retrieval by the
	 *            {@link #getMessage()} method)
	 * 
	 * @param cause
	 *            The cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public CellReaderException(String message, Throwable cause) {
		super(message, cause);
	}

}

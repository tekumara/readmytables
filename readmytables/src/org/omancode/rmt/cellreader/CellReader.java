package org.omancode.rmt.cellreader;

/**
 * A function that takes an Object and returns a value of another type. Can be
 * used for reading cells from files (eg: Excel, CSV etc.) or for converting
 * between types.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 * 
 * @param <R>
 *            type of value returned
 */
public interface CellReader<R> {

	/**
	 * Type of value returned.
	 * 
	 * @return the type of object that the reader produces
	 */
	Class<R> getResultType();

	/**
	 * Reads the passed Object value and converts it.
	 * 
	 * @param value
	 *            the value to convert.
	 * 
	 * @return the result of conversion.
	 * 
	 */
	R call(Object value);
}

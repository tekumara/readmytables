package org.omancode.rmt.cellreader.narrow;

import org.omancode.rmt.cellreader.CellReaderException;
import org.omancode.rmt.cellreader.CellReaders;

/**
 * Performs type checking and conversion on an Object value.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class TypeCheckedValue {

	/**
	 * Integer missing value constant.
	 */
	public static final byte MISSING_VALUE_BYTE = -128;
	
	/**
	 * Integer missing value constant.
	 */
	public static final int MISSING_VALUE_INTEGER = -2147483648;

	/**
	 * Double missing value constant.
	 */
	public static final double MISSING_VALUE_DOUBLE = Double
			.longBitsToDouble(0x7ff00000000007a2L);

	private final Object value;

	/**
	 * Use {@link CellReaders#OPTIONAL_DOUBLE} and
	 * {@link CellReaders#OPTIONAL_INTEGER}?
	 */
	private boolean convertMissing = false;

	/**
	 * Create {@link TypeCheckedValue} from Object.
	 * 
	 * @param value
	 *            object value to type check
	 */
	public TypeCheckedValue(Object value) {
		this.value = value;
	}

	/**
	 * If set, then missing integers and doubles will be returned as
	 * {@link #MISSING_VALUE_INTEGER} or {@link #MISSING_VALUE_DOUBLE} instead.
	 * 
	 * @param convertMissing
	 *            true to convert missing ints/doubles
	 * @return {@link TypeCheckedValue}
	 */
	public TypeCheckedValue setConvertMissing(boolean convertMissing) {
		this.convertMissing = convertMissing;
		return this;
	}

	/**
	 * Check whether this value can be a boolean.
	 * 
	 * @return {@code true} if this can be a boolean.
	 */
	public boolean canBeBoolean() {
		try {
			asBoolean();
			return true;
		} catch (CellReaderException e) {
			return false;
		}

	}

	/**
	 * Check whether this value can be a double.
	 * 
	 * @return {@code true} if this can be a double.
	 */
	public boolean canBeDouble() {
		try {
			asDouble();
			return true;
		} catch (CellReaderException e) {
			return false;
		}

	}

	/**
	 * Check whether this value can be an integer.
	 * 
	 * @return {@code true} if this can be an integer.
	 */
	public boolean canBeInteger() {
		try {
			asInteger();
			return true;
		} catch (CellReaderException e) {
			return false;
		}
	}

	/**
	 * Check whether this value can be a String.
	 * 
	 * @return {@code true} if this can be a String.
	 */
	public boolean canBeString() {
		try {
			asString();
			return true;
		} catch (CellReaderException e) {
			return false;
		}

	}

	/**
	 * Check whether this value can be a Character.
	 * 
	 * @return {@code true} if this can be a Character.
	 */
	public boolean canBeChar() {
		try {
			asChar();
			return true;
		} catch (CellReaderException e) {
			return false;
		}

	}

	/**
	 * Return this value as a boolean.
	 * 
	 * @return boolean
	 */
	public boolean asBoolean() {
		return CellReaders.BOOLEAN.call(value);
	}

	/**
	 * Return this value as a double.
	 * 
	 * @return double
	 */
	public double asDouble() {

		if (convertMissing) {
			return CellReaders.OPTIONAL_DOUBLE.call(value);
		}

		return CellReaders.DOUBLE.call(value);
	}

	/**
	 * Return this value as an integer.
	 * 
	 * @return integer
	 */
	public int asInteger() {

		if (convertMissing) {
			return CellReaders.OPTIONAL_INTEGER.call(value);
		}

		return CellReaders.INTEGER.call(value);
	}

	/**
	 * Return this value as a String.
	 * 
	 * @return boolean
	 */
	public String asString() {
		return CellReaders.STRING.call(value);
	}

	/**
	 * Return this value as a Character.
	 * 
	 * @return boolean
	 */
	public char asChar() {
		return CellReaders.CHARACTER.call(value);
	}

}

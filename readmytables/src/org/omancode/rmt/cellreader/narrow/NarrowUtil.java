package org.omancode.rmt.cellreader.narrow;

/**
 * Performs type checking and conversion on Object values.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public final class NarrowUtil {

	private NarrowUtil() {
		// static util class
	}

	/**
	 * Calculate the narrowest possible type for an array of Objects.
	 * 
	 * @param values
	 *            array of Object to calculate narrowest type for
	 * @param allowMissing
	 *            allow values that are empty string or null
	 * @return narrowest possible type that will not reduce fidelity of any of
	 *         the objects
	 */
	public static Class<?> calcNarrowestType(Object[] values,
			boolean allowMissing) {

		if (values == null) {
			return null;
		}

		// initially all types possible
		boolean booleanPossible = true;
		boolean integerPossible = true;
		boolean doublePossible = true;
		boolean charPossible = true;
		boolean stringPossible = true;

		boolean allSameClass = true;
		Class<?> firstClass = values[0].getClass();

		// iterate, testing each value, and
		// widen if necessary
		for (Object value : values) {
			TypeCheckedValue checkValue = new TypeCheckedValue(value)
					.setConvertMissing(allowMissing);

			if (booleanPossible) {
				booleanPossible = checkValue.canBeBoolean();
			}

			if (doublePossible) {
				doublePossible = checkValue.canBeDouble();

				// if can't be a double, then can't be an integer
				if (!doublePossible) {
					integerPossible = false;
				} else if (integerPossible) {
					integerPossible = checkValue.canBeInteger();
				}

			}

			if (stringPossible) {
				stringPossible = checkValue.canBeString();

				// if can't be a string, then can't be a char
				if (!stringPossible) {
					charPossible = false;
				} else if (charPossible) {
					charPossible = checkValue.canBeChar();
				}

			}

			if (allSameClass) {
				allSameClass = value.getClass() == firstClass;
			}
		}

		if (booleanPossible) {
			return Boolean.class;
		} else if (integerPossible) {
			return Integer.class;
		} else if (doublePossible) {
			return Double.class;
		} else if (charPossible) {
			return Character.class;
		} else if (stringPossible) {
			return String.class;
		} else if (allSameClass) {
			return firstClass;
		}

		return Object.class;
	}

	/**
	 * Narrow the array to the smallest possible type without losing fidelity.
	 * 
	 * @param array
	 *            array to narrow
	 * @return narrowed array
	 * @throws NarrowException
	 *             if problem narrowing row
	 */
	public static Object[] narrowArray(Object[] array) throws NarrowException {
		// narrow the row names so that if they are numbers,
		// they will be number sorted not string sorted
		Class<?> rowNamesType = calcNarrowestType(array, false);
		return narrowArray(array, rowNamesType, false);
	}

	/**
	 * Narrow the array to the specified type.
	 * 
	 * @param values
	 *            array to narrow
	 * @param toType
	 *            type to narrow to
	 * @param convertMissing
	 *            convert missing values
	 * @return {@code values} converted to smallest possible type
	 * @throws NarrowException
	 *             if a value cannot be narrowed.
	 */
	public static Object[] narrowArray(Object[] values, Class<?> toType,
			boolean convertMissing) throws NarrowException {
		Object[] narrowedValues = new Object[values.length];

		for (int i = 0; i < values.length; i++) {
			narrowedValues[i] = narrow(values[i], toType, convertMissing);
		}

		return narrowedValues;

	}

	/**
	 * Narrow each value in {@code values} to the type specified in the parallel
	 * array {@code narrowTypes}.
	 * 
	 * @param values
	 *            array of different values to narrow
	 * @param narrowTypes
	 *            parrallel array that specifies the type to narrow the
	 *            corresponding {@code values} value to
	 * @param convertMissing
	 *            whether to convert missing values to there corresponding
	 *            integer/double representation
	 * @return narrowed version of {@code values}
	 * @throws NarrowException
	 *             if problem narrowing
	 */
	public static Object[] narrowArray(Object[] values, Class<?>[] narrowTypes,
			boolean convertMissing) throws NarrowException {
		int length = values.length;

		if (length != narrowTypes.length) {
			throw new IllegalArgumentException(
					"Length of values array does not "
							+ "match length of narrowTypes array");

		}

		Object[] narrowedValues = new Object[length];

		for (int i = 0; i < length; i++) {
			narrowedValues[i] = narrow(values[i], narrowTypes[i],
					convertMissing);
		}

		return narrowedValues;
	}

	/**
	 * Narrow an Object to the specified type.
	 * 
	 * @param value
	 *            Object value to narrow
	 * @param toType
	 *            type to narrow to
	 * @param convertMissing
	 *            whether to convert missing values to there corresponding
	 *            integer/double representation
	 * @return narrowed type
	 * @throws NarrowException
	 *             if problem narrowing
	 */
	public static Object narrow(Object value, Class<?> toType,
			boolean convertMissing) throws NarrowException {

		if (value == null) {
			return null;
		}

		Class<?> currentClass = value.getClass();

		if (toType.equals(Object.class) || toType.equals(currentClass)) {
			return value;
		}

		TypeCheckedValue convertedValue = new TypeCheckedValue(value)
				.setConvertMissing(convertMissing);

		if (toType == Boolean.class) {
			return convertedValue.asBoolean();
		} else if (toType == Integer.class) {
			return convertedValue.asInteger();
		} else if (toType == Double.class) {
			return convertedValue.asDouble();
		} else if (toType == Character.class) {
			return convertedValue.asChar();
		} else if (toType == String.class) {
			return convertedValue.asString();
		}

		throw new NarrowException("Object [" + value.toString() + "] of type "
				+ value.getClass() + "cannot be narrowed to type" + toType);

	}

}
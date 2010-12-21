package org.omancode.rmt.cellreader;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Utility class providing default cell readers. Provides map from default cell
 * reader name to the cell reader, and vice versa.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public final class CellReaders {

	/**
	 * Default Boolean reader.
	 */
	public static final BooleanReader BOOLEAN = new BooleanReader();

	/**
	 * Default Character reader.
	 */
	public static final CharacterReader CHARACTER = new CharacterReader();

	/**
	 * Default Double reader.
	 */
	public static final DoubleReader DOUBLE = new DoubleReader();

	/**
	 * Default Integer reader.
	 */
	public static final IntegerReader INTEGER = new IntegerReader();

	/**
	 * Default String reader.
	 */
	public static final StringReader STRING = new StringReader();

	/**
	 * Default Identity reader.
	 */
	public static final IdentityReader IDENTITY = new IdentityReader();

	/**
	 * Integer missing value constant.
	 */
	public static final int MISSING_VALUE_INTEGER = -2147483648;

	/**
	 * Double missing value constant.
	 */
	public static final double MISSING_VALUE_DOUBLE =
			Double.longBitsToDouble(0x7ff00000000007a2L);

	/**
	 * An integer cell reader that returns {@link #MISSING_VALUE_INTEGER} when
	 * it reads the empty string.
	 */
	public static final CellReader<Integer> OPTIONAL_INTEGER =
			compose(new IntegerReader(), new TokenReader("",
					MISSING_VALUE_INTEGER));

	/**
	 * A double cell reader that returns {@link #MISSING_VALUE_DOUBLE} when it
	 * reads the empty string.
	 */
	public static final CellReader<Double> OPTIONAL_DOUBLE =
			compose(new DoubleReader(), new TokenReader("",
					MISSING_VALUE_DOUBLE));

	private static final Map<String, CellReader<?>> MAP_DEFAULT_READERS =
			new HashMap<String, CellReader<?>>();

	static {
		MAP_DEFAULT_READERS.put("boolean", BOOLEAN);
		MAP_DEFAULT_READERS.put("character", CHARACTER);
		MAP_DEFAULT_READERS.put("double", DOUBLE);
		MAP_DEFAULT_READERS.put("integer", INTEGER);
		MAP_DEFAULT_READERS.put("string", STRING);
		MAP_DEFAULT_READERS.put("optional double", OPTIONAL_DOUBLE);
		MAP_DEFAULT_READERS.put("optional integer", OPTIONAL_INTEGER);
	}

	private CellReaders() {
		// static class, no instantiation
	}

	/**
	 * Returns an array of default cell readers for the given names.
	 * 
	 * @param cellReaderNames
	 *            column types
	 * @return array of default cell readers
	 * @throws CellReaderException
	 *             if one of {@code cellReaderNames} does not map to a default
	 *             cell reader
	 */
	public static CellReader<?>[] generateCellReaders(String[] cellReaderNames) {

		if (cellReaderNames == null) {
			return null;
		}

		CellReader<?>[] result = new CellReader<?>[cellReaderNames.length];

		for (int i = 0; i < cellReaderNames.length; i++) {
			result[i] = getDefaultReader(cellReaderNames[i]);
			if (result[i] == null) {
				throw new CellReaderException("No default reader called \""
						+ cellReaderNames[i] + "\"");
			}
		}

		return result;
	}

	/**
	 * Maps a string to a default reader.
	 * 
	 * @param name
	 *            reader name. Can be one of {@code "boolean", "character",
	 *            "double", "integer", "string", "optional double",
	 *            "optional integer"}. Case insensitive.
	 * @return default reader, or {@code null} if {@code name} does not map to a
	 *         default cell reader
	 */
	public static CellReader<?> getDefaultReader(String name) {
		CellReader<?> reader =
				MAP_DEFAULT_READERS
						.get(name.toLowerCase(Locale.getDefault()));

		return reader;
	}

	/**
	 * Returns the name of reader if it is a default reader, or {@code null} if
	 * it is not.
	 * 
	 * @param reader
	 *            reader
	 * @return name of the default cell reader, or {@code null} if it is not a
	 *         default cell reader.
	 */
	public static String getDefaultReaderName(CellReader<?> reader) {
		for (Map.Entry<String, CellReader<?>> entry : MAP_DEFAULT_READERS
				.entrySet()) {
			if (entry.getValue() == reader) {
				return entry.getKey();
			}
		}

		return null;
	}

	/**
	 * Composes one {@link CellReader} with another to return a new CellReader
	 * according to <i>function composition</i>.
	 * 
	 * @param f
	 *            the cell reader <code>f</code>.
	 * @param g
	 *            the cell reader <code>g</code> to compose with <code>f</code>.
	 * @param <F>
	 *            type returned by cell reader <code>f</code>
	 * @param <G>
	 *            type returned by cell reader <code>g</code>
	 * @return a new {@link CellReader} <code>h</code> such that
	 *         <code>h(x) = f(g(x))</code>, where <code>x</code> represents a
	 *         parameter accepted by <code>g</code>.
	 */
	public static <F, G> CellReader<F> compose(final CellReader<F> f,
			final CellReader<G> g) {

		return new CellReader<F>() {
			@Override
			public F call(Object param) {
				return f.call(g.call(param));
			}

			@Override
			public Class<F> getResultType() {
				return f.getResultType();
			}
		};
	}
}
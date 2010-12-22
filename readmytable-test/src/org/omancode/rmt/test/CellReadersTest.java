package org.omancode.rmt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.omancode.rmt.cellreader.CellReaderException;
import org.omancode.rmt.cellreader.CellReaders;

public class CellReadersTest {

	public static final Double EPSILON = 1e-15;

	@Test(expected = CellReaderException.class)
	public void booleanNullHandling() {
		CellReaders.BOOLEAN.call(null);
	}

	@Test(expected = CellReaderException.class)
	public void characterNullHandling() {
		CellReaders.CHARACTER.call(null);
	}

	@Test(expected = CellReaderException.class)
	public void doubleNullHandling() {
		CellReaders.DOUBLE.call(null);
	}

	@Test(expected = CellReaderException.class)
	public void integerNullHandling() {
		CellReaders.INTEGER.call(null);
	}

	@Test(expected = CellReaderException.class)
	public void stringNullHandling() {
		CellReaders.STRING.call(null);
	}

	@Test
	public void identityNullHandling() {
		assertNull(CellReaders.IDENTITY.call(null));
	}

	@Test
	public void optionalDoubleNullHandling() {
		assertEquals(CellReaders.MISSING_VALUE_DOUBLE,
				(double) CellReaders.OPTIONAL_DOUBLE.call(null), EPSILON);
	}

	@Test
	public void optionalIntegerNullHandling() {
		assertEquals(CellReaders.MISSING_VALUE_INTEGER,
				(int) CellReaders.OPTIONAL_INTEGER.call(null));
	}

}

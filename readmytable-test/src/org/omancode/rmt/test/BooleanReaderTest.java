package org.omancode.rmt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.omancode.rmt.cellreader.BooleanReader;
import org.omancode.rmt.cellreader.CellReaderException;

public class BooleanReaderTest {

	BooleanReader transformer = new BooleanReader();

	@Test
	public void testFnBoolean() {
		assertEquals(Boolean.TRUE, transformer.objectToBoolean(Boolean.TRUE));
		assertEquals(Boolean.FALSE, transformer.objectToBoolean(Boolean.FALSE));
	}

	/**
	 * 
	 */
	@Test
	public void testFnNumber() {
		Double d0 = new Double(0);
		Double d1 = new Double(1.0000000000);
		Double d11 = new Double(1.00000000001);
		Double d2 = new Double(2);
		Float f1 = new Float(1);
		Float f11 = new Float(1.0001);

		Long maxLong = Long.valueOf(0x7fffffffffffffffL);
		Double doubleMaxLong = new Double(maxLong);
		Float floatMaxLong = new Float(maxLong);

		assertEquals(Boolean.FALSE, transformer.objectToBoolean(d0));
		assertEquals(Boolean.TRUE, transformer.objectToBoolean(d1));

		try {
			assertEquals(Boolean.TRUE, transformer.objectToBoolean(d11));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {

		}
		try {
			assertEquals(Boolean.FALSE, transformer.objectToBoolean(d2));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {

		}

		assertEquals(Boolean.TRUE, transformer.objectToBoolean(f1));

		try {
			assertEquals(Boolean.FALSE, transformer.objectToBoolean(f11));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {
		}

		try {
			assertEquals(Boolean.FALSE, transformer.objectToBoolean(maxLong));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {
		}

		try {
			assertEquals(Boolean.FALSE, transformer.objectToBoolean(doubleMaxLong));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {
		}

		try {
			assertEquals(Boolean.FALSE, transformer.objectToBoolean(floatMaxLong));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {
		}

	}

	@Test
	public void testFnObject() {
		Object objTrue = Boolean.TRUE;
		assertEquals(true, objTrue instanceof Boolean);

		try {
			transformer.objectToBoolean(new Object());
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {

		}

		assertEquals(Boolean.TRUE, transformer.objectToBoolean(objTrue));

	}

	@Test
	public void testFnString() {
		String s1 = "1";
		String s10 = "1.0";
		String s11 = "1.1";
		String s0 = "0";
		String s00 = "0.0";
		String s001 = "0.01";

		assertEquals(Boolean.TRUE, transformer.objectToBoolean(s1));
		assertEquals(Boolean.TRUE, transformer.objectToBoolean(s10));

		try {
			assertEquals(Boolean.TRUE, transformer.objectToBoolean(s11));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {

		}

		assertEquals(Boolean.FALSE, transformer.objectToBoolean(s0));
		assertEquals(Boolean.FALSE, transformer.objectToBoolean(s00));

		try {
			assertEquals(Boolean.FALSE, transformer.objectToBoolean(s001));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {

		}

	}

	@Test
	public void testFnCharacter() {
		Character c1 = '1';
		Character cT = 'T';
		Character ca = 'a';
		Character c0 = '0';
		Character cF = 'F';
		Character cb = 'b';

		assertEquals(Boolean.TRUE, transformer.objectToBoolean(c1));
		assertEquals(Boolean.TRUE, transformer.objectToBoolean(cT));

		try {
			assertEquals(Boolean.TRUE, transformer.objectToBoolean(ca));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {

		}

		assertEquals(Boolean.FALSE, transformer.objectToBoolean(c0));
		assertEquals(Boolean.FALSE, transformer.objectToBoolean(cF));

		try {
			assertEquals(Boolean.FALSE, transformer.objectToBoolean(cb));
			fail("RuntimeNarrowException not generated.");
		} catch (CellReaderException e) {

		}

	}

	@Test
	public void testStringExceptionMessage() {
		try {
			transformer.stringToBoolean("foobar");
			fail("Exception not generated.");
		} catch (CellReaderException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(BooleanReaderTest.class);
	}
}

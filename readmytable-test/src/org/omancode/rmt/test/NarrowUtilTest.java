package org.omancode.rmt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.omancode.rmt.cellreader.narrow.NarrowException;
import org.omancode.rmt.cellreader.narrow.NarrowUtil;
import org.omancode.rmt.cellreader.narrow.TypeCheckedValue;

public class NarrowUtilTest {

	private static final double EPSILON = 1.0e-15;
	
	private Integer i1 = Integer.valueOf(1);

	private Double d0 = new Double(0);
	private Double d1 = new Double(1.0000000000);
	private Double d11 = new Double(1.00000000001);
	private Double d2 = new Double(2);
	private Float f1 = new Float(1);
	private Float f11 = new Float(1.0001);

	private Long maxLong = Long.valueOf(0x7fffffffffffffffL);
	private Double doubleMaxLong = new Double(maxLong);
	private Float floatMaxLong = new Float(maxLong);
	
	private String s1 = "1";
	private String s10 = "1.0";
	private String s11 = "1.1";
	private String s0 = "0";
	private String s00 = "0.0";
	private String s001 = "0.01";
	private Character c0 = '0';
	private Character c1 = '1';
	private Character c2 = '2';
	private Character cA = 'A';
	
	
	private Object[] boolArray =   {s1,s10,s0,s00,d0,d1,f1,i1,c0,c1}; 
	private Object[] intArray =    {s1,s10,s0,s00,d0,d1,f1,i1,c0,c1,c2,d2};
	private Object[] doubleArray = {s1,s10,s0,s00,d0,d1,f1,i1,c0,c1,c2,d2,d11,f11,maxLong,doubleMaxLong,floatMaxLong,s11,s001};
	private Object[] stringArray = {s1,s10,s0,s00,d0,d1,f1,i1,c0,c1,c2,d2,d11,f11,maxLong,doubleMaxLong,floatMaxLong,s11,s001,"a string"};
	private Object[] charArray = {s1,s0,c1,c2,cA};
	private Object[] allCharArray = {c1,c2,cA};
	private Object[] mixedArray = {i1,c1,s1,new Object()};
	

	@Test
	public void testNarrowObjectToObject() throws NarrowException {
		Double test = new Double(22);
		assertEquals(test, NarrowUtil.narrow(test, Object.class, false));
	}
	
	@Test
	public void testNarrowSameClass() throws NarrowException {
		Double test = new Double(22);
		assertEquals(test, NarrowUtil.narrow(test, Double.class, false));
	}

	
	@Test
	public void testBoolArray() {
		assertEquals("boolArray", Boolean.class, NarrowUtil.calcNarrowestType(boolArray, false));
	}

	@Test
	public void testIntArray() {
		assertEquals("intArray", Integer.class, NarrowUtil.calcNarrowestType(intArray, false));
	}

	@Test
	public void testDoubleArray() {
		assertEquals("doubleArray", Double.class, NarrowUtil.calcNarrowestType(doubleArray, false));
	}

	@Test
	public void testStringArray() {
		assertEquals("stringArray", String.class, NarrowUtil.calcNarrowestType(stringArray, false));
	}

	@Test
	public void testCharArray() {
		assertEquals("charArray", Character.class, NarrowUtil.calcNarrowestType(charArray, false));
	}

	@Test
	public void testAllCharArray() {
		assertEquals("allCharArray", Character.class, NarrowUtil.calcNarrowestType(allCharArray, false));
	}

	@Test
	public void testMixedArray() {
		assertEquals("mixedArray", Object.class, NarrowUtil.calcNarrowestType(mixedArray, false));
	}

	
	@Test
	public void testCanObjectBeBoolean() {
		assertEquals("d0", true,  canObjectBeBoolean(d0));
		assertEquals("d1", true, canObjectBeBoolean(d1));
		assertEquals("d11", false, canObjectBeBoolean(d11));
		assertEquals("f1", true, canObjectBeBoolean(f1));
		assertEquals("f11", false, canObjectBeBoolean(f11));

		assertEquals("maxLong", false, canObjectBeBoolean(maxLong));
		assertEquals("doubleMaxLong", false, canObjectBeBoolean(doubleMaxLong));
		assertEquals("floatMaxLong", false, canObjectBeBoolean(floatMaxLong));
		
		assertEquals("s1", true, canObjectBeBoolean(s1));
		assertEquals("s10", true, canObjectBeBoolean(s10));
		assertEquals("s11", false, canObjectBeBoolean(s11));
		assertEquals("s0", true, canObjectBeBoolean(s0));
		assertEquals("s00", true, canObjectBeBoolean(s00));
		assertEquals("s001", false, canObjectBeBoolean(s001));

	}

	public boolean canObjectBeBoolean(Object value) {
		return new TypeCheckedValue(value).canBeBoolean();
	}

	public boolean canObjectBeInteger(Object value) {
		return new TypeCheckedValue(value).canBeInteger();
	}
	
	public boolean canObjectBeDouble(Object value) {
		return new TypeCheckedValue(value).canBeDouble();
	}
	
	@Test
	public void testCanObjectBeDouble() {
		assertEquals("d0", true, canObjectBeDouble(d0));
		assertEquals("d1", true, canObjectBeDouble(d1));
		assertEquals("d11", true, canObjectBeDouble(d11));
		assertEquals("f1", true, canObjectBeDouble(f1));
		assertEquals("f11", true, canObjectBeDouble(f11));

		assertEquals("maxLong", true, canObjectBeDouble(maxLong));
		assertEquals("doubleMaxLong", true, canObjectBeDouble(doubleMaxLong));
		assertEquals("floatMaxLong", true, canObjectBeDouble(floatMaxLong));

		assertEquals("s1", true, canObjectBeDouble(s1));
		assertEquals("s10", true, canObjectBeDouble(s10));
		assertEquals("s11", true, canObjectBeDouble(s11));
		assertEquals("s0", true, canObjectBeDouble(s0));
		assertEquals("s00", true, canObjectBeDouble(s00));
		assertEquals("s001", true, canObjectBeDouble(s001));

	}

	@Test
	public void testCanObjectBeInteger() {
		assertEquals("d0", true, canObjectBeInteger(d0));
		assertEquals("d1", true, canObjectBeInteger(d1));
		assertEquals("d11", false, canObjectBeInteger(d11));
		assertEquals("f1", true, canObjectBeInteger(f1));
		assertEquals("f11", false, canObjectBeInteger(f11));

		assertEquals("maxLong", false, canObjectBeInteger(maxLong));
		assertEquals("doubleMaxLong", false, canObjectBeInteger(doubleMaxLong));
		assertEquals("floatMaxLong", false, canObjectBeInteger(floatMaxLong));
		
		assertEquals("s1", true, canObjectBeInteger(s1));
		assertEquals("s10", true, canObjectBeInteger(s10));
		assertEquals("s11", false, canObjectBeInteger(s11));
		assertEquals("s0", true, canObjectBeInteger(s0));
		assertEquals("s00", true, canObjectBeInteger(s00));
		assertEquals("s001", false, canObjectBeInteger(s001));

	}
	
	@Test
	public void testMissingIndividualValues() throws NarrowException {
		assertEquals(true, new TypeCheckedValue("").setConvertMissing(true).canBeInteger());
		assertEquals(true, new TypeCheckedValue(null).setConvertMissing(true).canBeInteger());
		
		assertEquals(true, new TypeCheckedValue("").setConvertMissing(true).canBeDouble());
		assertEquals(true, new TypeCheckedValue(null).setConvertMissing(true).canBeDouble());
		
		assertEquals(TypeCheckedValue.MISSING_VALUE_INTEGER, 
				new TypeCheckedValue("").setConvertMissing(true).asInteger());
		assertEquals(TypeCheckedValue.MISSING_VALUE_INTEGER, 
				new TypeCheckedValue(null).setConvertMissing(true).asInteger());
		
		assertEquals(TypeCheckedValue.MISSING_VALUE_DOUBLE, 
				new TypeCheckedValue("").setConvertMissing(true).asDouble(), EPSILON);
		assertEquals(TypeCheckedValue.MISSING_VALUE_DOUBLE, 
				new TypeCheckedValue(null).setConvertMissing(true).asDouble(), EPSILON);

	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(NarrowUtilTest.class);
	}

}

package org.omancode.rmt.test;

import org.junit.*;
import org.omancode.rmt.cellreader.MissingValueReader;

import static org.junit.Assert.*;

/**
 * The class <code>MissingValueReaderTest</code> contains tests for the class <code>{@link MissingValueReader}</code>.
 *
 * @generatedBy CodePro at 22/12/10 2:41 PM
 * @author oman002
 * @version $Revision$
 */
public class MissingValueReaderTest {
	/**
	 * Run the MissingValueReader(Object) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	@Test
	public void testMissingValueReader_1()
		throws Exception {
		Object returnValue = new Object();

		MissingValueReader result = new MissingValueReader(returnValue);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Object call(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	@Test
	public void testCall_1()
		throws Exception {
		MissingValueReader fixture = new MissingValueReader(new Object());
		Object value = new Object();

		Object result = fixture.call(value);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Class<Object> getResultType() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	@Test
	public void testGetResultType_1()
		throws Exception {
		MissingValueReader fixture = new MissingValueReader(new Object());

		Class<Object> result = fixture.getResultType();

		// add additional test code here
		assertNotNull(result);
		assertEquals("class java.lang.Object", result.toString());
		assertEquals(1, result.getModifiers());
		assertEquals(false, result.isInterface());
		assertEquals(false, result.isArray());
		assertEquals(false, result.isPrimitive());
		assertEquals(null, result.getSuperclass());
		assertEquals(null, result.getComponentType());
		assertEquals("java.lang.Object", result.getName());
		assertEquals(false, result.desiredAssertionStatus());
		assertEquals("java.lang.Object", result.getCanonicalName());
		assertEquals(null, result.getClassLoader());
		assertEquals(null, result.getDeclaringClass());
		assertEquals(null, result.getEnclosingClass());
		assertEquals(null, result.getEnclosingConstructor());
		assertEquals(null, result.getEnclosingMethod());
		assertEquals(null, result.getEnumConstants());
		assertEquals(null, result.getGenericSuperclass());
		assertEquals(null, result.getSigners());
		assertEquals("Object", result.getSimpleName());
		assertEquals(false, result.isAnnotation());
		assertEquals(false, result.isAnonymousClass());
		assertEquals(false, result.isEnum());
		assertEquals(false, result.isLocalClass());
		assertEquals(false, result.isMemberClass());
		assertEquals(false, result.isSynthetic());
	}

	/**
	 * Run the Object iifMissing(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	@Test
	public void testIifMissing_1()
		throws Exception {
		MissingValueReader fixture = new MissingValueReader(123);
		Object value = null;

		Object result = fixture.iifMissing(value);
		assertEquals(123, result);

		// add additional test code here
	}

	/**
	 * Run the Object iifMissing(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	@Test
	public void testIifMissing_2()
		throws Exception {
		MissingValueReader fixture = new MissingValueReader(123);
		Object value = 456;

		Object result = fixture.iifMissing(value);
		assertEquals(456, result);

		// add additional test code here
	}

	/**
	 * Run the Object iifMissing(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	@Test
	public void testIifMissing_3()
		throws Exception {
		MissingValueReader fixture = new MissingValueReader(123);
		Object value = "";

		Object result = fixture.iifMissing(value);
		assertEquals(123, result);

		// add additional test code here
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 22/12/10 2:41 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(MissingValueReaderTest.class);
	}
}
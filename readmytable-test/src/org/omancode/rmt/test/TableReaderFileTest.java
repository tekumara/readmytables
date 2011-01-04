package org.omancode.rmt.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;
import org.omancode.rmt.cellreader.CellReader;
import org.omancode.rmt.cellreader.CellReaders;
import org.omancode.rmt.tablereader.AbstractTableReader;
import org.omancode.rmt.tablereader.file.DelimitedFileReader;
import org.omancode.rmt.tablereader.file.ExcelFileReader;
import org.omancode.util.ArrayUtil;

public class TableReaderFileTest {

	public static final String TEST_DIR = "resources/";
	
	public static final Double EPSILON = 1e-15;
	
	private String[] header = new String[] {"id", "name", "gender", "age", "updated"}; 
	private String[] columnsSelection = new String[] {"name", "age", "id"};
	
	private CellReader<?>[] readersSelection = new CellReader<?>[] {
			CellReaders.STRING, CellReaders.DOUBLE, CellReaders.INTEGER}; 

	private CellReader<?>[] readers = new CellReader<?>[] {CellReaders.INTEGER,
			CellReaders.STRING, CellReaders.CHARACTER,
			CellReaders.DOUBLE, CellReaders.BOOLEAN}; 

	private CellReader<?>[] optionalReaders = new CellReader<?>[] {CellReaders.OPTIONAL_INTEGER,
			CellReaders.STRING, CellReaders.CHARACTER,
			CellReaders.OPTIONAL_DOUBLE, CellReaders.BOOLEAN}; 

	private final AbstractTableReader xlsUnspecified = new ExcelFileReader(new File(TEST_DIR + "xls_people.xls"));
	private final AbstractTableReader csvUnspecified = new DelimitedFileReader(new File(TEST_DIR + "xls_people.csv"));
	private final AbstractTableReader xlsSpecified = new ExcelFileReader(new File(TEST_DIR + "xls_people.xls"), header, readers);
	private final AbstractTableReader csvSpecified = new DelimitedFileReader(new File(TEST_DIR + "xls_people.csv"), header, readers, null);
	private final AbstractTableReader xlsSelection = new ExcelFileReader(new File(TEST_DIR + "xls_people.xls"), columnsSelection, readersSelection);
	private final AbstractTableReader csvSelection = new DelimitedFileReader(new File(TEST_DIR + "xls_people.csv"), columnsSelection, readersSelection, null);

	
	public TableReaderFileTest() throws IOException {

	}

	@Test
	public void testGetColumnsReadWhenNotSpecifiedCSV() throws IOException {
		assertArrayEquals(header, csvUnspecified.getColumnsRead());
	}


	@Test
	public void testGetColumnsReadWhenNotSpecifiedXLS() throws IOException {
		assertArrayEquals(header, xlsUnspecified.getColumnsRead());
	}
		

	@Test
	public void getRowsCSVUnspecified() throws IOException {
		Iterator<Object[]> iterator = csvUnspecified.iterator();

		Object[] row1 = new Object[] {"1", "mike", "m" , Double.valueOf(18.25).toString(), "TRUE"};
		Object[] row2 = new Object[] {"2", "michael", "m" , Double.valueOf(28.25).toString(), "TRUE"}; 
		Object[] row3 = new Object[] {"3", "peter", "m" , Double.valueOf(28.25 + (1.0/3.0)).toString(), "FALSE"}; 
		Object[] row4 = new Object[] {"4", "bob", "m" , "17", "TRUE"}; 
		Object[] row5 = new Object[] {"5", "barbara", "f" , Double.valueOf(18.7635120384).toString(), "FALSE"}; 

		assertEquals(true, iterator.hasNext());
		Object[] arow1 = iterator.next();
		assertArrayEquals(row1, arow1);
		assertArrayEquals(row2, iterator.next());
		assertArrayEquals(row3, iterator.next());
		assertArrayEquals(row4, iterator.next());
		assertArrayEquals(row5, iterator.next());
		assertEquals(false, iterator.hasNext());
	}
	
	
	@Test
	public void getRowsXLSUnspecified() throws IOException {
		Iterator<Object[]> iterator = xlsUnspecified.iterator();

		Object[] row1 = new Object[] {Double.valueOf(1), "mike", "m" , Double.valueOf(18.25), true};
		Object[] row2 = new Object[] {Double.valueOf(2), "michael", "m" , Double.valueOf(28.25), true}; 
		Object[] row3 = new Object[] {Double.valueOf(3), "peter", "m" , Double.valueOf(28.25 + (1.0/3.0)), false}; 
		Object[] row4 = new Object[] {Double.valueOf(4), "bob", "m" , Double.valueOf(17), true}; 
		Object[] row5 = new Object[] {Double.valueOf(5), "barbara", "f" , Double.valueOf(18.7635120384), false}; 

		assertEquals(true, iterator.hasNext());
		Object[] arow1 = iterator.next();
		assertArrayEquals(row1, arow1);
		assertArrayEquals(row2, iterator.next());
		assertArrayEquals(row3, iterator.next());
		assertArrayEquals(row4, iterator.next());
		assertArrayEquals(row5, iterator.next());
		assertEquals(false, iterator.hasNext());
	}


	@Test
	public void testDoubleFieldXLSSpecified() throws IOException {
		testDoubleField3(xlsSpecified);
	}

	@Test
	public void testDoubleFieldCSVSpecified() throws IOException {
		testDoubleField3(csvSpecified);
	}

	public void testDoubleField3(AbstractTableReader cellfile) throws IOException {
		testDoubleField(cellfile, 3);
	}

	@Test
	public void testDoubleFieldXLSSelection() throws IOException {
		testDoubleField1(xlsSelection);
	}

	@Test
	public void testDoubleFieldCSVSelection() throws IOException {
		testDoubleField1(csvSelection);
	}

	public void testDoubleField1(AbstractTableReader cellfile) throws IOException {
		testDoubleField(cellfile, 1);
	}

	@Test
	public void testRowTypesSpecifiedCSV() throws IOException {
		testRowTypesSpecified(csvSpecified);
	}

	@Test
	public void testRowTypesSpecifiedXLS() throws IOException {
		testRowTypesSpecified(xlsSpecified);
	}

	public void testRowTypesSpecified(AbstractTableReader cellfile) throws IOException {
		//"id", "name", "gender", "age", "updated"
		Iterator<Object[]> iterator = cellfile.iterator();
		Object[] readRow1 = iterator.next();

		assertEquals(Integer.class, readRow1[0].getClass());
		assertEquals(String.class, readRow1[1].getClass());
		assertEquals(Character.class, readRow1[2].getClass());
		assertEquals(Double.class, readRow1[3].getClass());
		assertEquals(Boolean.class, readRow1[4].getClass());
	}

	
	@Test
	public void testSelectColumnThatDoesntExistBOTH() {
		try {
			String[] bogusHeader = new String[] {"id", "name", "gender", "age2"};
			AbstractTableReader xlsMissingHeader = new ExcelFileReader(new File(TEST_DIR + "xls_people.xls"), bogusHeader);
			fail("IOException not genereated");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		try {
			String[] bogusHeader = new String[] {"id", "name", "gender", "age2"};
			AbstractTableReader csvMissingHeader = new DelimitedFileReader(new File(TEST_DIR + "xls_people.csv"), bogusHeader);
			fail("IOException not genereated");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}


	@Test
	public void testSelectionOfColumnsCSV() throws IOException {
		testSelectionOfColumns(csvSelection);
	}

	@Test
	public void testSelectionOfColumnsXLS() throws IOException {
		testSelectionOfColumns(xlsSelection);
	}

	public void testSelectionOfColumns(AbstractTableReader xls) throws IOException {
		
		assertArrayEquals(columnsSelection, xls.getColumnsRead());
		
		Object[] row1 = new Object[] {"mike", Double.valueOf(18.25), Integer.valueOf(1)};
		Object[] row2 = new Object[] {"michael", Double.valueOf(28.25), Integer.valueOf(2)}; 
		Object[] row3 = new Object[] {"peter", Double.valueOf(28.25 + (1.0/3.0)), Integer.valueOf(3)}; 
		Object[] row4 = new Object[] {"bob", Double.valueOf(17), Integer.valueOf(4)}; 
		Object[] row5 = new Object[] {"barbara", Double.valueOf(18.7635120384), Integer.valueOf(5)}; 

		Iterator<Object[]> iterator = xls.iterator();
		assertEquals(true, iterator.hasNext());

		assertArrayEquals(row1, iterator.next());
		assertArrayEquals(row2, iterator.next());
		assertArrayEquals(row3, iterator.next());
		assertArrayEquals(row4, iterator.next());
		assertArrayEquals(row5, iterator.next());
		assertEquals(false, iterator.hasNext());
		
		
	}
	
	
	private void testDoubleField(AbstractTableReader xls, int doubleIndex) throws IOException {
		Iterator<Object[]> iterator = xls.iterator();

		assertEquals(true, iterator.hasNext());

		Object[] readRow1 = iterator.next();
		assertEquals(Double.valueOf(18.25), (Double)readRow1[doubleIndex], EPSILON);
		
		Object[] readRow2 = iterator.next();
		assertEquals(Double.valueOf(28.25), (Double)readRow2[doubleIndex], EPSILON);

		Object[] readRow3 = iterator.next();
		assertEquals(Double.valueOf(28.25 + (1.0/3.0)), (Double)readRow3[doubleIndex], EPSILON);

		Object[] readRow4 = iterator.next();
		assertEquals(Double.valueOf(17), (Double)readRow4[doubleIndex], EPSILON);

		Object[] readRow5 = iterator.next();
		assertEquals(Double.valueOf(18.7635120384), (Double)readRow5[doubleIndex], EPSILON);

		assertEquals(false, iterator.hasNext());
		
		
	}

	
	@Test
	public void testBlankBOTH() {
		try {
			AbstractTableReader xlsBlankSheet = new ExcelFileReader(new File(TEST_DIR + "blank.xlsx"));
			fail("IOException not genereated");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			AbstractTableReader xlsBlankSheet = new DelimitedFileReader(new File(TEST_DIR + "blank.csv"));
			fail("IOException not genereated");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testMissingColumnHeaderBOTH()  {
		try {
			AbstractTableReader xlsMissingHeader = new ExcelFileReader(new File(TEST_DIR + "xls_missing_column_header.xls"));
			fail("IOException not generated");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			AbstractTableReader csvMissingHeader = new DelimitedFileReader(new File(TEST_DIR + "xls_missing_column_header.csv"));
			fail("IOException not generated. Header [" + ArrayUtil.toString(csvMissingHeader.getHeaderRow()) + "]" );
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testMissingValuesXLS() throws IOException  {
		AbstractTableReader xls = 
			new ExcelFileReader(new File(TEST_DIR + "xls_people_missing_values.xls"), header, optionalReaders);
		testMissingValues(xls);
	}


	@Test
	public void testMissingValuesCSV() throws IOException  {
		AbstractTableReader csv = 
			new DelimitedFileReader(new File(TEST_DIR + "xls_people_missing_values.csv"), header, optionalReaders, null);
		testMissingValues(csv);
	}

	public void testMissingValues(AbstractTableReader xls) throws IOException  {
		Iterator<Object[]> iterator = xls.iterator();
		Object[] readRow1 = iterator.next();

		assertEquals(Integer.class, readRow1[0].getClass());
		assertEquals(CellReaders.MISSING_VALUE_INTEGER, readRow1[0]);
		
		assertEquals(String.class, readRow1[1].getClass());
		assertEquals(Character.class, readRow1[2].getClass());
		assertEquals(Double.class, readRow1[3].getClass());
		assertEquals(CellReaders.MISSING_VALUE_DOUBLE, readRow1[3]);
		
		assertEquals(Boolean.class, readRow1[4].getClass());
	}


	@Test
	public void testMissingValuesExceptionXLS() throws IOException  {
		AbstractTableReader xls = 
			new ExcelFileReader(new File(TEST_DIR + "xls_people_missing_values.xls"), header, readers);
		testMissingValuesException(xls);
	}

	@Test
	public void testMissingValuesExceptionCSV() throws IOException  {
		AbstractTableReader xls = 
			new DelimitedFileReader(new File(TEST_DIR + "xls_people_missing_values.csv"), header, readers, null);
		testMissingValuesException(xls);
	}

	public void testMissingValuesException(AbstractTableReader xls) throws IOException  {

		try {
			Iterator<Object[]> iterator = xls.iterator();
			Object[] readRow1 = iterator.next();
			fail("RuntimeException not generated");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		
	}

}

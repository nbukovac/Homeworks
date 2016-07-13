package hr.fer.zemris.java.cstr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class CStringTests {

	@Test
	public void testAddMethod() {
		final CString string1 = new CString("Krafna".toCharArray());
		final CString string2 = new CString(" i burek s mesom".toCharArray());
		final CString string3 = string1.add(string2);
		final CString string4 = string3.left(3).add(string1);

		assertEquals("Expected 'Krafna i burek s mesom'", true, "Krafna i burek s mesom".equals(string3.toString()));
		assertEquals("Expexted 'KraKrafna'", true, "KraKrafna".equals(string4.toString()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddWithNull() {
		final CString string1 = new CString("Burek".toCharArray());
		// throws
		string1.add(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBigConstructorWithIllegallLength() {
		// throws
		new CString(new char[2], 0, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBigConstructorWithIllegallOffset() {
		// throws
		new CString(new char[2], -1, 0);
	}

	@Test
	public void testCharAt() {
		final CString string1 = new CString("Krafna".toCharArray());
		final CString string2 = new CString("Krafna".toCharArray(), 3, 2);

		assertEquals('f', string1.charAt(3));
		assertEquals('n', string2.charAt(1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testCharAtInvalidIndex() {
		final CString string1 = new CString("Krafna".toCharArray());

		// throws
		string1.charAt(string1.length());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testCharAtInvalidIndex2() {
		final CString string1 = new CString("Krafna".toCharArray());

		// throws
		string1.charAt(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCharConstructorWithNull() {
		// throws
		final char[] data = null;
		new CString(data);
	}

	@Test
	public void testConstructors() {
		final CString string1 = new CString("Krafna".toCharArray(), 3, 2);
		final CString string2 = new CString(string1);
		final CString string3 = new CString("Burek".toCharArray());

		assertEquals("Expected 'fn'", true, "fn".equals(string1.toString()));
		assertEquals(2, string2.length());
		assertEquals(5, string3.length());
	}

	@Test
	public void testContains() {
		final CString string1 = new CString("Krafna".toCharArray());
		final CString string2 = new CString("af".toCharArray());
		final CString string3 = new CString("Burek".toCharArray());
		final CString string4 = new CString("Burek".toCharArray(), 3, 1);

		assertEquals(true, string1.contains(string1));
		assertEquals(true, string1.contains(string2));
		assertEquals(false, string1.contains(string3));
		assertEquals(true, string3.contains(string4));
	}

	@Test
	public void testContainsWithNull() {
		final CString string1 = new CString("Krafna".toCharArray());

		assertEquals(false, string1.contains(null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCStringConstructorWithNull() {
		// throws
		final CString data = null;
		new CString(data);
	}

	@Test
	public void testEndsWith() {
		final CString string1 = new CString("Krafna".toCharArray());
		final CString string2 = new CString("af".toCharArray());
		final CString string3 = new CString("Burek".toCharArray());
		final CString string4 = new CString("Burek".toCharArray(), 4, 1);
		final CString string5 = new CString("afna".toCharArray());

		assertEquals(false, string1.endsWith(string2));
		assertEquals(true, string1.endsWith(string5));
		assertEquals(true, string3.endsWith(string4));
		assertEquals(true, string3.endsWith(string3));
		assertEquals(false, string4.endsWith(string3));
	}

	@Test
	public void testEndsWithNull() {
		final CString string1 = new CString("Krafna".toCharArray());

		assertEquals(false, string1.endsWith(null));
	}

	@Test
	public void testExampleFromHomework() {
		final CString string1 = CString.fromString("ababab").replaceAll(CString.fromString("ab"),
				CString.fromString("abab"));

		assertEquals(true, "ababab".replaceAll("ab", "abab").equals(string1.toString()));
	}

	@Test
	public void testIndexOf() {
		final CString string1 = new CString("Krafna".toCharArray());
		final CString string2 = new CString("af".toCharArray());
		final CString string3 = new CString("Burek".toCharArray());
		final CString string4 = new CString("Burek".toCharArray(), 0, 3);

		assertEquals(-1, string1.indexOf('k'));
		assertEquals(0, string2.indexOf('a'));
		assertEquals(-1, string3.indexOf('j'));
		assertEquals(1, string2.indexOf('f'));
		assertEquals(-1, string4.indexOf('e'));
	}

	@Test
	public void testLeft() {
		final CString string1 = new CString("Krafna".toCharArray());
		final CString string2 = new CString("Kraf".toCharArray());
		final CString string3 = new CString("Burek".toCharArray());
		final CString string4 = new CString("Burek".toCharArray(), 1, 3);

		assertEquals(true, string1.left(4).startsWith(string2));
		assertEquals(true, "Krafna".equals(string1.left(string1.length()).toString()));
		assertEquals(false, string3.left(4).startsWith(string4));
		assertEquals(true, "".equals(string1.left(0).toString()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLeftInvalid() {
		final CString string1 = new CString("Krafna".toCharArray());
		string1.left(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLeftInvalid2() {
		final CString string1 = new CString("Krafna".toCharArray());
		string1.left(string1.length() + 1);
	}

	@Test
	public void testReplaceChar() {
		CString string1 = new CString("Krafna\n".toCharArray());

		string1 = string1.replaceAll('a', 'e');

		assertEquals(true, "Krefne\n".equals(string1.toString()));

		string1 = string1.replaceAll('a', 'b');

		assertEquals(true, "Krefne\n".equals(string1.toString()));

		string1 = string1.replaceAll('\n', '\t');

		assertEquals(true, "Krefne\t".equals(string1.toString()));
	}

	@Test
	public void testReplaceCString() {
		CString string1 = new CString("Krafna\nafafasfaf".toCharArray());
		final CString string2 = new CString("af".toCharArray());
		CString string3 = new CString("Burek".toCharArray());

		string1 = string1.replaceAll(string2, string3);

		assertEquals(true, "KrBurekna\nBurekBurekasfBurek".equals(string1.toString()));

		string3 = string3.replaceAll(string3, string2);

		assertEquals(true, "af".equals(string3.toString()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testReplaceCStringWithNewNull() {
		final CString string1 = new CString("Krafna\nafafasfaf".toCharArray());
		string1.replaceAll(string1, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReplaceCStringWithOldNull() {
		final CString string1 = new CString("Krafna\nafafasfaf".toCharArray());
		string1.replaceAll(null, string1);
	}

	@Test
	public void testRight() {
		final CString string1 = new CString("Krafna".toCharArray());
		final CString string3 = new CString("Burek".toCharArray());
		final CString string4 = new CString("Burek".toCharArray(), 1, 3);

		assertEquals(true, "afna".equals(string1.right(4).toString()));
		assertEquals(true, "Krafna".equals(string1.right(string1.length()).toString()));
		assertEquals(false, string3.right(4).endsWith(string4));
		assertEquals(true, "".equals(string1.right(0).toString()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRightInvalid() {
		final CString string1 = new CString("Krafna".toCharArray());
		string1.right(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRightInvalid2() {
		final CString string1 = new CString("Krafna".toCharArray());
		string1.right(string1.length() + 1);
	}

	@Test
	public void testStartsWith() {
		final CString string1 = new CString("Krafna".toCharArray());
		final CString string2 = new CString("af".toCharArray());
		final CString string3 = new CString("Burek".toCharArray());
		final CString string4 = new CString("Burek".toCharArray(), 0, 1);
		final CString string5 = new CString("Kr".toCharArray());

		assertEquals(false, string1.startsWith(string2));
		assertEquals(true, string1.startsWith(string5));
		assertEquals(true, string3.startsWith(string4));
		assertEquals(true, string3.startsWith(string3));
		assertEquals(false, string4.startsWith(string3));
	}

	@Test
	public void testStartsWithNull() {
		final CString string1 = new CString("Krafna".toCharArray());

		assertEquals(false, string1.startsWith(null));
	}

	@Test
	public void testSubstring() {
		final CString string1 = new CString("Krafna".toCharArray());
		final CString string2 = new CString("af".toCharArray());
		final CString string3 = new CString("Burek".toCharArray());
		final CString string4 = new CString("Burek".toCharArray(), 0, 1);

		assertEquals(true, "Krafna".equals(string1.substring(0, string1.length()).toString()));
		assertEquals(true, "a".equals(string2.substring(0, 1).toString()));
		assertEquals(true, "".equals(string2.substring(0, 0).toString()));
		assertEquals(true, string3.substring(0, 1).startsWith(string4));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSubstringInvalidEndIndex() {
		final CString string1 = new CString("Krafna".toCharArray());
		string1.substring(2, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSubstringInvalidStartIndex() {
		final CString string1 = new CString("Krafna".toCharArray());
		string1.substring(-1, 0);
	}
}

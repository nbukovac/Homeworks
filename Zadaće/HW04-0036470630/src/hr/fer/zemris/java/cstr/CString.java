package hr.fer.zemris.java.cstr;

/**
 * Class similar to {@link String} with similar functions as the mentioned
 * {@link String} class. You can create new strings, concatenate them, replace
 * characters inside them, create substrings, check boolean functions such as to
 * find out if a string contains a substring and so on
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class CString {

	/**
	 * Creates a new {@link CString} from a provided <code>String</code>. A
	 * {@link IllegalArgumentException} is thrown if the provided argument is a
	 * null reference
	 * 
	 * @param s
	 *            String for creation
	 * @return new {@link CString} with value same as String argument
	 * @throws IllegalArgumentException
	 *             if argument is null
	 */
	public static CString fromString(final String s) {
		if (s == null) {
			throw new IllegalArgumentException("Null reference is not a valid argument for fromString method");
		}

		return new CString(s.toCharArray());
	}

	/**
	 * Size of the {@link CString}
	 */
	private final int length;

	/**
	 * Offset used to indicate the starting position in the <code>value</code>
	 * array
	 */
	private final int offset;

	/**
	 * Value of {@link CString}
	 */
	private final char[] value;

	/**
	 * Constructs a new {@link CString} with the provided <code>data</code>
	 * argument as value. An {@link IllegalArgumentException} is thrown if data
	 * is null.
	 * 
	 * @param data
	 *            char array that specifies {@link CString} value
	 * @throws IllegalArgumentException
	 *             if data is null
	 */
	public CString(final char[] data) {
		this(data, 0, data == null ? 0 : data.length);
	}

	/**
	 * Constructs a new {@link CString} with the provided <code>data</code>
	 * argument as value. <code>offset</code> and <code>length</code> are used
	 * to specify which part of <code>data</code> argument is used for
	 * {@link CString} value. An {@link IllegalArgumentException} is thrown if
	 * data is null, offset is set to &lt; 0 or &gt; data length, or if
	 * specified length is &gt; data length - offset
	 * 
	 * @param data
	 *            char array that specifies {@link CString} value
	 * @param offset
	 *            offset in data array
	 * @param length
	 *            size of String
	 * @throws IllegalArgumentException
	 *             if data is null, offset is set to &lt; 0 or &gt; data length,
	 *             or if specified length is &gt; data length - offset
	 */
	public CString(final char[] data, final int offset, final int length) {
		if (data == null || offset < 0 || offset > data.length || length > data.length - offset) {
			throw new IllegalArgumentException("Illegal argument was passed to CString constructor");
		}

		value = new char[length];

		for (int i = 0; i < length; i++) {
			value[i] = data[offset + i];
		}

		this.offset = 0;
		this.length = length;
	}

	/**
	 * Constructs a new {@link CString} from an other {@link CString}. An
	 * {@link IllegalArgumentException} is thrown if <code>original</code> is
	 * null.
	 * 
	 * @param original
	 *            original {@link CString}
	 */
	public CString(final CString original) {
		checkArgumentNull(original, "Null can't be passed to CString Constructor");

		if (original.value.length == original.length) {
			value = original.value;

		} else {
			value = original.toCharArray();
		}

		offset = 0;
		length = value.length;
	}

	/**
	 * Constructs a new {@link CString} with the provided <code>data</code>
	 * argument as value. <code>offset</code> and <code>length</code> are used
	 * to specify which part of <code>data</code> argument is used for
	 * {@link CString} value. An {@link IllegalArgumentException} is thrown if
	 * data is null, offset is set to &lt; 0 or &gt; data length, or if
	 * specified length is &gt; data length - offset
	 * 
	 * @param data
	 *            char array that specifies {@link CString} value
	 * @param offset
	 *            offset in data array
	 * @param length
	 *            size of String
	 * @throws IllegalArgumentException
	 *             if data is null, offset is set to &lt; 0 or &gt; data length,
	 *             or if specified length is &gt; data length - offset
	 */
	private CString(final int length, final char[] data, final int offset) {
		if (data == null || offset < 0 || offset > data.length || length > data.length - offset) {
			throw new IllegalArgumentException("Illegal argument was passed to CString constructor");
		}

		value = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Concatenates argument <code>s</code> to the existing {@link CString}.
	 * 
	 * @param s
	 *            string to concatenate
	 * @return new {@link CString} with concatenated value
	 */
	public CString add(final CString s) {
		checkArgumentNull(s, "Null reference can't be concatenated to a CString");

		final char[] thisArray = toCharArray();
		final char[] otherArray = s.toCharArray();
		final int combinedSize = thisArray.length + otherArray.length;
		final char[] returnArray = new char[combinedSize];

		for (int i = 0, end = thisArray.length; i < end; i++) {
			returnArray[i] = thisArray[i];
		}

		for (int i = thisArray.length, j = 0; i < combinedSize; i++, j++) {
			returnArray[i] = otherArray[j];
		}

		return new CString(returnArray);
	}

	/**
	 * Returns the <code>char</code> at the specified position in the
	 * {@link CString}. An {@link IndexOutOfBoundsException} is thrown if the
	 * provided index is invalid. Index is invalid if it is &lt; 0 or &gt;=
	 * {@link CString} <code>length</code>
	 * 
	 * @param index
	 *            position in {@link CString}
	 * @return char at the specified position
	 * @throws IndexOutOfBoundsException
	 *             if index is &lt; 0 or &gt;= {@link CString}
	 *             <code>length</code>
	 */
	public char charAt(final int index) {
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException("Invalid index value ->" + index);
		}

		return value[offset + index];
	}

	/**
	 * Checks if the provided <code>s</code> argument is <code>null</code>. If
	 * it is a {@link IllegalArgumentException} is thrown with the specified
	 * message.
	 * 
	 * @param s
	 *            argument to check
	 * @param message
	 *            message for exception
	 * @throws IllegalArgumentException
	 *             if argument s is null
	 */
	private void checkArgumentNull(final CString s, final String message) {
		if (s == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Checks if the provided <code>n</code> argument is valid. Argument is
	 * valid if it is &gt;= 0 and &lt;= data length. If it is invalid an
	 * {@link IllegalArgumentException} is thrown;
	 * 
	 * @param n
	 *            argument to check
	 * @throws IllegalArgumentException
	 *             if n is &lt; 0 or &gt; data length
	 */
	private void checkSize(final int n) {
		if (n < 0 || n > length) {
			throw new IllegalArgumentException("Invalid argument for methods left and right" + n);
		}
	}

	/**
	 * Checks if a {@link CString} contains a specified {@link CString} inside
	 * itself.
	 * 
	 * @param s
	 *            {@link CString} to check
	 * @return true if it does contain the specified {@link CString}, else false
	 */
	public boolean contains(final CString s) {
		if (s == null) {
			return false;
		}

		final char[] checkArray = s.toCharArray();

		for (int i = offset, end = offset + length; i < end; i++) {
			if (value[i] == checkArray[0]) {
				int count = 1;
				int tmp = i + 1;
				final int size = checkArray.length;

				for (int j = 1; j < size; j++, tmp++) {
					if (value[tmp] == checkArray[j]) {
						count++;
					} else {
						break;
					}
				}

				if (count == size) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if a {@link CString} ends with a sequence same as a whole other
	 * {@link CString}. If the other {@link CString} is larger than this false
	 * is returned.
	 * 
	 * @param s
	 *            {@link CString} sequence to check
	 * @return true if this {@link CString} ends like the other {@link CString}
	 *         , else false
	 */
	public boolean endsWith(final CString s) {
		if (s == null || s.length() > length) {
			return false;
		}

		final char[] thisArray = toCharArray();
		final char[] endArray = s.toCharArray();

		for (int end = endArray.length, i = thisArray.length
				- end, terminate = thisArray.length, j = 0; i < terminate; i++, j++) {
			if (thisArray[i] != endArray[j]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the position of a specified <code>char</code> values first
	 * occurrence in this {@link CString}. If the specified <code>char</code>
	 * value isn't in this {@link CString} then returns -1. This method is case
	 * sensitive.
	 * 
	 * @param c
	 *            character specified to be found
	 * @return position of the first character occurrence or -1 if it isn't
	 *         found
	 */
	public int indexOf(final char c) {
		for (int i = offset, end = offset + length; i < end; i++) {
			if (value[i] == c) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns a new {@link CString} based on this {@link CString} but only the
	 * first <code>n</code> characters. If <code>n</code> is invalid a
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param n
	 *            size of the new {@link CString}
	 * @return new {@link CString}
	 * @throws IllegalArgumentException
	 *             if n is &lt; 0 or &gt; data length
	 */
	public CString left(final int n) {
		checkSize(n);

		return substring(0, n);
	}

	/**
	 * Returns the length of {@link CString}.
	 * 
	 * @return length of {@link CString}
	 */
	public int length() {
		return length;
	}

	/**
	 * Returns a new {@link CString} based on this {@link CString} but with
	 * replaced characters. Every <code>oldChar</code> is replaced with
	 * <code>newChar</code>.
	 * 
	 * @param oldChar
	 *            chars to be replaced
	 * @param newChar
	 *            chars which are used for replacing
	 * @return new {@link CString} with replaced characters
	 */
	public CString replaceAll(final char oldChar, final char newChar) {
		final char[] array = toCharArray();

		for (int i = 0, size = array.length; i < size; i++) {
			if (array[i] == oldChar) {
				array[i] = newChar;
			}
		}

		return new CString(array);
	}

	/**
	 * Returns a new {@link CString} based on this {@link CString} but with
	 * replaced parts defined as {@link CString}. Every <code>oldStr</code> is
	 * replaced with <code>newStr</code>.
	 * 
	 * @param oldStr
	 *            {@link CString} to be replaced
	 * @param newStr
	 *            replacing {@link CString}
	 * @return new {@link CString} with replaced parts
	 */
	public CString replaceAll(final CString oldStr, final CString newStr) {
		checkArgumentNull(oldStr, "Null reference isn't allowed as a argument in CString replaceAll method");
		checkArgumentNull(newStr, "Null reference isn't allowed as a argument in CString replaceAll method");

		final char[] oldArray = oldStr.toCharArray();
		final StringBuilder sb = new StringBuilder();

		for (int i = offset, end = offset + length; i < end; i++) {
			if (value[i] == oldArray[0]) {
				int count = 1;
				int tmp = i + 1;
				final int size = oldArray.length;

				for (int j = 1; j < size; j++, tmp++) {
					if (value[tmp] == oldArray[j]) {
						count++;
					} else {
						break;
					}
				}

				if (count == size) {
					sb.append(newStr.toString());
					i += oldStr.length - 1;

				} else {
					sb.append(value[i]);
				}

			} else {
				sb.append(value[i]);
			}
		}

		return new CString(sb.toString().toCharArray());
	}

	/**
	 * Returns a new {@link CString} based on this {@link CString} but only the
	 * last <code>n</code> characters. If <code>n</code> is invalid a
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param n
	 *            size of the new {@link CString}
	 * @return new {@link CString}
	 * @throws IllegalArgumentException
	 *             if n is &lt; 0 or &gt; data length
	 */
	public CString right(final int n) {
		checkSize(n);

		return substring(length - n, length);
	}

	/**
	 * Checks if a {@link CString} starts with a sequence same as a whole other
	 * {@link CString}. If the other {@link CString} is larger than this false
	 * is returned.
	 * 
	 * @param s
	 *            {@link CString} sequence to check
	 * @return true if this {@link CString} starts like the other
	 *         {@link CString} , else false
	 */
	public boolean startsWith(final CString s) {
		if (s == null || s.length() > length) {
			return false;
		}

		final char[] thisArray = toCharArray();
		final char[] startArray = s.toCharArray();

		for (int i = 0, end = startArray.length; i < end; i++) {
			if (thisArray[i] != startArray[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns a new {@link CString} based on this {@link CString} but only the
	 * characters starting from <code>startIndex</code> and
	 * <code>endIndex</code> but <code>endIndex</code> isn't included. An
	 * {@link IndexOutOfBoundsException} is thrown if <code>startIndex</code> is
	 * &lt; 0 or &gt; length same as <code>endIndex</code> and if
	 * <code>startIndex</code> &gt; <code>endIndex</code>
	 * 
	 * @param startIndex
	 *            starting position, included
	 * @param endIndex
	 *            end position, not included
	 * @return new {@link CString} between startIndex and endIndex
	 */
	public CString substring(final int startIndex, final int endIndex) {
		if (startIndex < 0 || startIndex > length || startIndex > endIndex || endIndex > length) {
			throw new IndexOutOfBoundsException("Invalid start or endindex for substring method");
		}

		return new CString(endIndex - startIndex, value, offset + startIndex);
	}

	/**
	 * Returns a <code>char</code> array representation of this {@link CString}.
	 * 
	 * @return {@link CString} as a new char array
	 */
	public char[] toCharArray() {
		final char[] returnArray = new char[length];

		for (int i = offset, j = 0, end = offset + length; i < end; i++, j++) {
			returnArray[j] = value[i];
		}

		return returnArray;
	}

	/**
	 * Returns a <code>String</code> representation of this {@link CString}.
	 * 
	 * @return {@link CString} as a new <code>String</code>
	 */
	@Override
	public String toString() {
		final char[] array = toCharArray();
		final StringBuilder sb = new StringBuilder();

		for (int i = 0, size = array.length; i < size; i++) {
			sb.append(array[i]);
		}

		return sb.toString();
	}

}

package hr.fer.zemris.java.tecaj.hw07.crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class HexToByteTests {

	private static boolean checkIfEqual(final byte[] predicted, final byte[] calculated) {
		for (int i = 0, size = calculated.length; i < size; i++) {
			if (predicted[i] != calculated[i]) {
				return false;
			}
		}

		return true;
	}

	@Test
	public void testByteToHex() {
		final byte[] byteArray = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		final String hexString = "000102030405060708090a0b0c0D0E0f";

		final String returnString = Crypto.bytetohex(byteArray);

		assertEquals(true, returnString.equals(hexString.toLowerCase()));
	}

	@Test
	public void testHexToByte() {
		final byte[] byteArray = new byte[] { 15, 32, (byte) 0xFF };
		final String hexString = "0F20FF";

		final byte[] returnValue = Crypto.hextobyte(hexString);

		assertEquals(true, checkIfEqual(byteArray, returnValue));
	}

	@Test
	public void testHexToByte2() {
		final byte[] byteArray = new byte[] { 0, (byte) 0xED, 0, 0x43 };
		final String hexString = "00ED0043";

		final byte[] returnValue = Crypto.hextobyte(hexString);

		assertEquals(true, checkIfEqual(byteArray, returnValue));
	}

	@Test
	public void testHexToByte3() {
		final byte[] byteArray = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		final String hexString = "000102030405060708090a0b0c0D0E0f";

		final byte[] returnValue = Crypto.hextobyte(hexString);

		assertEquals(true, checkIfEqual(byteArray, returnValue));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHexToByteIllegalHexValue() {
		Crypto.hextobyte("Ssss");
	}

}

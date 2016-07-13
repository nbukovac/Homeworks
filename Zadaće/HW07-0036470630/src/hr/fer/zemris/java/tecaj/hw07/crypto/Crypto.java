package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program that performs file checksum calculation using SHA-256 algorithm, file
 * encryption/decryption using the AES algorithm. Checksum calculation is done
 * with a {@link MessageDigest} object, while file encryption is done with a
 * {@link Cipher} object. Three commands are provided and they are
 * {@code checksha} for message digest, {@code encrypt} and {@code decrypt} for
 * file encryption. {@code checksha} requires one additional argument specifying
 * file name, while {@code encrypt} and {@code decrypt} require two additional
 * arguments specifying source file name and destination file name. File
 * processing is done in small blocks so large files can be processed.
 * 
 * @see "https://en.wikipedia.org/wiki/SHA-2"
 * @see "https://en.wikipedia.org/wiki/Advanced_Encryption_Standard"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Crypto {

	/**
	 * Size of read and write buffer
	 */
	private static final int KB = 1024;

	/**
	 * Algorithm used for digest calculation
	 */
	private static final String CHECK_SUM = "SHA-256";

	/**
	 * Encryption algorithm
	 */
	private static final String ENCRYPTION = "AES";

	/**
	 * Returns a {@code String} with a hex representation of a byte array.
	 * 
	 * @param array
	 *            byte array
	 * @return {@code String} with a hex representation of a byte array.
	 */
	public static String bytetohex(final byte[] array) {
		final StringBuilder sb = new StringBuilder();

		for (final byte b : array) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	/**
	 * Calculates message digest of the file specified by {@code fileName} and
	 * returns it as array of bytes. Algorithm used to calculate the digest is
	 * {@code SHA-256}.
	 * 
	 * @param fileName
	 *            name of the file for which we are calculating the digest
	 * @return message digest as a array of bytes
	 */
	private static byte[] calculateDigest(final String fileName) {
		FileInputStream inputStream = null;
		byte[] hash = null;

		try {
			final MessageDigest digest = MessageDigest.getInstance(CHECK_SUM);
			inputStream = new FileInputStream(fileName);
			final byte[] readBytes = new byte[KB];

			int numberOfReadBytes = inputStream.read(readBytes);

			while (numberOfReadBytes != -1) {
				digest.update(readBytes, 0, numberOfReadBytes);

				numberOfReadBytes = inputStream.read(readBytes);
			}

			hash = digest.digest();

		} catch (NoSuchAlgorithmException | IOException e) {
			System.err.println("Coudln't calculate checksum");
			System.exit(-2);
		} finally {
			try {
				inputStream.close();
			} catch (final Exception ignorable) {
			}
		}

		return hash;
	}

	/**
	 * Checks if the number of command line arguments are valid for a specific
	 * command. Commands are {@code checksha}, {@code encrypt} and
	 * {@code decrypt}. {@code checksha} requires one argument specifying the
	 * source file. {@code encrypt} and {@code decrypt} require two arguments
	 * specifying source file and destination file.
	 * 
	 * @param args
	 *            command arguments
	 * @param operation
	 *            command
	 * @return true if operation is encrypt, if it is decrypt false
	 */
	private static boolean checkCommandLineArguments(final String[] args, final String operation) {
		boolean encrypt = true;
		if (operation.equals("checksha")) {
			if (args.length != 2) {
				System.err.println("Checksum operation requires one argument");
				System.exit(1);
			}

			try {
				checksum(args[1]);
			} catch (final IllegalArgumentException e) {
				System.err.println("Illegal hex string");
			}

			System.exit(0);

		} else if (operation.equals("encrypt") || operation.equals("decrypt")) {
			if (args.length != 3) {
				System.err.println("Encryption operation requires 2 arguments");
				System.exit(1);
			}

			if (operation.equals("decrypt")) {
				encrypt = false;
			}
		} else {
			System.err.println("Invalid operation requested");
			System.exit(2);
		}
		return encrypt;
	}

	/**
	 * Checks if calculated and provided message digest are equal.
	 * 
	 * @param calculated
	 *            calculated digest
	 * @param expected
	 *            provided digest
	 * @return true if they are equal, else false
	 */
	private static boolean checkIfDigestsAreEqual(final byte[] calculated, final byte[] expected) {
		for (int i = 0, size = calculated.length; i < size; i++) {
			if (calculated[i] != expected[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Informs the user if the provided file specified by the {@code fileName}
	 * argument has the expected checksum value, if not the files checksum value
	 * is shown for comparison.
	 * 
	 * @param fileName
	 *            file to check
	 */
	private static void checksum(final String fileName) {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Please provide expected sha-256 digest for " + fileName);
		System.out.print("> ");

		try {
			final String userEnteredDigest = reader.readLine();
			final byte[] userByteDigest = hextobyte(userEnteredDigest);
			final byte[] calculatedDigest = calculateDigest(fileName);

			if (checkIfDigestsAreEqual(calculatedDigest, userByteDigest)) {
				System.out.println("Digesting completed. Digest of " + fileName + " matches expected digest.");
			} else {
				System.out
						.println("Digesting completed. Digest of " + fileName + " does not match the expected digest.");
				System.out.println(" Digest was: " + bytetohex(calculatedDigest));
			}
		} catch (final IOException e) {
			System.err.println("IOException : " + e.getMessage());
		}

	}

	/**
	 * Returns the byte representation of a hex value. An
	 * {@link IllegalArgumentException} is thrown if {@code hexChar} isn't a
	 * hexadecimal value.
	 * 
	 * @param hexChar
	 *            char containing a hex symbol
	 * @return byte representation of a hex value
	 * @throws IllegalArgumentException
	 *             if {@code hexChar} isn't a hexadecimal value.
	 */
	private static byte determineHexValue(final char hexChar) {
		byte byteValue = -1;

		switch (hexChar) {
		case '0':
			byteValue = 0;
			break;
		case '1':
			byteValue = 1;
			break;
		case '2':
			byteValue = 2;
			break;
		case '3':
			byteValue = 3;
			break;
		case '4':
			byteValue = 4;
			break;
		case '5':
			byteValue = 5;
			break;
		case '6':
			byteValue = 6;
			break;
		case '7':
			byteValue = 7;
			break;
		case '8':
			byteValue = 8;
			break;
		case '9':
			byteValue = 9;
			break;
		case 'A':
			byteValue = 10;
			break;
		case 'B':
			byteValue = 11;
			break;
		case 'C':
			byteValue = 12;
			break;
		case 'D':
			byteValue = 13;
			break;
		case 'E':
			byteValue = 14;
			break;
		case 'F':
			byteValue = 15;
			break;
		default:
			throw new IllegalArgumentException("The provided string has non hex characters");
		}

		return byteValue;
	}

	/**
	 * Transforms a {@code String} with hexadecimal value to a appropriate byte
	 * array.
	 * 
	 * @param text
	 *            hexadecimal value
	 * @return byte array
	 */
	public static byte[] hextobyte(final String text) {
		final byte[] byteArray = new byte[text.length() / 2];
		final char[] textArray = text.toCharArray();
		int position = 0;

		for (int i = 0, size = textArray.length; i + 1 < size; i += 2) {
			byte tempByte = determineHexValue(Character.toUpperCase(textArray[i]));
			tempByte <<= 4;
			tempByte += determineHexValue(Character.toUpperCase(textArray[i + 1]));

			byteArray[position] = tempByte;

			position++;
		}

		return byteArray;
	}

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments specifying commands and command arguments
	 */
	public static void main(final String[] args) {
		if (args.length < 2) {
			System.err.println("At least 2 arguments have to be provided");
			System.exit(-1);
		}

		final String operation = args[0].toLowerCase();

		final boolean encrypt = checkCommandLineArguments(args, operation);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print("> ");
			final String keyText = reader.readLine().trim();

			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print("> ");
			final String ivText = reader.readLine().trim();

			final SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), ENCRYPTION);
			final AlgorithmParameterSpec parameterSpec = new IvParameterSpec(hextobyte(ivText));
			final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, parameterSpec);

			processFile(cipher, args[1], args[2], encrypt);

		} catch (final IOException e) {
			System.err.println("Error while reading user input");
			System.exit(3);

		} catch (final GeneralSecurityException e) {
			System.err.println("Error while creating cipher");
			System.exit(4);
		}
	}

	/**
	 * Encrypts or decrypts a file with the AES encryption algorithm. A new file
	 * is created, containing either decrypted or encrypted data from the source
	 * file. Encryption/decryption is done in smaller blocks so large files can
	 * be encrypted/decrypted. Encryption/decryption is determined by
	 * {@code encrypt}, if {@code encrypt} is true then encryption process is
	 * perform, else decryption
	 * 
	 * @param cipher
	 *            {@link Cipher} object used to perform the
	 *            encryption/decryption
	 * @param sourceFile
	 *            source file for encryption/decryption
	 * @param destinationFile
	 *            destination file for the encrypted/decrypted data
	 * @param encrypt
	 *            if true then encryption process is perform, else decryption
	 */
	private static void processFile(final Cipher cipher, final String sourceFile, final String destinationFile,
			final boolean encrypt) {
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		final byte[] readBytes = new byte[KB];
		byte[] processedBytes = new byte[KB];

		try {
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(destinationFile);

			int numberOfReadBytes = inputStream.read(readBytes);

			while (numberOfReadBytes != -1) {
				if (numberOfReadBytes != KB) {
					processedBytes = cipher.doFinal(readBytes, 0, numberOfReadBytes);
				} else {
					processedBytes = cipher.update(readBytes, 0, numberOfReadBytes);
				}

				outputStream.write(processedBytes, 0, processedBytes.length);

				numberOfReadBytes = inputStream.read(readBytes);
			}
		} catch (final IOException e) {
			System.err.println("IOException : " + e.getMessage());
		} catch (final GeneralSecurityException e) {
			System.err.println("GeneralSecurityException : " + e.getMessage());
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (final IOException ignorable) {
			}
		}

		final String message = encrypt ? "Encryption" : "Decryption";
		System.out.print(message + " completed. Generated file " + destinationFile);
		System.out.println(" based upon file " + sourceFile);
	}

}

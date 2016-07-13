package hr.fer.zemris.java.simplecomp.helper;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Pomoćna klasa pomoću koje se određuje da li su zadovoljeni pojedini uvjeti
 * potrebni za pravilan rad. Provjerava se broj argumenata, vrste argumenata i
 * slično.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class HelperClass {

	/**
	 * Provjerava da li je predani argument registar i da li se koristi direktno
	 * adresiranje. Ukoliko je jedno od navedenih nije poštivano baca se
	 * {@link IllegalArgumentException}.
	 * 
	 * @param argument
	 *            argument za provjeru
	 * @throws IllegalArgumentException
	 *             ako argument nije registar ili ako se koristi indirektno
	 *             adresiranje
	 */
	public static void checkDirectAdressingRegister(final InstructionArgument argument) {
		if (!argument.isRegister() || RegisterUtil.isIndirect((int) argument.getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument");
		}
	}

	/**
	 * Provjerava da li je predani argument broj. Ukoliko nije baca se
	 * {@link IllegalArgumentException}.
	 * 
	 * @param argument
	 *            argument za provjeru
	 * @throws IllegalArgumentException
	 *             ako argument nije broj
	 */
	public static void checkIfNumber(final InstructionArgument argument) {
		if (!argument.isNumber()) {
			throw new IllegalArgumentException("Expected a number!");
		}
	}

	/**
	 * Provjerava da li je predani argument registar. Ukoliko nije baca se
	 * {@link IllegalArgumentException}.
	 * 
	 * @param argument
	 *            argument za provjeru
	 * @throws IllegalArgumentException
	 *             ako argument nije registar
	 */
	public static void checkIfRegister(final InstructionArgument argument) {
		if (!argument.isRegister()) {
			throw new IllegalArgumentException("Expected a register!");
		}
	}

	/**
	 * Provjerava da li je predani registar u dopuštenom rasponu. Dopušteni
	 * raspon je od [0, {@code limit}]. Ukoliko index nije u rasponu baca se
	 * {@link IndexOutOfBoundsException}.
	 * 
	 * @param index
	 *            indeks za provjeru
	 * @param limit
	 *            gornja granica
	 * @throws IndexOutOfBoundsException
	 *             ukoliko index nije u rasponu
	 */
	public static void checkIndexArgument(final int index, final int limit) {
		if (index < 0 || index >= limit) {
			throw new IndexOutOfBoundsException(index + " isn't in the valid range of [0, " + limit + ")");
		}
	}

	/**
	 * Provjeraca da li je broj predanih argumenata jednak zahtijevanom. Ukoliko
	 * nije baca se {@link IllegalArgumentException}.
	 * 
	 * @param numberOfArguments
	 *            broj predanih argumenata
	 * @param required
	 *            zahtijevani broj argumenata
	 * @param instruction
	 *            naziv instrukcije
	 * @throws IllegalArgumentException
	 *             ako je broj predanih argumenata različit od zahtijevanih
	 */
	public static void checkNumberOfArguments(final int numberOfArguments, final int required,
			final String instruction) {
		if (numberOfArguments != required) {
			throw new IllegalArgumentException(instruction + " instruction requires " + required + " arguments");
		}
	}

}

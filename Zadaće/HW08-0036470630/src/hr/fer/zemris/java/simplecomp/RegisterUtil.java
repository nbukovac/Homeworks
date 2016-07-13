package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Pomoćna klasa koja služi za određivanje vrste {@link Registers} objekta kako
 * bi bilo moguće odrediti na koji način treba raditi pojedina instrukcija.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class RegisterUtil {

	/**
	 * Vraća indeks registra koji se određuje iz {@code registerDescriptor}
	 * argumenta.
	 * 
	 * @param registerDescriptor
	 *            deskriptor registra
	 * @return indeks registra
	 */
	public static int getRegisterIndex(final int registerDescriptor) {
		return registerDescriptor & 0xFF;
	}

	/**
	 * Vraća offset koji se određuje iz {@code registerDescriptor} argumenta.
	 * 
	 * @param registerDescriptor
	 *            deskriptor registra
	 * @return offset
	 */
	public static int getRegisterOffset(final int registerDescriptor) {
		final int help = registerDescriptor >> 8;
		final short offset = (short) (help & 0xFFFF);
		return offset;
	}

	/**
	 * Određuje da li se koristi indirektno adresiranje. Ukoliko da vraća se
	 * {@code true}, inače {@code false}.
	 * 
	 * @param registerDescriptor
	 *            deskriptor registra
	 * @return {@code true} ukoliko se koristi indirektno adresiranje, inače
	 *         {@code false}
	 */
	public static boolean isIndirect(final int registerDescriptor) {
		final int value = registerDescriptor >> 24;
		return value == 1;
	}

}

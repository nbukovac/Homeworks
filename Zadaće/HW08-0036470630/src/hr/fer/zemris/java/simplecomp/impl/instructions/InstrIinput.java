package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja na određenu lokaciju sprema korisnikov unos cijeloga broja,
 * te ukoliko je unesen cijeli broj postavlja zastavicu {@code flag} na
 * {@code true}, inače na {@code false}.
 * 
 * iinput 200 &lt;- sprema korisnikov unos na adresu 200
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrIinput implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 1;

	/**
	 * Adresa za spremanje korisnikovog unosa
	 */
	private final int memoryAdress;

	/**
	 * Stvara se novi {@link InstrIinput} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 1, baca se
	 * {@link IllegalArgumentException}. Ukoliko preadani argument nije broj,
	 * također se baca {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Iinput instrukciju
	 * @throws IllegalArgumentException
	 *             ukoliko je broj predanih argumenata različit od 1 ili predani
	 *             argument nije broj
	 */
	public InstrIinput(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Iinput");
		HelperClass.checkIfNumber(arguments.get(0));

		memoryAdress = (int) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(final Computer computer) {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try {
			final String line = reader.readLine().trim();
			final int number = Integer.parseInt(line);
			computer.getRegisters().setFlag(true);
			computer.getMemory().setLocation(memoryAdress, number);
		} catch (final Exception e) {
			computer.getRegisters().setFlag(false);
		}

		return false;
	}

}

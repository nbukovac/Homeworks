package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja služi za manipulaciju s programskim brojilom kako bi se
 * ostvarile funkcionalnosti petlji. Za ispravan rad je potreban jedan argument,
 * adresa sljedeće instrukcije.
 * 
 * jump adress &lt;- pc = adress
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrJump implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 1;

	/**
	 * Adresa sljedeće instrukcije
	 */
	private final int newProgramCounter;

	/**
	 * Stvara se novi {@link InstrJump} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 1, baca se
	 * {@link IllegalArgumentException}. Ukoliko predani argument nije broj,
	 * također se baca {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Jump instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 1 ili argument nije broj
	 */
	public InstrJump(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Jump");
		HelperClass.checkIfNumber(arguments.get(0));

		newProgramCounter = (int) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(final Computer computer) {
		computer.getRegisters().setProgramCounter(newProgramCounter);

		return false;
	}

}

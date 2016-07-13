package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja zaustavlja rad procesora. Ne prima argumente.
 * 
 * halt &lt;- kraj rada procesora
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrHalt implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 0;

	/**
	 * Stvara se novi {@link InstrHalt} objekt s predanim argumentima. Ukoliko
	 * je broj argumenata različit od 0, baca se
	 * {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            lista argumenata za Halt instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 0
	 */
	public InstrHalt(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Halt");
	}

	@Override
	public boolean execute(final Computer computer) {
		return true;
	}

}

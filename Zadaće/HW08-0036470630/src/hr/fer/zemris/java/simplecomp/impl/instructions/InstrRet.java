package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija koja služi za vraćanje iz potprograma. Prilikom vraćanja se vraća
 * na instrukciju koja je slijedila poslije poziva potprograma. Za ispravan rad
 * se ne predaju argumenti.
 * 
 * ret &lt;- povratak iz potprograma
 * 
 * @author Nikola Nukovac
 * @version 1.0
 *
 */
public class InstrRet implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 0;

	/**
	 * Stvara se novi {@link InstrCall} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 0, baca se
	 * {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Ret instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 0
	 */
	public InstrRet(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Ret");
	}

	@Override
	public boolean execute(final Computer computer) {
		final int stackPointerAdress = (int) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX)
				+ 1;
		final int nextInstructionAdress = (int) computer.getMemory().getLocation(stackPointerAdress);

		computer.getRegisters().setProgramCounter(nextInstructionAdress);

		computer.getMemory().setLocation(stackPointerAdress, null);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, stackPointerAdress);

		return false;
	}

}

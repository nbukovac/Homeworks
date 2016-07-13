package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija koja poziva potprogram koji se nalazi na predanoj memorijskoj
 * adresi. Prilikom poziva na stog se sprema trenutna adresa pohranjena u
 * programskom brojilu kako bi se kasnije mogao nastaviti rad programa.
 * 
 * 
 * @author Nikola Bukovac
 *
 */
public class InstrCall implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 1;

	/**
	 * Adresa prve instrukcije potprograma
	 */
	private final int nextInstructionAdress;

	/**
	 * Stvara se novi {@link InstrCall} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 1, baca se
	 * {@link IllegalArgumentException}. Ukoliko je predani argument nešto što
	 * nije broj, baca se {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Call instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 1 ili predani argument
	 *             nije broj
	 * 
	 */
	public InstrCall(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Call");
		HelperClass.checkIfNumber(arguments.get(0));

		nextInstructionAdress = RegisterUtil.getRegisterIndex((int) arguments.get(0).getValue());
	}

	@Override
	public boolean execute(final Computer computer) {

		final int programCounter = computer.getRegisters().getProgramCounter();
		final int stackPointerAdress = (int) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);

		computer.getMemory().setLocation(stackPointerAdress, programCounter);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, stackPointerAdress - 1);

		computer.getRegisters().setProgramCounter(nextInstructionAdress);

		return false;
	}

}

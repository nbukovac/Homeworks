package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija koja radi sa stogom na način da stavlja na stoga podatak na vrh
 * stoga iz registra opće namjene te pomiče kazalo stoga na novi vrh stoga. Za
 * ispravan rad je potrebno predati jedan argument.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrPush implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 1;

	/**
	 * Indeks registra
	 */
	private final int registerIndex;

	/**
	 * Stvara se novi {@link InstrPush} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 1, baca se
	 * {@link IllegalArgumentException}. Ukoliko ijedan argument nije registar s
	 * direktnim adresiranjem, također se baca {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Push instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 1 ili postoji argument
	 *             koji nije registar s direktnim adresiranjem
	 */
	public InstrPush(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Push");
		HelperClass.checkDirectAdressingRegister(arguments.get(0));

		registerIndex = RegisterUtil.getRegisterIndex((int) arguments.get(0).getValue());
	}

	@Override
	public boolean execute(final Computer computer) {
		int stackPointerAdress = (int) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		final Object registerValue = computer.getRegisters().getRegisterValue(registerIndex);

		computer.getMemory().setLocation(stackPointerAdress, registerValue);

		stackPointerAdress--;
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, stackPointerAdress);

		return false;
	}

}

package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja provjerava da li su sadržaji dva registara međusobno jednaki
 * te ukoliko jesu postavlja zastavicu {@code flag} na {@code true}, ukoliko
 * nisu postavlja na {@code false}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrTestEquals implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 2;

	/**
	 * Indeks prvog registra
	 */
	private final int firstRegisterIndex;

	/**
	 * Indeks drugog registra
	 */
	private final int secondRegisterIndex;

	/**
	 * Stvara se novi {@link InstrTestEquals} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 2, baca se
	 * {@link IllegalArgumentException}. Ukoliko ijedan argument nije registar s
	 * direktnim adresiranjem, također se baca {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za TestEquals instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 2 ili postoji argument
	 *             koji nije registar s direktnim adresiranjem
	 */
	public InstrTestEquals(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "JumpIfTrue");
		HelperClass.checkDirectAdressingRegister(arguments.get(0));
		HelperClass.checkDirectAdressingRegister(arguments.get(1));

		firstRegisterIndex = RegisterUtil.getRegisterIndex((int) arguments.get(0).getValue());
		secondRegisterIndex = RegisterUtil.getRegisterIndex((int) arguments.get(1).getValue());
	}

	@Override
	public boolean execute(final Computer computer) {
		final Object value1 = computer.getRegisters().getRegisterValue(firstRegisterIndex);
		final Object value2 = computer.getRegisters().getRegisterValue(secondRegisterIndex);

		if (value1.equals(value2)) {
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}

		return false;
	}

}

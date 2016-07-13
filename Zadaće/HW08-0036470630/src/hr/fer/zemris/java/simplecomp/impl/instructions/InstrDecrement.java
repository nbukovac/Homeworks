package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja smanjuje trenutni sadržaj registra za 1 te rezultat sprema u
 * taj registar. Za ispravan rad je potreban 1 argument.
 * 
 * decrement r1 &lt;- r1 = r1 - 1
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrDecrement implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 1;

	/**
	 * Indeks registra
	 */
	private final int registerIndex;

	/**
	 * Stvara se novi {@link InstrDecrement} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 1, baca se
	 * {@link IllegalArgumentException}. Ukoliko argument nije registar s
	 * direktnim adresiranjem, također se baca {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Decrement instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 1 ili argument nije
	 *             registar s direktnim adresiranjem
	 */
	public InstrDecrement(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Decrement");
		HelperClass.checkDirectAdressingRegister(arguments.get(0));

		registerIndex = RegisterUtil.getRegisterIndex((int) arguments.get(0).getValue());
	}

	@Override
	public boolean execute(final Computer computer) {
		final Integer currentRegisterValue = (Integer) computer.getRegisters().getRegisterValue(registerIndex);
		computer.getRegisters().setRegisterValue(registerIndex, currentRegisterValue - 1);

		return false;
	}

}

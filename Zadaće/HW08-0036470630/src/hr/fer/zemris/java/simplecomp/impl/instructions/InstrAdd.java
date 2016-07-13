package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja zbraja sadržaje dva registra kao prirodne brojeve te
 * rezultat sprema u odredišni registar. Za ispravan rad su potrebna 3
 * argumenta.
 * 
 * add r0, r1, r2 &lt;- r0 = r1 + r2
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrAdd implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 3;

	/**
	 * Indeks registra u koji se sprema rezultat operacije
	 */
	private final int destinationRegisterIndex;

	/**
	 * Indeks registra koji se zbraja
	 */
	private final int firstRegisterIndex;

	/**
	 * Indeks registra koji se zbraja
	 */
	private final int secondRegisterIndex;

	/**
	 * Stvara se novi {@link InstrAdd} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od tri, baca se
	 * {@link IllegalArgumentException}. Ukoliko ijedan argument nije registar s
	 * direktnim adresiranjem, također se baca {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Add instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 3 ili postoji argument
	 *             koji nije registar s direktnim adresiranjem
	 */
	public InstrAdd(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Add");

		for (int i = 0; i < NUMBER_OF_ARGUMENTS; i++) {
			HelperClass.checkDirectAdressingRegister(arguments.get(i));
		}

		destinationRegisterIndex = RegisterUtil.getRegisterIndex((int) arguments.get(0).getValue());
		firstRegisterIndex = RegisterUtil.getRegisterIndex((int) arguments.get(1).getValue());
		secondRegisterIndex = RegisterUtil.getRegisterIndex((int) arguments.get(2).getValue());
	}

	@Override
	public boolean execute(final Computer computer) {
		final Integer value1 = (Integer) computer.getRegisters().getRegisterValue(firstRegisterIndex);
		final Integer value2 = (Integer) computer.getRegisters().getRegisterValue(secondRegisterIndex);

		computer.getRegisters().setRegisterValue(destinationRegisterIndex, value1 + value2);

		return false;
	}

}

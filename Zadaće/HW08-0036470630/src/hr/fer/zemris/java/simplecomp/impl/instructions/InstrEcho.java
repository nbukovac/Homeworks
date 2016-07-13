package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja ispisuje sadržaj registra ili memorije definiranog registrom
 * i offsetom. Za ispravan rad je potreban 1 argument.
 * 
 * <pre>
 * 	echo rx &lt;- ispis sadržaja registra rx
 * 	echo[rx + 5] &lt;- ispis sadržaja na memorijskoj adresi onoga što je u rx + 5
 * </pre>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrEcho implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 1;

	/**
	 * Indeks registra čiji se sadržaj ispisuje
	 */
	private final int registerIndex;

	/**
	 * Offset u memoriji u odnosu na sadržaj registra
	 */
	private int offset = 0;

	/**
	 * Zastavica koja označava da li se koristi offset
	 */
	private boolean offsetBool = false;

	/**
	 * Stvara se novi {@link InstrEcho} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 1, baca se
	 * {@link IllegalArgumentException}. Ukoliko predani argument nije registar,
	 * također se baca {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Echo instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 1 ili argument nije
	 *             registar
	 */
	public InstrEcho(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Echo");
		HelperClass.checkIfRegister(arguments.get(0));

		registerIndex = RegisterUtil.getRegisterIndex((int) arguments.get(0).getValue());

		if (RegisterUtil.isIndirect((int) arguments.get(0).getValue())) {
			offset = RegisterUtil.getRegisterOffset((int) arguments.get(0).getValue());
			offsetBool = true;
		}
	}

	@Override
	public boolean execute(final Computer computer) {
		if (offsetBool) {
			final int memoryAdress = (int) computer.getRegisters().getRegisterValue(registerIndex) + offset;
			System.out.print(computer.getMemory().getLocation(memoryAdress));

		} else {
			System.out.print(computer.getRegisters().getRegisterValue(registerIndex));

		}

		return false;
	}

}

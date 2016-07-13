package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja služi za spremanje vrijednosti iz memorije u registre opće
 * namjene. Za ispravan rad je potrebno predati dva argumenta.
 * 
 * load r1, 300 &lt;- u registar r1 se sprema podatak na memorijskoj adresi 300
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrLoad implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 2;

	/**
	 * Indeks registra za spremanje podatka
	 */
	private final int registerIndex;

	/**
	 * Memorijska adresa s koje se sprema podatak
	 */
	private final int memoryAdress;

	/**
	 * Stvara se novi {@link InstrLoad} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 2, baca se
	 * {@link IllegalArgumentException}. Ukoliko prvi predani argument nije
	 * registar s direktnim adresiranjem, također se baca
	 * {@link IllegalArgumentException}. Ukoliko drugi argument nije broj
	 * također se baca {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Load instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj predanih argumenata različit od dva, prvi nije
	 *             registar s direktnim adresiranjem, a drugi broj
	 */
	public InstrLoad(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Load");
		HelperClass.checkDirectAdressingRegister(arguments.get(0));
		HelperClass.checkIfNumber(arguments.get(1));

		registerIndex = RegisterUtil.getRegisterIndex((int) arguments.get(0).getValue());
		memoryAdress = (int) arguments.get(1).getValue();
	}

	@Override
	public boolean execute(final Computer computer) {
		final Object memoryValue = computer.getMemory().getLocation(memoryAdress);
		computer.getRegisters().setRegisterValue(registerIndex, memoryValue);

		return false;
	}

}

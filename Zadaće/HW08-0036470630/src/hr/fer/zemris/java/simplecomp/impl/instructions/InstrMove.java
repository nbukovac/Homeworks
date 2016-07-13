package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja služi za prijenos između dva registra, registra i memorijske
 * lokacije, postavljanje registra na neku vrijednost ili prijenos podataka
 * između memorijskih lokacija. Za ispravan rad je potrebno predati 2 argumenta.
 * 
 * Legalne uporabe move instrukcije su:
 * 
 * <pre>
 * 	move r1, r2
 * 	move [r1], r3
 * 	move [r2], 3
 * 	...
 * </pre>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class InstrMove implements Instruction {

	/**
	 * Broj očekivanih argumenata
	 */
	private static final int NUMBER_OF_ARGUMENTS = 2;

	/**
	 * Indeks odredišnog registra
	 */
	private final int destinationRegister;

	/**
	 * Offset u memoriji u odnosu na sadržaj odredišnog registra
	 */
	private int destinationOffset = 0;

	/**
	 * Zastavica koja označava da li se koristi offset
	 */
	private boolean destinationBool = false;

	/**
	 * Indeks izvorišnog registra
	 */
	private int sourceRegister;

	/**
	 * Offset u memoriji u odnosu na sadržaj izvorišnog registra
	 */
	private int sourceOffset = 0;

	/**
	 * Zastavica koja označava da li se koristi offset
	 */
	private boolean sourceBool = false;

	/**
	 * Broj unesen kao izvor
	 */
	private int sourceNumber;

	/**
	 * Zastavica koja označava da li se koristi broj
	 */
	private boolean number = false;

	/**
	 * Stvara se novi {@link InstrEcho} objekt kojemu se predaje lista
	 * argumenata. Ukoliko je broj predanih argumenata različit od 2, baca se
	 * {@link IllegalArgumentException}. Ukoliko prvi predani argument nije
	 * registar, također se baca {@link IllegalArgumentException}.
	 * 
	 * @param arguments
	 *            argumenti za Move instrukciju
	 * @throws IllegalArgumentException
	 *             ako je broj argumenata različit od 2 ili prvi predani
	 *             argument nije registar
	 */
	public InstrMove(final List<InstructionArgument> arguments) {
		HelperClass.checkNumberOfArguments(arguments.size(), NUMBER_OF_ARGUMENTS, "Move");

		final InstructionArgument firstArgument = arguments.get(0);
		final InstructionArgument secondArgument = arguments.get(1);

		HelperClass.checkIfRegister(firstArgument);

		if (!secondArgument.isRegister() && !secondArgument.isNumber()) {
			throw new IllegalArgumentException("Argument isn't valid");
		}

		destinationRegister = RegisterUtil.getRegisterIndex((int) firstArgument.getValue());

		if (RegisterUtil.isIndirect((int) firstArgument.getValue())) {
			destinationOffset = RegisterUtil.getRegisterOffset((int) firstArgument.getValue());
			destinationBool = true;
		}

		if (secondArgument.isRegister()) {
			sourceRegister = RegisterUtil.getRegisterIndex((int) secondArgument.getValue());

			if (RegisterUtil.isIndirect((int) secondArgument.getValue())) {
				sourceOffset = RegisterUtil.getRegisterOffset((int) secondArgument.getValue());
				sourceBool = true;
			}

		} else {
			sourceNumber = (int) secondArgument.getValue();
			number = true;
		}
	}

	/**
	 * Određuje se vrijednost podatka koji će se postaviti u registar ili
	 * memoriju
	 * 
	 * @param computer
	 *            {@link Computer} objekt
	 * @return određenu vrijednost podatka
	 */
	private Object determineValue(final Computer computer) {
		Object value;

		if (number) {
			value = sourceNumber;

		} else if (sourceBool) {
			final int sourceAdress = (int) computer.getRegisters().getRegisterValue(sourceRegister) + sourceOffset;
			value = computer.getMemory().getLocation(sourceAdress);

		} else {
			value = computer.getRegisters().getRegisterValue(sourceRegister);
		}

		return value;
	}

	@Override
	public boolean execute(final Computer computer) {
		final Object value = determineValue(computer);

		if (destinationBool) {
			final int destinationAdress = (int) computer.getRegisters().getRegisterValue(destinationRegister)
					+ destinationOffset;

			computer.getMemory().setLocation(destinationAdress, value);

		} else {
			computer.getRegisters().setRegisterValue(destinationRegister, value);

		}

		return false;
	}

}

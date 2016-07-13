package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Predstavlja registre procesora koji služe za izvršavanje instrukcija. Postoje
 * registri opće namjene, programsko brojilo i kazalo za stog.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class RegistersImpl implements Registers {

	/**
	 * Registri opće namjene
	 */
	private final Object[] registers;

	/**
	 * Programsko brojilo
	 */
	private int programCounter;

	/**
	 * Zastavica
	 */
	private boolean flag;

	/**
	 * Stvara novi objekt klase {@link RegistersImpl} s definiranim brojem
	 * registara. Ukoliko je predani broj registara &lt; 1 baca se
	 * {@link IllegalArgumentException}.
	 * 
	 * @param numberOfRegisters
	 * @throws IllegalArgumentException
	 *             ukoliko je predani broj registara &lt; 1
	 */
	public RegistersImpl(final int numberOfRegisters) {
		if (numberOfRegisters < 1) {
			throw new IllegalArgumentException("Number of registers can not be less than 1.");
		}
		registers = new Object[numberOfRegisters];
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public Object getRegisterValue(final int index) {
		HelperClass.checkIndexArgument(index, registers.length);
		return registers[index];
	}

	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	@Override
	public void setFlag(final boolean value) {
		flag = value;
	}

	@Override
	public void setProgramCounter(final int value) {
		programCounter = value;
	}

	@Override
	public void setRegisterValue(final int index, final Object value) {
		HelperClass.checkIndexArgument(index, registers.length);
		registers[index] = value;
	}

}

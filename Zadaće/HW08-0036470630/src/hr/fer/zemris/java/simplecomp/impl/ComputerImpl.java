package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Apstrakcija računala koja može izvršavati instrukcije, spremati podatke i
 * slično. Sastoji se od registara, koji su objekti klase {@link Registers} i
 * memorije, koja je objekt klase {@link Memory}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ComputerImpl implements Computer {

	/**
	 * Registri
	 */
	private final Registers registers;

	/**
	 * Memorija
	 */
	private final Memory memory;

	/**
	 * Stvara novi objekt klase {@link ComputerImpl} s definiranom veličinom
	 * memorije i brojem registara.
	 * 
	 * @param memorySize
	 *            veličina memorije
	 * @param numberOfRegisters
	 *            broj registara
	 */
	public ComputerImpl(final int memorySize, final int numberOfRegisters) {
		memory = new MemoryImpl(memorySize);
		registers = new RegistersImpl(numberOfRegisters);
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

}

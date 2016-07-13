package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * Izvršavatelj instrukciju kod procesora. Dohvaća instrukciju definiranu s
 * programskim brojilom, potom povećava programsko brojilo i izvršava naredbu.
 * Izvršavanje naredbi se događa sve dok se ne dođe do instrukcije koja prekida
 * rad procesora.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(final Computer computer) {
		computer.getRegisters().setProgramCounter(0);

		while (true) {
			int programCounter = computer.getRegisters().getProgramCounter();
			final Instruction instruction = (Instruction) computer.getMemory().getLocation(programCounter);
			programCounter++;
			computer.getRegisters().setProgramCounter(programCounter);
			if (instruction.execute(computer)) {
				break;
			}
		}

		return true;
	}

}

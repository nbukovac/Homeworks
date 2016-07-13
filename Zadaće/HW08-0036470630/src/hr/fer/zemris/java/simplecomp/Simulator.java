package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Program koji simulira rad jednog procesora. Programu je potrebno predati put
 * do datoteke koja sadrži asemblerski kod koji procesor može tumačiti.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Simulator {

	/**
	 * Pokreće program.
	 * 
	 * @param args
	 *            potrebno je predati put do datoteke
	 */
	public static void main(final String[] args) {
		if (args.length != 1) {
			System.err.println("Očekuje se sami jedan argument -> staza do datoteke s asemblerskim kodom");
			System.exit(1);
		}

		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		final Computer comp = new ComputerImpl(256, 16);

		// Stvori objekt koji zna stvarati primjerke instrukcija
		final InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions");

		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		try {
			ProgramParser.parse(args[0], comp, creator);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Stvori izvršnu jedinicu
		final ExecutionUnit exec = new ExecutionUnitImpl();

		// Izvedi program
		try {
			exec.go(comp);
		} catch (final Exception e) {
			System.err.println("Došlo je do pogreške prilikom izvršavanja datoteke " + args[0]);
			e.printStackTrace();
		}

	}

}

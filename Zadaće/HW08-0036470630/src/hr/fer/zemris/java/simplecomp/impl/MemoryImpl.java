package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.helper.HelperClass;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Predstavlja memoriju {@link Computer} objekt i služi za spremanje instrukcija
 * i podataka. Moguće je dohvaćati podatke i instrukcije sa memorijskih adresa,
 * kao i postavljati vrijednosti na memorijske adrese.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class MemoryImpl implements Memory {

	/**
	 * Memorija za čuvanje
	 */
	private final Object[] memory;

	/**
	 * Stvara novi objekt klase {@link MemoryImpl} s definiranom veličinom.
	 * Ukoliko je predana veličina &lt; 1 baca se
	 * {@link IllegalArgumentException}.
	 * 
	 * @param size
	 *            veličina memorije
	 * @throws IllegalArgumentException
	 *             ako je predana veličina &lt; 1
	 */
	public MemoryImpl(final int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Memory size can not be less than 1.");
		}

		memory = new Object[size];
	}

	@Override
	public Object getLocation(final int location) {
		HelperClass.checkIndexArgument(location, memory.length);

		return memory[location];
	}

	@Override
	public void setLocation(final int location, final Object value) {
		HelperClass.checkIndexArgument(location, memory.length);

		memory[location] = value;
	}

}

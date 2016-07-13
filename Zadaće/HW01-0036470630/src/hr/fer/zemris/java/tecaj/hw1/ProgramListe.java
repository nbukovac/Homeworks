package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program which uses lists for storing data
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ProgramListe {

	/**
	 * List element
	 */
	static class CvorListe{
		
		/**Reference to next list element */
		CvorListe sljedeci;
		
		/**Element storage */
		String podatak;
	}

	/**
	 * Main method for the ProgramListe program which demonstrates the usage of the 
	 * CvorListe class
	 * @param args not used
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;

		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");

		System.out.println("Ispisujem listu uz originalni poredak");
		ispisiListu(cvor);

		cvor = sortirajListu(cvor);

		System.out.println("Ispisujem listu nakon sortiranja");
		ispisiListu(cvor);

		int vel = velicinaListe(cvor);
		System.out.println("Lista sadr�i " + vel + " elemenata");
	}

	/**
	 * Counts the number of elements in a list
	 * @param cvor head of the list 
	 * @return number of elements
	 */
	private static int velicinaListe(CvorListe cvor) {
		int brojac = 0;

		while(cvor != null){
			brojac++;
			cvor = cvor.sljedeci;
		}

		return brojac;
	}

	/**
	 * Sorts a list in ascending order
	 * @param cvor head of the list
	 * @return head of the sorted list
	 */
	private static CvorListe sortirajListu(CvorListe cvor) {
		if(velicinaListe(cvor) < 2 ){
			return cvor;
		}

		CvorListe help = cvor;
		boolean sorted = false;

		while(!sorted){
			sorted = true;
			while(cvor != null){
				if(cvor.sljedeci != null && cvor.podatak.compareTo(cvor.sljedeci.podatak) > 0){
					String temp = cvor.podatak;
					cvor.podatak = cvor.sljedeci.podatak;
					cvor.sljedeci.podatak = temp;
					sorted = false;
				}
				cvor = cvor.sljedeci;
			}
		}

		return help;
	}

	/**
	 * Prints the contents of a list
	 * @param cvor head of the list
	 */
	private static void ispisiListu(CvorListe cvor) {
		if(cvor == null){
			System.out.println("Lista je prazna");
		}
		else{
			while(cvor != null){
				System.out.println(cvor.podatak);
				cvor = cvor.sljedeci;
			}
		}
		System.out.println();
	}

	/**
	 * Inserts a new element at the end of the list
	 * @param cvor head of the list
	 * @param string new data we want to add to the list
	 * @return head of the list
	 */
	private static CvorListe ubaci(CvorListe cvor, String string) {
		if(cvor == null){
			cvor = new CvorListe();
			cvor.podatak = string;
			cvor.sljedeci = null;
		}
		else{
			CvorListe help = cvor;  

			while(help.sljedeci != null){
				help = help.sljedeci;
			}

			CvorListe temp = new CvorListe();
			temp.podatak = string;
			temp.sljedeci = null;
			help.sljedeci = temp;
		}

		return cvor;
	}

}

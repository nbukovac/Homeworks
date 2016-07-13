package hr.fer.zemris.java.tecaj.hw1;

/**
 * Calculates the nth number of HofstadterQ sequence
 * @author Nikola Bukovac
 * @version 1.0
 */
public class HofstadterQ {

	/**
	 * Main method for the HofstadterQ program
	 * @param args command line argument that specifies what element should be calculated
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("Invalid number of arguments");
			return;
		}
		
		int nthNumber = Integer.parseInt(args[0].trim());
		
		if(nthNumber < 1){
			System.out.println("The number must be >= 1");
			return;
		}
		
		System.out.println(nthNumber + ". number of Hofstadter Q sequence is "
				+ hofstadterQ(nthNumber));
		
	}
	
	/**
	 * Calculates the nth number of the HofstadterQ sequence using memoization
	 * @param number the order number of the element we want
	 * @return nth number of the HofstadterQ sequence
	 */
	private static long hofstadterQ(int number){
		long[] array = new long[number + 1];
		array[1] = array[2] = 1;
		for(int i = 3; i <= number; i++){
			array[i] = array[(int) (i - array[i - 1])] + array[(int) (i - array[i - 2])];
		}
		return array[number];
	}
	
}

package hr.fer.zemris.java.tecaj.hw1;

/**
 * Decomposes a natural number to its prime numbers
 * @author Nikola Bukovac
 * @version 1.0
 */
public class NumberDecomposition {

	/**
	 * Main method for the Number decomposition program
	 * @param args command line argument that specifies the number we want decomposed
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("Invalid number of arguments");
			return;
		}
		
		int number = Integer.parseInt(args[0].trim());
		
		if(number <= 1){
			System.out.println("The specified number can't be decomposed to prime numbers");
			return;
		}
		
		System.out.println("You requested decomposition of the number " + number);
		
		for(int i = 2, count = 1; number / i > 0; i++){
			if(isPrimeNumber(i)){
				while(number % i == 0){
					System.out.println(count + ". " + i);
					number /= i;
					count++;
				}
			}
		}
	}

	/**
	 * Determines if a number is a prime number
	 * @param i the number we want to check
	 * @return true if the number is a prime number, false if not
	 */
	private static boolean isPrimeNumber(int i) {
		for(int j = 2; j <= Math.sqrt(i); j++){
			if(i % j == 0){
				return false;
			}
		}
		return true;
	}
}

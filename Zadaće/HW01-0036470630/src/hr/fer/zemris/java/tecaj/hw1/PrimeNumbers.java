package hr.fer.zemris.java.tecaj.hw1;

import java.lang.Math;

/**
 * Determines the first n prime numbers.
 * @author Nikola Bukovac
 * @version 1.0
 */
public class PrimeNumbers {

	/**
	 * Main method for the Prime numbers program
	 * @param args command line argument that specifies how many prime numbers we want 
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("Invalid number of arguments");
			return;
		}
		
		int firstNPrime = Integer.parseInt(args[0].trim());
		
		System.out.println("You requested the calculation of " + firstNPrime + " prime"
				+ " numbers.");
		
		for(int i = 2, count = 1; count <= firstNPrime; i++){
			if(isPrimeNumber(i)){
				System.out.println(count + ". " + i);
				count++;
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

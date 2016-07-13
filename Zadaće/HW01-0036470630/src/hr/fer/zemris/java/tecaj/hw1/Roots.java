package hr.fer.zemris.java.tecaj.hw1;

import java.lang.Math;
import java.text.DecimalFormat;

/**
 * Calculates the nth roots of a given complex number
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Roots {

	/**
	 * Main method for the program Roots
	 * @param args command line arguments which define the real, and imaginary part of a complex
	 * number and the desired root
	 */
	public static void main(String[] args) {
		if(args.length != 3){
			System.err.println("Invalid number of arguments");
			return;
		}
		
		double real = Double.parseDouble(args[0].trim());
		double imaginary = Double.parseDouble(args[1].trim());
		int root = Integer.parseInt(args[2].trim());
		
		if(root < 1){
			System.out.println("Root has to be >= 1");
			return;
		}
		
		double modul = Math.sqrt(real * real + imaginary * imaginary);
		double fi = Math.atan(imaginary / real);
		double modulRoot = Math.pow(modul, 1.0 / root);
		
		System.out.printf("The %d. roots of %.2f + %.2fi are:%n", root, real, imaginary);
		for(int i = 1; i <= root; i++){
			double realRoot = modulRoot * Math.cos((fi + (2 * Math.PI * (i - 1))) / root);
			double imaginaryRoot = modulRoot * Math.sin((fi + (2 * Math.PI * (i - 1))) / root);
			DecimalFormat format = new DecimalFormat(" +###.##; -###.##");
			System.out.println(i + ")" + format.format(realRoot) + format.format(imaginaryRoot)
					+ "i");
		}
	}

}

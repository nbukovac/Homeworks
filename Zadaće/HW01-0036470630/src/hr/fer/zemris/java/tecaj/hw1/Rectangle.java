package hr.fer.zemris.java.tecaj.hw1;

import java.io.*;

/**
 * Calculates the area and perimeter of a rectangle 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Rectangle {

	/**
	 * Main method for the Rectangle program
	 * @param args arguments from the console
	 * @throws IOException if there was a problem while reading from standard input
	 */
	public static void main(String[] args) throws IOException {
		double width = -1, height = -1;
		
		if(args.length > 0){
			if(args.length == 2){
				width = Double.parseDouble(args[0].trim());
				height = Double.parseDouble(args[1].trim());
				if(width < 0 || height < 0){
					System.err.println("At least one arguments is negative");
					return;
				}
				calculateAreaPerimeter(width, height);
				return;
			}
			else{
				System.err.println("Invalid number of arguments");
				return;
			}
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(width < 0){
			System.out.println("Please specify the rectangle width");
			width = readFromInput(reader, "width");
		}
		while(height < 0){
			System.out.println("Please specify the rectangle height");
			height = readFromInput(reader, "width");
		}
		
		calculateAreaPerimeter(width, height);
	}
	
	/**
	 * Reads data from the standard input
	 * @param reader tool used to read from the standard input
	 * @param mode specifies what is read 
	 * @return -1 if an error occurs, else returns specified number
	 * @throws IOException if there was a problem while reading from standard input
	 */
	private static double readFromInput(BufferedReader reader, String mode) throws IOException{
		String read = reader.readLine().trim();
		if(read == null || read.isEmpty()){
			System.out.println("No value was provided");
			return -1;
		}
		double number = Double.parseDouble(read);
		if(number < 0){
			System.out.println("The provided " + mode + " is negative");
		}
		else if (number == 0){
			System.out.println(mode + " can not be be 0");
			number = -1;
		}
		return number;
	}
	
	/**
	 * Calculates the area and the perimeter of the a rectangle
	 * @param width non negative value that determines rectangle width
	 * @param height non negative value that determines rectangle height
	 */
	private static void calculateAreaPerimeter(double width, double height) {
		System.out.println("You have specified a rectangle with a width of " + width
				+ " cm and a height of " + height + " cm");
		System.out.printf("The calculated area is %f cm^2, and the perimeter is %f cm",
				width * height, 2 * width + 2 * height);
	}

}

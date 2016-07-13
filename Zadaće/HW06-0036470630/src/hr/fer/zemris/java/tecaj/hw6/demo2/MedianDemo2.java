package hr.fer.zemris.java.tecaj.hw6.demo2;

/**
 * Program that demonstrates the usage of class {@link LikeMedian}.
 * 
 * @version 1.0
 *
 */
public class MedianDemo2 {

	/**
	 * Entry point of the program
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		final LikeMedian<String> likeMedian = new LikeMedian<String>();

		likeMedian.add("Joe");
		likeMedian.add("Jane");
		likeMedian.add("Adam");
		likeMedian.add("Zed");

		final String result = likeMedian.get().get();
		System.out.println(result); // Writes: Jane
	}

}

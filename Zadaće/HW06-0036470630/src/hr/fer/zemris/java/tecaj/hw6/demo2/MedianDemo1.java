package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * Program that demonstrates the usage of class {@link LikeMedian}.
 * 
 * @version 1.0
 *
 */
public class MedianDemo1 {

	/**
	 * Entry point of the program
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		final LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();

		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));

		final Optional<Integer> result = likeMedian.get();

		System.out.println(result.get());
		System.out.println();

		for (final Integer elem : likeMedian) {
			System.out.println(elem);
		}

	}

}

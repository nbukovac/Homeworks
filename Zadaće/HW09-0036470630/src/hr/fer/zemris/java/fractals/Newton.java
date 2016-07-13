package hr.fer.zemris.java.fractals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.complex.Complex;
import hr.fer.zemris.java.fractals.complex.ComplexPolynomial;
import hr.fer.zemris.java.fractals.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Program that calculates fractals based on the Newton-Raphson iteration and
 * shows the calculation as a image.
 * 
 * @see "http://www.chiark.greenend.org.uk/~sgtatham/newton/"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Newton {

	/**
	 * Class that implements {@link Callable} and represents a single job.
	 * 
	 * @author Nkola Bukovac
	 * @version 1.0
	 */
	public static class CalculationWork implements Callable<Void> {
		/** Minimum real value */
		private final double realMin;

		/** Maximum real value */
		private final double realMax;

		/** Minimum imaginary value */
		private final double imaginaryMin;

		/** Maximum imaginary value */
		private final double imaginaryMax;

		/** Width */
		private final int width;

		/** Height */
		private final int height;

		/** Starting y */
		private final int yMin;

		/** Ending y */
		private final int yMax;

		/** Number of iterations */
		private final int m;

		/** Picture data */
		private final short[] data;

		/** Observer */
		IFractalResultObserver observer;

		/** Request number */
		long requestNo;

		/**
		 * Constructs a new {@link CalculationWork} with the specified arguments
		 * 
		 * @param realMin
		 *            Minimum real value
		 * @param realMax
		 *            maximum real value
		 * @param imaginaryMin
		 *            Minimum imaginary value
		 * @param imaginaryMax
		 *            maximum real value
		 * @param width
		 *            width
		 * @param height
		 *            height
		 * @param yMin
		 *            starting y
		 * @param yMax
		 *            ending y
		 * @param m
		 *            number of iterations
		 * @param data
		 *            picture data
		 * @param observer
		 *            observer
		 * @param requestNo
		 *            request number
		 */
		public CalculationWork(final double realMin, final double realMax, final double imaginaryMin,
				final double imaginaryMax, final int width, final int height, final int yMin, final int yMax,
				final int m, final short[] data, final IFractalResultObserver observer, final long requestNo) {
			super();
			this.realMin = realMin;
			this.realMax = realMax;
			this.imaginaryMin = imaginaryMin;
			this.imaginaryMax = imaginaryMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.observer = observer;
			this.requestNo = requestNo;
		}

		@Override
		public Void call() throws Exception {
			Complex zn = null;
			Complex zn1 = null;

			for (int i = yMin; i <= yMax; i++) {
				for (int j = 0; j <= width; j++) {
					final double cre = 1.0 * j * (realMax - realMin) / (width - 1) + realMin;
					final double cim = 1.0 * (height - 1 - i) * (imaginaryMax - imaginaryMin) / (height - 1)
							+ imaginaryMin;
					final Complex c = new Complex(cre, cim);
					zn = c;
					int iter = 0;
					double module = 0;

					do {
						final Complex numerator = polynomial.apply(zn);
						final Complex denominator = derived.apply(zn);
						final Complex fraction = numerator.divide(denominator);
						zn1 = zn.sub(fraction);
						module = Math.abs(zn1.sub(zn).module());
						zn = zn1;
						iter++;
					} while (module > CONVERGENCE_THRESHOLD && iter < m);

					final int index = rootedPolynomial.indexOfClosestRootFor(zn1, ROOT_THRESHOLD);

					if (index == -1) {
						data[i * width + j] = 0;
					} else {
						data[i * width + j] = (short) (index + 1);
					}
				}
			}

			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);

			return null;
		}

	}

	/**
	 * Class that implements {@link ThreadFactory} and creates daemon
	 * {@link Thread}s.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	public static class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(final Runnable arg0) {
			final Thread thread = Executors.defaultThreadFactory().newThread(arg0);
			thread.setDaemon(true);
			return thread;
		}

	}

	/**
	 * Class that implements {@link IFractalProducer} and creates jobs. For more
	 * informations see {@link IFractalProducer}.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	public static class Producer implements IFractalProducer {

		/** {@link ExecutorService} object that contains all created threads **/
		ExecutorService pool;

		/**
		 * Constructs a new {@link Producer} object.
		 */
		public Producer() {
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new DaemonicThreadFactory());
		}

		@Override
		public void produce(final double reMin, final double reMax, final double imMin, final double imMax,
				final int width, final int height, final long requestNo, final IFractalResultObserver observer) {

			final int m = MAX_ITERATIONS;
			final short data[] = new short[width * height];
			final int numberOfJobs = Runtime.getRuntime().availableProcessors() * 8;
			final int linesPerJobs = height / numberOfJobs;

			final List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < numberOfJobs; i++) {
				final int yMin = i * linesPerJobs;
				int yMax = (i + 1) * linesPerJobs - 1;

				if (i == numberOfJobs - 1) {
					yMax = height - 1;
				}

				final CalculationWork work = new CalculationWork(reMin, reMax, imMin, imMax, width, height, yMin, yMax,
						m, data, observer, requestNo);
				results.add(pool.submit(work));
			}

			for (final Future<Void> posao : results) {
				try {
					posao.get();
				} catch (final Exception e) {
				}
			}

			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

	}

	/**
	 * {@link ComplexPolynomial} representation of the user entered roots
	 */
	private static ComplexPolynomial polynomial;

	/**
	 * Derived {@code polynomial}
	 */
	public static ComplexPolynomial derived;

	/**
	 * {@link ComplexRootedPolynomial} representation of the user entered roots
	 */
	private static ComplexRootedPolynomial rootedPolynomial;

	/**
	 * Convergence threshold
	 */
	private static final double CONVERGENCE_THRESHOLD = 0.001;

	/**
	 * Convergence threshold for roots
	 */
	private static final double ROOT_THRESHOLD = 0.002;

	/**
	 * Maximum number of iterations for calculation
	 */
	private static final int MAX_ITERATIONS = 16 * 16;

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final List<Complex> roots = new ArrayList<>();
		String line = "";
		int enteredRoots = 0;

		while (true) {
			try {
				System.out.print("Root " + (enteredRoots + 1) + " > ");
				line = reader.readLine();

				if (line == null || line.trim().toLowerCase().equals("done")) {
					break;
				}

				line = line.trim();

				if (line.isEmpty()) {
					continue;
				}

				try {
					roots.add(Complex.parse(line));
					enteredRoots++;
				} catch (final NumberFormatException e) {
					System.err.println("The provided input doens't contain a valid complex number -> " + line);
				}

			} catch (final IOException e) {
			}
		}

		if (enteredRoots < 2) {
			System.err.println("You entered and insufficient amount of roots");
			System.exit(-1);
		}

		rootedPolynomial = new ComplexRootedPolynomial(roots.toArray(new Complex[roots.size()]));
		polynomial = rootedPolynomial.toComplexPolynom();
		derived = polynomial.derive();

		FractalViewer.show(new Producer());
	}
}

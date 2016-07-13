package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * Class that provides binary operations for the {@link SmartScriptEngine}
 * class.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public final class BinaryOperations {

	/**
	 * {@link Map} with defined {@link BinaryOperations} used for calculations
	 * in {@link SmartScriptEngine}
	 */
	private static Map<String, BinaryOperator<Double>> operations;

	/**
	 * Returns the {@link BinaryOperator} for the specified {@code operator}
	 * argument. If the {@code operations} map isn't initialized, initializes
	 * it.
	 * 
	 * @param operator
	 *            binary operation symbol
	 * @return the requested {@link BinaryOperator}
	 */
	public static BinaryOperator<Double> getOperator(final String operator) {
		if (operations == null) {
			initialize();
		}

		return operations.get(operator);
	}

	/**
	 * Initializes {@code operations} map with all of the supported binary
	 * operations.
	 */
	private static void initialize() {
		operations = new HashMap<>();
		operations.put("+", (x, y) -> (x + y));
		operations.put("-", (x, y) -> (x - y));
		operations.put("*", (x, y) -> (x * y));
		operations.put("/", (x, y) -> (x / y));
	}
}

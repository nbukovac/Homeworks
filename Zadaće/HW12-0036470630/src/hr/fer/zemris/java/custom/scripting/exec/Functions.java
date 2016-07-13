package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;

/**
 * Class that provides function definition used for executing found
 * {@link ElementFunction} objects in {@link SmartScriptEngine}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public final class Functions {

	/**
	 * {@link Map} with defined {@link IFunction}s used for function processing
	 * in {@link SmartScriptEngine}
	 */
	private static Map<String, IFunction> functions;

	/**
	 * Returns the {@link IFunction} for the specified {@code function}
	 * argument. If the {@code functions} map isn't initialized, initializes it.
	 * 
	 * @param function
	 *            name of the function
	 * @return the requested {@link IFunction}
	 */
	public static IFunction getFunction(final String function) {
		if (functions == null) {
			initialize();
		}

		return functions.get(function);
	}

	/**
	 * Initializes {@code functions} map with all of the supported binary
	 * operations.
	 */
	private static void initialize() {
		functions = new HashMap<>();
		functions.put("sin", (stack, context) -> {
			final double value = (double) stack.pop().getValue();
			stack.push(new ValueWrapper(Math.sin(value % (Math.PI * 2))));
		});
		functions.put("decfmt", (stack, context) -> {
			final DecimalFormat format = new DecimalFormat(stack.pop().getValue().toString());
			stack.push(new ValueWrapper(format.format(stack.pop().getValue())));
		});
		functions.put("dup", (stack, context) -> {
			final ValueWrapper value = stack.pop();
			stack.push(value);
			stack.push(value);
		});
		functions.put("swap", (stack, context) -> {
			final ValueWrapper value1 = stack.pop();
			final ValueWrapper value2 = stack.pop();
			stack.push(value1);
			stack.push(value2);
		});
		functions.put("setMimeType", (stack, context) -> {
			context.setMimeType(stack.pop().getValue().toString());
		});
		functions.put("paramGet", (stack, context) -> {
			final ValueWrapper defaultValue = stack.pop();
			final String name = stack.pop().getValue().toString();
			final String value = context.getParameter(name);
			stack.push(value == null ? defaultValue : new ValueWrapper(value));
		});
		functions.put("pparamGet", (stack, context) -> {
			final ValueWrapper defaultValue = stack.pop();
			final String name = stack.pop().getValue().toString();
			final String value = context.getPersistentParameter(name);
			stack.push(value == null ? defaultValue : new ValueWrapper(value));
		});
		functions.put("pparamSet", (stack, context) -> {
			final String name = stack.pop().getValue().toString();
			final String value = stack.pop().getValue().toString();
			context.setPersistentParameter(name, value);
		});
		functions.put("pparamDel", (stack, context) -> {
			context.removePersistentParameter(stack.pop().getValue().toString());
		});
		functions.put("tparamGet", (stack, context) -> {
			final ValueWrapper defaultValue = stack.pop();
			final String name = stack.pop().getValue().toString();
			final String value = context.getTemporaryParameter(name);
			stack.push(value == null ? defaultValue : new ValueWrapper(value));
		});
		functions.put("tparamSet", (stack, context) -> {
			final String name = stack.pop().getValue().toString();
			final String value = stack.pop().getValue().toString();
			context.setTemporaryParameter(name, value);
		});
		functions.put("tparamDel", (stack, context) -> {
			context.removeTemporaryParameter(stack.pop().getValue().toString());
		});
	}
}

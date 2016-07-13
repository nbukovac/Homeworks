package hr.fer.zemris.java.custom.scripting.exec;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Interface used to define a Strategy used for executing found functions in the
 * {@link SmartScriptEngine}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IFunction {

	/**
	 * Does the specified function as it is specified by a concrete
	 * implementation.
	 * 
	 * @param stack
	 *            stack structure containing stored values
	 * @param context
	 *            {@link RequestContext} used to customize headers
	 */
	void apply(Stack<ValueWrapper> stack, RequestContext context);
}

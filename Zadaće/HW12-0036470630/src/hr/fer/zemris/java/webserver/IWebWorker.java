package hr.fer.zemris.java.webserver;

/**
 * Interface used to define all necessary methods that are required for creation
 * of web workers.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IWebWorker {

	/**
	 * Processes the specified {@link RequestContext} and creates the necessary
	 * header definitions and content creation. Everything is determined by the
	 * concrete implementation of a {@link IWebWorker}.
	 * 
	 * @param context
	 *            {@link RequestContext} we are using for header creation and
	 *            content writing
	 */
	public void processRequest(RequestContext context);
}

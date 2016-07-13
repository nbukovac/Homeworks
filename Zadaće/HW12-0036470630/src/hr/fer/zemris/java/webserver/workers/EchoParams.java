package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that implements {@link IWebWorker} and writes a HTML page with the
 * parameters specified in {@link RequestContext} in a table to the
 * {@link RequestContext}s output stream.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(final RequestContext context) {
		context.setMimeType("text/html");
		final Set<String> parameterNames = context.getParameterNames();

		try {
			context.write("<html><head><meta charset=\"utf-8\"/></head><body>");
			context.write("<table style={border=1px;}><tr><th>Name</th><th>Value</th></tr>");

			for (final String name : parameterNames) {
				context.write("<tr><td>" + name + "</td><td>" + context.getParameter(name) + "</td></tr>");
			}

			context.write("</table></body></html>");

		} catch (final IOException e) {
			System.out.println(
					"Writing to the output stream has been interrupted because of an exception " + e.getMessage());
		}
	}

}

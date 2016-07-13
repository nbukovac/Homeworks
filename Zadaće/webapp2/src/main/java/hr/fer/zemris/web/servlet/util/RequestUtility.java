package hr.fer.zemris.web.servlet.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class containing methods used to process requests from the client.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class RequestUtility {

	/**
	 * Creates a error message that is shown to the user if something went wrong
	 * during request processing.
	 * 
	 * @param request
	 *            {@link HttpServletRequest} clients request
	 * @param message
	 *            error message
	 * @param response
	 *            {@link HttpServletResponse} response
	 * @return true
	 * @throws ServletException
	 *             if anything happens that is determined in the
	 *             {@link ServletException} documentation
	 * @throws IOException
	 *             if anything happens that is determined in the
	 *             {@link IOException} documentation
	 */
	public static boolean createErrorMessage(final HttpServletRequest request, final String message,
			final HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("errorMessage", message);
		request.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(request, response);

		return true;
	}
}

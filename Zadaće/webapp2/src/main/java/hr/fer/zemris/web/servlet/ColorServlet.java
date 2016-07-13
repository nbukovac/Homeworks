package hr.fer.zemris.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that extends {@link HttpServlet} and enables the user to change the
 * background color of all pages contained in this web application.
 * <p>
 * It is possible to select any of the listed colors.
 * </p>
 * <ul>
 * <li>White</li>
 * <li>Red</li>
 * <li>Green</li>
 * <li>Cyan</li>
 * </ul>
 * White is the default color.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebServlet(name = "setcolor", urlPatterns = { "/setcolor" })
public class ColorServlet extends HttpServlet {

	/** Serial version uid */
	private static final long serialVersionUID = -8984356220824200680L;

	/** Picked background color attribute name */
	private static final String COLOR_PARAMETER = "pickedBgCol";

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final Map<String, String[]> parameters = request.getParameterMap();
		final String[] colors = parameters.get(COLOR_PARAMETER);

		if (colors != null && colors.length == 1) {
			final HttpSession session = request.getSession();
			session.setAttribute(COLOR_PARAMETER, colors[0]);
		}

		request.getRequestDispatcher("/colors.jsp").forward(request, response);
	}

}

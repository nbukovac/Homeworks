package hr.fer.zemris.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.web.servlet.definitions.TrigPair;
import hr.fer.zemris.web.servlet.util.RequestUtility;

/**
 * Class that extends {@link HttpServlet} and generates a table of sine and
 * cosine values for every integer degree in the specified range. The range is
 * specified by parameters passed in the URL. The default range is from 0 to
 * 360.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebServlet(name = "trigonometric", urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {

	/** Serial version uid */
	private static final long serialVersionUID = 5500025937286035006L;

	/** First parameter attribute name */
	private static final String FIRST_PARAMETER = "a";

	/** Second parameter attribute name */
	private static final String SECOND_PARAMETER = "b";

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final Map<String, String[]> parameters = request.getParameterMap();
		final String[] aParameters = parameters.get(FIRST_PARAMETER);
		final String[] bParameters = parameters.get(SECOND_PARAMETER);

		int a = 0;
		int b = 360;

		try {
			if (aParameters != null && aParameters.length == 1) {
				a = Integer.parseInt(aParameters[0]);
			}

			if (bParameters != null && bParameters.length == 1) {
				b = Integer.parseInt(bParameters[0]);
			}

		} catch (final NumberFormatException e) {
			RequestUtility.createErrorMessage(request, "A parameter wasn't a integer value.", response);
			return;
		}

		if (a > b) {
			final int tmp = b;
			b = a;
			a = tmp;
		}

		final List<TrigPair> pairs = new ArrayList<>();

		for (int i = a; i <= b; i++) {
			pairs.add(new TrigPair(Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i))));
		}

		request.setAttribute("a", a);
		request.setAttribute("pairs", pairs);

		request.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(request, response);
	}

}

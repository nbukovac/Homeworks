package hr.fer.zemris.web.servlet.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.web.servlet.voting.util.Utility;

/**
 * Class that extends {@link HttpServlet} and provides a list of bands the user
 * can vote for.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebServlet(name = "glasanje", urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	/** Serial version uid */
	private static final long serialVersionUID = 3666481638027446585L;

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String fileName = request.getServletContext().getRealPath("WEB-INF/voting/glasanje-definicija.txt");

		request.setAttribute("bands", Utility.extractBandInfo(fileName));
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(request, response);
	}
}

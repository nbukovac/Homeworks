package hr.fer.zemris.web.servlet.voting;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.web.servlet.definitions.ResultsInfo;
import hr.fer.zemris.web.servlet.voting.util.Utility;

/**
 * Class that extends {@link HttpServlet} and shows to the user the current
 * standings in voting.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebServlet(name = "glasanjeRezultati", urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** Serial version uid */
	private static final long serialVersionUID = 6630521001229588860L;

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String bandFile = request.getServletContext().getRealPath("WEB-INF/voting/glasanje-rezultati.txt");
		final String resultFile = request.getServletContext().getRealPath("WEB-INF/voting/glasanje-definicija.txt");

		final List<ResultsInfo> results = Utility.createResults(bandFile, resultFile);
		final Optional<ResultsInfo> top = results.stream()
				.max((t1, t2) -> t1.getNumberOfVotes() - t2.getNumberOfVotes());

		request.setAttribute("top", top.get());
		request.setAttribute("results", results);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(request, response);
	}
}

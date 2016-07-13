package hr.fer.zemris.web.servlet.voting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.web.servlet.definitions.BandInfo;
import hr.fer.zemris.web.servlet.definitions.VotingInfo;
import hr.fer.zemris.web.servlet.voting.util.Utility;

/**
 * Class that extends {@link HttpServlet} and increments the vote counter for
 * the band the user voted. If there wasn't any voting information it is created
 * now.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebServlet(name = "glasanjeGlasaj", urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	/** Serial version uid */
	private static final long serialVersionUID = 8371814911836634770L;

	/**
	 * Creates a map of {@link VotingInfo} if there wasn't a file containing
	 * voting information.
	 * 
	 * @param request
	 *            {@link HttpServletRequest} client request
	 * @return map of {@link VotingInfo}
	 * @throws IOException
	 *             if anything happens that is specified in the
	 *             {@link IOException} documentation
	 */
	private Map<Integer, VotingInfo> createResults(final HttpServletRequest request) throws IOException {
		final String bandFile = request.getServletContext().getRealPath("WEB-INF/voting/glasanje-definicija.txt");
		final List<BandInfo> bands = Utility.extractBandInfo(bandFile);
		final Map<Integer, VotingInfo> votingInfo = new TreeMap<>();

		for (final BandInfo bandInfo : bands) {
			votingInfo.put(bandInfo.getId(), new VotingInfo(bandInfo.getId(), 0));
		}

		return votingInfo;
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String[] votes = request.getParameterValues("id");
		int voted = 0;

		if (votes != null && votes.length == 1) {
			voted = Integer.parseInt(votes[0]);
		}

		final String fileName = request.getServletContext().getRealPath("WEB-INF/voting/glasanje-rezultati.txt");
		Map<Integer, VotingInfo> votingResults = null;

		if (!Files.exists(Paths.get(fileName))) {
			votingResults = createResults(request);
		} else {
			votingResults = Utility.extractVotingInfo(fileName);
		}

		final VotingInfo votingInfo = votingResults.get(voted);

		if (votingInfo != null) {
			votingInfo.incrementVote();
		}

		final BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));

		for (final Entry<Integer, VotingInfo> entry : votingResults.entrySet()) {
			writer.write(entry.getKey() + "\t" + entry.getValue().getVotes() + "\n");
		}

		writer.flush();
		writer.close();

		response.sendRedirect(request.getContextPath() + "/glasanje-rezultati");
	}
}

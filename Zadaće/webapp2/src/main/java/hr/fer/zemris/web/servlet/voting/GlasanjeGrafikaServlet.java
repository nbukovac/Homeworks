package hr.fer.zemris.web.servlet.voting;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.web.servlet.definitions.ResultsInfo;
import hr.fer.zemris.web.servlet.voting.util.Utility;

/**
 * Class that extends {@link HttpServlet} and creates a graphical representation
 * of the current voting results as a pie chart.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebServlet(name = "glasanjeGrafika", urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** Serial version uid */
	private static final long serialVersionUID = 4601368824624695352L;

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String bandFile = request.getServletContext().getRealPath("WEB-INF/voting/glasanje-rezultati.txt");
		final String resultFile = request.getServletContext().getRealPath("WEB-INF/voting/glasanje-definicija.txt");

		final List<ResultsInfo> results = Utility.createResults(bandFile, resultFile);

		response.setContentType("image/png");
		final ServletOutputStream outputStream = response.getOutputStream();

		final DefaultPieDataset dataset = new DefaultPieDataset();
		for (final ResultsInfo result : results) {
			dataset.setValue(result.getBandName(), result.getNumberOfVotes());
		}

		final JFreeChart chart = ChartFactory.createPieChart("Votes", dataset);
		final BufferedImage image = chart.createBufferedImage(500, 500);
		ImageIO.write(image, "png", outputStream);
		outputStream.flush();
		outputStream.close();
	}
}

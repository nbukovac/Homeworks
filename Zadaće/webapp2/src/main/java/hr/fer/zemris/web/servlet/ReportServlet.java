package hr.fer.zemris.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

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

/**
 * Class that extends {@link HttpServlet} and generates a PNG image containing a
 * pie chart with preset data to demonstrate in which ratio operating systems
 * are used.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebServlet(name = "reportImage", urlPatterns = { "/reportImage" })
public class ReportServlet extends HttpServlet {

	/** Serial version uid */
	private static final long serialVersionUID = -8178876164723335490L;

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("image/png");
		final ServletOutputStream outputStream = response.getOutputStream();

		final DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 29);
		dataset.setValue("windows", 51);
		dataset.setValue("Mac", 20);

		final JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset);
		final BufferedImage image = chart.createBufferedImage(500, 500);
		ImageIO.write(image, "png", outputStream);
		outputStream.flush();
		outputStream.close();
	}
}

package hr.fer.zemris.web.servlet.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.web.servlet.definitions.ResultsInfo;
import hr.fer.zemris.web.servlet.voting.util.Utility;

/**
 * Class that extends {@link HttpServlet} and creates a XLS workbook containing
 * voting results.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebServlet(name = "glasanjeXLS", urlPatterns = { "/glasanje-xls" })
public class GlasanjeXLSServlet extends HttpServlet {

	/** Serial version uid */
	private static final long serialVersionUID = -3653215556389444004L;

	/**
	 * Creates the {@link HSSFWorkbook} based upon the specified
	 * {@code createResults} parameter. First row is the header and every next
	 * row contains the bands name in the first column and in the second number
	 * of collected votes.
	 * 
	 * @param createResults
	 *            list of {@link ResultsInfo}
	 * @return created {@link HSSFWorkbook}
	 */
	private HSSFWorkbook createWorkbook(final List<ResultsInfo> createResults) {
		final HSSFWorkbook workbook = new HSSFWorkbook();
		final HSSFSheet sheet = workbook.createSheet(String.format("Results"));
		final HSSFRow headerRow = sheet.createRow(0);

		HSSFCell cell = headerRow.createCell(0);
		cell.setCellValue("Band");

		cell = headerRow.createCell(1);
		cell.setCellValue("Votes");

		int i = 1;
		for (final ResultsInfo resultsInfo : createResults) {
			final HSSFRow row = sheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue(resultsInfo.getBandName());
			cell = row.createCell(1);
			cell.setCellValue(resultsInfo.getNumberOfVotes());

			i++;
		}

		return workbook;
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String bandFile = request.getServletContext().getRealPath("WEB-INF/voting/glasanje-rezultati.txt");
		final String resultFile = request.getServletContext().getRealPath("WEB-INF/voting/glasanje-definicija.txt");

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=Results.xls");
		final HSSFWorkbook workbook = createWorkbook(Utility.createResults(bandFile, resultFile));
		workbook.write(response.getOutputStream());
		workbook.close();
	}
}

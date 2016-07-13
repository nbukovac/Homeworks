package hr.fer.zemris.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.web.servlet.util.RequestUtility;

/**
 * Class that extends {@link HttpServlet} and creates a XLS workbook that is
 * defined by parameters passed in the request URL. First parameter is the start
 * of range, second is the end of the range and the last one is the number of
 * sheets in the workbook. The range parameters have to be in the range of
 * [-100, 100], and the number of sheets parameter has to be in range of [1,5].
 * <p>
 * First row in every sheet is the header and every next row contains a number
 * in the specified range in the first column and in the second its i-th power
 * where i is determined by the number of sheet we are currently located.
 * </p>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebServlet(name = "powers", urlPatterns = { "/powers" })
public class XLSServlet extends HttpServlet {

	/** Serial version uid */
	private static final long serialVersionUID = -3382177958692521991L;

	/** Minimum value for a range parameter */
	private static final int MIN_RANGE = -100;
	/** Maximum value for a range parameter */
	private static final int MAX_RANGE = 100;
	/** Minimum value for number of sheets parameter */
	private static final int MIN_SHEETS = 1;
	/** Maximum value for number of sheets parameter */
	private static final int MAX_SHEETS = 5;

	/**
	 * Creates the {@link HSSFWorkbook} as it is defined by the specified
	 * parameters. First row is the header and every next row contains a number
	 * in the specified range in the first column and in the second its i-th
	 * power where i is determined by the number of sheet we are currently
	 * located.
	 * 
	 * @param from
	 *            start of range
	 * @param to
	 *            end of range
	 * @param numberOfSheets
	 *            number of sheets
	 * @return created {@link HSSFWorkbook}
	 */
	private HSSFWorkbook createWorkbook(final int from, final int to, final int numberOfSheets) {
		final HSSFWorkbook workbook = new HSSFWorkbook();

		for (int i = 0; i < numberOfSheets; i++) {
			final HSSFSheet sheet = workbook.createSheet(String.format("Sheet %d", i + 1));
			final HSSFRow headerRow = sheet.createRow(0);

			HSSFCell cell = headerRow.createCell(0);
			cell.setCellValue("Number value");

			cell = headerRow.createCell(1);
			cell.setCellValue("Power");

			for (int j = from, rowIndex = 1; j <= to; j++, rowIndex++) {
				final HSSFRow row = sheet.createRow(rowIndex);
				cell = row.createCell(0);
				cell.setCellValue(j);
				cell = row.createCell(1);
				cell.setCellValue(Math.pow(j, i + 1));
			}
		}

		return workbook;
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String[] aParameters = request.getParameterValues("a");
		final String[] bParameters = request.getParameterValues("b");
		final String[] nParameters = request.getParameterValues("n");

		int from = 0;
		int to = 0;
		int numberOfSheets = 0;
		boolean error = false;

		try {
			if (aParameters != null && aParameters.length == 1) {
				from = Integer.parseInt(aParameters[0]);

				if (from < MIN_RANGE || from > MAX_RANGE) {
					error = RequestUtility.createErrorMessage(request, "Start number has to be in range of [-100, 100]",
							response);
				}
			} else {
				error = RequestUtility.createErrorMessage(request, "Start number wasn't provided", response);
			}

			if (!error && bParameters != null && bParameters.length == 1) {
				to = Integer.parseInt(bParameters[0]);

				if (to < MIN_RANGE || to > MAX_RANGE) {
					error = RequestUtility.createErrorMessage(request, "End number has to be in range of [-100, 100]",
							response);
				}
			} else if (!error) {
				error = RequestUtility.createErrorMessage(request, "End number wasn't provided", response);
			}

			if (!error && nParameters != null && nParameters.length == 1) {
				numberOfSheets = Integer.parseInt(nParameters[0]);

				if (numberOfSheets < MIN_SHEETS || numberOfSheets > MAX_SHEETS) {
					error = RequestUtility.createErrorMessage(request, "Number of sheets has to be in range of [1, 5]",
							response);
				}
			} else if (!error) {
				error = RequestUtility.createErrorMessage(request, "Number of sheets wasn't provided", response);
			}
		} catch (final NumberFormatException e) {
			error = RequestUtility.createErrorMessage(request, "One of the provided parameters wasn't a integer",
					response);
		} finally {
			if (error) {
				return;
			}
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=Results.xls");
		final HSSFWorkbook workbook = createWorkbook(from, to, numberOfSheets);
		workbook.write(response.getOutputStream());
		workbook.close();
	}
}

package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program used to test header and content creation by setting custom options
 * for the {@link RequestContext} object.
 * 
 * @author Nikola Bukovac
 *
 */
public class DemoRequestContext {

	/**
	 * Creates a new {@link RequestContext} and requests to write a new message
	 * with the specified header.
	 * 
	 * @param filePath
	 *            path to the specified file
	 * @param encoding
	 *            desired character encoding
	 * @throws IOException
	 *             if an error occurs while writing to the output stream,
	 *             specified file is not found or stream closing fails
	 */
	private static void demo1(final String filePath, final String encoding) throws IOException {
		final OutputStream os = Files.newOutputStream(Paths.get(filePath));
		final RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}

	/**
	 * Creates a new {@link RequestContext} and requests to write a new message
	 * with the specified header.
	 * 
	 * @param filePath
	 *            path to the specified file
	 * @param encoding
	 *            desired character encoding
	 * @throws IOException
	 *             if an error occurs while writing to the output stream,
	 *             specified file is not found or stream closing fails
	 */
	private static void demo2(final String filePath, final String encoding) throws IOException {
		final OutputStream os = Files.newOutputStream(Paths.get(filePath));
		final RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            not used
	 * @throws IOException
	 *             if an error occurs while writing to the output stream,
	 *             specified file is not found or stream closing fails
	 */
	public static void main(final String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}
}
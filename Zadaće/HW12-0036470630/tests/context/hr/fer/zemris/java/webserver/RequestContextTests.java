package hr.fer.zemris.java.webserver;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

@SuppressWarnings("javadoc")
public class RequestContextTests {

	private List<RCCookie> cookies;
	private Map<String, String> parameters;
	private Map<String, String> persistent;
	private Map<String, String> temporary;

	@Before
	public void init() {
		cookies = new ArrayList<>();
		parameters = new HashMap<>();
		persistent = new HashMap<>();
		temporary = new HashMap<>();

		cookies.add(new RCCookie("burek", "sir", 200, null, null));
		cookies.add(new RCCookie("krafna", "pekmez", null, "localhost", "/"));

		parameters.put("štrudla", "jabuka");
		persistent.put("lignje", "pečene");
		temporary.put("fibonacci", "13");

	}

	@Test
	public void testAddPersistentParameter() {
		final RequestContext context = new RequestContext(System.out, null, null, cookies);
		context.setPersistentParameter("krafna", "čokolada");

		assertEquals("čokolada", context.getPersistentParameter("krafna"));
	}

	@Test(expected = RuntimeException.class)
	public void testChangeHeaderAfterCreation() {
		final RequestContext context = new RequestContext(System.out, null, null, cookies);
		context.generateHeader();
		context.setEncoding("ISO_8859_1");
	}

	@Test
	public void testGenerateHeader1() {
		final RequestContext context = new RequestContext(System.out, parameters, persistent, null);
		context.setMimeType("image/png");
		final String generated = context.generateHeader();
		final String expected = "HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n";

		assertEquals(generated, expected);
	}

	@Test
	public void testGenerateHeader2() {
		final RequestContext context = new RequestContext(System.out, parameters, persistent, null);
		context.setMimeType("text/plain");
		context.setEncoding("ISO_8859_1");
		context.setStatusCode(403);
		final String generated = context.generateHeader();
		final String expected = "HTTP/1.1 403 OK\r\nContent-Type: text/plain; charset=ISO_8859_1\r\n\r\n";

		assertEquals(generated, expected);
	}

	@Test
	public void testGenerateHeader3() {
		final RequestContext context = new RequestContext(System.out, null, null, cookies);
		context.setMimeType("text/html");
		context.setStatusText("Burek");
		context.setStatusCode(403);
		final String generated = context.generateHeader();
		final String expected = "HTTP/1.1 403 Burek\r\nContent-Type: text/html; charset=UTF-8\r\n"
				+ "Set-Cookie: burek=\"sir\"; Max-Age=200\r\nSet-Cookie: krafna=\"pekmez\"; Domain=localhost; Path=/"
				+ "\r\n\r\n";

		assertEquals(generated, expected);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullOutputStream() {
		new RequestContext(null, null, null, cookies);
	}
}

package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.java.webserver.util.Utility;

/**
 * Class that contains information about HTTP headers. Used to create a HTTP
 * header and to write content to the output stream. Allows usage of a cookie
 * mechanism used for creating sessions with the client.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class RequestContext {

	/**
	 * Class that represents a cookie and is used for remembering information
	 * about the user that is using the web application.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	public static class RCCookie {

		/** Cookie parameter name */
		private final String name;

		/** Cookie parameter value */
		private final String value;

		/** Domain in which the cookie is used */
		private final String domain;

		/** Path for which the cookie is valid */
		private final String path;

		/** Maximum age the cookie is valid */
		private final Integer maxAge;

		/**
		 * Constructs a new {@link RCCookie} with the specified values.
		 * 
		 * @param name
		 *            parameter name
		 * @param value
		 *            parameter value
		 * @param maxAge
		 *            maximum age
		 * @param domain
		 *            domain in which the cookie is used
		 * @param path
		 *            path for which the cookie is valid
		 */
		public RCCookie(final String name, final String value, final Integer maxAge, final String domain,
				final String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Returns the domain in which the cookie is used.
		 * 
		 * @return cookie domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns the maximum age for which the cookie is valid.
		 * 
		 * @return maximum age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Returns parameter name.
		 * 
		 * @return parameter name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns the path in which the cookie is valid.
		 * 
		 * @return path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Returns the parameter value.
		 * 
		 * @return parameter value.
		 */
		public String getValue() {
			return value;
		}
	}

	/** Default character encoding */
	public static final String DEFAULT_ENCODING = "UTF-8";

	/** Default status code */
	public static final int DEFAULT_STATUS_CODE = 200;

	/** Default status text */
	public static final String DEFAULT_STATUS_TEXT = "OK";

	/** Default mime type */
	public static final String DEFAULT_MIME_TYPE = "text/html";

	/** Newline delimiter */
	private static final String NEWLINE = "\r\n";

	// member variables

	/** {@link OutputStream} to which we write */
	private final OutputStream outputStream;

	/** {@link Character} used for character encoding */
	private Charset charset;

	/** Type of character encoding */
	private String encoding;

	/** Status code */
	private int statusCode;

	/** Status text */
	private String statusText;

	/** Mime type */
	private String mimeType;

	/** Parameters parsed from the URL query */
	private Map<String, String> parameters;

	/** Temporary parameters that are used for local calculation */
	private Map<String, String> temporaryParameters;

	/** Parameters connected with a certain session */
	private Map<String, String> persistentParameters;

	/** Cookies that are sent to the client */
	private List<RCCookie> outputCookies;

	/** Flag that determine if the header has been generated */
	private boolean headerGenerated;

	/**
	 * Constructs a new {@link RequestContext} with the specified values.
	 * 
	 * @param outputStream
	 *            output stream where we are writing
	 * @param parameters
	 *            parameters from URL query
	 * @param persistentParameters
	 *            cookie parameters
	 * @param outputCookies
	 *            cookies for header
	 */
	public RequestContext(final OutputStream outputStream, final Map<String, String> parameters,
			final Map<String, String> persistentParameters, final List<RCCookie> outputCookies) {
		super();

		Utility.checkIfNull(outputStream, "Parameter outputStream can't be null");
		this.outputStream = outputStream;

		if (Utility.checkIfNullableEmpty(parameters)) {
			this.parameters = Collections.unmodifiableMap(new HashMap<String, String>());
		} else {
			this.parameters = Collections.unmodifiableMap(parameters);
		}

		if (Utility.checkIfNullableEmpty(persistentParameters)) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = new HashMap<>(persistentParameters);
		}

		if (Utility.checkIfNullableEmpty(outputCookies)) {
			this.outputCookies = new ArrayList<>();
		} else {
			this.outputCookies = outputCookies;
		}

		this.statusCode = DEFAULT_STATUS_CODE;
		this.mimeType = DEFAULT_MIME_TYPE;
		this.statusText = DEFAULT_STATUS_TEXT;
		this.encoding = DEFAULT_ENCODING;
		this.temporaryParameters = new HashMap<>();
	}

	/**
	 * Adds a new {@link RCCookie} to the {@code outputCookies} list.
	 * 
	 * @param rcCookie
	 *            {@link RCCookie} to add to the list
	 */
	public void addRCCookie(final RCCookie rcCookie) {
		outputCookies = new ArrayList<>(outputCookies);
		outputCookies.add(rcCookie);
	}

	/**
	 * Generates response header from the member variables.
	 * 
	 * @return generated header
	 */
	String generateHeader() {
		charset = Charset.forName(encoding);

		final String firstLine = "HTTP/1.1 " + statusCode + " " + statusText + NEWLINE;
		String secondLine = "Content-Type: " + mimeType;

		if (mimeType.startsWith("text/")) {
			secondLine += "; charset=" + encoding;
		}
		secondLine += NEWLINE;

		final StringBuilder cookies = new StringBuilder();

		for (final RCCookie rcCookie : outputCookies) {
			cookies.append("Set-Cookie: " + rcCookie.getName() + "=\"" + rcCookie.getValue() + "\"");

			if (!Utility.checkIfNullableEmpty(rcCookie.getDomain())) {
				cookies.append("; Domain=" + rcCookie.getDomain());
			}

			if (!Utility.checkIfNullableEmpty(rcCookie.getPath())) {
				cookies.append("; Path=" + rcCookie.getPath());
			}

			if (!Utility.checkIfNullableEmpty(rcCookie.maxAge)) {
				cookies.append("; Max-Age=" + rcCookie.getMaxAge());
			}

			cookies.append(NEWLINE);
		}

		final String header = firstLine + secondLine + cookies.toString() + NEWLINE;

		try {
			headerGenerated = true;
			write(header.getBytes(StandardCharsets.ISO_8859_1));
		} catch (final IOException ignorable) {
		}

		return header;
	}

	/**
	 * Returns a parameter from the {@code parameters} map under the provided
	 * {@code name}.
	 * 
	 * @param name
	 *            key for {@code parameters} map
	 * @return value under the provided key
	 */
	public String getParameter(final String name) {
		return parameters.get(name);
	}

	/**
	 * Returns the set of keys for {@code parameters} map.
	 * 
	 * @return set of keys for {@code parameters} map
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Returns a parameter from the {@code pesistentParameters} map under the
	 * provided {@code name}.
	 * 
	 * @param name
	 *            key for {@code persistentParameters} map
	 * @return value under the provided key
	 */
	public String getPersistentParameter(final String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Returns the set of keys for {@code persistentParameters} map.
	 * 
	 * @return set of keys for {@code persistentParameters} map
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Returns a parameter from the {@code temporaryParameters} map under the
	 * provided {@code name}.
	 * 
	 * @param name
	 *            key for {@code temporaryParameters} map
	 * @return value under the provided key
	 */
	public String getTemporaryParameter(final String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Returns the set of keys for {@code temporaryParameters} map.
	 * 
	 * @return set of keys for {@code temporaryParameters} map
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Removes the value specified by the key {@code name} from the
	 * {@code persistentParameters} map.
	 * 
	 * @param name
	 *            key in the {@code persistentParameters} map
	 */
	public void removePersistentParameter(final String name) {
		persistentParameters = new HashMap<>(persistentParameters);
		persistentParameters.remove(name);
	}

	/**
	 * Removes the value specified by the key {@code name} from the
	 * {@code temporaryParameters} map.
	 * 
	 * @param name
	 *            key in the {@code temporaryParameters} map
	 */
	public void removeTemporaryParameter(final String name) {
		temporaryParameters = new HashMap<>(temporaryParameters);
		temporaryParameters.remove(name);
	}

	/**
	 * Sets the {@code encoding} to the specified {@code encoding} argument.
	 * 
	 * @param encoding
	 *            character encoding
	 */
	public void setEncoding(final String encoding) {
		Utility.checkIfHeaderGenerated(headerGenerated);
		this.encoding = encoding;
	}

	/**
	 * Sets the {@code mimeType} to the specified {@code mimeType} argument
	 * 
	 * @param mimeType
	 *            type of files content
	 */
	public void setMimeType(final String mimeType) {
		Utility.checkIfHeaderGenerated(headerGenerated);
		this.mimeType = mimeType;
	}

	/**
	 * Puts a new value in the {@code persistentParameter} map.
	 * 
	 * @param name
	 *            map key
	 * @param value
	 *            value under the key
	 */
	public void setPersistentParameter(final String name, final String value) {
		persistentParameters = new HashMap<>(persistentParameters);
		persistentParameters.put(name, value);
	}

	/**
	 * Sets the status code for header.
	 * 
	 * @param statusCode
	 *            header status code
	 */
	public void setStatusCode(final int statusCode) {
		Utility.checkIfHeaderGenerated(headerGenerated);
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text for header.
	 * 
	 * @param statusText
	 *            header status text
	 */
	public void setStatusText(final String statusText) {
		Utility.checkIfHeaderGenerated(headerGenerated);
		this.statusText = statusText;
	}

	/**
	 * Puts a new value in the {@code temporaryParameter} map.
	 * 
	 * @param name
	 *            map key
	 * @param value
	 *            value under the key
	 */
	public void setTemporaryParameter(final String name, final String value) {
		temporaryParameters = new HashMap<>(temporaryParameters);
		temporaryParameters.put(name, value);
	}

	/**
	 * Writes the {@code data} to the {@code outputStream}.
	 * 
	 * @param data
	 *            byte array data for writing
	 * @return new {@link RequestContext} with the same values as this
	 * @throws IOException
	 *             if a error occurred while writing to the output stream
	 */
	public RequestContext write(final byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data, 0, data.length);

		return new RequestContext(outputStream, parameters, persistentParameters, outputCookies);
	}

	/**
	 * Writes the {@code text} to the {@code outputStream}.
	 * 
	 * @param text
	 *            {@link String} data for writing
	 * @return new {@link RequestContext} with the same values as this
	 * @throws IOException
	 *             if a error occurred while writing to the output stream
	 */
	public RequestContext write(final String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		return write(text.getBytes(charset));
	}
}

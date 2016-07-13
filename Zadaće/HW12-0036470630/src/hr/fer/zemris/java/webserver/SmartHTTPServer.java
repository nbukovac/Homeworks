package hr.fer.zemris.java.webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class that represents a implementation of a server that processes HTTP
 * request from a client and sends responses to the client request also using
 * HTPP.
 * <p>
 * It is possible to call predefined scripts to get dynamically generated HTML
 * pages, there is also a implementation of a cookie mechanism used for
 * remembering the clients activities. Of course it is possible to get regular
 * HTML pages and other mime types that are supported by default browsers such
 * as images, plain text...
 * </p>
 * This program requires one argument and that is the path to the
 * server.properties configuration file, in which all necessary configurations
 * are located.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class SmartHTTPServer {

	/**
	 * Class that processes users request and returns the appropriate response
	 * based on the users request. Every instance of this class does processes
	 * one request.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable {

		/** Buffer size for reading and writing from streams */
		private static final int BUFFER_SIZE = 1024;

		/** Default HTTP method */
		private static final String DEFAULT_METHOD = "GET";

		/** Acceptable version of HTTP */
		private static final String VERSION_1 = "HTTP/1.0";

		/** Acceptable version of HTTP */
		private static final String VERSION_2 = "HTTP/1.1";

		/** Default mime type */
		private static final String DEFAULT_MIME = "application/octet-stream";

		/** Status code for bad header */
		private static final int BAD_HEADER_CODE = 400;
		/** Status text for bad header */
		private static final String BAD_HEADER_TEXT = "Bad header!";

		/** Status code for forbidden access */
		private static final int FORBIDDEN_CODE = 403;
		/** Status text for forbidden access */
		private static final String FORBIDDEN_TEXT = "Forbidden access";

		/** Status code for file not found */
		private static final int NOT_FOUND_CODE = 404;
		/** Status text for file not found */
		private static final String NOT_FOUND_TEXT = "File not found";

		/** Status code for success */
		private static final int SUCCESS_CODE = 200;
		/** Status text for success */
		private static final String SUCCESS_TEXT = "OK";

		/** Position of the root folder */
		private static final String ROOT = "webroot";

		/** Script extension */
		private static final String SMSCR = "smscr";

		/** Worker server path */
		private static final String WORKER_EXTENSION = "/ext/";

		/** Worker package */
		private static final String WORKER_SPACE = "hr.fer.zemris.java.webserver.workers.";

		/** Session ID */
		private static final String SID_NAME = "sid";

		// member variables

		/** {@link Socket} used for connection to server */
		private final Socket clientSocket;

		/**
		 * {@link PushbackInputStream} decorator used for reading the user
		 * request
		 */
		private PushbackInputStream istream;

		/** {@link OutputStream} used to write the response to */
		private OutputStream outputStream;

		/** Version of the HTTP */
		private String version;

		/** HTTP method used to generate the request */
		private String method;

		/** Parameters parsed from the request */
		private final Map<String, String> parameters = new HashMap<>();

		/** Session parameters */
		private Map<String, String> persistentParameters;

		/** Cookies */
		private final List<RCCookie> outputCookies = new ArrayList<>();

		/** Session ID */
		private String SID;

		/**
		 * Constructs a new {@link ClientWorker} with the specified
		 * {@link Socket} used to communicate with the client.
		 * 
		 * @param client
		 *            client {@link Socket}
		 */
		public ClientWorker(final Socket client) {
			super();
			clientSocket = client;
		}

		/**
		 * Checks if a new line character combination is encountered and if it
		 * is returns {@code true} else {@code false}.
		 * 
		 * @param buffer
		 *            byte array containing request header
		 * @param i
		 *            position of the first character
		 * @return true if newline character combination is encountered, else
		 *         false
		 */
		private boolean checkNewline(final byte[] buffer, final int i) {
			return (buffer[i] == '\r' && buffer[i + 1] == '\n') || (buffer[i] == '\n' && buffer[i + 1] == '\r');
		}

		/**
		 * Checks if there is a cookie with the a SID parameter and if there is
		 * one the session id is parsed an used for continuing the connection
		 * between the client and the server. If there isn't a cookie with the
		 * SID parameter then a new SID is created and given to this connection.
		 * 
		 * @param request
		 *            request header parsed into separate lines
		 * @param action
		 *            name of the action that is called
		 */
		private void checkSession(final List<String> request, final String action) {
			String sidCandidate = null;
			String domain = null;
			for (final String line : request) {
				if (!line.startsWith("Cookie:")) {
					if (line.startsWith("Host:")) {
						domain = line.split(":")[1].trim();
					}
					continue;
				}
				final String s = line.substring("Cookie: ".length());
				final String[] split = s.split(";");

				for (final String cookieValue : split) {
					if (cookieValue.contains(SID_NAME)) {
						String value = cookieValue.split("=")[1].trim();
						value = value.substring(1, value.length() - 1);
						final long time = LocalTime.now().toSecondOfDay();
						final SessionMapEntry session = sessions.get(value);
						if (session != null && session.validUntil > time) {
							sidCandidate = session.sid;
						}
					}
				}
			}

			if (sidCandidate == null) {
				sidCandidate = generateSID();
				sessions.put(sidCandidate,
						new SessionMapEntry(sidCandidate, LocalTime.now().toSecondOfDay() + sessionTimeout));
			}

			SID = sidCandidate;
			persistentParameters = sessions.get(SID).map;
			outputCookies.add(new RCCookie(SID_NAME, SID, null, domain, "/"));
		}

		/**
		 * Based on the data found inside the header the appropriate response
		 * header is created and written to the output stream.
		 * 
		 * @param request
		 *            request header parsed into separate lines
		 * @throws IOException
		 *             if an error occurs during writing to the output stream
		 */
		private void extractFromHeader(final List<String> request) throws IOException {
			if (request.isEmpty()) {
				final RequestContext context = getBadHeaderContext();
				context.write(BAD_HEADER_TEXT);
				return;
			}

			final String firstLine = request.get(0);
			final String[] split = firstLine.split("\\s+");
			method = split[0];
			version = split[2];

			if (!method.equals(DEFAULT_METHOD) || (!version.equals(VERSION_1) && !version.equals(VERSION_2))) {
				final RequestContext context = getBadHeaderContext();
				context.write(BAD_HEADER_TEXT);
				return;
			}

			final String[] pathSplit = split[1].split("\\?");
			checkSession(request, getActionName(pathSplit[0]));

			if (pathSplit.length > 1) {
				extractParameters(pathSplit[1]);
			}

			final Path path = Paths.get(ROOT + pathSplit[0]);

			if (!path.startsWith(documentRoot)) {
				final RequestContext context = getBadHeaderContext();
				context.setStatusCode(FORBIDDEN_CODE);
				context.setStatusText(FORBIDDEN_TEXT);
				context.write(FORBIDDEN_TEXT);
				return;
			} else if (workersMap.containsKey(pathSplit[0])) {
				final RequestContext context = new RequestContext(outputStream, parameters, persistentParameters,
						outputCookies);
				setSucessHeader(context);
				workersMap.get(pathSplit[0]).processRequest(context);
				return;

			} else if (path.startsWith(documentRoot.toString() + WORKER_EXTENSION)) {
				processExtWorker(path);

				return;
			} else if (!Files.exists(path) || Files.isDirectory(path) || !Files.isReadable(path)) {
				final RequestContext context = getBadHeaderContext();
				setNotFoundHeader(context);
				return;
			}

			final String fileName = path.getFileName().toString();
			final int pos = fileName.lastIndexOf(".");
			final String extension = fileName.substring(pos + 1);
			String mime = mimeTypes.get(extension);

			if (mime == null) {
				mime = DEFAULT_MIME;
			}

			if (extension.equals(SMSCR)) {
				processScript(path);
				return;
			}

			final RequestContext context = new RequestContext(outputStream, parameters, persistentParameters,
					outputCookies);
			context.setMimeType(mime);
			setSucessHeader(context);
			if (!extension.equals(SMSCR)) {
				context.write(Files.readAllBytes(path));
			}
		}

		/**
		 * Parses the parameter from the URL query passed as
		 * {@code parametersString}. A query is some form of
		 * {@code name=value&name2=value2}.
		 * 
		 * @param parametersString
		 *            URL query
		 */
		private void extractParameters(final String parametersString) {
			for (final String parameter : parametersString.split("&")) {
				final String[] parameterSplit = parameter.split("=");
				parameters.put(parameterSplit[0], parameterSplit[1]);
			}
		}

		/**
		 * Returns the name of the action that is specified in the URL from the
		 * request header.
		 * 
		 * @param action
		 *            full action name
		 * @return action name
		 */
		private String getActionName(final String action) {
			final int dotPos = action.lastIndexOf(".");
			final int slashPos = action.lastIndexOf("/");
			return action.substring(slashPos + 1, dotPos != -1 ? dotPos : action.length());
		}

		/**
		 * Returns a {@link RequestContext} with a set bad header status code
		 * and text.
		 * 
		 * @return {@link RequestContext} with a set bad header status code and
		 *         text
		 */
		private RequestContext getBadHeaderContext() {
			final RequestContext context = new RequestContext(outputStream, parameters, persistentParameters,
					outputCookies);
			context.setStatusCode(BAD_HEADER_CODE);
			context.setStatusText(BAD_HEADER_TEXT);
			return context;
		}

		/**
		 * Processes a call made to a web worker located in the {@code /ext/}
		 * folder.
		 * 
		 * @param path
		 *            path in the URL
		 * @throws IOException
		 *             if an error occurs during writing to the output stream
		 */
		private void processExtWorker(final Path path) throws IOException {
			final String name = path.getFileName().toString();
			final RequestContext context = new RequestContext(outputStream, parameters, persistentParameters,
					outputCookies);
			try {
				final Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(WORKER_SPACE + name);
				final IWebWorker webWorker = (IWebWorker) referenceToClass.newInstance();
				setSucessHeader(context);
				webWorker.processRequest(context);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				setNotFoundHeader(context);
			}
		}

		/**
		 * Processes a call made to a script located in the {@code /scripts/}
		 * folder.
		 * 
		 * @param path
		 *            path in the URL
		 * @throws IOException
		 *             if an error occurs during writing to the output stream
		 */
		private void processScript(final Path path) throws IOException {
			final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			final StringBuilder script = new StringBuilder();
			lines.forEach(l -> script.append(l));
			final RequestContext context = new RequestContext(outputStream, parameters, persistentParameters,
					outputCookies);
			context.setMimeType(DEFAULT_MIME);
			setSucessHeader(context);
			new SmartScriptEngine(new SmartScriptParser(script.toString()).getDocumentNode(), context).execute();
			final Map<String, String> sessionsMap = sessions.get(SID).map;

			for (final String name : context.getPersistentParameterNames()) {
				sessionsMap.put(name, context.getPersistentParameter(name));
			}
		}

		/**
		 * Reads the header request and parses it into separates lines based on
		 * the newline character combinations and returns a {@link List} of
		 * strings.
		 * 
		 * @return {@link List} of strings
		 */
		private List<String> readRequest() {
			final List<String> header = new ArrayList<>();
			final byte[] buffer = new byte[BUFFER_SIZE];
			int readBytes;
			int headerSize = 0;
			boolean headerExtracted = false;
			try {
				while (true) {
					readBytes = istream.read(buffer);
					if (readBytes == -1) {
						break;
					}

					int offset = 0;
					for (int i = 0; i < readBytes - 1; i++) {
						if (checkNewline(buffer, i)) {
							final String line = new String(buffer, offset, i - offset, StandardCharsets.UTF_8).trim();

							if (line.isEmpty()) {
								headerExtracted = true;
								headerSize = i + 1;
								break;
							}

							header.add(line);
							i = i + 1;
							offset = i;
						}
					}

					if (headerExtracted) {
						istream.unread(buffer, headerSize, readBytes - headerSize - 1);
						break;
					}

				}
			} catch (final IOException e) {
				e.printStackTrace();
			}

			return header;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(clientSocket.getInputStream());
				outputStream = clientSocket.getOutputStream();
				try {
					extractFromHeader(readRequest());
				} catch (final Exception e) {
				}
			} catch (final IOException e) {
			} finally {
				try {
					clientSocket.close();
				} catch (final IOException ignorable) {
				}
			}
		}

		/**
		 * Sets file not found status code and status text and writes the header
		 * to the provided {@link RequestContext} output stream.
		 * 
		 * @param context
		 *            {@link RequestContext}
		 * @throws IOException
		 *             if an error occurs during writing to the output stream
		 */
		private void setNotFoundHeader(final RequestContext context) throws IOException {
			context.setStatusCode(NOT_FOUND_CODE);
			context.setStatusText(NOT_FOUND_TEXT);
			context.write(NOT_FOUND_TEXT);
		}

		/**
		 * Sets success status code and status text to the provided
		 * {@link RequestContext}.
		 * 
		 * @param context
		 *            {@link RequestContext}
		 */
		private void setSucessHeader(final RequestContext context) {
			context.setStatusCode(SUCCESS_CODE);
			context.setStatusText(SUCCESS_TEXT);
		}

	}

	/**
	 * Class that acts as the server. It accepts request from clients and
	 * creates new threads that are responsible for further communication with
	 * the client. Every request gets a new {@link ClientWorker} thread.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	protected class ServerThread extends Thread {

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));

				while (true) {
					final Socket client = serverSocket.accept();
					final ClientWorker clientWorker = new ClientWorker(client);
					threadPool.submit(clientWorker);
				}
			} catch (final IOException e) {
			} finally {
				if (serverSocket != null) {
					try {
						serverSocket.close();
						timer.cancel();
					} catch (final IOException e) {
						System.err.println("Couldn't close the server socket");
					}
				}
				threadPool.shutdown();
			}
		}
	}

	/**
	 * Class used as a storage for information about established sessions.
	 * Contains info about every session and stores cookie information as well.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	private static class SessionMapEntry {

		/** Session id */
		private final String sid;

		/** Specifies until this session entry is valid */
		private final long validUntil;

		/** Map of persistent parameters */
		private final Map<String, String> map;

		/**
		 * Constructs a new {@link SessionMapEntry} with the specified
		 * parameters.
		 * 
		 * @param sid
		 *            session id
		 * @param validUntil
		 *            time until this session is valid
		 */
		SessionMapEntry(final String sid, final long validUntil) {
			super();
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = Collections.synchronizedMap(new HashMap<String, String>());
		}

	}

	/** Length of the generated session id */
	private static final int SID_LENGTH = 30;

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            requires one argument containing the file path to the
	 *            server.properties configuration file.
	 */
	public static void main(final String[] args) {
		if (args.length != 1) {
			System.err.println("Config file isn't provided");
		}

		final SmartHTTPServer smartHTTPServer = new SmartHTTPServer(args[0]);
		smartHTTPServer.start();
	}

	/** Server address */
	private String address;

	/** Port the server application listens */
	private int port;

	/** Number of worker threads */
	private int workerThreads;

	/** Session timeout limit */
	private int sessionTimeout;

	/** Supported mime types */
	private final Map<String, String> mimeTypes = new HashMap<>();

	/** Supported workers */
	private final Map<String, IWebWorker> workersMap = new HashMap<>();

	/** {@link ServerThread} that serves clients */
	private ServerThread serverThread;

	/** {@link ExecutorService} thread pool */
	private ExecutorService threadPool;

	/** Servers root folder */
	private Path documentRoot;

	/** Map containing session information */
	private volatile Map<String, SessionMapEntry> sessions = new HashMap<>();

	/** {@link Random} used to generate session ids */
	private final Random sessionRandom = new Random();

	/**
	 * {@link TimerTask} used for removing expired session information from the
	 * {@code sessions} map
	 */
	private final TimerTask sessionRemover = new TimerTask() {

		@Override
		public void run() {
			final long time = LocalTime.now().toSecondOfDay();
			for (final SessionMapEntry session : sessions.values()) {
				if (session.validUntil >= time) {
					sessions.remove(session);
				}
			}
		}
	};

	/** {@link Timer} used to schedule removal of expired sessions */
	final Timer timer = new Timer(true);

	/**
	 * Constructs a new {@link SmartHTTPServer} and initializes the classes
	 * member variables from the provided configuration file.
	 * 
	 * @param configFileName
	 *            path to the server configuration file
	 */
	public SmartHTTPServer(final String configFileName) {
		final Properties properties = new Properties();
		try {
			properties.load(new BufferedReader(new InputStreamReader(new FileInputStream(new File(configFileName)))));
			address = properties.getProperty("server.address");
			port = Integer.parseInt(properties.getProperty("server.port"));
			workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
			documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
			sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
			final String mimeConfig = properties.getProperty("server.mimeConfig");
			final String workersConfig = properties.getProperty("server.workers");

			properties.clear();
			properties.load(new BufferedReader(new InputStreamReader(new FileInputStream(new File(mimeConfig)))));
			final Enumeration<?> mimeProps = properties.propertyNames();

			while (mimeProps.hasMoreElements()) {
				final String s = (String) mimeProps.nextElement();
				mimeTypes.put(s, properties.getProperty(s));
			}

			properties.clear();
			properties.load(new BufferedReader(new InputStreamReader(new FileInputStream(new File(workersConfig)))));
			final Enumeration<?> workerProps = properties.propertyNames();

			while (workerProps.hasMoreElements()) {
				final String path = (String) workerProps.nextElement();
				final String fqcn = properties.getProperty(path);
				final Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				final IWebWorker webWorker = (IWebWorker) referenceToClass.newInstance();
				workersMap.put(path, webWorker);
			}

		} catch (final IOException ignorable) {
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			System.err.println("Couldn't create a instance of IWebWorker" + e.getMessage());
		}
	}

	/**
	 * Generates a session id that consists of {@code SID_LENGTH} characters
	 * between 0 and 9.
	 * 
	 * @return session id
	 */
	private String generateSID() {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < SID_LENGTH; i++) {
			sb.append(sessionRandom.nextInt(10));
		}

		return sb.toString();
	}

	/**
	 * Starts the {@link SmartHTTPServer} and its {@link ServerThread} if the
	 * {@code serverThread} is {@code null}.
	 */
	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
			timer.scheduleAtFixedRate(sessionRemover, 0, 300 * 1000);
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();
		}
	}

}

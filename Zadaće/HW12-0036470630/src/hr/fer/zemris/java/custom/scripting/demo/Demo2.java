package hr.fer.zemris.java.custom.scripting.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import hr.fer.zemris.java.webserver.util.Utility;

/**
 * Program used to test response header and content creation for script
 * &quot;zbrajanje.smscr&quot;.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Demo2 {

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		final String documentBody = Utility.readFromDisk("webroot/scripts/zbrajanje.smscr");
		final Map<String, String> parameters = new HashMap<String, String>();
		final Map<String, String> persistentParameters = new HashMap<String, String>();
		final List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

}

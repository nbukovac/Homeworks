package hr.fer.zemris.web.servlet.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class that implements {@link ServletContextListener} and is used to
 * initialize the time when the application was started that is stored in the
 * {@code startTime} attribute found in the servlet context.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
@WebListener
public class TimeListener implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent context) {

	}

	@Override
	public void contextInitialized(final ServletContextEvent event) {
		final ServletContext context = event.getServletContext();
		context.setAttribute("startTime", System.currentTimeMillis());
	}

}

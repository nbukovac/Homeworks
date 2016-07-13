package hr.fer.zemris.weapps.baza.listener;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class InitializationListener implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent event) {
		final Properties properties = new Properties();
		final String fileName = event.getServletContext().getRealPath("WEB-INF/dbsettings.properties");

		try {
			properties.load(new FileInputStream(new File(fileName)));

			final String host = properties.getProperty("host");
			int port = 0;
			try {
				port = Integer.parseInt(properties.getProperty("port"));
			} catch (final NumberFormatException e) {
				e.printStackTrace();
			}

			final String dbName = properties.getProperty("name");
			final String user = properties.getProperty("user");
			final String password = properties.getProperty("password");

			final String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user
					+ ";password=" + password;

			final ComboPooledDataSource cpds = new ComboPooledDataSource();

			try {
				cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			} catch (final PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			cpds.setJdbcUrl(connectionURL);

			event.getServletContext().setAttribute("dbpool", cpds);

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(final ServletContextEvent event) {
		final ComboPooledDataSource cpds = (ComboPooledDataSource) event.getServletContext().getAttribute("dbpool");

		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (final SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

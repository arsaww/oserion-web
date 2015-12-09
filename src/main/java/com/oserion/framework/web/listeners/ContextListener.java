package com.oserion.framework.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.oserion.framework.api.business.IDBConnection;
import com.oserion.framework.api.util.OserionBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContextListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(ContextListener.class.getName());

	private final String PROPERTY_CONFIG_PATH = "oserion.config.path";
	private final String PROPERTY_DB_CONNECTION = "database.connection";


	public void contextDestroyed(ServletContextEvent scl) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent scl) {

		LOGGER.log(Level.INFO,"Context Initialisation starts...");

		try {

			//1 get properties from config file.
			FileInputStream configFile = new FileInputStream(System.getProperty(PROPERTY_CONFIG_PATH));
			System.getProperties().load(configFile);

			//2 set context objects
			IDBConnection c = new OserionBuilder().buildDBConnection();
			scl.getServletContext().setAttribute(PROPERTY_DB_CONNECTION, c);

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Context Initialisation failure :", e);
		}


		LOGGER.log(Level.INFO, "Context Initialisation done");


	}

	
}

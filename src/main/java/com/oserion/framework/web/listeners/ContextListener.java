package com.oserion.framework.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.oserion.framework.api.business.IDBConnection;
import com.oserion.framework.api.util.OserionBuilder;
import com.oserion.framework.web.util.ConfigWebApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContextListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(ContextListener.class.getName());

	public void contextDestroyed(ServletContextEvent scl) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent scl) {

		LOGGER.log(Level.INFO,"Context Initialisation starts...");

		try {

			//1 get properties from config file.
			FileInputStream configFile = new FileInputStream(System.getProperty(ConfigWebApp.PROPERTY_CONFIG_PATH));
			System.getProperties().load(configFile);

			//2 set context objects
			AnnotationConfigApplicationContext c = new AnnotationConfigApplicationContext(OserionBuilder.class);
			scl.getServletContext().setAttribute(ConfigWebApp.PROPERTY_DB_CONNECTION, c.getBean(IDBConnection.class));
			scl.getServletContext().setAttribute(ConfigWebApp.PROPERTY_SPRING_CONTEXT, c);

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Context Initialisation failure :", e);
		}


		LOGGER.log(Level.INFO, "Context Initialisation done");


	}

	
}

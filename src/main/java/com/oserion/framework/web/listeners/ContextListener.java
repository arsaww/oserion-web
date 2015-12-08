package com.oserion.framework.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.oserion.framework.web.util.LoadConfigFile;

public class ContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent scl) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent scl) {
		
		System.out.println("initialization of servlet oserion-web");
        LoadConfigFile lcf = new LoadConfigFile("oserionWeb.config.file.path");
        scl.getServletContext().setAttribute("xmlconf", lcf.xmlConfiguration);
        
        String bddUrlBaseMongo = lcf.xmlConfiguration.getString("mongoDB.bddUrlBaseMongo");
        String bddPortMongo = lcf.xmlConfiguration.getString("mongoDB.bddPortMongo");
        String bddName = lcf.xmlConfiguration.getString("mongoDB.bddName");

		MongoClient mongoClient = new MongoClient( bddUrlBaseMongo , Integer.parseInt(bddPortMongo) );
		MongoDatabase database = mongoClient.getDatabase(bddName);

		scl.getServletContext().setAttribute("dbconnection", database);

	}

	
}

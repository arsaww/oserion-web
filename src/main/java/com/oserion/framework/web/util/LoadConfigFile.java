package com.oserion.framework.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.XMLConfiguration;

public class LoadConfigFile {
	  
	  private static final Logger LOGGER = Logger.getLogger(LoadConfigFile.class.getName());
	  
	//  private static LoadConfigFile INSTANCE = null;
	  public XMLConfiguration xmlConfiguration = null;
	  
	  public LoadConfigFile(String variableFromProcessus ) {
	    getFileConf(variableFromProcessus);
	  }
	  
	  public XMLConfiguration getXmlConfiguration() {
	    return xmlConfiguration;
	  }
	  
	  
	  public void getFileConf(String systemPropertyName) {

		  LOGGER.log(Level.CONFIG, "line 30");
		  LOGGER.log(Level.ALL, "line 30");
		  LOGGER.log(Level.INFO, "line 30");

	    URL url = null;
	    if (systemPropertyName != null && System.getProperty(systemPropertyName) != null && System.getProperty(systemPropertyName) != ""  ) {
	      File configFile = new File(System.getProperty(systemPropertyName));
		  LOGGER.log(Level.CONFIG, "line 35");
		  LOGGER.log(Level.INFO, "line 35");
		  
	      if (configFile.isFile() && configFile.canRead()) {
			  LOGGER.log(Level.INFO, "line 38");
	        try {
	          url = configFile.toURI().toURL();
	          loadProperties(url);
	          LOGGER.log(Level.INFO, "Config file found at {}.", configFile.getAbsolutePath());
	        } catch (MalformedURLException e) {
	          LOGGER.log(Level.INFO, "error loading properties file", e);
	        } catch (Exception e) {
	          LOGGER.log(Level.INFO,"error loading properties file", e);
	        }
	      } else {
	    	  LOGGER.log(Level.INFO, "config file is not a file or cant be read");
	      }
	    } else {
			  LOGGER.log(Level.INFO, "line 55");
	    }
	    
	  }
	  
	  
	  private void loadProperties(URL url) throws Exception {
	    XMLConfiguration xmlConfiguration = new XMLConfiguration();
	    xmlConfiguration.setValidating(false);
	    
	    xmlConfiguration.load(new FileInputStream(new File(url.toURI())), "UTF-8");
	    
	    this.xmlConfiguration = xmlConfiguration;
	  }
	}

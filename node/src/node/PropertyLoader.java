package node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertyLoader {
	private static final Properties properties = new Properties();
	private static final Logger log = Logger.getLogger(PropertyLoader.class
			.getName());
	
	public void init(){
		try {
			properties.load(new FileInputStream("resources" + File.separator
					+ "constants.properties"));
		} catch (FileNotFoundException e) {
			log.severe("configuration file not found");
		} catch (IOException e) {
			log.severe("Configuration file did not get loaded");
		}
	}
	
	public Properties getProperties(){
		return properties;
	}
}

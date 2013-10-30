package ui;

import java.util.Map;

import logic.EchoClient;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* Logger root = LogManager.getRootLogger();
		org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) root;
		coreLogger.setLevel(Level.ALL);
		Logger logger = LogManager.getLogger(Application.class);
		coreLogger.setLevel(Level.ALL);
		logger.info("logger info");
		((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).setLevel(Level.FATAL);
		logger.info("logger info after");
		logger.trace("Test"); */
		
		// starts the client.
		new EchoClient();
		
	}
}

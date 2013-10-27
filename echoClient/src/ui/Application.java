package ui;

import logic.EchoClient;

public class Application {

	public static void main(String[] args) {
		/*// TODO Auto-generated method stub
		Logger root = LogManager.getRootLogger();
		//((org.apache.logging.log4j.core.Logger) root).setLevel(Level.ALL);
		logger.info("logger info");
		((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).setLevel(Level.OFF);
		logger.info("logger info after");
		logger.trace("Test");*/
		
		// starts the client.
		new EchoClient();
		
	}
}

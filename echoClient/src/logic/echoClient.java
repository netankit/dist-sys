package logic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import communication.Connection;
import communication.tcp.TCPConnection;

public class echoClient implements ClientInterface {
	private Connection connection = null;
	private static Logger logger = LogManager.getLogger(echoClient.class);

	public echoClient() {
		this.connection = new TCPConnection();
		((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.ALL);
	}

	// to do
	public String connect(String address, String port) {

		try {
			int portnumber = Integer.parseInt(port);
			logger.info("Try to connect to server: " + address + " " + port);
			connection.connect(new InetSocketAddress(address, portnumber));
			String reply = connection.receiveString(Connection.ASCII);
			logger.info("Reply from server: " + reply);
			return reply;

		} catch (UnknownHostException e) {
			return "The given host address could not be resolved. Please check if the address is valid.";
		} catch (IllegalArgumentException e) {
			return "The given address or port is invalid.";
		} catch (SocketTimeoutException e) {
			return "Your request timed out. Please try again.";
		} catch (IOException e) {
			return "An error occured while trying to connect to the server.";
		}
	}

	// to do
	public String disconnect() {
		try {
			connection.close();
			return "disconnected";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	// to do
	public String sendMessage(String message) {
		try {
			logger.info("This message will be send to server: " + message);
			connection.sendString(
					message + Connection.DEFAULT_STRING_DELIMITER,
					Connection.ASCII);
			String reply = connection.receiveString(Connection.ASCII);
			logger.info("Received reply message from server: " + reply);
			return reply;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	// to do: set all loggers to the right level
	public String setLoglevel(String loglevel) {
		loglevel = loglevel.toUpperCase();
	
		if (loglevel.equals("ALL")) {
			((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.ALL);
		} else if (loglevel.equals("DEBUG")) {
			((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.DEBUG);
		} else if (loglevel.equals("INFO")) {
			((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.INFO);
		} else if (loglevel.equals("WARN")) {
			((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.WARN);
		} else if (loglevel.equals("ERROR")) {
			((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.ERROR);
		} else if (loglevel.equals("FATAL")) {
			((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.FATAL);
		} else if (loglevel.equals("OFF")) {
			((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.OFF);
		} else {
			return "The loglevel is invalid. Levels: (ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF)";
		}

		logger.log(Level.ERROR, "test");
		return "The log level is now set to " + loglevel;
	}

	// to do
	public String getHelp() {

		String helpString = "All commands: "
				+ "\r\n connect <address> <port> \t Establishes the connection to a server based on the given address and the port number"
				+ "\r\n disconnect \t\t\t Disconnect from the connected server"
				+ "\r\n send <message> \t\t Sends a text message to the server"
				+ "\r\n logLevel <level> \t\t Sets the logger to the specified level"
				+ "\r\n quit \t\t\t\t Exits the program execution";

		return helpString;
	}

	// to do
	public String quit() {
		try {
			connection.close();
			return "program shutdown";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}

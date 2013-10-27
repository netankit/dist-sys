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
	}


	@Override
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
			return "Your request timed out. Could not connect to server.";
		} catch (IOException e) {
			return "An error occured. Could not connect to server.";
		}
	}

	// to do
	@Override
	public String disconnect() {
		try {
			connection.close();
			return "Client was disconnected from the server.";
		} catch (IOException e) {
			return "Error while trying to disconnect from server.";
		}
	}

	// to do
	@Override
	public String sendMessage(String message) {
		try {
			logger.info("This message will be send to server: " + message);
			connection.sendString(
					message + Connection.DEFAULT_STRING_DELIMITER,
					Connection.ASCII);
			String reply = connection.receiveString(Connection.ASCII);
			logger.info("Received reply message from server: " + reply);
			return reply;
		} catch (IOException e) {
			return "An error occured. " + disconnect();
		}
	}

	// to do: set all loggers to the right level
	@Override
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

	
	@Override
	public String getHelp() {
		String helpString = "All commands: "
				+ "\r\n connect <address> <port> \t Establishes the connection to a server based on the given address and the port number"
				+ "\r\n disconnect \t\t\t Disconnect from the connected server"
				+ "\r\n send <message> \t\t Sends a text message to the server"
				+ "\r\n logLevel <level> \t\t Sets the logger to the specified level"
				+ "\r\n quit \t\t\t\t Exits the program execution";
		return helpString;
	}

	// System actually does not shutdown. 
	// This method could be replaced by the disconnect() method.
	@Override
	public String quit() {
		String status = "";
		status = disconnect() + "\r\nSystem will shutdown.";
		return status;
	}
}

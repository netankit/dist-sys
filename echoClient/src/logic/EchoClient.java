package logic;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ui.CommandLine;
import ui.CommandLineUser;
import communication.Connection;
import communication.tcp.TCPConnection;

public class EchoClient implements CommandLineUser {
	private Connection connection = null;
	private static Logger logger = LogManager.getLogger(EchoClient.class);
	boolean isConnected = false;

	private CommandLine commandLine;

	public EchoClient() {
		this.connection = new TCPConnection();
		this.commandLine = new CommandLine(this);
		commandLine.start();
	}

	// to do : connection lost check
	@Override
	public void connect(String address, String port) {

		String output;

		try {
			int portnumber = Integer.parseInt(port);
			logger.info("Try to connect to server: " + address + " " + port);
			connection.connect(new InetSocketAddress(address, portnumber));
			String reply = connection.receiveString(Connection.ASCII);
			logger.info("Reply from server: " + reply);
			isConnected = true;
			output = reply;

		} catch (UnknownHostException e) {
			output = "The given host address could not be resolved. Please check if the address is valid.";
		} catch (IllegalArgumentException e) {
			output = "The given address or port is invalid.";
		} catch (SocketTimeoutException e) {
			output = "Your request timed out. Could not connect to server.";
		} catch (ConnectException e) {
			output = "Error connecting to server: " + e.getMessage();
		 } catch (NoRouteToHostException e) {
			output = "Route to host unknown. Is connection to server available?";
		 } catch (IOException e) {
				output = "An error occured. Could not connect to server.";
			}

		commandLine.printLine(output);
	}

	// to do: check if server is connected
	@Override
	public void disconnect() {

		String output;
	if (isConnected){
		try {
			connection.close();
			isConnected = false;
			output = "Client was disconnected from the server.";
		} catch (IOException e) {
			output = "Error while trying to disconnect from server.";
		}
	} else {
		output = "Client is not connected to a server.";
	}
		commandLine.printLine(output);
	}

	// TO DO: check if server is connected
	@Override
	public void sendMessage(String message) {

		String output;

		try {

			if (isConnected) {
				logger.info("This message will be send to server: " + message);
				connection
						.sendString(message
								+ Connection.DEFAULT_STRING_DELIMITER,
								Connection.ASCII);
				String reply = connection.receiveString(Connection.ASCII);
				logger.info("Received reply message from server: " + reply);
				output = reply;
			} else {
				output = "Please connect to a server first.";
			}
		} catch (IOException e) {
			output = "An error occured.";
			disconnect();
		}

		commandLine.printLine(output);
	}

	// TO DO: set all loggers to the right level
	@Override
	public void setLoglevel(String loglevel) {

		String output;

		loglevel = loglevel.toUpperCase();

		if (loglevel.equals("ALL")) {
			((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.ALL);
		} else if (loglevel.equals("DEBUG")) {
			((org.apache.logging.log4j.core.Logger) logger)
					.setLevel(Level.DEBUG);
		} else if (loglevel.equals("INFO")) {
			((org.apache.logging.log4j.core.Logger) logger)
					.setLevel(Level.INFO);
		} else if (loglevel.equals("WARN")) {
			((org.apache.logging.log4j.core.Logger) logger)
					.setLevel(Level.WARN);
		} else if (loglevel.equals("ERROR")) {
			((org.apache.logging.log4j.core.Logger) logger)
					.setLevel(Level.ERROR);
		} else if (loglevel.equals("FATAL")) {
			((org.apache.logging.log4j.core.Logger) logger)
					.setLevel(Level.FATAL);
		} else if (loglevel.equals("OFF")) {
			((org.apache.logging.log4j.core.Logger) logger).setLevel(Level.OFF);
		} else {
			output = "The loglevel is invalid. Levels: (ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF)";
		}

		logger.log(Level.ERROR, "test");
		output = "The log level is now set to " + loglevel;

		commandLine.printLine(output);
	}

	@Override
	public void getHelp() {
		String helpString = "All commands: "
				+ "\r\n connect <address> <port> \t Establishes the connection to a server based on the given address and the port number"
				+ "\r\n disconnect \t\t\t Disconnect from the connected server"
				+ "\r\n send <message> \t\t Sends a text message to the server"
				+ "\r\n logLevel <level> \t\t Sets the logger to the specified level"
				+ "\r\n quit \t\t\t\t Exits the program execution";
		commandLine.printLine(helpString);
	}

	// To do: check if client is connected to server
	@Override
	public void quit() {
		commandLine.printLine("\r\nSystem will shutdown.");
		if (isConnected) {
			disconnect();
		}
		try {
			commandLine.close();
		} catch (IOException e) {
			// TODO Log this exception
			e.printStackTrace();
		}
	}
}

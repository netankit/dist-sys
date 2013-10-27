package ui;

/**
 * This interface is used to get informed about user input in the
 * {@link CommandLine}.
 * 
 */
public interface CommandLineUser {

	/**
	 * Establishes a connection for a given address and port.
	 * 
	 * @param address
	 *            The host name or IP-address of the server.
	 * @param port
	 *            The port of the service on the server.
	 * @return The confirmation message of the server if the connection was
	 *         established or error message if connection establishment failed.
	 */
	public void connect(String address, String port);

	/**
	 * Disconnects the from the connected server.
	 * 
	 * @return The notification message for the user if the client got
	 *         disconnected from the server.
	 */
	public void disconnect();

	/**
	 * Sends the input message to the server and gets the server reply.
	 * 
	 * @param message
	 *            The message string that will be send to the server.
	 * @return The received server reply string that should be displayed to the
	 *         user.
	 */
	public void sendMessage(String message);

	/**
	 * Sets the logger to the specified log level and prints out current log
	 * status.
	 * 
	 * @param loglevel
	 *            One of the following log4j log levels: (ALL | DEBUG | INFO |
	 *            WARN | ERROR | FATAL | OFF)
	 * @return A status message with the current log level.
	 */
	public void setLoglevel(String loglevel);

	/**
	 * Gets the help text with the intended usage of the client application and
	 * describes its set of commands.
	 * 
	 * @return The help string that should be displayed to the user.
	 */
	public void getHelp();

	/**
	 * Tears down the active connection to the server and returns the info
	 * message before program shutdown. System.exit() should be called right
	 * after this method to really quit the application.
	 * 
	 * @return The notification string about the program shutdown that should be
	 *         displayed to the user.
	 */
	public void quit();
}

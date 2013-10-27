package ui;

import java.io.Closeable;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLine implements Closeable {

	private Scanner in;
	private boolean isRunning = false;
	/**
	 * The object which wants to know about user input
	 */
	private CommandLineUser user;

	public CommandLine(CommandLineUser user) {
		this.in = new Scanner(System.in);
		this.user = user;
	}

	/**
	 * Starts the commandline. The CommandLine will listen to user input on
	 * System.in. This method returns immediately after a new Thread was
	 * created, which executes the code for the commandline.
	 */
	public synchronized void start() {
		if (!isRunning) {
			new Thread("CommandLine") {
				public void run() {
					readInput();
					isRunning = false;
				}
			}.start();
			isRunning = true;
		} else {
			// TODO: log debug: commandLine already running
		}
	}

	private void readInput() {
		String userInput;
		String[] userInputArg;
		System.out.println("------------------------------");
		System.out.println("## TEAM LAD : ECHO CLIENT ##");
		System.out.println("==============================\n");
		System.out
				.println("USAGE 1: connect SERVER_IP_ADDRESS PORT\nUSAGE 2: send string_to_server");
		System.out
				.println("------------------------------------------------------------------------");
		do {
			System.out.print("\nTeamLAD_Console > ");
			userInput = in.nextLine();

			// Check if the userInput has a white space: The boolean variable
			// isWhitespace keeps a track of it!
			Pattern pattern = Pattern.compile("\\s");
			Matcher matcher = pattern.matcher(userInput);
			boolean isWhitespace = matcher.find();

			if (isWhitespace) {
				// System.out.println("White Space Exists");

				// SPLIT ON WHITE SPACE
				userInputArg = userInput.split(" ");
				// System.out.println("Input Length: " + userInputArg.length);

				// CONNECT
				if (userInputArg[0].equals("connect")) {

					if (userInputArg.length == 3) {
						System.out
								.println("Connecting to the server: "
										+ userInputArg[1] + " Port: "
										+ userInputArg[2]);

						// ADD CODE HERE TO CONNECT TO SERVER

					} else {
						System.err
								.println("Invalid syntax.\nUSAGE: connect <IP_ADDR> <PORT>");
					}

				}
				// SEND
				else if (userInputArg[0].equals("send")) {
					System.out
							.println("Connection established. Now, sending the user string to server");

					String userString = "";

					for (int i = 1; i < (userInputArg.length); i++) {
						userString = userString + userInputArg[i] + " ";
					}
					// Trim the last white space character.
					userString = userString.trim();

					if (userInputArg.length > 1) {
						System.out.println("Sending user string '" + userString
								+ "' to the Server.");
						// ADD CODE HERE FOR SENDING STRING TO THE SERVER

					} else {
						System.err
								.println("Cannot send a blank string to the server.\nUSAGE: send <string>");
					}
				} else {
					System.err.println("Invalid Input");
				}

			} else {
				// System.out.println("No white space!");

				// DISCONNECT
				if (userInput.equals("disconnect")) {
					System.out.println("Connection Terminated");

					// ADD CODE HERE TO TERMINATE THE CONNECTION

				}
				// QUIT
				else if (userInput.equals("quit")) {
					System.out.println("Application Exit!");

					// ADD CODE HERE TO TERMINATE THE APPLICATION
				}

				else {
					System.err.println("Invalid Input");
				}

			}
		} while (!userInput.equalsIgnoreCase("quit"));

	}

	@Override
	public void close() throws IOException {
		in.close();
		// TODO: this will cause an IllegalSTateException when reading from
		// Scanner afterwards. Handling the exception should be done...
	}

	/**
	 * Prints the given string to the console.
	 * 
	 * @param str
	 *            The string, printed to the console.
	 */
	public void printLine(String str) {
		synchronized (System.out) {
			// TODO
			new Throwable("Not implemented").printStackTrace();
		}
	}

}

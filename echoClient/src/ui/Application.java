package ui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

	// private static Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// logger.info("Hello World!");
		// logger.trace("Test");

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
			Scanner in = new Scanner(System.in);
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
}

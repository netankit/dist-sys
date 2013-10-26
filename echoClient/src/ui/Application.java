package ui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

	private static Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.info("Hello World!");
		logger.trace("Test");

		String userInput;
		String[] userInputArg;

		do {
			Scanner in = new Scanner(System.in);
			System.out
					.println("USAGE 1: connect <IP_ADDR> <PORT>\nUSAGE 2: send <string>\nNote: Please don't use brackets. They are for explainatory purpose only.");

			System.out.print("TeamLAD_Console > ");
			userInput = in.nextLine();

			// Check if the userInput has a white space: The boolean variable
			// isWhitespace keeps a track of it!
			Pattern pattern = Pattern.compile("\\s");
			Matcher matcher = pattern.matcher(userInput);
			boolean isWhitespace = matcher.find();

			if (isWhitespace) {
				System.out.println("White Space Exists");

				userInputArg = userInput.split(" ");
				System.out.println("Input Length: " + userInputArg.length);
				switch (userInputArg[0]) {
				case "connect": {
					if (userInputArg.length == 3) {
						System.out
								.println("Connecting to the server: "
										+ userInputArg[1] + " Port: "
										+ userInputArg[2]);
						break;

						// ADD CODE HERE TO CONNECT TO SERVER

					} else {
						System.err
								.println("Invalid syntax.\nUSAGE: connect <IP_ADDR> <PORT>");
						break;
					}

				}

				case "send": {
					System.out
							.println("Connection established. Now, sending the user string to server");

					String userString = "";

					for (int i = 1; i < (userInputArg.length - 1); i++) {
						userString = userString + userInputArg[i] + " ";
					}
					// Trim the last white space character.
					userString = userString.trim();

					if (userInputArg.length > 1) {
						System.out.println("Sending user string '" + userString
								+ "' to the Server.");

						// ADD CODE HERE FOR SENDING STRING TO THE SERVER -- We
						// need
						// to check here if the connection has been first
						// established in the previous command!

						// #####NEEDS DISCUSSION#######

					} else {
						System.err
								.println("Cannot send a blank string to the server.\nUSAGE: send <string>");
					}
					break;
				}
				default:
					System.err.println("Invalid Input");
				}

			} else {
				System.out.println("No white space!");

				switch (userInput) {

				case "disconnect": {
					System.out.println("Connection Terminated");

					// ADD CODE HERE TO TERMINATE THE CONNECTION
					break;
				}
				case "quit": {
					System.out.println("Application Exit!");
					// ADD CODE HERE TO TERMINATE THE APPLICATION
					break;
				}

				default:
					System.err.println("Invalid Input");
				}

			}
		} while (userInput.equalsIgnoreCase("quit"));

	}
}

package logic;

import java.io.IOException;
import java.net.InetSocketAddress;

import communication.Connection;
import communication.tcp.TCPConnection;

public class echoClient implements ClientInterface {
	private Connection connection = null;
	
	
	public echoClient() {
		this.connection = new TCPConnection();
	}
	
	// to do
	public String connect (String address, String port){
		
		try {
			int portnumber = Integer.parseInt(port);
			connection.connect(new InetSocketAddress(address, portnumber));
			return connection.receiveString(Connection.ASCII);
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		} 
	}
	
	// to do
	public String disconnect(){
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
	public String sendMessage(String message){
		try {
			connection.sendString(message + Connection.DEFAULT_STRING_DELIMITER, Connection.ASCII);
			return connection.receiveString(Connection.ASCII);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	// to do
	public void setLoglevel(String loglevel){
		
	}
		
	// to do
	public String getHelp(){
		
		String helpString = 
			"All commands: " +
			"\r\n connect <address> <port> \t Establishes the connection to a server based on the given address and the port number" +
			"\r\n disconnect \t\t\t Disconnect from the connected server" +
			"\r\n send <message> \t\t Sends a text message to the server" +
			"\r\n logLevel <level> \t\t Sets the logger to the specified level" +
			"\r\n quit \t\t\t\t Exits the program execution";
		
		return helpString;
	}
	
	// to do 
	public String quit(){
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

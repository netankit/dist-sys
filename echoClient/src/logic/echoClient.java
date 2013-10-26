package logic;

import java.io.IOException;

public class echoClient implements ClientInterface {
	
	public echoClient() {
		
	}
	
	// to do
	public String connect (String adresse, String port){
		return "connected to: " + adresse + " port: " + port;
	}
	
	// to do
	public String disconnect(){
		return "disconnected";
	}
	
	
	// to do
	public String sendMessage(String message){
		return "sendMessage: " + message;
	}
	
	// to do
	public void setLoglevel(String loglevel){
		
	}
		
	// to do
	public String getHelp(){
		
		String helpString = "helptext";
		
		return helpString;
	}
	
	// to do 
	public String quit(){
		return "program shutdown";
	}
}

package test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import communication.Connection;
import communication.tcp.TCPConnection;

public class EchoServerTest {

	private static final Charset ASCII = Charset.forName("ASCII"); 
	
	public static void main(String[] args){
		
		Connection conn=null;
		
		try {
			Socket s = new Socket("131.159.52.1", 50000);
			conn = new TCPConnection(s);
			
			conn.sendString("Hello!\r", ASCII);
			System.out.print(conn.receiveString(ASCII));
			System.out.print(conn.receiveString(ASCII));
			System.out.println((byte)'\r');
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (IOException e) {
				// ignore
				e.printStackTrace();
			}
		}
		
	}
	
}

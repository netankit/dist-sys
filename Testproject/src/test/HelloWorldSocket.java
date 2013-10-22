package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import communication.Connection;
import communication.tcp.TCPConnection;

public class HelloWorldSocket {

	// The port used for the sockets
	public static final int PORT = 1232;

	public static void main(String[] args) {
		HelloWorldSocket hello = new HelloWorldSocket();

		// listen to the port and wait for a connection
		// --> starts a new thread, which will send a string when a connection
		// is established
		hello.startHelloWorldServer();

		// open a connection to the server and wait until a line is received.
		// Print that line to the console
		hello.receiveHelloWorldMessage();
	}

	public void startHelloWorldServer() {
		// Thread in which the server waits for a connection
		new Thread(new Runnable() {
			public void run() {

				// The "server". Listens to a port.
				ServerSocket server = null;
				// The "connection". Created by the ServerSocket when a
				// connection is established.
				Socket socket = null;

				try {
					// listen to port PORT
					server = new ServerSocket(PORT);
					// wait for a connection being established by the client
					socket = server.accept();
					
					Connection connection = new TCPConnection(socket);
					connection.sendString("Hello World!\n", Charset.forName("ASCII"));

				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				} finally {
					try {
						// close the socket with all associated streams (in and
						// out)
						socket.close();
					} catch (IOException e1) {
						// ignore
					}
					try {
						// close the server (frees the port: another server may
						// now listen to the port)
						server.close();
					} catch (IOException e) {
						// ignore
					}
				}
			}
		}, "Server").start(); // execute the code above in another thread
	}

	public void receiveHelloWorldMessage() {

		
		try {
			Connection connection = new TCPConnection(new Socket("localhost", PORT));
			
			System.out.println(connection.receiveString("\n", Charset.forName("ASCII")));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		/*
		// the client side of the connection
		Socket socket = null;
		// strings can be read by that reader from a stream
		BufferedReader in = null;
		try {
			socket = new Socket();
			// try to connect to the given address and port. timout is 1000ms
			socket.connect(new InetSocketAddress("localhost", PORT), 1000);

			// create the reader, which reads from the inputstream of the
			// socket.
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// read one line from the socket and print it to the console
			System.out.println(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		} finally {
			if (socket != null) {
				try {
					// close the socket and associated streams
					socket.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}*/

	}

}

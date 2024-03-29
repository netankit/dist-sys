package communication;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * A connection provides methods to send and receive data.
 * 
 * @author Daniel
 * 
 */
public interface Connection extends Closeable {

	public static final String DEFAULT_STRING_DELIMITER = "\r";

	/**
	 * Represents the ASCII charset.
	 */
	public static final Charset ASCII = Charset.forName("ASCII");

	/**
	 * Sends a string.
	 * 
	 * @param string
	 *            The string which will be sent.
	 * @param charset
	 *            The charset of the string (UTF-8, ASCII, ...).
	 * @throws IOException
	 *             when sending the string fails due to a connection issue.
	 */
	public void sendString(String string, Charset charset) throws IOException;

	/**
	 * Sends a string using the machine's default charset.
	 * 
	 * @param string
	 *            The string which will be sent.
	 * @throws IOException
	 *             when sending the string fails due to a connection issue.
	 */
	public void sendString(String string) throws IOException;

	/**
	 * Receives a string. The string is assumed to be terminated by the
	 * {@link #DEFAULT_STRING_DELIMITER} ({@value #DEFAULT_STRING_DELIMITER}).
	 * 
	 * @param charset
	 *            The charset of the string (UTF-8, ASCII, ...).
	 * @return The received string.
	 * @throws IOException
	 *             When receiving the string fails due to a connection issue.
	 */
	public String receiveString(Charset charset) throws IOException;

	/**
	 * Receives a string. The string is assumed to be terminated by the given
	 * delimiter.
	 * 
	 * @param delimiter
	 *            The character(s) at the end of the string.
	 * @param charset
	 *            The charset of the string (UTF-8, ASCII, ...).
	 * @return The received string.
	 * @throws IOException
	 *             When receiving the string fails due to a connection issue.
	 */
	public String receiveString(String delimiter, Charset charset)
			throws IOException;



	/**
	 * Sets up the connection to the given address.
	 * 
	 * @see Socket#connect(java.net.SocketAddress)
	 * 
	 * @param address
	 *            The address to which the connection will be established.
	 */
	public abstract void connect(InetSocketAddress address) throws IOException;

	/**
	 * Sets up the connection to the given address.
	 * 
	 * @see Socket#connect(java.net.SocketAddress, int)
	 * 
	 * @param address
	 *            The address to which the connection will be established.
	 * @param timeout
	 *            The timeout for establishing the connection.
	 */
	public abstract void connect(InetSocketAddress address, int timeout)
			throws IOException;
}

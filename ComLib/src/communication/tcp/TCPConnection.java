package communication.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class TCPConnection extends AbstractTCPConnection {

	/**
	 * Creates an empty connection. Call {@link #connect(InetSocketAddress)} or
	 * {@link #connect(InetSocketAddress, int)} before using it!
	 */
	public TCPConnection() {
		super();
	}

	/**
	 * Creates a new connection using the given socket.
	 * 
	 * @param sock
	 *            The socket to use for this connection.
	 * @throws NullPointerException
	 *             When the given socket is null.
	 */
	public TCPConnection(Socket sock) throws NullPointerException {
		super(sock);
	}

	/**
	 * @throws IOException
	 *             if an error occurs during the connection
	 * @throws IllegalBlockingModeException
	 *             if the used socket has an associated channel, and the channel
	 *             is in non-blocking mode
	 * @throws IllegalArgumentException
	 *             if address is null or is a SocketAddress subclass not
	 *             supported by this socket
	 */
	@Override
	public void connect(InetSocketAddress address) throws IOException {
		lockRead();
		lockWrite();

		Socket s = new Socket();
		s.connect(address);
		init(s);

		unlockWrite();
		unlockRead();
	}

	/**
	 * @throws IOException
	 *             if an error occurs during the connection
	 * @throws SocketTimeoutException
	 *             if timeout expires before connecting
	 * @throws IllegalBlockingModeException
	 *             if the used socket has an associated channel, and the channel
	 *             is in non-blocking mode
	 * @throws IllegalArgumentException
	 *             if address is null or is a SocketAddress subclass not
	 *             supported by this socket
	 */
	@Override
	public void connect(InetSocketAddress address, int timeout)
			throws IOException {
		lockRead();
		lockWrite();

		Socket s = new Socket();
		s.connect(address, timeout);
		init(s);

		unlockWrite();
		unlockRead();
	}

	@Override
	public void sendString(String string) throws IOException {
		sendString(string, Charset.defaultCharset());
	}

	@Override
	public void sendString(String string, Charset charset) throws IOException {
		byte[] bytes = string.getBytes(charset);

		lockWrite();
		out.write(bytes);
		unlockWrite();
	}

	@Override
	public String receiveString(Charset charset) throws IOException {
		return receiveString(DEFAULT_STRING_DELIMITER, charset);
	}

	private static final Charset ASCII = Charset.forName("ASCII");

	@Override
	public String receiveString(String delimiter, Charset charset)
			throws IOException {

		if (!charset.equals(ASCII)) {
			throw new UnsupportedCharsetException(
					"This method currently supports only ASCII for receiving strings!");
		}

		StringBuilder sb = new StringBuilder();
		byte lastByte;

		// TODO: This will not work when a character is more than one byte (=
		// not ASCII). So it's fine for now. Has to be changed if needed.
		lockRead();
		while (true) {
			lastByte = (byte) in.read();

			sb.append((char) lastByte);

			if (lastByte == delimiter.getBytes(ASCII)[0]) {
				break;
			}
		}
		unlockRead();

		return sb.toString();
	}

}

package communication.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection extends AbstractTCPConnection {

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

	@Override
	public String receiveString(String delimiter, Charset charset)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[] buffer = new byte[1024];
		int readBytes = 0;
		String appString;

		// TODO: This may not work when a character is more than one byte (= not ASCII). So
		// it's fine for now. Any idea how to solve this?
		lockRead();
		while (!sb.toString().endsWith(delimiter)) {
			readBytes = in.read(buffer, readBytes, buffer.length - readBytes);

			if (readBytes == -1)
				break;

			appString = new String(buffer, charset);
			sb.append(appString);

			if (appString.endsWith(delimiter))
				break;
		}
		unlockRead();

		return sb.toString();
	}

}

package communication.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import communication.Connection;

public abstract class AbstractTCPConnection implements Connection {

	protected Socket socket;

	/**
	 * The {@link #socket}'s {@link InputStream}
	 */
	protected InputStream in;
	/**
	 * The {@link #socket}'s {@link OutputStream}
	 */
	protected OutputStream out;

	private ReentrantLock readLock = new ReentrantLock();
	private ReentrantLock writeLock = new ReentrantLock();

	/**
	 * Creates an empty connection. Call {@link #connect(InetSocketAddress)} or
	 * {@link #connect(InetSocketAddress, int)} before using it!
	 */
	public AbstractTCPConnection() {
	}

	// /**
	// * Creates a new connection using the given socket.
	// *
	// * @param sock
	// * The socket to use for this connection.
	// * @throws NullPointerException
	// * When the given socket is null.
	// */
	// public AbstractTCPConnection(Socket sock) throws NullPointerException {
	// init(sock);
	// }

	protected void init(Socket sock) {
		lockRead();
		lockWrite();

		if (sock == null) {
			throw new NullPointerException("Given socket is null");
		}
		this.socket = sock;

		try {

			in = socket.getInputStream();
			out = socket.getOutputStream();

		} catch (IOException e) {
			e.printStackTrace();
		}

		unlockWrite();
		unlockRead();
	}

	/**
	 * Locks for a read operation on this connection.
	 * 
	 * @see ReentrantLock#lock()
	 */
	public void lockRead() {
		readLock.lock();
	}

	/**
	 * Tries to lock for a read operation.
	 * 
	 * @return true when lock was acquired, false otherwise
	 * 
	 * @see ReentrantLock#tryLock(long, TimeUnit);
	 */
	public boolean tryLockRead(int timeout, TimeUnit unit)
			throws InterruptedException {
		return readLock.tryLock(timeout, unit);
	}

	/**
	 * Unlocks for read operations
	 * 
	 * @see ReentrantLock#unlock()
	 */
	public void unlockRead() {
		readLock.unlock();
	}

	/**
	 * Locks for a write operation
	 * 
	 * @see ReentrantLock#lock()
	 */
	public void lockWrite() {
		writeLock.lock();
	}

	/**
	 * Tries to lock for a write operation
	 * 
	 * @return true when lock was acquired, false otherwise
	 * 
	 * @see ReentrantLock#tryLock(long, TimeUnit);
	 */
	public boolean tryLockWrite(int timeout, TimeUnit unit)
			throws InterruptedException {
		return writeLock.tryLock(timeout, unit);
	}

	/**
	 * Unlocks for read operations
	 * 
	 * @see ReentrantLock#unlock()
	 */
	public void unlockWrite() {
		writeLock.unlock();
	}

	/**
	 * Reads the given number of bytes from the input stream. Does not stop
	 * until the number of bytes were read or an exception occurred.
	 * 
	 * @param num
	 *            The number of bytes to read.
	 * @return the byte array with the read bytes. Size is equal to the
	 *         parameter <code>num</code>.
	 * @throws IOException
	 *             Thrown when an IOException occurred or the end of stream was
	 *             reached before all bytes were read.
	 */
	protected byte[] readBytes(int num) throws IOException {

		byte[] bytes = new byte[num];

		for (int i = 0; i < num; i++) {
			int byteInt = in.read();

			if (byteInt < 0) {
				socket.close();
				throw new IOException("Nothing received from "
						+ socket.getInetAddress() + ":" + socket.getPort()
						+ ". Socket on destination was shut down properly.");
			}

			bytes[i] = (byte) byteInt;
		}

		return bytes;

	}
	
	/**
	 * Closes the socket.
	 */
	public void close() throws IOException{
		socket.close();
	}
}

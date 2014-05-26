package com.depalma.whoswhere;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Queue;

import com.depalma.whoswhere.shared.Message;
import com.depalma.whoswhere.shared.messages.JoinMessage;

public class Communicator implements Runnable {

	private static Communicator singleton;
	private static Object sLock = new Object();
	public static final String SERVER_ADDR = "10.128.70.36";
	public static final int SERVER_PORT = 2055;

	public static Communicator getCommunicator() {
		if (singleton == null) {
			synchronized (sLock) {
				if (singleton == null) {
					singleton = new Communicator();
				}
			}
		}
		return singleton;
	}

	private Thread myThread;
	private boolean threadRunning = false;

	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private Queue<Message> messageQueue;
	private JoinMessage joinMessage;

	public Communicator() {
		myThread = new Thread(this);
		myThread.start();
	}
	
	public void setJoinMessage(JoinMessage msg) {
		joinMessage = msg;
	}

	public void queueMessage(Message message) {
		messageQueue.add(message);
		synchronized (myThread) {
			myThread.notify();
		}
	}

	private boolean sendMessage(Message message) {
		try {
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeByte((byte) 123);
			objOut.writeObject(message);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean connect() {
		try {
			socket = new Socket(SERVER_ADDR, SERVER_PORT);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			sendMessage(joinMessage);
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean sendMessages() {
		boolean result = true;
		while(!messageQueue.isEmpty() && result) {
			Message msg = messageQueue.peek();
			result &= sendMessage(msg);
			if(result) {
				messageQueue.poll();
			}
		}
		return result;
	}

	@Override
	public void run() {
		synchronized (myThread) {
			if (threadRunning)
				return;
			threadRunning = true;
		}
		while (true) {
			synchronized (myThread) {
				if (!messageQueue.isEmpty()) {
					if(!sendMessages()) {
						connect();
						continue;
					}
				}
				try {
					myThread.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

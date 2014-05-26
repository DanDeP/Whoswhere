package com.depalma.whoswhere;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Queue;

import com.depalma.whoswhere.shared.Message;

public class Communicator implements Runnable {

	private static Communicator singleton;
	private static Object sLock = new Object();
	
	public static Communicator getCommunicator() {
		if(singleton == null) {
			synchronized(sLock) {
				if(singleton == null) {
					singleton = new Communicator();
				}
			}
		}
		return singleton;
	}
	
	private Thread myThread;
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private Queue<Message> messageQueue;
	
	public Communicator() {
		myThread = new Thread(this);
	}
	
	public void send(Message message) {
		messageQueue.add(message);
	}
	
	@Override
	public void run() {
		
	}
	
	public void close() {
		
	}
}

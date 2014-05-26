package com.depalma.whoswhere.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.depalma.whoswhere.shared.Gender;
import com.depalma.whoswhere.shared.messages.JoinMessage;

public class Server {
	
	public static void main(String[] args) {
		try {
			ServerSocket srv = new ServerSocket(43599);
			ArrayList<Socket> sockets = new ArrayList<Socket>();
			while(true) {
				Socket incoming = srv.accept();	
				System.out.println("Incoming connection from "+incoming.getInetAddress().getHostAddress());
				sockets.add(incoming);
			}
		} catch (IOException e) {
			System.out.println("Port 43599 is in use. Exiting");
		}
	}
}

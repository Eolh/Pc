package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import view.MainFrameViewTest;

public class PCServerBackground {
	private ServerSocket serverSocket;
	private Socket socket;
	private MainFrameViewTest gui;
	
	public void setGui(MainFrameViewTest gui) {
		this.gui = gui;
	}

	public void setting() {
		try {
			serverSocket = new ServerSocket(8888);
			System.out.println("waiting...");
			socket = serverSocket.accept();
			gui.connectClient();
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class Receiver extends Thread{
		
	}
}

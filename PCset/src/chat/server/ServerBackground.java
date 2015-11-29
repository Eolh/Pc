package chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerBackground {

	// �̽�1. ���� �޽����� �ְ� �ް� �;��.
	// �̽�2. �ϱ����� ���� GUI�� ���鵵�� �ϰڽ��ϴ�.
	// �̽�3. ����

	private ServerSocket serverSocket;
	private Socket socket;
	private ServerGui gui;
	private String msg;

	// 세번째 중요한 것 사용자들의 정보를 저장하는 맵
	private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();

	public void setting() {

		try {
			
			Collections.synchronizedMap(clientsMap);//교통 정리
			serverSocket = new ServerSocket(9999);
			while (true) {
				// 서버가 할 일 :방문자를 계속 받아서, 쓰레드 리시버를 계속 생성
				System.out.println("서버대기중...");
				socket = serverSocket.accept(); // 먼저 서버가 할 일은 계속 반복해서 사용자를 받는다.
				System.out.println(socket.getInetAddress() + "에서 접속했습니다");
				// 여기서 새로운 사용자 쓰레드 클래스를 생성해서 소켓정보를 넣어줌
				Receiver receiver = new Receiver(socket);
				receiver.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final void setGui(ServerGui gui) {
		this.gui = gui;
	}

	public static void main(String[] args) {
		ServerBackground serverbackground = new ServerBackground();
		serverbackground.setting();
	}

	public void sendMessage(String msg2) {
		try {
			out.writeUTF("서버 :" + msg2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Receiver extends Thread {

		private DataInputStream in;
		private DataOutputStream out;

		// xxx 리시버가 할 일은 네트워크 소켓을 받아서 계속 듣고, 요청하는 일

		public Receiver(Socket socket) {
			try {
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				
				//리시버
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {

			try {
				msg = in.readUTF();
				System.out.println("클라이언트로 부터의 메시지: " + msg);
				gui.appendMsg(msg);

				while (in != null) {
					msg = in.readUTF();
					gui.appendMsg(msg);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

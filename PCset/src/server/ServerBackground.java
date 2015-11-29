package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerBackground {

	private ServerSocket serberSocket;
	private Socket socket;
	private ServerGUI gui;
	private String msg;
	
	// 3.사용자들의 정보를 저장하는 맵
	private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
	
	
	// Source - Generate Getters and Setters - gui�� select Setters ����
	public void setGui(ServerGUI gui) {
		this.gui = gui;
	}

	public void setting() {
		try {
			// 교통정리
			// 프로그램이 돌다보면 엉키는 경우가 있는데 이 한줄이 네트워크를 정리해줌.
			Collections.synchronizedMap(clientsMap);
			// 서버를 열었음.
			serberSocket = new ServerSocket(8888);
			
			// 클라이언트가 올 때까지 대기 (사용자 받는곳)
			while (true) {
				// 1. 서버가 하는 일 : 사용자를 계속 접속 받아서, 쓰레드 리시버를 생성하는것
				System.out.println("�����..");
				socket = serberSocket.accept();
				System.out.println(socket.getInetAddress() + "�� ���� �߽��ϴ�.");
				
				// 사용자 쓰레드 클래스 생성해서 소켓 정보 생성
				// 같은 이름의 reseiver이라도 쓰레드를 사용하면 다르게 나옴
				Receiver reseiver = new Receiver(socket);
				reseiver.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerBackground serverBackground = new ServerBackground();
		serverBackground.setting();
	}

	// 맵의 내용 ( 클라이언트 ) 저장과 삭제
	public void addClient(String nick, DataOutputStream out) {
		sendMessage(nick+"���� �����ϼ̽��ϴ�.\n");
		clientsMap.put(nick, out);
	}
	private void removeClient(String nick) {
		sendMessage(nick+"���� �����̽��ϴ�.\n");
		clientsMap.remove(nick);
	}
	//메세지 내용 전달
	public void sendMessage(String msg) {
		//닉네임
		Iterator<String> it = clientsMap.keySet().iterator();
		// 반복자 : 리스트 같은 컬렉션을 하나씩 뽑아내서 처리하는 것
		String key="";
		
		while(it.hasNext()){
			try {
				// 이렇게 해주는 이유는 그냥 it 으로 받으면 컬럼 이름이기 떄문에 
				// 다음 밑에 단계 값을 받기 위해서
				key = it.next();
				
				clientsMap.get(key).writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 	
	}
	
	//---------------------------------------------------
	// Thread를 사용하는 방법은 2가지
	// 인터페이스 구현 하는것을 실행하는 것
	// 쓰레드를 상속한 클래스를 바로 run 해서 동작 시키는 경우
	class Receiver extends Thread{
		
		// 소켓 - 소켓 끼리 주고 받는것을 스트림이라고 함
		private DataInputStream in;
		private DataOutputStream out;
		private String nick;
		// 2. 리시버가 하는 일 : 네트워크 소켓을 받아서 계쏙 듣고, 처리하는 일 !!
		public Receiver(Socket s){
			try {
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());

				// 리시버가 처음에는 클라이언트 아이디를 받아옴
				nick = in.readUTF();
				addClient(nick,out);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			
			// 소켓에서 in과 out 얻기
			try {
/*				msg = in.readUTF();

				System.out.println("Ŭ���̾�Ʈ �޼��� : " + msg);

				// Background 가 gui한테 msg 보냄
				gui.appendMsg(msg);
*/
				while (in != null) {
					msg = in.readUTF();
					sendMessage(msg); 
					gui.appendMsg(msg);
					//gui.appendMsg(msg);
				}
			} catch (IOException e) {
				// 사용자 접속 종료시 여기서 에러 발생, 여기서 리무브 해서 닉 없애줌
				removeClient(nick);
			}

		}
	}

}

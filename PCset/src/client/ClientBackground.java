package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientBackground {
	private Socket socket;
	// 소켓 - 소켓 끼리 주고 받는것을 스트림이라고 함
	private DataInputStream in;
	private DataOutputStream out;
	private ClientGUI gui;
	private String msg;
	private String nickName;
	
	public void setGui(ClientGUI gui) {
		this.gui = gui;
	}

	public void connet(){
		try {
			//서버 연결
			socket = new Socket("127.0.0.1",8888);
			System.out.println("�������� ��");
			
			// 소켓에서 in과 out 얻기
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			// 접속 하자마자 닉네임 전송하면 서버가 이것을 닉네임으로 인식하고 맵에 집어 넣음
			out.writeUTF(nickName);
			System.out.println("�޼��� ���ۿϷ�");
		
			while(in!=null){
				msg = in.readUTF();
				gui.appendMsg(msg);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String [] args){
		ClientBackground clientBackground = new ClientBackground();
		clientBackground.connet();
	}



	public void sendMessage(String msg2) {
		try {
			out.writeUTF(msg2);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void setNickname(String nickName) {
		this.nickName = nickName;
	}
}

package chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientBackground {

	private Socket socket;

	private DataInputStream in;
	private DataOutputStream out;
	private ClientGui gui;
	private String msg;

	public final void setGui(ClientGui gui) {
		this.gui = gui;
	}

	public void connet() {
		try {
			socket = new Socket("192.168.0.3", 9999);
			System.out.println("서버 연결됨");

			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());

			out.writeUTF("안녕 시벨롬아\n");
			System.out.println("클라이언트:메시지 전송 완료");

			while (in != null) {
				msg = in.readUTF();
				gui.appendMsg(msg);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ClientBackground clientbackground = new ClientBackground();
		clientbackground.connet();
	}

	public void sendMessage(String msg2) {
		try {
			out.writeUTF("클라이언트 :"+msg2);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}

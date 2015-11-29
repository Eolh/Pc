package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import view.FrameLogin;
import view.FrameManage;
import DAO.DaoLogin;
import DAO.H2DB_Initializer;
import asset.Setting;

public class Main {
	private static ServerSocket serverSocket;
	private Socket socket;
	private String msg;

	private FrameManage frameManage;
	private FrameLogin frameLogin;
	private DaoLogin daoLogin;
	private static Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();

	public static final void main(String[] args) throws Exception {
		Main main = new Main();
		main.frameLogin = new FrameLogin();
		main.frameLogin.setMain(main);
		// database 초기화(테이블 만듦)
		H2DB_Initializer hdInitializer = new H2DB_Initializer();
		hdInitializer.initDatabase();

		// login을 위한 데이터 액세스 오브젝트 만들고, 사용자 입력.
		DaoLogin daoLogin = new DaoLogin();
		// 프레임에 dao 주입
		main.frameLogin.setDaoLogin(daoLogin);
		main.setting();
	}

	public void showFrameManage(FrameLogin frameLogin) {
		frameLogin.dispose();
		FrameManage manageView = new FrameManage();
		try {
			manageView.setRectangles(FrameManage.class, manageView,
					Setting.class, Setting.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setting() throws IOException {
		Collections.synchronizedMap(clientsMap); // 이걸 교통정리 해줍니다^^
		serverSocket = new ServerSocket(9999);
		while (true) {
			/** XXX 01. 첫번째. 서버가 할일 분담. 계속 접속받는것. */
			System.out.println("서버 대기중...");
			socket = serverSocket.accept(); // 먼저 서버가 할일은 계속 반복해서 사용자를 받는다.

			System.out.println(socket.getInetAddress() + "에서 접속했습니다.");
			// 여기서 새로운 사용자 쓰레드 클래스 생성해서 소켓정보를 넣어줘야겠죠?!
			Receiver receiver = new Receiver(socket);
			receiver.start();
		}
	}

	class Receiver extends Thread {
		private DataInputStream in;
		private DataOutputStream out;
		private String info;

		/** XXX 2. 리시버가 한일은 자기 혼자서 네트워크 처리 계속..듣기.. 처리해주는 것. */
		public Receiver(Socket socket) throws IOException {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			info = in.readUTF();
			System.out.println(info);
		}

		public void run() {
			try {// 계속 듣기만!!
				while (in != null) {
					msg = in.readUTF();
					System.out.print(msg);
				}
			} catch (IOException e) {
				// 사용접속종료시 여기서 에러 발생. 그럼나간거에요.. 여기서 리무브 클라이언트 처리 해줍니다.
			}
		}
	}

}

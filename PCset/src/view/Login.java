package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.stream.IIOByteBuffer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
	private Socket socket;
	static JLabel label1, label2, label3,label4;
	public JTextField text1,text3;
	public JPasswordField text2;
	Button button1, button2, button3;
	public String id1, pw1;
	static String [] info;
    DataOutputStream out;
	Login() {
		
		try {
			socket = new Socket("192.168.0.3", 9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 패널1
		Panel p1 = new Panel();
		// 기본레이아웃을 flowLayout으로 준다.
		p1.setLayout(new FlowLayout());
		// 패널2
		Panel p2 = new Panel();
		
		// 이패널에는 그리드레이아웃을 준다 (가로,세로 , 넓이, 높이)라고 생각하면된다.
		p2.setLayout(new GridLayout(5, 4, 5, 5));
		
		// 버튼1
		button1 = new Button("로그인");
		button1.addActionListener(this);
		// 버튼2
		button2 = new Button("cancel");
		// 버튼3
		button3 = new Button("회원가입");

		// 패널1에 버튼 추가
		p1.add(button1);
		p1.add(button2);
		p1.add(button3);
		
		// 라벨
		label1 = new JLabel("ID : ");
		label1.setSize(10, 10);
		
		// 패널2에 라벨추가
		p2.add(label1);
		
		// 텍스트필드
		text1 = new JTextField(10);
		text1.setSize(10, 10);
		
		// 패널2에 텍스트필드 추가.
		p2.add(text1);

		label2 = new JLabel("PW");
		label2.setSize(10, 10);
		p2.add(label2);

		text2 = new JPasswordField();
		text2.setSize(10, 10);
		p2.add(text2);
		
		JPanel p4 = new JPanel();
		label4 = new JLabel("Seat");
		label4.setSize(10, 10);
		p2.add(label4);

		text3 = new JTextField();
		text3.setSize(10, 10);
		p2.add(text3);

		Panel p3 = new Panel();
		p3.setLayout(new FlowLayout());
		label3 = new JLabel("로그인");
		p3.add(label3);

		setTitle("Login");
		setSize(350, 350);
		setVisible(true);
		setLayout(new BorderLayout());
		
		// p1즉 버튼들을 아래쪽으로 배치한다.
		add(p1, BorderLayout.SOUTH);
		add(p2);
		// 로그인이란 라벨을 위쪽에 배치한다.
		add(p3, BorderLayout.NORTH);
		
		// 윈도우리스너를 줘서 x누르면 awt창이 꺼지도록 한다.
		addWindowListener(new WindowHandler());
	}

	public static void main(String[] args){
		new Login();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(text1.getText());
		System.out.println(new String(text2.getPassword()));
		System.out.println(text3.getText());

		// TODO Auto-generated method stub
		info[0]= text1.getText();
		info[1]= new String(text2.getPassword());
		info[2]= text3.getText();
		sendInfo(info);
	}
	public void sendInfo(String[] info) {
		for (int i=0 ; i<3;i++) {
			String info1 = info[i];
			System.out.println(info1);
			try {
				out.writeUTF(info1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

//윈도우 핸들러 x버튼 클릭시 종료 해주는것.
class WindowHandler extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}
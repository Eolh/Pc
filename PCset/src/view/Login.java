package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
	private Socket socket;
	private static final long serialVersionUID = 1L;
	private JPanel p;
	private JTextField tf_id;
	private JTextField tf_Num;
	private JPasswordField tf_pass; // 비밀번호
	private JTextArea taIntro;
	private JButton btn2, btn3; // 가입, 취소 버튼
	private GridBagLayout gb;
	private GridBagConstraints gbc;
	private DataOutputStream out;
	// 연동

	public Login() {
		super("회원가입");
		 try {
			socket = new Socket("192.168.0.3",9999);
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gb = new GridBagLayout();
		setLayout(gb);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// 아이디
		JLabel bId = new JLabel("아이디 : ");
		tf_id = new JTextField(20);
		// 그리드백에 붙이기
		gbAdd(bId, 0, 0, 1, 1);
		gbAdd(tf_id, 1, 0, 3, 1);

		// 비밀번호
		JLabel bPwd = new JLabel("비밀번호 : ");
		tf_pass = new JPasswordField(20);
		gbAdd(bPwd, 0, 1, 1, 1);
		gbAdd(tf_pass, 1, 1, 3, 1);

		// 자리 번호
		JLabel bNum = new JLabel("번호 :");
		tf_Num = new JTextField(20);
		gbAdd(bNum, 0, 2, 1, 1);
		gbAdd(tf_Num, 1, 2, 3, 1);

		// 버튼
		JPanel pButton = new JPanel();
		btn2 = new JButton("로그인");
		btn3 = new JButton("회원가입");
		pButton.add(btn2);
		pButton.add(btn3);
		gbAdd(pButton, 0, 10, 4, 1);

		btn2.addActionListener(this);
		btn3.addActionListener(this);

		setSize(350, 500);
		setVisible(true);

	}

	// 그리드백레이아웃에 붙이는 메소드
	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		// gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2, 2, 2, 2);
		add(c, gbc);
	}// gbAdd

	public static void main(String[] args) {
		new Login();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id = tf_id.getText().trim();
		String pwd = tf_pass.getText().trim();
		String seat = tf_Num.getText().trim();
		// TODO Auto-generated method stub
		sendInfo(id, pwd, seat);
	}

	public void sendInfo(String id, String password, String seat) {

		try {
			out.writeUTF(id);
			out.writeUTF(password);
			out.writeUTF(seat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
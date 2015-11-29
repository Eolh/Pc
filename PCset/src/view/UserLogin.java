package view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserLogin extends JFrame{
	public UserLogin(){
		setLayout(new GridLayout(3,2));
		setSize(500,300);
		JLabel log = new JLabel("아이디"); 
		JTextField logf = new JTextField(10);
		JLabel pwd = new JLabel("비밀번호"); 
		JTextField pwdf = new JPasswordField(10);
		JLabel seat = new JLabel("자리번호"); 
		JTextField seatf = new JTextField(10);

		add(log);
		add(logf);
		add(pwd);
		add(pwdf);
		add(seat);
		add(seatf);
		setVisible(true);
	}
	public static void main(String [] args){
		new UserLogin();
	}
}

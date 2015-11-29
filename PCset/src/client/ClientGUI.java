package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.ServerBackground;
import server.ServerGUI;

public class ClientGUI extends JFrame implements ActionListener {
	 
    private JTextArea jta = new JTextArea(40, 25);
    private JTextField jtf = new JTextField(25);
    // 연동
    private ClientBackground client = new ClientBackground();
    private static String nickName;
    public ClientGUI() {
 
    	add(jta, BorderLayout.CENTER);
        add(jtf, BorderLayout.SOUTH);
        jtf.addActionListener(this);
 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(800, 100, 400, 600);
        setTitle("Ŭ���̾�Ʈ");
 
        client.setGui(this);
        client.setNickname(nickName);
        client.connet();
    }
 
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("����� �г��Ӻ��� �����ϼ��� : ");
    	nickName = scanner.nextLine();
    	scanner.close();
    	
        new ClientGUI();
    }
    
    //말치면 보내는 부분
    public void actionPerformed(ActionEvent e) {
        String msg = nickName+ ":" + jtf.getText() + "\n";
        client.sendMessage(msg);
        jtf.setText("");
    }

	public void appendMsg(String msg) {
		 //�������� ���� �޼������� ��
		jta.append(msg);
	}
}
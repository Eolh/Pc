package chat.server;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 

public class ServerGUI extends JFrame implements ActionListener {
 
    private JTextArea jta = new JTextArea(40, 25);
    private JTextField jtf = new JTextField(25);
    // 연동
    // GUI가 서버를 실행 시키기 위해 객체생성 
    private ServerBackground server = new ServerBackground();
 
    public ServerGUI() {
 
        add(jta, BorderLayout.CENTER);
        add(jtf, BorderLayout.SOUTH);
        jtf.addActionListener(this);
 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(200, 100, 400, 600);
        setTitle("�����κ�");
        
        //GUI가 여기 주소를 가져와 !
        server.setGui(this);
        server.setting();
        
 
        
    }
 
    public static void main(String[] args) {
        new ServerGUI();
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = "���� : "+jtf.getText() + "\n";
        System.out.print(msg);
        server.sendMessage(msg);
        jtf.setText("");
    }
 
    //클라이언트에서 날라온 메세지가 뜨는곳
    public void appendMsg(String msg) {
        jta.append(msg);
    }
 
}
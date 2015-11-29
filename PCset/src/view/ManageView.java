package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import view.panel.ClockMessage;
import view.panel.ImgClock;
import view.panel.MyStarPanel;
import view.panel.PanImgload;

@SuppressWarnings("serial")
public class ManageView extends JFrame {

	private JLayeredPane layeredPane = new JLayeredPane();;

	public ManageView() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1600, 900);
		setTitle("ManageView");
		setLayout(null);

		// 내 윈도우 화면
		Dimension frameSize = this.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2,
				(windowSize.height - frameSize.height) / 2);
		setPanel(layeredPane).setBounds(0, 0, 1600, 900);

		// 배경
		JPanel backGround = new PanImgload("img/mainHud_back.png");
		setPanel(backGround).setBounds(0, -30, 1600, 900);

		// 시계
		ImgClock imgClock = new ImgClock();
		setPanel(imgClock).setBounds(15, 20, 179, 149);

		// 시계글씨
		ClockMessage clockMessage = new ClockMessage();
		setPanel(clockMessage).setBounds(80, 53, 100, 100);

		// 움직이는 광원처리
		MyStarPanel myStarPanel = new MyStarPanel();
		setPanel(myStarPanel).setBounds(0, -30, 1600, 900);

		//Thread
		threadStart(imgClock,clockMessage,myStarPanel);
		
		// 최종삽입
		add(setJLayeredPane(backGround, myStarPanel, imgClock, clockMessage));

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ManageView();
	}

	public JComponent setPanel(JComponent panel) {
		panel.setLayout(null);
		panel.setOpaque(false);
		return panel;
	}

	public JLayeredPane setJLayeredPane(Component... components) {
		int i = 0;
		for (Component component : components)
			layeredPane.add(component, new Integer(i++));
		return layeredPane;
	}
	
	public void threadStart(Runnable...target){
		for (Runnable runnable : target) {
			new Thread(runnable).start();
		}
	}
}

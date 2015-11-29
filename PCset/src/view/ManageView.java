package view;

import java.awt.Component;
import java.awt.Rectangle;
import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import view.panel.ClockMessage;
import view.panel.ImgClock;
import view.panel.MyStarPanel;
import view.panel.PanImgload;
import asset.Setting;

@SuppressWarnings("serial")
public class ManageView extends JFrame {

	private JLayeredPane layeredPane = new JLayeredPane();
	// JPanels
	private JPanel backGround = new PanImgload("img/mainHud_back.png");
	private ImgClock imgClock = new ImgClock();
	private ClockMessage clockMessage = new ClockMessage();
	private MyStarPanel myStarPanel = new MyStarPanel();

	public ManageView() {

		setLayout(null);
		setVisible(true);
		setTitle("ManageView");
		setSize(Setting.bDimen);

		// 내 윈도우 화면
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(Setting.locationX, Setting.locationY);

		// setPanel(layeredPane).setBounds(Setting.bRectangle);

		// 배경
		// setPanel(backGround).setBounds(Setting.bpanRectangle);

		// 시계
		// setPanel(imgClock).setBounds(Setting.imgClock);

		// 시계글씨
		// setPanel(clockMessage).setBounds(Setting.clockMessage);

		// 움직이는 광원처리
		// setPanel(myStarPanel).setBounds(Setting.bpanRectangle);

		// Thread
		// threadStart(imgClock, clockMessage, myStarPanel);

		// 최종삽입
		add(setJLayeredPane(backGround, myStarPanel, imgClock, clockMessage));

	}

	public void setRectangles(Class<?> clazz, Object instancs,
			Class<?> targetClass, Object target)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
		Object tempObject = null;
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			tempObject = field.get(instancs);

			if (tempObject instanceof JComponent) {
				Rectangle rectangle = (Rectangle) targetClass.getDeclaredField(
						field.getName()).get(target);
				((JComponent) tempObject).setBounds(rectangle);
				((JComponent) tempObject).setOpaque(false);
				((JComponent) tempObject).setLayout(null);
			}

			if (tempObject instanceof Runnable) {
				new Thread((Runnable) tempObject).start();
			}
		}

	}

	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		ManageView manageView = new ManageView();
		manageView.setRectangles(ManageView.class, manageView, Setting.class,
				Setting.getInstance());

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

	public void threadStart(Runnable... target) {
		for (Runnable runnable : target) {
			new Thread(runnable).start();
		}
	}
}

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import view.panel.ClockMessage;
import view.panel.ImgClock;
import view.panel.MyStarPanel;
import view.panel.PanImgload;
import asset.Setting;

@SuppressWarnings("serial")
public class FrameManage extends JFrame {

	public JLayeredPane layeredPane = new JLayeredPane();
	// JPanels
	private PanImgload backGround = new PanImgload("img/mainHud_back.png");
	private MyStarPanel myStarPanel = new MyStarPanel();
	private ImgClock imgClock = new ImgClock();
	private ClockMessage clockMessage = new ClockMessage();
	int posXpanSeat, posYpanSeat;
	PanSeat[] pan = new PanSeat[50];
	JPanel seat50 = new JPanel();

	public FrameManage() {
		// Configure this Frame
		setLayout(null);
		setVisible(true);
		setTitle("ManageView");
		setSize(Setting.bDimen);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(Setting.locationX, Setting.locationY);

		for (int seat = 0; seat < 50; seat++) {
			pan[seat] = new PanSeat(seat);
			if (seat % 10 == 0 && seat != 0) {
				posXpanSeat = 0;
				posYpanSeat += 140;
			}
			this.pan[seat].addMouseListener(this.pan[seat]);
			pan[seat].setBounds(posXpanSeat, posYpanSeat, 99, 99);
			posXpanSeat += 135;
			// seat50.add(pan[seat]);

		}
		
			
		
		
		add(setJLayered(backGround, myStarPanel, imgClock, clockMessage, seat50));
		add(layeredPane);
		new SeatThread().start();
		
	}

	public static void main(String[] args) throws Exception {
		FrameManage manageView = new FrameManage();
		manageView.setRectangles(FrameManage.class, manageView, Setting.class,
				Setting.getInstance());

	}

	// Setting inner Methods
	private JComponent setJLayered(Component... components) {
		int i = 0;
		for (Component component : components)
			layeredPane.add(component, new Integer(i++));
		return layeredPane;
	}

	// Reflection Practice
	public void setRectangles(Class<?> clazz, Object instance,
			Class<?> targetClass, Object target) throws Exception {
		Object tempObject = null;
		for (Field field : clazz.getDeclaredFields()) {
			if ((tempObject = field.get(instance)) instanceof JComponent) {
				((JComponent) tempObject).setBounds((Rectangle) targetClass
						.getDeclaredField(field.getName()).get(target));
				((JComponent) tempObject).setOpaque(false);
				((JComponent) tempObject).setLayout(null);
			}
			if (tempObject instanceof Runnable)
				new Thread((Runnable) tempObject).start();
		}
	}

	// TODO 계속 들어감

	// 좌석 쇼 쓰레드
	class SeatThread extends Thread {
		@Override
		public void run() {
			Set<Integer> randomNumbers = new LinkedHashSet<Integer>();
			for (; randomNumbers.size() < 50;) {
				int x = (int) ((Math.random() * 50));
				randomNumbers.add(x);
			}
			int tmp = 0;
			try {
				for (Integer s : randomNumbers) {
					tmp++;
					if (tmp > 30)
						Thread.sleep(5);
					if (tmp == 50) {
						Thread.sleep(1000);
						System.out.println("50번째");
					}
					seat50.add(pan[s]);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class PanSeat extends JPanel implements MouseListener {
		private BufferedImage img = null;
		JLabel[] label = new JLabel[4];
		private int numSeat;
		private int flag = 0;

		public PanSeat(int numSeat) {
			this.numSeat = numSeat;
			check(flag);

			setLayout(null);

			JPanel panImg = new InnerPanel();
			panImg.setBounds(0, 0, 99, 99);
			panImg.setOpaque(false);

			// 상태정보 패널
			JPanel panContent = new JPanel();
			panContent.setLayout(null);
			panContent.setBounds(0, 0, 99, 99);
			int posLabel = 15;
			for (int i = 0; i < 4; i++) {

				switch (i) {
				case 1:
					label[i] = new JLabel((numSeat + 1) + ". 빈자리");
					break;
				case 2:
					try {
						label[i] = new JLabel(
								main.Main.setseatinfo[numSeat+1].getId());
					} catch (Exception e) {
						label[i] = new JLabel("");
						// TODO: handle exception
					}break;
				case 3:
					try {
						label[i] = new JLabel(
								main.Main.setseatinfo[numSeat+1].getId());
					} catch (Exception e) {
						label[i] = new JLabel("");
						// TODO: handle exception
					}break;
				default:
					label[i] = new JLabel("");

					break;
				}

				label[i].setBounds(20, posLabel, 80, 15);
				posLabel += 16;
				label[i].setForeground(new Color(36, 205, 198));
				label[i].setFont(new Font("배달의민족 한나", 1, 12));
				panContent.add(label[i]);
			}
			panContent.setOpaque(false);

			// 제이레이어패널
			JLayeredPane panLayered = new JLayeredPane();
			panLayered.setBounds(0, 0, 1600, 900);
			panLayered.setLayout(null);
			panLayered.setOpaque(false);
			panLayered.add(panImg, new Integer(0), 0);
			panLayered.add(panContent, new Integer(1), 0);
			add(panLayered);
			setVisible(true);
			setOpaque(false);
			setFocusable(true);
		}

		class InnerPanel extends JPanel {
			private static final long serialVersionUID = 1547128190348749556L;

			public void paint(Graphics g) {
				super.paint(g);
				g.drawImage(img, 0, 0, null);
			}
		}

		public void check(int flag) {
			if (flag == 0) {
				img("gameOff");
			} else {
				img("gameOn");
			}
		}

		public void img(String filename) {
			// 이미지 받아오기 - gameOn, gameOff (로그인, 로그오프)
			try {

				img = ImageIO.read(new File("img/" + filename + ".png"));

			} catch (IOException e) {
				System.out.println("이미지 불러오기 실패!");
				System.exit(0);
			}
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			flag = (flag != 0) ? 0 : 1;
			check(flag);

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
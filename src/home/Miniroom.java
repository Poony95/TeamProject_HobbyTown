package home;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import dao.MiniroomDAO;
import first.LogInPage;

//아바타 action 삽입을 위해 Home Panel 안에 Miniroom Panel 삽입
public class Miniroom extends JPanel {
	private Image homeimage;
	private MyAvatar avatar;
	public Miniroom(int NO) { 
		// 여성이면 여성 캐릭터, 남성이면 남성 캐릭터 삽입
		String gender = new MiniroomDAO().getGender(NO).trim();
		if(gender.equals("여")) gender="girl.png";
		else if(gender.equals("남")) gender="boy.png";
		avatar = new MyAvatar(gender);
		
		// avatar의 움직임을 위해 avatar 클래스의 마우스리스너 삽입
		addMouseMotionListener(avatar);
		
		// avatar의 움직임을 위한 쓰레드 구현
		class MyThread extends Thread{
			public void run() {
				while(true) {
					repaint();
					try { Thread.sleep(100);} 
					catch (InterruptedException e) {}
				}
			}
		}
		new MyThread().start();
		
		// 화면 설정 ( 홈 이미지 수정 필요 )
		setSize(620,300);
		setBackground(new Color(206,212,192));
		homeimage = new ImageIcon("image.gif").getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
	} 

	// image를 그려주는 paint 메서드 
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(homeimage, 0, 0, null);
		avatar.draw(g);
	}
}

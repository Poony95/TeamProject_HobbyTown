package home;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;

import dao.MiniroomDAO;
import dao.UserDAO;
import first.LogInPage;

// avatar의 움직임을 위해 별도로 클래스 작성
public class MyAvatar extends Thread implements MouseMotionListener{
	private static int x, y;
	private Image img = null;
	
	// 아바타 생성
	public MyAvatar(String gender) {
		int[] arr = new MiniroomDAO().getAvatar(LogInPage.getNO());
		x = arr[0]; y = arr[1];
		try {
			img = new ImageIcon(gender).getImage();
		} catch (Exception e) {System.out.println(" MyAvatar 예외발생 : "+e.getMessage());}
	}
	
	// 마우스로 아바타를 드래그하면 그 위치로 이동
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		x = Math.max(0, x);	// 아바타가 미니룸 범위를 벗어나지 않게 조정
	    y = Math.max(0, y);
	    x = Math.min(570, x);
	    y = Math.min(210, y);
		new MiniroomDAO().saveAvatar(x, y, LogInPage.getNO());
	}
	
	public void mouseMoved(MouseEvent e) {}

	public void draw(Graphics g) {
		g.drawImage(img, x, y, 50,90, null);
	}
}
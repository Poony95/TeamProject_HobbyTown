package home;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import messages.MessageFrame;
import messages.MessageNew;
import utillist.RoundedButton;
// 친구 미니룸 방문 화면을 위한 프레임
public class FriendRoom extends JFrame {
	public FriendRoom(int NO){
		Home home= new Home(NO);
		// 친구 미니룸 방문시 접근할 수 있는 패널 설정
		JPanel panel = new JPanel();
		JLabel jlb = new JLabel(new ImageIcon("froom.png"));	// 친구 미니룸 방문 중 이미지 생성
		jlb.setBackground(Color.white);
		jlb.setOpaque(false);
		panel.add(jlb);
		panel.setBounds(150, 35, 620, 210);
		home.add(panel);
		
		// 화면 설정
		add(home);
		setVisible(true);
		setSize(800, 600);
	
	}
}

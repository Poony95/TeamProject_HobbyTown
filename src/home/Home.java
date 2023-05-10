package home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import dao.MiniroomDAO;
import first.LogInPage;
import utillist.RoundedButton;


public class Home extends JPanel {
	private static JLabel jlb_title;
	private static JTextArea jta_home_content;	// 둥근 모서리를 위해 utillist의 roundedbutton 사용
	private static JLabel home_image;
	private int likes;
	public Home(int NO) {
		// 제목 생성 (MiniroomDAO의 getMTitle() 호출)
		jlb_title=new JLabel("     "+new MiniroomDAO().getMTitle(NO));
		jlb_title.setBackground(Color.white);
		jlb_title.setOpaque(true);
		jlb_title.setFont(new Font("", Font.PLAIN, 15));
		jlb_title.setBounds(0, 0, 800, 30);
		
		// 미니홈피 생성
		JPanel miniroom = new Miniroom(NO);
		miniroom.setBounds(150, 250, 620, 300);
		
		// 친구리스트, 인기글 리스트 생성
		JPanel panel = new JPanel(new GridLayout(1,2));
		JTabbedPane jtp_board = new BoardTab();
		JTabbedPane jtp_friend = new FriendTab();
		jtp_board.setPreferredSize(new Dimension(300, 190));
		jtp_friend.setPreferredSize(new Dimension(300, 190));
		panel.add(jtp_board);
		panel.add(jtp_friend);
		panel.setBounds(150, 35, 620, 210);
		
		// 소개글 생성 ( MiniroomDAO의 getMcontent 호출 )
		jta_home_content = new JTextArea(new MiniroomDAO().getMcontent(NO));
		jta_home_content.setBounds(15,235,120,200);
		JButton home_content = new RoundedButton();
		home_content.setBounds(10, 230, 130, 210);
		home_content.setEnabled(false);
		home_content.setBackground(Color.white);
		home_content.setVerticalAlignment(SwingConstants.TOP);
		
		// 소개이미지 생성 ( MiniroomDAO의 getMImg 호출 )
		home_image = new JLabel(new ImageIcon(new MiniroomDAO().getMImg(NO)));
		home_image.setBounds(10, 50, 130, 180);
		home_image.setBackground(Color.white);
		
		// 좋아요 버튼 생성 ( MiniroomDAO의 getMLikes 호출 )
		likes = new MiniroomDAO().getMLikes(NO);
		RoundedButton home_likes = new RoundedButton("♥ "+likes);
		home_likes.setBounds(10, 470, 130, 60);
		home_likes.setBackground(Color.white);
		home_likes.setForeground(Color.red);
		
		
		// 좋아요 버튼 actionlistener
		home_likes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int re = new MiniroomDAO().updateMLikes(NO); // 좋아요 값을 +1 해주는 update sql 실행
				if(re==-1) System.out.println("home_likes 오류 발생");
				else {
					int likes = new MiniroomDAO().getMLikes(NO);
					home_likes.setText("♥ "+likes);
					home_likes.updateUI();	// 증가한 좋아요 값 업데이트
				}
				
			}
		});
		
		// 환경 설정
		setLayout(null);
		add(jlb_title);
		add(jta_home_content);
		add(home_content);
		add(home_image);
		add(home_likes);
		add(miniroom);
		// 친구를 관리할 수 있는 panel은 방문자일 경우 숨김처리
		if(LogInPage.getNO()==NO) {
			add(panel);
		}
	
	}
	
	public static void setTitle(String title) {
		jlb_title.setText(new MiniroomDAO().getMTitle(LogInPage.getNO()));
	}
	public static void setContent(String content) {
		jta_home_content.setText(new MiniroomDAO().getMcontent(LogInPage.getNO()));
	}
	public static void setImage(String img) {
		home_image.setIcon(new ImageIcon(new MiniroomDAO().getMImg(LogInPage.getNO())));
	}
	
}


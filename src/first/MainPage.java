package first;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Game.GameMain;
import boards.Board;
import customer.MainTest;
import dao.UserDAO;
import home.Home;
import home.Setting;
import messages.Message;
import utillist.RoundedButton;


public class MainPage extends JFrame implements ActionListener{
	private RoundedButton[] btn_arr;
	private JPanel pan_main;
	private CardLayout cadrdlayout = new CardLayout();
	private JPanel pan_home;
	
	public MainPage() {
		// 메인 화면 및 각 게시판 Panel 생성 (CardLayout을 이용한 화면 전환)
		pan_main = new JPanel(cadrdlayout);
		pan_home = new Home(LogInPage.getNO());	// JPanel을 상속한 Home 클래스 호출
		JPanel pan_board = new Home(LogInPage.getNO());
		JPanel pan_memssage = new Message();	// JPanel을 상속한 Memo 클래스 호출
		JPanel pan_game = new Home(LogInPage.getNO());
		JPanel pan_cs = new MainTest();
		JPanel pan_setting = new Setting();	// JPanel을 상속한 Setting 클래스 호출
		
		pan_main.add("홈", pan_home);
		pan_main.add("쪽지함", pan_memssage);
		pan_main.add("고객센터",pan_cs);
		pan_main.add("관리", pan_setting);
		
		// 기본으로 보여지는 화면은 홈 Panel
		cadrdlayout.show(pan_main, "홈");
		
		// 배열을 사용한 우측 메뉴 버튼 생성
		JPanel pan_btn = new JPanel(new GridLayout(7, 1));
		String[] btn_name = {"홈", "커뮤니티", "쪽지함", "게임", "고객센터", "관리", "로그아웃"};
		btn_arr =new RoundedButton[7];
		for(int i=0; i<7; i++) {
			btn_arr[i] = new RoundedButton(btn_name[i]);
			btn_arr[i].setFont(new Font("HY헤드라인", Font.BOLD, 15));
			btn_arr[i].addActionListener(this);
			pan_btn.add(btn_arr[i]);
		}
		
		// 화면 설정
		add(pan_main, BorderLayout.CENTER);
		add(pan_btn, BorderLayout.EAST);
		setVisible(true);
		setSize(900,600);
		setBackground(new Color(206,212,192));
		setLocation(330, 70);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MY HOBBY TOWN");
	}
	
	// 버튼 액션 생성
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_arr[6]) {	// 로그아웃 버튼 눌렀을 때 로그인 페이지로 돌아가기
			dispose();
			new LogInPage();
		} else if(e.getSource()==btn_arr[1]) {	// 커뮤니티 버튼 눌렀을 때 board frame 호출
			new Board();	
		} else if(e.getSource()==btn_arr[3]) {
			new GameMain();
		} 
		
		// 카드 레이아웃을 사용한 화면 전환
		cadrdlayout.show(pan_main, e.getActionCommand());
	}
	
	
	


}

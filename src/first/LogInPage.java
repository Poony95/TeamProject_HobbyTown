package first;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import admin.Admin;
import dao.UserDAO;
import vo.UserVO;

// 로그인 화면
public class LogInPage extends JFrame {
	private JTextField jtf_id;
	private JTextField jtf_pwd;
	private JButton btn_login;
	private JButton btn_signup;
	private static  int NO;

	public LogInPage() {
		// 배경 이미지 넣기 ( 라벨에 넣어줌)
		JLabel jlb = new JLabel(new ImageIcon("login.jpg"));
		jlb.setBounds(-10, -10, 790, 730);
		
		
		// 로그인 판넬 구성
 		jtf_id = new JTextField(10);
		jtf_pwd = new JPasswordField(10);
		jtf_id.setBounds(350, 340, 200, 40);
		jtf_pwd.setBounds(350, 400, 200, 40);

		// 버튼 판넬 구성
		btn_login = new JButton("로그인");
		btn_signup = new JButton("회원가입");
		btn_login.setBounds(230, 450, 130, 70);
		btn_signup.setBounds(390, 450, 130, 70);
	
		
		// 전체 프레임 화면 구성
		setLayout(null);
		setLocation(310, 50);
		add(jlb);
		add(jtf_id);
		add(jtf_pwd);
		add(btn_login);
		add(btn_signup);
		
		// 로그인 버튼 actionlistener
		btn_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String input_id = jtf_id.getText();
				String input_pwd = jtf_pwd.getText();
				// id, pwd 텍스트 필드가 공란일 때
				if(input_id.equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
					return;
				} else if(input_pwd.equals("")) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
					return;
				}
				// id 존재 여부 검사
				if(new UserDAO().checkID(input_id)<1) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다.");
					jtf_id.setText("");
					jtf_pwd.setText("");
					return;
				}
				
				if(input_id.equals("admin") && input_pwd.equals("admin")) {
					new Admin();
					dispose();
					return;
				}
				// id, pwd 로그인 유효성 검사
				String PWD = new UserDAO().getPWD(input_id);
				if(input_pwd.equals(PWD)) {		// 아이디와 비밀번호가 맞을떄
					NO = new UserDAO().getNO(input_id);		// 로그인 시 프로그램 내에서 쓰일 회원 번호 초기화
					new MainPage();
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "비밀번호가 잘못되었습니다.");
					jtf_pwd.setText("");
				}
			}
		});
		
		// 회원가입 버튼 눌렀을 때 회원가입 frame 호출
		btn_signup.addActionListener(e->new Signup());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(790, 730);
	}
	
	// main 메서드
	public static void main(String[] args) {
		new LogInPage();
	}
	
	public static int getNO() {
		return NO;
	}
	


}

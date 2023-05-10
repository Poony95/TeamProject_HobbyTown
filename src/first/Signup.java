package first;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.UserDAO;

import utillist.RoundedButton;
import utillist.Utils;
import vo.UserVO;

public class Signup extends JFrame implements KeyListener {
	private JTextField jtf_id;
	private JPasswordField jtf_pwd;
	private JPasswordField jtf_pwdcheck;
	private JTextField jtf_name;
	private JTextField jtf_jumin1;
	private JPasswordField jtf_jumin2;
	private JTextField jtf_phone1;
	private JTextField jtf_phone2;
	private JTextField jtf_phone3;
	private JTextField jtf_email1;
	private JTextField jtf_email2;
	private boolean isCheckedID;
	private boolean isCheckedPWD;
	private JLabel jlb_pwdCheck;
	
	public Signup() {
		
		// 회원가입을 위한 필드 생성... 
		jtf_id = new JTextField(10);
		jtf_pwd = new JPasswordField(10);
		jtf_pwdcheck = new JPasswordField(10);
		jtf_name = new JTextField(10);
		jtf_jumin1 = new JTextField(6);
		jtf_jumin2 = new JPasswordField(6);
		jtf_phone1 = new JTextField(3);
		jtf_phone2 = new JTextField(4);
		jtf_phone3 = new JTextField(4);
		jtf_email1 = new JTextField(6);
		jtf_email2 = new JTextField(6);
		// 콤보박스를 이용한 관심사, 거주지 선택 
		JComboBox<String> addr_list = new JComboBox<String>(Utils.address);
		JComboBox<String> interest_list = new JComboBox<String>(Utils.interest);
		
		// 각 패널에 각 필드 담아주기
		JPanel pan_id = new JPanel();
		JPanel pan_pwd = new JPanel();
		JPanel pan_pwdcheck = new JPanel();
		JPanel pan_name = new JPanel();
		JPanel pan_interest = new JPanel();
		JPanel pan_jumin = new JPanel();
		JPanel pan_address = new JPanel();
		JPanel pan_phone = new JPanel();
		JPanel pan_email = new JPanel();
		pan_id.add(new JLabel(String.format("%-8s", "아이디")));
		pan_id.add(jtf_id);
		pan_pwd.add(new JLabel(String.format("%-8s","비밀번호")));
		pan_pwd.add(jtf_pwd);
		pan_pwdcheck.add(new JLabel(String.format("%-8s","비밀번호 확인")));
		pan_pwdcheck.add(jtf_pwdcheck);
		pan_name.add(new JLabel(String.format("%-8s","이름")));
		pan_name.add(jtf_name);
		pan_interest.add(new JLabel(String.format("%-8s","관심사")));
		pan_interest.add(interest_list);
		pan_jumin.add(new JLabel(String.format("%-8s","주민번호")));
		pan_jumin.add(jtf_jumin1);
		pan_jumin.add(new JLabel("-"));
		pan_jumin.add(jtf_jumin2);
		pan_address.add(new JLabel(String.format("%-8s","거주지")));
		pan_address.add(addr_list);
		pan_phone.add(new JLabel(String.format("%-8s","전화번호")));
		pan_phone.add(jtf_phone1);
		pan_phone.add(new JLabel("-"));
		pan_phone.add(jtf_phone2);
		pan_phone.add(new JLabel("-"));
		pan_phone.add(jtf_phone3);
		pan_email.add(new JLabel(String.format("%-8s","이메일")));
		pan_email.add(jtf_email1);
		pan_email.add(new JLabel("@"));
		pan_email.add(jtf_email2);
		 
		// 아이디 중복 확인버튼 만들기
		JButton btn_idcheck = new RoundedButton("중복 확인");
		btn_idcheck.setBounds(260,80, 80, 20);
		isCheckedID = false;	// 중복확인 여부 boolean

		
		// 비밀번호 확인 라벨 만들기
		jlb_pwdCheck = new JLabel();
		jlb_pwdCheck.setForeground(Color.red);
		JPanel pan_checkjlb = new JPanel();
		pan_checkjlb.add(jlb_pwdCheck);
		
		// 생성한 필드를 pan_input에 넣어주기
		JPanel pan_input = new JPanel();
		pan_input.add(pan_id);
		pan_input.add(pan_pwd);
		pan_input.add(pan_pwdcheck);
		pan_input.add(pan_checkjlb);
		pan_input.add(pan_name);
		pan_input.add(pan_interest);
		pan_input.add(pan_address);
		pan_input.add(pan_jumin);
		pan_input.add(pan_phone);
		pan_input.add(pan_email);
		pan_input.setBounds(20, 70, 300, 400);
		
		// 가입버튼, 제목 label 만들기
		JButton btn_signup = new RoundedButton("가입하기");
		JLabel title_signup = new JLabel("회원가입");
		title_signup.setBackground(Color.white);
		title_signup.setBounds(140, 30, 130, 30);
		title_signup.setFont(new Font("", Font.BOLD, 25));
		btn_signup.setBounds(140, 490, 100, 50);
		
		
		// 화면 설정
		setLayout(null);
		add(title_signup);
		add(btn_idcheck);
		add(pan_input);
		add(btn_signup);
		setSize(400,620);
		setVisible(true);
		setTitle("HOBBY TOWN 회원가입");
		
		// 아이디, 비밀번호 확인 위한 keylistener 
		jtf_pwdcheck.addKeyListener(this);
		jtf_pwd.addKeyListener(this);
		jtf_id.addKeyListener(this);
		
		// 중복확인 버튼 actionlistener
		btn_idcheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(jtf_id.getText().equals("")) checkSignUp("아이디를 입력하세요.");
					if(jtf_id.getText().length()<5) checkSignUp("아이디는 5글자 이상이어야 합니다.");
					int re = new UserDAO().checkID(jtf_id.getText());
					if(re>0) {
						jtf_id.setText("");
						checkSignUp("이미 사용중인 아이디있니다. 다시 입력해주세요.");
					}
				} catch (RuntimeException e2) {
					return;
				}
				JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
				isCheckedID = true;
			}
		});
		
		// 회원가입 버튼 actionlistener
		btn_signup.addActionListener(new ActionListener() {
			// 회원가입 검증
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!isCheckedID) checkSignUp("아이디 중복확인을 해주세요.");
					if(!isCheckedPWD) checkSignUp("비밀번호를 확인 해주세요.");
					if(jtf_name.getText().equals("")) {
						checkSignUp("이름을 입력해주세요.");
					} else if(jtf_jumin1.getText().equals("") || jtf_jumin2.getText().equals("")) {
						checkSignUp("주민번호를 입력해주세요.");
					} else if(jtf_phone1.getText().equals("") || jtf_phone2.getText().equals("") || jtf_phone3.getText().equals("")) {
						checkSignUp("전화번호를 입력해주세요.");
					} else if(jtf_email1.getText().equals("") || jtf_email2.getText().equals("")) {
						checkSignUp("이메일을 입력해주세요.");
					}
					if(jtf_jumin1.getText().length()!=6 || jtf_jumin2.getText().length()!=7) {
						checkSignUp("정확한 주민번호를 입력하세요.");
						jtf_jumin1.setText("");
						jtf_jumin2.setText("");
					}
					Integer.parseInt(jtf_phone1.getText());
					Integer.parseInt(jtf_phone2.getText());
					Integer.parseInt(jtf_phone3.getText());
					Integer.parseInt(jtf_jumin1.getText());
					Integer.parseInt(jtf_jumin2.getText());
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "전화번호와 주민번호에 숫자를 입력하세요.");
					return;
				} catch (RuntimeException e3) {
					return;
				}
				
				// 입력 값을 uservo에 담기
				UserVO u = new UserVO();
				u.setName(jtf_name.getText());
				u.setId(jtf_id.getText());
				u.setPwd(jtf_pwd.getText());
				u.setJumin(jtf_jumin1.getText()+jtf_jumin2.getText());
				u.setEmail(jtf_email1.getText()+"@"+jtf_email2.getText());
				u.setPhone(jtf_phone1.getText()+"-"+jtf_phone2.getText()+"-"+jtf_phone3.getText());
				u.setAddress(Utils.address[addr_list.getSelectedIndex()]);
				u.setInterest(Utils.interest[interest_list.getSelectedIndex()]);
				
				// db에 회원 정보 저장
				int re = new UserDAO().signup(u);
				if(re==-1) JOptionPane.showMessageDialog(null, "회원가입 실패");
				else {
					JOptionPane.showMessageDialog(null, "회원가입 성공");
				}
			}
		});
	}
	
	
	// 비밀번호 중복 확인을 위해 상속받은 keylistener
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getSource() == jtf_id) {
			isCheckedID = false;
			return;
		} 
		if(jtf_pwd.getText().length()<8) {
			jlb_pwdCheck.setText("비밀번호는 8자 이상이어야 합니다.");
		} else {
			String tmp = jtf_pwdcheck.getText();
			if(tmp.equals(jtf_pwd.getText())) {
				jlb_pwdCheck.setText("비밀번호가 일치합니다.");
				isCheckedPWD = true;
			} else {
				jlb_pwdCheck.setText("비밀번호가 일치하지 않습니다.");
				isCheckedPWD = false;
			}
		}
	}
	
	
	// 입력 값이 잘못될 시 runtimeexception 발생
	public void checkSignUp(String s) {
		JOptionPane.showMessageDialog(null, s);
		throw new RuntimeException();
	}
}

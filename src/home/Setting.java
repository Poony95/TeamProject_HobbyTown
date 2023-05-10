package home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.MiniroomDAO;
import dao.UserDAO;
import first.LogInPage;
import oracle.net.aso.j;
import oracle.net.aso.l;
import utillist.Utils;
import vo.UserVO;


public class Setting extends JPanel implements ActionListener, KeyListener {
	private JTextArea jta_title;
	private JTextArea jta_content;
	private JFileChooser jfc_img;
	private JComboBox<String> jcb_thema;
	private String[] thema_arr = {"default", "a","b","c"};
	private JPasswordField jpf_pwd;
	private JPasswordField jpf_pwd_check;
	private JLabel jlb_pwdCheck;
	private JTextField jtf_email1;
	private JTextField jtf_email2;
	private JComboBox<String> jcb_interest;
	private JTextField jtf_phone1;
	private JTextField jtf_phone2;
	private JTextField jtf_phone3;
	private JComboBox<String> jcb_address;
	private JButton btn_open;
	private JButton[] btn_change;
	
	public Setting() {
		// 버튼 배열 생성
		btn_change = new JButton[7];
		for(int i=0; i<btn_change.length; i++) {
			btn_change[i] = new JButton("수정");
			btn_change[i].addActionListener(this);
		}
		// 홈화면 관리 컴포넌트 선언
		jta_title = new JTextArea(1,20);
		jta_title.setText(new MiniroomDAO().getMTitle(LogInPage.getNO()));
		jta_content = new JTextArea(10, 20);
		jta_content.setText(new MiniroomDAO().getMcontent(LogInPage.getNO()));
		jfc_img = new JFileChooser();
		jcb_thema = new JComboBox<String>(thema_arr);
		btn_open = new JButton("파일 찾기");
		btn_open.addActionListener(this);
		jfc_img.setFileFilter(new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));
		// 홈화면 관리 panel 설정
		JPanel setting_home = new JPanel();
		setting_home.setLayout(new BoxLayout(setting_home, BoxLayout.Y_AXIS));
		JPanel pan_title = new JPanel(new FlowLayout());
		pan_title.add(new JLabel("제목 변경 : "));
		pan_title.add(new JScrollPane(jta_title));
		pan_title.add(btn_change[0]);
		pan_title.setOpaque(false);
		jta_title.setBackground(Color.white);
		JPanel pan_content = new JPanel(new FlowLayout());
		pan_content.add(new JLabel("소개글 변경 : "));
		pan_content.add(new JScrollPane(jta_content,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		pan_content.add(btn_change[1]);
		pan_content.setOpaque(false);
		jta_content.setBackground(Color.white);
		jta_content.setLineWrap(true);
		JPanel pan_img = new JPanel(new FlowLayout());
		pan_img.add(new JLabel("소개 이미지 변경 : "));
		pan_img.add(btn_open);
		pan_img.setOpaque(false);
		
		JLabel jlb_title_home = new JLabel("홈 화면 설정");
		jlb_title_home.setAlignmentX(Component.CENTER_ALIGNMENT);
		jlb_title_home.setFont(new Font("", Font.BOLD, 15));
		setting_home.add(jlb_title_home);
		setting_home.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_home.add(pan_title);
		setting_home.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_home.add(pan_content);
		setting_home.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_home.add(pan_img);
		setting_home.add(Box.createRigidArea(new Dimension(0, 30)));
		setting_home.setBackground(Color.LIGHT_GRAY);
		setting_home.setPreferredSize(new Dimension(385, 545));

		// 개인정보 관리 panel 설정
		UserVO u =  new UserDAO().getUser(LogInPage.getNO());
		jpf_pwd = new JPasswordField(10);
		jpf_pwd_check = new JPasswordField(10);
		jlb_pwdCheck = new JLabel();
		jlb_pwdCheck.setForeground(Color.red);
		jpf_pwd.addKeyListener(this);
		jpf_pwd_check.addKeyListener(this);
		jcb_interest = new JComboBox<String>(Utils.interest);
		jcb_interest.setSelectedIndex(Utils.getInterestIndex(u.getInterest()));
		String[] phone = u.getPhone().split("-");
		jtf_phone1 = new JTextField(phone[0]);
		jtf_phone2 = new JTextField(phone[1]);
		jtf_phone3 = new JTextField(phone[2]);
		String[] email = u.getEmail().split("@");
		jtf_email1 = new JTextField(email[0]);
		jtf_email2 = new JTextField(email[1]);
		jcb_address = new JComboBox<String>(Utils.address);
		jcb_address.setSelectedIndex(Utils.getAddrIndex(u.getAddress()));
		
		JPanel setting_personal = new JPanel();
		setting_personal.setLayout(new BoxLayout(setting_personal, BoxLayout.Y_AXIS));
		JPanel pan_pwd = new JPanel(new FlowLayout());
		pan_pwd.add(new JLabel("비밀번호 변경 : "));
		pan_pwd.add(jpf_pwd);
		pan_pwd.setOpaque(false);
		jpf_pwd.setBackground(Color.WHITE);
		JPanel pan_pwd_check = new JPanel(new FlowLayout());
		pan_pwd_check.add(new JLabel("비밀변호 변경 확인 : "));
		pan_pwd_check.add(jpf_pwd_check);
		pan_pwd_check.add(btn_change[2]);
		pan_pwd_check.setOpaque(false);
		jpf_pwd_check.setBackground(Color.WHITE);
		JPanel pan_email = new JPanel(new FlowLayout());
		pan_email.add(new JLabel("이메일 변경 : "));
		pan_email.add(jtf_email1);
		pan_email.add(new JLabel("@"));
		pan_email.add(jtf_email2);
		pan_email.add(btn_change[3]);
		pan_email.setOpaque(false);
		JPanel pan_interest = new JPanel(new FlowLayout());
		pan_interest.add(new JLabel("관심사 변경 : "));
		pan_interest.add(jcb_interest);
		pan_interest.add(btn_change[4]);
		pan_interest.setOpaque(false);
		JPanel pan_phone = new JPanel(new FlowLayout());
		pan_phone.add(new JLabel("전화번호 변경 : "));
		pan_phone.add(jtf_phone1);
		pan_phone.add(new JLabel("-"));
		pan_phone.add(jtf_phone2);
		pan_phone.add(new JLabel("-"));
		pan_phone.add(jtf_phone3);
		pan_phone.add(btn_change[5]);
		pan_phone.setOpaque(false);
		JPanel pan_addr = new JPanel(new FlowLayout());
		pan_addr.add(new JLabel("주소 변경 : "));
		pan_addr.add(jcb_address);
		pan_addr.add(btn_change[6]);
		pan_addr.setOpaque(false);
		JLabel jlb_title_personal = new JLabel("개인 화면 설정");
		jlb_title_personal.setAlignmentX(Component.CENTER_ALIGNMENT);
		jlb_title_personal.setFont(new Font("", Font.BOLD, 15));
		JLabel jlb_name = new JLabel("이름 : "+ u.getName());
		jlb_name.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel jlb_id = new JLabel("아이디 : "+ u.getId());
		jlb_id.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel jlb_date = new JLabel("가입 일자 : "+ u.getUpdate());
		jlb_date.setAlignmentX(Component.CENTER_ALIGNMENT);
		setting_personal.add(jlb_title_personal);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(jlb_name);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(jlb_id);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(jlb_date);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(pan_pwd);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(pan_pwd_check);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(jlb_pwdCheck);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(pan_email);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(pan_interest);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(pan_phone);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.add(pan_addr);
		setting_personal.add(Box.createRigidArea(new Dimension(0, 10)));
		setting_personal.setBackground(Color.lightGray);
		setting_personal.setPreferredSize(new Dimension(385, 545));
		
		JPanel pan_setting = new JPanel(new FlowLayout());
		pan_setting.add(setting_home);
		pan_setting.add(setting_personal);
		add(pan_setting);
		setVisible(true);
		setSize(300,400);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_change[0]) {	// 제목 수정 버튼
			String title = jta_title.getText();
			int re = new MiniroomDAO().saveTitle(title, LogInPage.getNO());
			if(re!=1) {
				JOptionPane.showMessageDialog(null, "수정에 실패하였습니다.");
				return;
			}
			JOptionPane.showMessageDialog(null, "제목이 수정되었습니다.");
			Home.setTitle(title);
			
		} else if(e.getSource()==btn_change[1]) {	// 소개글 수정 버튼
			String content = jta_content.getText();
			int re = new MiniroomDAO().saveContent(content, LogInPage.getNO());
			if(re!=1) {
				JOptionPane.showMessageDialog(null, "수정에 실패하였습니다.");
				return;
			}
			JOptionPane.showMessageDialog(null, "소개글이 수정되었습니다.");
			Home.setContent(content);
		} else if (e.getSource()==btn_change[2]) {	// 비밀번호 체크
			String PWD =jpf_pwd.getText();
			if(!jpf_pwd_check.getText().equals(PWD)) {
				JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다. \n 다시 입력해주세요.");
				jpf_pwd.setText("");
				jpf_pwd_check.setText("");
				return;
			}
			int re = new UserDAO().updatePWD(PWD, LogInPage.getNO());
			if(re==-1) {
				JOptionPane.showMessageDialog(null, "비밀번호 변경에 실패했습니다.");
				return;
			} 
			JOptionPane.showMessageDialog(null, "비밀번호를 변경했습니다.");
		} else if (e.getSource()==btn_change[3]) {	// 이메일 변경
			String email =jtf_email1.getText()+"@"+jtf_email2.getText();
			int re = new UserDAO().updateEmail(email, LogInPage.getNO());
			if(re==-1) {
				JOptionPane.showMessageDialog(null, "이메일 변경에 실패했습니다.");
				return;
			} 
			JOptionPane.showMessageDialog(null, "이메일을 변경했습니다.");
		} else if (e.getSource()==btn_change[4]) {	// 관심사 변경
			String interest = Utils.interest[jcb_interest.getSelectedIndex()];
			int re = new UserDAO().updateInterest(interest, LogInPage.getNO());
			if(re==-1) {
				JOptionPane.showMessageDialog(null, "관심사 변경에 실패했습니다.");
				return;
			} 
			JOptionPane.showMessageDialog(null, "관심사를 변경했습니다.");
		} else if (e.getSource()==btn_change[5]) {	// 번호 변경
			String phone =jtf_phone1.getText()+"-"+jtf_phone2.getText()+"-"+jtf_phone3.getText();
			int re = new UserDAO().updatePhone(phone, LogInPage.getNO());
			if(re==-1) {
				JOptionPane.showMessageDialog(null, "전화번호 변경에 실패했습니다.");
				return;
			} 
			JOptionPane.showMessageDialog(null, "전화번호를 변경했습니다.");
		} else if (e.getSource()==btn_change[6]) {	// 주소 변경
			String addr = Utils.address[jcb_address.getSelectedIndex()];
			int re = new UserDAO().updateAddr(addr, LogInPage.getNO());
			if(re==-1) {
				JOptionPane.showMessageDialog(null, "주소 변경에 실패했습니다.");
				return;
			} 
			JOptionPane.showMessageDialog(null, "주소를 변경했습니다.");
		} else if (e.getSource()==btn_open) {	// 홈 이미지 수정 버튼
			if(jfc_img.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = jfc_img.getSelectedFile();
				String img = file.getPath();
				int re = new MiniroomDAO().saveMImg(img, LogInPage.getNO());
				if(re==-1) {
					JOptionPane.showMessageDialog(null, "이미지 변경에 실패했습니다.");
					return;
				}
				JOptionPane.showMessageDialog(null, "이미지가 수정되었습니다.");
				Home.setImage(img);
			}
		} 
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
		String tmp = jpf_pwd.getText();
		if(tmp.equals(jpf_pwd_check.getText())) {
			jlb_pwdCheck.setText("비밀번호가 일치합니다.");
		} else {
			jlb_pwdCheck.setText("비밀번호가 일치하지 않습니다.");
		}
	}
	

}

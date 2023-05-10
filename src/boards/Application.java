package boards;

import javax.swing.*;

import dao.ApplicationDAO;
import vo.BoardVO;

import java.awt.*;
import java.awt.event.*;
//수정 예정
public class Application extends JFrame {
    JLabel title, content;
    JTextArea jta_application;
    JButton applyButton;

    public Application(int b_no) {

        Container c = getContentPane();
        c.setLayout(null);

        // 타이틀 레이블
        title = new JLabel("신청하기");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        title.setBounds(20, 20, 120, 30);
        c.add(title);

        // 신청내용 레이블과 필드
        content = new JLabel("신청내용");
        content.setFont(new Font("맑은 고딕", Font.TRUETYPE_FONT, 12));
        content.setBounds(20, 70, 50, 30);
        c.add(content);

        jta_application = new JTextArea();
        jta_application.setBounds(20, 100, 440, 150);
        c.add(jta_application);

        // 신청하기 버튼
        applyButton = new JButton("신청하기");
        applyButton.setBounds(200, 280, 100, 30);
        c.add(applyButton);
        
        setTitle("신청하기");
        setSize(500, 380);
        setVisible(true);
        setLocationRelativeTo(null);
        
        applyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String content = jta_application.getText();
				BoardVO vo = new BoardVO();
				vo.setAp_content(content);
				ApplicationDAO dao = new ApplicationDAO();
				int re = dao.insertApplication(vo);
	            JOptionPane.showMessageDialog(null, "신청이 완료되었습니다.");
	            dispose();  // 폼을 닫습니다.
			}
		});
    }
	

}
;
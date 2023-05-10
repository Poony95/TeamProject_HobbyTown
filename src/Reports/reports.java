package Reports;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import User_No.User_inform;
import dao.commentDAO;
import dao.reportsDAO;
import vo.reportsVO;
//가능하면 신고사유 콤보박스 추가
public class reports extends JFrame {
	JTextArea jta_reportReason;
	
	public reports() {
		setTitle("사용자 신고하기");
		setLayout(new BorderLayout());
		
		User_inform u_Infom = new User_inform();
		HashMap<String, Object> map_inform = u_Infom.userInform();

		JLabel jlb_reportUser = new JLabel("신고대상: "+map_inform.get("user_id"));
		JLabel jlb_reportReason = new JLabel("신고사유를 작성해주세요==>");
		jlb_reportReason.getText();
		jta_reportReason = new JTextArea();
		JScrollPane jsp = new JScrollPane(jta_reportReason);
		JButton btn_report = new JButton("확인");
		JButton btn_cancel = new JButton("취소");
		
		JPanel p_upper = new JPanel();	//신고대상, 신고사유 라벨
		p_upper.setLayout(new GridLayout(2,1));
		p_upper.add(jlb_reportUser);
		p_upper.add(jlb_reportReason);
		
		JPanel p_reportMain = new JPanel();
		p_reportMain.setLayout(new BorderLayout());
		JPanel p_buttonArea = new JPanel();
		
		p_reportMain.add(jsp, BorderLayout.CENTER);
		p_buttonArea.add(btn_report);
		p_buttonArea.add(btn_cancel);
		
		add(p_upper, BorderLayout.NORTH);
		add(p_reportMain, BorderLayout.CENTER);
		add(p_buttonArea, BorderLayout.SOUTH);
		
		setSize(400, 300);
		setVisible(true);
		
		btn_report.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String r_content = jta_reportReason.getText();
				reportsVO voR = new reportsVO(r_content);
				reportsDAO daoR = new reportsDAO();
				int re = daoR.report(voR);
				if(re == 1) {
					int chk = JOptionPane.showConfirmDialog(null, "작성내용과 같이 신고하시겠습니까?", "신고확인", JOptionPane.YES_NO_OPTION);
						if(chk == 0) {
							JOptionPane.showMessageDialog(null, "신고처리 되었습니다.");
							setVisible(false);
						}
				}
	
			}
		});
		
		btn_cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int chk = JOptionPane.showConfirmDialog(null, "취소하시겠습니까?", "신고 취소하기", JOptionPane.YES_NO_OPTION);
				if(chk==0) {
					setVisible(false);
				}
				
			}
		});
	}
}
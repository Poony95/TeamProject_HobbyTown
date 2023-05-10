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
//�����ϸ� �Ű���� �޺��ڽ� �߰�
public class reports extends JFrame {
	JTextArea jta_reportReason;
	
	public reports() {
		setTitle("����� �Ű��ϱ�");
		setLayout(new BorderLayout());
		
		User_inform u_Infom = new User_inform();
		HashMap<String, Object> map_inform = u_Infom.userInform();

		JLabel jlb_reportUser = new JLabel("�Ű���: "+map_inform.get("user_id"));
		JLabel jlb_reportReason = new JLabel("�Ű������ �ۼ����ּ���==>");
		jlb_reportReason.getText();
		jta_reportReason = new JTextArea();
		JScrollPane jsp = new JScrollPane(jta_reportReason);
		JButton btn_report = new JButton("Ȯ��");
		JButton btn_cancel = new JButton("���");
		
		JPanel p_upper = new JPanel();	//�Ű���, �Ű���� ��
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
					int chk = JOptionPane.showConfirmDialog(null, "�ۼ������ ���� �Ű��Ͻðڽ��ϱ�?", "�Ű�Ȯ��", JOptionPane.YES_NO_OPTION);
						if(chk == 0) {
							JOptionPane.showMessageDialog(null, "�Ű�ó�� �Ǿ����ϴ�.");
							setVisible(false);
						}
				}
	
			}
		});
		
		btn_cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int chk = JOptionPane.showConfirmDialog(null, "����Ͻðڽ��ϱ�?", "�Ű� ����ϱ�", JOptionPane.YES_NO_OPTION);
				if(chk==0) {
					setVisible(false);
				}
				
			}
		});
	}
}
package messages;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dao.FriendDAO;
import dao.MessageDAO;
import dao.UserDAO;
import first.LogInPage;
import vo.MessageVO;

public class MessageNew extends MessageFrame{
	private String[] arr;
	private JComboBox<String> jcb_list;
	MessageNew(){
		super();
		ArrayList<String> list = new FriendDAO().getFriendlist(LogInPage.getNO());
		arr = list.toArray(new String[list.size()]);
		jcb_list = new JComboBox<String>(arr);
		
		pan_recp.add(jcb_list);
		
		add(jlb_recp);
		add(jcb_list);
		add(jsp);
		add(btn_send);
		add(btn_exit);

		// btn_send에 적용될 actionlistener 생성
		btn_send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = arr[jcb_list.getSelectedIndex()];
				String ID = str.substring(str.indexOf("(")+1, str.length()-1);
				MessageVO m = new MessageVO();
				m.setContent(jtf_content.getText());
				m.setSender_no(LogInPage.getNO());
				m.setRecipient_no(new UserDAO().getNO(ID));
				int re = new MessageDAO().sendMessages(m);
				if(re==-1) JOptionPane.showMessageDialog(null, "쪽지 전송이 실패했습니다.");
				else {
					JOptionPane.showMessageDialog(null, "쪽지 전송에 성공했습니다.");
					dispose();
				}	
			}
		});
		
	}
}
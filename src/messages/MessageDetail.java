package messages;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.FriendDAO;
import dao.MessageDAO;
import dao.UserDAO;
import first.LogInPage;
import vo.MessageVO;
// �� �޼����� �����ִ� jframe
public class MessageDetail extends MessageFrame {
	private MessageVO m;
	public MessageDetail(String type, MessageVO m) {
		this.m=m;
		System.out.println(m);
		// ���� ���� ���� textarea ����
		jta_content.setText(m.getContent());
		jta_content.setEditable(false);

		// ���� ����������, ���� ������������ ���� �ٸ� String ����
		String tmp = type.equals("send")?"���� ��� : ":"���� ��� : ";
		
		// ȭ�鼳��
		add(new JLabel(tmp+m.getSender_name()));
		add(new JScrollPane(jta_content));
		if(type.equals("receive")) {
			add(btn_reply);
		}
		add(btn_delete);
		add(btn_exit);
		
		// �� ��ư actionlistener ����
		btn_reply.addActionListener(e->new MessageReply(m));
		btn_delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int re = new MessageDAO().deleteMessage(m.getM_no()); 
				if(re==-1) {
					JOptionPane.showMessageDialog(null, "���� ���� ����");
				} else {
					JOptionPane.showMessageDialog(null, "���� ���� ����");
					dispose();
					new Message().loadReceivedMessages();
					new Message().loadSentMessages();
				}
				
			}
		});
		
	}
}

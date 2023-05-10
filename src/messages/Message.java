package messages;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import dao.FriendDAO;
import dao.MessageDAO;
import dao.UserDAO;
import first.LogInPage;
import utillist.RoundedButton;
import utillist.Utils;
import vo.MessageVO;

public class Message extends JPanel implements MouseListener {
	private Vector<Vector<String>> receive_rowData;
	private Vector<String> receive_colName;
	private JTable receive_table;
	private Vector<Vector<String>> send_rowData;
	private Vector<String> send_colName;
	private JTable send_table;
	private ArrayList<MessageVO> receive_list;
	private ArrayList<MessageVO> send_list;
	private JCheckBox jcb_check;
	private JTabbedPane jtp;
	public Message() {
		// �������� ������ ���� checkbox ����
		jcb_check = new JCheckBox("���� ���� ������ ����");
		jcb_check.setBounds(590, 100, 170, 20);
		
		// ���� ������ ���̺� ����
		receive_colName = new Vector<String>();
		receive_rowData = new Vector<Vector<String>>();
		receive_table = Utils.makeJTable(new String[] {"���� ���", "����", "���� �ð�", "����"}, receive_rowData, receive_colName);
		JScrollPane jsp_receive = new JScrollPane(receive_table);
		jsp_receive.setBounds(20, 100, 750, 400);
		loadReceivedMessages();
		
		// ���� ������ ����
		send_colName = new Vector<String>();
		send_rowData = new Vector<Vector<String>>();
		send_table = Utils.makeJTable(new String[] {"�޴� ���", "����", "���� �ð�", "����"}, send_rowData, send_colName);
		JScrollPane jsp_send = new JScrollPane(send_table);
		jsp_send.setBounds(20, 100, 750, 400);
		
		// ������ ��ư ����
		JButton btn_new = new RoundedButton("�� ����");
		btn_new.setBounds(360, 505, 100,40);
		
		
		// ���� �� ���� 
		JLabel jlb_messages = new JLabel("������");
		jlb_messages.setFont(new Font("", Font.BOLD, 30));
		jlb_messages.setBounds(370, 20, 100, 80);
		// �� ����
		jtp = new JTabbedPane();
		jtp.addTab("���� ������", jsp_receive);
		jtp.addTab("���� ������", jsp_send);
		jtp.setBounds(20, 100, 750, 400);
		// Message panel ȯ�� ����
		setLayout(null);
		add(jcb_check);
		add(btn_new);
		add(jlb_messages);
		add(jtp);
		
		// ���� table ���콺 ������ ����
		receive_table.addMouseListener(this);
		send_table.addMouseListener(this);
		jtp.addMouseListener(this);
		
		// ������ ���� üũ�ڽ� actionlistener ����
		jcb_check.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtp.getSelectedIndex()==0) {
					loadReceivedMessages();
				} else if(jtp.getSelectedIndex()==1) {
					loadSentMessages();
				}
			}
		});
		// ������ actionlistener ����
		btn_new.addActionListener(e->new MessageNew());
	}
	
	// ���� ���� ����Ʈ�� �ҷ����� �޼���
	public void loadReceivedMessages() {
		receive_rowData.clear(); 
		receive_list = new MessageDAO().viewReceivedMessages(LogInPage.getNO());
		for(MessageVO m:receive_list) {
			if(jcb_check.isSelected() && m.getState().equals("Y")) continue;
			Vector<String> v = new Vector<String>();
			v.add(m.getSender_name());
			v.add(m.getContent());
			v.add(String.valueOf(m.getDate_sent()));
			v.add(m.getState().equals("Y")?"����":"������");
			receive_rowData.add(v);
		}
		receive_table.updateUI();
	}
	// ���� ���� ����Ʈ�� �ҷ����� �޼���
	public void loadSentMessages() {
		send_rowData.clear(); 
		send_list = new MessageDAO().viewSentMessages(LogInPage.getNO());
		for(MessageVO m:send_list) {
			if(jcb_check.isSelected() && m.getState().equals("Y")) continue;
			Vector<String> v = new Vector<String>();
			v.add(m.getRecipient_name());
			v.add(m.getContent());
			v.add(String.valueOf(m.getDate_sent()));
			v.add(m.getState().equals("Y")?"����":"������");
			send_rowData.add(v);
		}
		send_table.updateUI();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==receive_table) {
			int row = receive_table.getSelectedRow();
			MessageVO m = receive_list.get(row);
			if(new MessageDAO().changeState(m)==1) {
				loadReceivedMessages();
				new MessageDetail("receive",m);	
			}
		} else if(e.getSource()==send_table) {
			loadSentMessages();
			int row = send_table.getSelectedRow();
			MessageVO m = send_list.get(row);
			new MessageDetail("send",m);	
		} else if(e.getSource()==jtp) {
			if(jtp.getSelectedIndex()==0) loadReceivedMessages();
			else if (jtp.getSelectedIndex()==1) loadSentMessages();
					
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}

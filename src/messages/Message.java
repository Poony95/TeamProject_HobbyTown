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
		// 읽지않은 쪽지만 보기 checkbox 생성
		jcb_check = new JCheckBox("읽지 않은 쪽지만 보기");
		jcb_check.setBounds(590, 100, 170, 20);
		
		// 받은 쪽지함 테이블 생성
		receive_colName = new Vector<String>();
		receive_rowData = new Vector<Vector<String>>();
		receive_table = Utils.makeJTable(new String[] {"보낸 사람", "내용", "보낸 시각", "상태"}, receive_rowData, receive_colName);
		JScrollPane jsp_receive = new JScrollPane(receive_table);
		jsp_receive.setBounds(20, 100, 750, 400);
		loadReceivedMessages();
		
		// 보낸 쪽지함 생성
		send_colName = new Vector<String>();
		send_rowData = new Vector<Vector<String>>();
		send_table = Utils.makeJTable(new String[] {"받는 사람", "내용", "보낸 시각", "상태"}, send_rowData, send_colName);
		JScrollPane jsp_send = new JScrollPane(send_table);
		jsp_send.setBounds(20, 100, 750, 400);
		
		// 새쪽지 버튼 생성
		JButton btn_new = new RoundedButton("새 쪽지");
		btn_new.setBounds(360, 505, 100,40);
		
		
		// 제목 라벨 생성 
		JLabel jlb_messages = new JLabel("쪽지함");
		jlb_messages.setFont(new Font("", Font.BOLD, 30));
		jlb_messages.setBounds(370, 20, 100, 80);
		// 탭 생성
		jtp = new JTabbedPane();
		jtp.addTab("받은 쪽지함", jsp_receive);
		jtp.addTab("보낸 쪽지함", jsp_send);
		jtp.setBounds(20, 100, 750, 400);
		// Message panel 환경 설정
		setLayout(null);
		add(jcb_check);
		add(btn_new);
		add(jlb_messages);
		add(jtp);
		
		// 쪽지 table 마우스 리스너 삽입
		receive_table.addMouseListener(this);
		send_table.addMouseListener(this);
		jtp.addMouseListener(this);
		
		// 안읽은 쪽지 체크박스 actionlistener 삽입
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
		// 새쪽지 actionlistener 삽입
		btn_new.addActionListener(e->new MessageNew());
	}
	
	// 받은 쪽지 리스트를 불러오는 메서드
	public void loadReceivedMessages() {
		receive_rowData.clear(); 
		receive_list = new MessageDAO().viewReceivedMessages(LogInPage.getNO());
		for(MessageVO m:receive_list) {
			if(jcb_check.isSelected() && m.getState().equals("Y")) continue;
			Vector<String> v = new Vector<String>();
			v.add(m.getSender_name());
			v.add(m.getContent());
			v.add(String.valueOf(m.getDate_sent()));
			v.add(m.getState().equals("Y")?"읽음":"안읽음");
			receive_rowData.add(v);
		}
		receive_table.updateUI();
	}
	// 보낸 쪽지 리스트를 불러오는 메서드
	public void loadSentMessages() {
		send_rowData.clear(); 
		send_list = new MessageDAO().viewSentMessages(LogInPage.getNO());
		for(MessageVO m:send_list) {
			if(jcb_check.isSelected() && m.getState().equals("Y")) continue;
			Vector<String> v = new Vector<String>();
			v.add(m.getRecipient_name());
			v.add(m.getContent());
			v.add(String.valueOf(m.getDate_sent()));
			v.add(m.getState().equals("Y")?"읽음":"안읽음");
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

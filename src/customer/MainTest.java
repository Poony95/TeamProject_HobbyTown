package customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import first.LogInPage;



public class MainTest extends JPanel{
	//<탭 생성화면>
	JTextArea jta_content;
	JButton btn_chat;
	JTabbedPane pane;
	JTabbedPane inner_pane; //메인탭 중 2개의 이너탭을 포함하는 탭1 
	Frequent_QnA tab;		//메인탭 중 탭2
	Inner_qna tab01;		//이너탭으로 넣을 클래스1
	Qna_List tab02;			//이너탭으로 넣을 클래스2
	
	public MainTest() {
		JPanel p1 = new JPanel();
		JLabel label = new JLabel("하비타운 고객센터");
		label.setFont(new Font("맑은고딕",getFont().BOLD,20));
		
		btn_chat = new JButton("채팅 상담하기");
		jta_content = new JTextArea();
	
	    pane = new JTabbedPane();
		inner_pane = new JTabbedPane();
		tab01 = new Inner_qna();
		tab02 = new Qna_List();
		tab = new Frequent_QnA();
		
		JPanel p = new JPanel();
		p.setBackground(new Color(221,234,255));
		p.setLayout(new BorderLayout());
		p.add(label, BorderLayout.CENTER);
		p.add(btn_chat,BorderLayout.EAST);
		add(p, BorderLayout.NORTH);
		
		pane.addTab("1:1문의", inner_pane);
		pane.addTab("자주묻는 질문", tab);
		inner_pane.addTab("문의하기", tab01);
		inner_pane.addTab("문의내역", tab02);
		inner_pane.setPreferredSize(new Dimension(680,480));
		tab.setPreferredSize(new Dimension(680,480));
		tab01.setPreferredSize(new Dimension(680,480));
		tab02.setPreferredSize(new Dimension(680,480));
		
		add(pane, BorderLayout.CENTER);
		
		setSize(700,600);
		setVisible(true);
		
		//<문의글 등록 후 문의내역탭 눌렀을 때 등록된 값을 추가한 화면을 보여주는 이벤트 리스너>
		inner_pane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int row = inner_pane.getSelectedIndex();
				if(row==0) {
				
				}else {
					
					tab02.loadData();
				}
			}
		});
		//<실시간 채팅버튼을 누르면 채팅창 실행되는 이벤트리스너>
		btn_chat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 int no = LogInPage.getNO();
		            if(no==0) {
		               ChatHandler server = new ChatHandler();
		            }
		            else {
		            Chat chat = new Chat();
		            }
			}
		});
	}
}


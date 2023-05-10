package messages;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public abstract class MessageFrame extends JFrame {
	protected JTextArea jtf_content;
	protected JButton btn_send;
	protected JButton btn_exit;
	protected JLabel jlb_recp;
	protected JPanel pan_recp;
	protected JScrollPane jsp;
	protected JButton btn_delete;
	protected JButton btn_reply;
	protected JTextArea jta_content;
	public MessageFrame() {
		jtf_content = new JTextArea(10, 20);
		jlb_recp = new JLabel("받는 사람 : ");
		jsp = new JScrollPane(jtf_content);
		btn_send = new JButton("전송");
		btn_reply = new JButton("답장");
		btn_delete = new JButton("삭제");
		btn_exit = new JButton("닫기");
		pan_recp = new JPanel();
		jta_content = new JTextArea(10,20);
		
		// 화면설정
		setLayout(new FlowLayout());
		setVisible(true);
		setSize(250,300);
		
		btn_exit.addActionListener(e->dispose());
		
	}
}



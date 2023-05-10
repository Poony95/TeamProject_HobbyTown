package customer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatHandler extends JFrame {
	JTextField jtf;
	JTextArea jta;
	JButton btn;
	
	ServerSocket serversocket;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	
	public ChatHandler() {
		JLabel label = new JLabel("관리자모드");
		label.setFont(new Font("맑은고딕", getFont().BOLD, 15));
		JButton btn2 = new JButton("종료");
		jta = new JTextArea();
		JScrollPane jsp = new JScrollPane();
		jta.setEditable(false);
		JPanel p = new JPanel();
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.setBackground(new Color(255,192,203));
		p2.add(label, BorderLayout.CENTER);
		p2.add(btn2, BorderLayout.EAST);
		jtf = new JTextField(35);
		btn = new JButton("전송");
	
		p.add(jtf);
		p.add(btn);
		
		add(p2, BorderLayout.NORTH);
		add(jta, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);
		
		setTitle("채팅상담 관리자모드");
		setSize(450,600);
		setVisible(true);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerThread th = new ServerThread();
				th.sendMessage();
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		ServerThread serverThread = new ServerThread();
		serverThread.setDaemon(true);
		serverThread.start();
		
	}
	
	public static void main(String[] args) {
		new ChatHandler();
	}
	class ServerThread extends Thread{
		public void run() {
			try {
				serversocket = new ServerSocket(9003); //서버소켓 열기
				jta.append("***연결중입니다***"+"\n");
				
			while(true) {
				socket = serversocket.accept(); //유저가 accept받아서 연결됨.
				jta.append("***유저와 연결되었습니다***"+"\n");
				
				dis = new DataInputStream(socket.getInputStream()); 
				dos = new DataOutputStream(socket.getOutputStream());
				
				while(true) {
					String msg = dis.readUTF(); //유저가 보낸 메세지 읽어오기
					jta.append("유저 :"+msg+"\n");
					jta.setCaretPosition(jta.getText().length());
				}
			}
			}catch(IOException e) {
				jta.append("채팅이 종료되었습니다" +e.getMessage());
			}
		}
		
		
		void sendMessage() {
			String msg = jtf.getText(); 
			jtf.setText("");
			jta.append("하비타운 채팅 상담센터 :" +msg+"\n");
			jta.setCaretPosition(jta.getText().length());
			
			Thread t = new Thread() {
			public void run() {
				try {
					dos.writeUTF(msg);
					dos.flush();
				}catch(IOException d) {
					jta.append("채팅이 종료되었습니다" + d.getMessage());
				}
			}
			};
			t.start();
		}
	}
}

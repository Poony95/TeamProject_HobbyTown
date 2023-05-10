package Post;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Comments.Comments;
import Reports.reports;
import User_No.User_inform;
import boards.Application;
import boards.Board;
import boards.UpdateBoard;
import dao.ApplicationDAO;
import dao.LikedDAO;
import dao.PostDAO;
import dao.commentDAO;
import vo.commentsVO;
import vo.BoardVO;
import vo.PostVO;
import db.ConnectionProvider;
import first.LogInPage;
	
public class postShow extends JFrame{
	
	reports rp;	//�Ű� ��ư Ŭ���� ��Ÿ�� â �������� ����
	Vector<String> post = new Vector<String>();
	UpdateBoard updateB;
	
	//��ü������ ��ġ �����ϱ�
	public postShow(int postNum) {
		postShow p = this;
		loadData(postNum);

		String category = post.get(1);
		String title = post.get(2);
		String user_id = post.get(3);
		String b_content = post.get(4);
		String interest = post.get(5);
		String date_board = post.get(6);	
		
		JPanel p_infrom = new JPanel();	//Ű����, ����, �۾���
		p_infrom.setLayout(new GridLayout(3,1));
		JLabel jlb_keyword = new JLabel(category+" | "+interest);
		JLabel jlb_title = new JLabel(title);
		jlb_title.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel jlb_writer = new JLabel(user_id);
		p_infrom.add(jlb_keyword);
		p_infrom.add(jlb_title);
		p_infrom.add(jlb_writer);
		
		JButton btn_apply;
		JButton btn_liked;
		
		LikedDAO ldao = new LikedDAO();
		
		ApplicationDAO aDao = new ApplicationDAO();
		int recruit_no = aDao.countApplication(Board.postNum);
		
		JPanel p_apply = new JPanel();	//�ο�, ������û ���� (���������� ������...)

		JLabel jlb_personnel = new JLabel("���� �ο� : "+recruit_no+" �� ");	
		btn_apply = new JButton("������û");
		p_apply.add(jlb_personnel);
		p_apply.add(btn_apply);
		
		//ī�װ��� ���� ������û visible/notvisible
		if(category.equals("���׻�Ȱ")) {
			p_apply.setVisible(false);
		}else {
			p_apply.setVisible(true);
		}
		
		JPanel p_liked = new JPanel();	//���ƿ� ���� (��ġ ���� ����)
		JLabel jlb_liked = new JLabel("���� �ο� : "+ldao.countLiked()+" ��");
		btn_liked = new JButton("��");
		p_liked.add(jlb_liked);
		p_liked.add(btn_liked);
		
		JPanel p_AandL = new JPanel();
		p_AandL.add(p_apply);
		p_AandL.add(p_liked);

		JPanel p_upperPart = new JPanel();
		p_upperPart.setLayout(new BorderLayout());
		p_upperPart.add(p_infrom, BorderLayout.CENTER);
		p_upperPart.add(p_AandL, BorderLayout.SOUTH);
		
		JPanel p_contents = new JPanel();
		p_contents.setLayout(new BorderLayout());
		JTextArea jta_contents = new JTextArea();
		jta_contents.setText(b_content);
		jta_contents.setEditable(false);
		JScrollPane jsp = new JScrollPane(jta_contents);
		
		JPanel p_lowerPart = new JPanel();
		
		JPanel p_updateDelete = new JPanel();
		JButton btn_update=new JButton("����");
		JButton btn_delete=new JButton("����");
		p_updateDelete.add(btn_update);
		p_updateDelete.add(btn_delete);
		
		JPanel p_report = new JPanel();
		JButton btn_report;
		btn_report = new JButton("�Ű�");	//�Ű� ��ư ��ġ ���� �� �� 
		p_report.add(btn_report);
		
		p_lowerPart.add(p_updateDelete);
		p_lowerPart.add(p_report);
		
		//�α��� ������ ���� �Խñ� �ٸ��� ���̱� ����(0508_23:26 ����)
		int chkUser = chkUserNo();
//		//�α��� ������ �Խñ� �ۼ��� ���Ͻ�
		if(chkUser==0) {
			p_report.setVisible(false);
		}else {
			p_updateDelete.setVisible(false);
		}
		
		p_contents.add(jsp, BorderLayout.CENTER);
		p_contents.add(p_lowerPart, BorderLayout.SOUTH);
		
		Comments cms = new Comments();
		
		add(p_upperPart, BorderLayout.NORTH);
		add(p_contents, BorderLayout.CENTER);
		add(cms, BorderLayout.SOUTH);
		
		setSize(800,600);
		setVisible(true);
		
		//������û ��ư Ŭ���� ������û â ����
		btn_apply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Application apply = new Application(new Board().postNum);
				
			}
		});
		//�Խñ� ����
		btn_update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateB = new UpdateBoard(p);
				updateB.setVisible(true);
			}
		});
		
		btn_delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PostDAO daoP = new PostDAO();
				int chk = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "�Խñ� ����", JOptionPane.YES_NO_OPTION);
					if(chk == 0) {
						int re = daoP.deletePost();
						JOptionPane.showMessageDialog(null, "�Խñ��� �����Ǿ����ϴ�.");
						setVisible(false);
						//�Խñ� ���ΰ�ħ �߰� �ʿ� ==> how?
					}
			}
		});
		
		btn_report.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rp = new reports();
				rp.setVisible(true);
			}
		});
		
		btn_liked.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int chk = ldao.checkAlready();
				System.out.println(chk);
				if(chk==1) {
					JOptionPane.showMessageDialog(null, "�̹� ���� ���ƿ信 �߰��ϼ̽��ϴ�.");
					return;
				}else {
					int re = ldao.addLiked();
					if(re==1) {
						JOptionPane.showMessageDialog(null, "���� ���ƿ信 �߰��Ǿ����ϴ�.");
						setVisible(false);
						new postShow(postNum);
					}
				}
			}
		});
		
	}
	//�Խñ� ���
	public void loadData(int p_b_no) {
		String sql = "SELECT b_no, category, title, user_id, b_content, b.interest, date_board, "
				+ "application, b_cnt "
				+ "FROM board b, user_info u "
				+ "WHERE b.user_no=u.user_no and b.b_no="+p_b_no;
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				int b_no = rs.getInt(1);
				String category = rs.getString(2);
				String title = rs.getString(3);
				String user_id = rs.getString(4);
				String b_content = rs.getString(5);
				String interest = rs.getString(6);
				String date_board = rs.getString(7);
				String application = rs.getString(8);
				int b_cnt = rs.getInt(9);
				post.add(b_no+"");
				post.add(category);
				post.add(title);
				post.add(user_id);
				post.add(b_content);
				post.add(interest);
				post.add(date_board);
				post.add(application);
				post.add(b_cnt+"");

			}
			ConnectionProvider.close(rs, stmt, conn);			
		} catch (Exception e) {
			System.out.println("����:"+e.getMessage());
		}
	}	
	
	public int chkUserNo() {
		int chkUser=-1;
		User_inform u_infrom = new User_inform();
		HashMap<String, Object> map = u_infrom.userInform();
		if(LogInPage.getNO() == Integer.parseInt(map.get("user_no").toString())) {
			chkUser = 0; //�α��� ������ �Խñ� �� ������ ���ٸ� 0�� ��ȯ
		}
		//�α��� ������ �Խñ� �� ������ �ٸ��ٸ� chkUser�� �״�� -1
		return chkUser;
	}
	public static void main(String[] args) {
//		new post_Reader2();
	}
	
	
}

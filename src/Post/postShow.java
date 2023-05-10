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
	
	reports rp;	//신고 버튼 클릭시 나타날 창 세팅위한 변수
	Vector<String> post = new Vector<String>();
	UpdateBoard updateB;
	
	//전체적으로 위치 수정하기
	public postShow(int postNum) {
		postShow p = this;
		loadData(postNum);

		String category = post.get(1);
		String title = post.get(2);
		String user_id = post.get(3);
		String b_content = post.get(4);
		String interest = post.get(5);
		String date_board = post.get(6);	
		
		JPanel p_infrom = new JPanel();	//키워드, 제목, 글쓴이
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
		
		JPanel p_apply = new JPanel();	//인원, 참가신청 관련 (오른쪽으로 보내기...)

		JLabel jlb_personnel = new JLabel("모집 인원 : "+recruit_no+" 명 ");	
		btn_apply = new JButton("참가신청");
		p_apply.add(jlb_personnel);
		p_apply.add(btn_apply);
		
		//카테고리에 따라서 참가신청 visible/notvisible
		if(category.equals("동네생활")) {
			p_apply.setVisible(false);
		}else {
			p_apply.setVisible(true);
		}
		
		JPanel p_liked = new JPanel();	//좋아요 관련 (위치 변경 예정)
		JLabel jlb_liked = new JLabel("관심 인원 : "+ldao.countLiked()+" 명");
		btn_liked = new JButton("♥");
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
		JButton btn_update=new JButton("수정");
		JButton btn_delete=new JButton("삭제");
		p_updateDelete.add(btn_update);
		p_updateDelete.add(btn_delete);
		
		JPanel p_report = new JPanel();
		JButton btn_report;
		btn_report = new JButton("신고");	//신고 버튼 위치 변경 할 것 
		p_report.add(btn_report);
		
		p_lowerPart.add(p_updateDelete);
		p_lowerPart.add(p_report);
		
		//로그인 유저에 따라서 게시글 다르게 보이기 설정(0508_23:26 수정)
		int chkUser = chkUserNo();
//		//로그인 유저와 게시글 작성자 동일시
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
		
		//참가신청 버튼 클릭시 참가신청 창 띄우기
		btn_apply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Application apply = new Application(new Board().postNum);
				
			}
		});
		//게시글 수정
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
				int chk = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "게시글 삭제", JOptionPane.YES_NO_OPTION);
					if(chk == 0) {
						int re = daoP.deletePost();
						JOptionPane.showMessageDialog(null, "게시글이 삭제되었습니다.");
						setVisible(false);
						//게시글 새로고침 추가 필요 ==> how?
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
					JOptionPane.showMessageDialog(null, "이미 나의 좋아요에 추가하셨습니다.");
					return;
				}else {
					int re = ldao.addLiked();
					if(re==1) {
						JOptionPane.showMessageDialog(null, "나의 좋아요에 추가되었습니다.");
						setVisible(false);
						new postShow(postNum);
					}
				}
			}
		});
		
	}
	//게시글 출력
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
			System.out.println("예외:"+e.getMessage());
		}
	}	
	
	public int chkUserNo() {
		int chkUser=-1;
		User_inform u_infrom = new User_inform();
		HashMap<String, Object> map = u_infrom.userInform();
		if(LogInPage.getNO() == Integer.parseInt(map.get("user_no").toString())) {
			chkUser = 0; //로그인 유저와 게시글 쓴 유저가 같다면 0을 반환
		}
		//로그인 유저와 게시글 쓴 유저가 다르다면 chkUser는 그대로 -1
		return chkUser;
	}
	public static void main(String[] args) {
//		new post_Reader2();
	}
	
	
}

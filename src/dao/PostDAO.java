package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import boards.Board;
import db.ConnectionProvider;
import first.LogInPage;
import vo.BoardVO;
import vo.PostVO;

public class PostDAO {
	//�Խñ� ����
	public int deletePost() {
		int re = -1;
		String sql = "DELETE FROM board WHERE b_no="+Board.postNum;
		try {
			Connection conn = new ConnectionProvider().getConnection();
			Statement stmt = conn.createStatement();
			re = stmt.executeUpdate(sql);
			ConnectionProvider.close(stmt, conn);
		} catch (Exception e) {
			System.out.println("����:"+e.getMessage());
		}
		return re;		
	}
	//�Խñ� ����
	public int updatePost(PostVO p) {
		int re = -1;
		String sql = "UPDATE board SET category='"+p.getCategory()+"', interest='"+p.getInterest()+"', "
				+ "application='"+p.getApplication()+"', "
				+ "title='"+p.getTitle()+"', b_content='"+p.getB_content()+"' "
				+ "WHERE b_no="+p.getB_no();
		try {
			Connection conn = new ConnectionProvider().getConnection();
			Statement stmt = conn.createStatement();
			re = stmt.executeUpdate(sql);
			ConnectionProvider.close(stmt, conn);
		} catch (Exception e) {
			System.out.println("����:"+e.getMessage());
		}
		return re;		
	}
	
	//�Խñ� ������ ���� ���� ���� ���� �޼ҵ�
	public ArrayList<Object> setDate(){
		ArrayList<Object> listData = new ArrayList<Object>();
		String sql = "SELECT category, interest, application, title, b_content "
				+ "FROM board "
				+ "WHERE b_no="+Board.postNum;
		
		int idx_cat = -1;
		int idx_interest = -1;
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				if(rs.getString(1).equals("�Բ��ؿ�")) {
					idx_cat=0;
				}else {
					idx_cat=1;
				}
				if (rs.getString(2).equals("�ǰ�/�")) {
					idx_interest=0;
				}else if (rs.getString(2).equals("����/�丮")) {
					idx_interest=1;
				}else if (rs.getString(2).equals("��ȭ/����/����")) {
					idx_interest=2;
				}else if (rs.getString(2).equals("�̼�/����")) {
					idx_interest=3;
				}else if (rs.getString(2).equals("�뷡/����")) {
					idx_interest=4;
				}else if (rs.getString(2).equals("����ũ")) {
					idx_interest=5;
				}else {
					idx_interest=6;
				}
				listData.add(idx_cat);	//ī�װ� �޺��ڽ� �ε���
				listData.add(idx_interest);	// ���ɻ� �޺��ڽ� �ε���
				listData.add(rs.getString(3));	//��û�ο�
				listData.add(rs.getString(4));	//����
				listData.add(rs.getString(5));	//����	
			}			
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {
			System.out.println("���ܹ߻�:"+e.getMessage());
		}
		return listData;		
	}
	
//	public ArrayList<PostVO> showPost(){
//		ArrayList<PostVO> list = new ArrayList<PostVO>();
//		String sql = "SELECT b_no, category, title, user_id, b_content, b.interest, date_board, "
//				+ "application, b_cnt "
//				+ "FROM board b, user_info "
//				+ "WHERE b.b_no=1";	//�Խ��ǿ��� Ŭ���� ���� �ѹ�
//		try {
//			Connection conn = ConnectionProvider.getConnection();
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(sql);
//			while(rs.next()) {
//				int b_no = rs.getInt(1);
//				String category = rs.getString(2);
//				String title = rs.getString(3);
//				String user_id = rs.getString(4);
//				String b_content = rs.getString(5);
//				String interest = rs.getString(6);
//				String date_board = rs.getString(7);
//				String application = rs.getString(8);
//				int b_cnt = rs.getInt(9);
//				PostVO p = new PostVO(b_no, category, title, user_id, b_content, interest, 
//						date_board, application, b_cnt);
//				list.add(p);			
//			}
//			
//		} catch (Exception e) {
//			System.out.println("����: "+e.getMessage());
//		}
//		return list;
//	}
}
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
	//게시글 삭제
	public int deletePost() {
		int re = -1;
		String sql = "DELETE FROM board WHERE b_no="+Board.postNum;
		try {
			Connection conn = new ConnectionProvider().getConnection();
			Statement stmt = conn.createStatement();
			re = stmt.executeUpdate(sql);
			ConnectionProvider.close(stmt, conn);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return re;		
	}
	//게시글 수정
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
			System.out.println("예외:"+e.getMessage());
		}
		return re;		
	}
	
	//게시글 수정시 기존 값을 띄우기 위한 메소드
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
				if(rs.getString(1).equals("함께해요")) {
					idx_cat=0;
				}else {
					idx_cat=1;
				}
				if (rs.getString(2).equals("건강/운동")) {
					idx_interest=0;
				}else if (rs.getString(2).equals("음식/요리")) {
					idx_interest=1;
				}else if (rs.getString(2).equals("영화/공연/전시")) {
					idx_interest=2;
				}else if (rs.getString(2).equals("미술/공예")) {
					idx_interest=3;
				}else if (rs.getString(2).equals("노래/음악")) {
					idx_interest=4;
				}else if (rs.getString(2).equals("재테크")) {
					idx_interest=5;
				}else {
					idx_interest=6;
				}
				listData.add(idx_cat);	//카테고리 콤보박스 인덱스
				listData.add(idx_interest);	// 관심사 콤보박스 인덱스
				listData.add(rs.getString(3));	//신청인원
				listData.add(rs.getString(4));	//제목
				listData.add(rs.getString(5));	//내용	
			}			
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return listData;		
	}
	
//	public ArrayList<PostVO> showPost(){
//		ArrayList<PostVO> list = new ArrayList<PostVO>();
//		String sql = "SELECT b_no, category, title, user_id, b_content, b.interest, date_board, "
//				+ "application, b_cnt "
//				+ "FROM board b, user_info "
//				+ "WHERE b.b_no=1";	//게시판에서 클릭한 보드 넘버
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
//			System.out.println("예외: "+e.getMessage());
//		}
//		return list;
//	}
}
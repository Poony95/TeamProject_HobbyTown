package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import boards.Board;
import db.ConnectionProvider;
import first.LogInPage;
import vo.BoardVO;

public class LikedDAO {

// 	//좋아요 버튼을 누를 때마다 게시글의 정보를 좋아요 테이블에 추가하는 쿼리
// 	public int insertLiked(BoardVO b) {
// 		int re = -1;
// 		try {
// 			String sql = "insert into liked (l_no, date_liked, b_no, user_no) values (seq_liked.nextval, sysdate, ?, ?)";
			
// 			Connection conn = ConnectionProvider.getConnection();
// 			PreparedStatement pstmt = conn.prepareStatement(sql);
// 			pstmt.setInt(1, b.getB_no());
// 			pstmt.setInt(2, LogInPage.getNO());
// 			re = pstmt.executeUpdate();
// 			ConnectionProvider.close(pstmt, conn);
// 		} catch (Exception e) {
// 			System.out.println("예외발생 : " +e.getMessage());
// 		}
// 		return re;
// 	}
	
	//좋아요 수 쿼리
	public ArrayList<BoardVO> countLiked(BoardVO b) {
			ArrayList<BoardVO> list = new ArrayList<>();
		try {
			String sql = "select count(l_no) from liked l, board b where l.b_no = b.b_no and b.b_no = ?";
			
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b.getB_no());
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int count = rs.getInt(1);
				BoardVO v = new BoardVO();
				v.setB_no(rs.getInt(1));
			}
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {
			System.out.println("예외발생 : " +e.getMessage());
		}
		return list;
	}
	//자신의 좋아요 목록을 조회하는 쿼리
		public ArrayList<BoardVO> myLikedlist(int no) {
				ArrayList<BoardVO> list = new ArrayList<>();
			try {
				String sql = "select a.* from (select l.b_no, u.address, b.title, l.date_liked from board b, liked l, user_info u  "
						+ "where u.user_no= l.user_no and l.b_no = b.b_no and l.user_no = "+no+" order by date_liked desc) a where rownum <=5";
				
				Connection conn = ConnectionProvider.getConnection();
				Statement pstmt = conn.createStatement();
				ResultSet rs = pstmt.executeQuery(sql);
				
				while(rs.next()) {
					
					BoardVO v = new BoardVO();
					v.setB_no(rs.getInt(1));
					v.setAddress(rs.getString(2));
					v.setTitle(rs.getString(3));
					v.setDate_board(rs.getDate(4));
					list.add(v);
				}
				ConnectionProvider.close(pstmt, conn);
			} catch (Exception e) {
				System.out.println("예외발생 : " +e.getMessage());
			}
			return list;
		}
	//해당 게시글 좋아요 수 출력
	public int countLiked() {
		int cnt_liked = -1;
		
		String sql="SELECT count(l_no) FROM liked l, board b "
				+ "WHERE l.b_no=b.b_no and b.b_no="+Board.postNum;
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				cnt_liked = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return cnt_liked;
	}
	//좋아요 클릭시 좋아요 정보 저장
	public int addLiked() {
		int re = -1;
		
		int user_log = LogInPage.getNO();	//로그인 유저 정보
		
		String sql = "INSERT INTO liked "
				+ "VALUES(seq_liked.NEXTVAL, sysdate, "+Board.postNum+", "+user_log+")";
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
	//이미 좋아요 누른 경우 체크하기 위한 쿼리
	public int checkAlready() {
		int chk=-1;
		
		int user_log = LogInPage.getNO(); //로그인 유저 정보
		String sql = "SELECT count(*) FROM "
				+ "(SELECT l.b_no, l.user_no "
				+ "FROM liked l, board b, user_info u "
				+ "WHERE l.b_no=b.b_no and l.user_no=u.user_no "
				+ "and l.b_no="+Board.postNum+" and l.user_no="+user_log+")";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				chk = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return chk;
	}
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;



import db.ConnectionProvider;
import first.LogInPage;
import vo.BoardVO;
import vo.UserVO;

public class ApplicationDAO {

	//사용자가 신청란에서 신청내용을 작성하는 쿼리
	public int insertApplication(BoardVO b) {
		int re = -1;
		try {
			String sql = "insert into application (ap_no, ap_content, date_application, b_no, user_no) "
					+ "values (seq_application.nextval, ?,sysdate,?,?)";
			
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getAp_content());
			pstmt.setInt(2, b.getB_no());
			pstmt.setInt(3, LogInPage.getNO());
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {
			System.out.println("�삁�쇅諛쒖깮 : " +e.getMessage());
		}
		return re;
	}
	public int countApplication(int postNum) {
		int apply=-1;
		try { 
			String sql = "SELECT nvl(b.application,0) "
					+ "FROM board b "
					+ "WHERE b_no = "+postNum;
			//寃뚯떆湲� �옉�꽦�옄 �닔源뚯� +1濡� �닔�젙 0508_15:38
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				apply=rs.getInt(1);
			}
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {
			System.out.println("�삁�쇅: " +e.getMessage());
		}
		return apply;
	}
	//신청 수 쿼리
	public ArrayList<HashMap<String, Integer>> cntApplication(int postNum) {
		ArrayList<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
		try { 
			String sql = "SELECT b.b_no, count(ap_no)+1, nvl(b.application,0) "
					+ "FROM application a, board b "
					+ "WHERE a.b_no=b.b_no and b.b_no = "+postNum+" "
					+ "GROUP BY b.b_no, b.application";
			//寃뚯떆湲� �옉�꽦�옄 �닔源뚯� +1濡� �닔�젙 0508_15:38
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			HashMap<String, Integer> map_apply = new HashMap<String, Integer>();
			if(rs.next()) {
				map_apply.put("cnt_apply",rs.getInt(2));
				map_apply.put("recruit_no",rs.getInt(3));
				list.add(map_apply);
			}
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {
			System.out.println("�삁�쇅: " +e.getMessage());
		}
		return list;
	}
	
	//작성자가 신청정보 조회
	public ArrayList<HashMap<String, Object>> getApplicantList(int NO, int b_no) {
			ArrayList<HashMap<String, Object>> list = new ArrayList<>();
			String sql = "select distinct b.b_no, b.title, u.user_no, u.user_name, "
					+ "decode(substr(jumin,7,1),1,'�궓',2,'�뿬',3,'�궓',4,'�뿬', '�븣�닔�뾾�쓬') gender, "
					+ " a.ap_content, a.date_application " + 
					"from board b, user_info u, application a " + 
					"where b.b_no=a.b_no and a.user_no=u.user_no " + 
					"and a.user_no <> ? and b.user_no = ? and b.b_no = ? " + 
					"order by date_application desc";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, NO);
			pstmt.setInt(2, NO);
			pstmt.setInt(3, b_no);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("b_no", rs.getInt(1));
				map.put("title", rs.getString(2));
				map.put("u_no", rs.getInt(3));
				map.put("name", rs.getString(4));
				map.put("gender", rs.getString(5));
				map.put("ap_content", rs.getString(6));
				map.put("date_application", rs.getDate(7));
				list.add(map);
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {
			System.out.println("getApplicationList() �삁�쇅諛쒖깮 : " +e.getMessage());
			}
			return list;
		}
		//작성자가 자신이 작성한 게시글 조회
		public ArrayList<BoardVO> loadApplicationList(int NO){
			ArrayList<BoardVO> list = new ArrayList<BoardVO>();
			String sql ="select b_no, title, date_board from board b, user_info u "
					+ "where u.user_no=b.user_no and application is not null and u.user_no=?";
			try {
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, NO);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					BoardVO b = new BoardVO();
					b.setB_no(rs.getInt(1));
					b.setTitle(rs.getString(2));
					b.setDate_board(rs.getDate(3));
					list.add(b);
				}
				ConnectionProvider.close(rs, pstmt, conn);
			} catch (Exception e) {System.out.println("loadApplicationList() �삁�쇅諛쒖깮 : "+ e.getMessage());}
			return list;
		}
}
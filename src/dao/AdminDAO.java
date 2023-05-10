package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import db.ConnectionProvider;
import vo.BoardVO;
import vo.UserVO;

public class AdminDAO {
	// 신고된 게시글을 가져오는 메서드
	public ArrayList<HashMap<String, Object>> viewReportList(){
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		String sql = "select r_no, r_content, date_report, date_complete, r_state, r.user_no, "
				+ "r.b_no, nvl(title, '삭제 완료'), nvl(b_content,'삭제 완료') "
				+ "from reports r left outer join board b on r.b_no=b.b_no";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("r_no", rs.getInt(1));
				map.put("r_content", rs.getString(2));
				map.put("date_report", rs.getDate(3));
				map.put("date_complete", rs.getDate(4));
				map.put("r_state", rs.getString(5));
				map.put("user_no", rs.getInt(6));
				map.put("b_no", rs.getInt(7));
				map.put("b_content", rs.getString(8));
				map.put("b_title", rs.getString(9));
				list.add(map);
			}
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {System.out.println("viewReportList() 예외 발생 : "+e.getMessage());}
		return list;
	}
	// 신고 처리를 완료하는 메서드 - 트리거를 이용해 게시글 삭제, 처리상태 및 처리 일시 업데이트
	public int updateReports(int r_no) {
		int re = -1;
		String sql = "update reports set r_state='Y' where r_no=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r_no);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {	System.out.println("updateReports() 예외 발생 : "+e.getMessage());}
		return re;
	}
	
	// 회원 목록을 가져오는 메서드
	public ArrayList<HashMap<String, Object>> viewUserList(){
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		String sql = "select user_no, user_name,user_id, date_update, nvl(r_cnt,0)  from user_info "+
				"left outer join (select u.user_no r_uno, count(*) r_cnt from reports r, user_info u\r\n" + 
				"where r.user_no=u.user_no group by u.user_no) a " + 
				"on a.r_uno=user_no where user_no <> 0 order by nvl(r_cnt,0) desc";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("user_no", rs.getInt(1));
				map.put("user_name", rs.getString(2));
				map.put("user_id", rs.getString(3));
				map.put("date_update", rs.getDate(4));
				map.put("r_cnt", rs.getInt(5));
			list.add(map);
			}
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {System.out.println("viewUserList() 예외 발생 : "+e.getMessage());}
		return list;
	}
	// 선택한 회원을 삭제한느 메서드
	public int deleteUser(int NO) {
		int re = -1;
		String sql = "delete from user_info where user_no=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, NO);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {	System.out.println("deleteUser() 예외 발생 : "+e.getMessage());}
		return re;
	}
	
	
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import db.ConnectionProvider;
import vo.UserVO;

public class FriendDAO {
	
	// 수락된 친구 목록을 불러오는 DAO
	public ArrayList<String> getFriendlist(int NO){
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select user_name, user_id from user_info, friend "
				+ "where user_no1 = user_no and user_no2=? and f_state = 'Y' union select user_name, user_id "
				+ "from user_info, friend where user_no2=user_no and user_no1=? and f_state = 'Y'";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, NO);
			pstmt.setInt(2, NO);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1)+"("+rs.getString(2)+")");
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {System.out.println("getFriendlist() 예외 발생 : "+e.getMessage());}
		return list;
	}
	// 대기중 친구 목록을 불러오는 DAO
	public ArrayList<String> getWaitinglist(int NO){
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select user_name, user_id from user_info, friend "
				+ "where user_no1 = user_no and user_no2=? and wait_no = ? and f_state = 'N' union select user_name, user_id "
				+ "from user_info, friend where user_no2=user_no and user_no1=? and wait_no = ? and f_state = 'N'";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, NO);
			pstmt.setInt(2, NO);
			pstmt.setInt(3, NO);
			pstmt.setInt(4, NO);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1)+"("+rs.getString(2)+")");
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {System.out.println("getFriendlist() 예외 발생 : "+e.getMessage());}
		return list;
	}
	// 친구를 수락하는 DAO
	public int acceptFriend(int u_no, int f_no) {
		int re = -1;
		String sql = "update friend set f_state = 'Y' where user_no1 = ? and user_no2 = ? ";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			System.out.println(u_no +" "+ f_no);
			pstmt.setInt(1, Math.min(u_no, f_no));	// 데이터의 중복을 줄이기 위해, user_no1<user_no2가 되도록 처리
			pstmt.setInt(2, Math.max(u_no, f_no));
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("acceptFriend() 예외 발생 : "+e.getMessage());}
		return re;
	}
	// 친구를 삭제하는 메서드
	public int deleteFriend(int u_no, int f_no) {
		int re = -1;
		String sql = "delete from friend where user_no1 = ? and user_no2=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Math.min(u_no, f_no));	// 데이터의 중복을 줄이기 위해, user_no1<user_no2가 되도록 처리
			pstmt.setInt(2, Math.max(u_no, f_no));
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("deleteFriend() 예외 발생 : "+e.getMessage());}
		return re;
	}
	// 친구를 추가하는 메서드
	public int addFriend(int u_no, int f_no) {
		int re = -1;
		// case when 문을 사용해 user_id1<user_id2가 되도록 설정
		String sql = "insert into friend values (sysdate, "
				+ "case when "+u_no+"<"+f_no+" then "+u_no+" else "+f_no+" end,"
				+ "case when "+u_no+"<"+f_no+" then "+f_no+" else "+u_no+" end, "
				+ "'N', "+u_no+", "+f_no+")";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			re = stmt.executeUpdate(sql);
			ConnectionProvider.close(stmt, conn);
		} catch (Exception e) {System.out.println("addFriend() 예외 발생 : "+e.getMessage());}
		return re;
	}
}

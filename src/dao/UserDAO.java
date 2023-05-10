package dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 

import db.ConnectionProvider;
import vo.UserVO;

public class UserDAO {
	// 로그인 메서드 ( 아이디에 맞는 비밀번호인지?)
	public String getPWD(String ID){
		String sql = "select user_pwd from user_info where user_id = ?";
		String result = "";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getString(1);
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return result;
	}
	// 유저  정보를 가져오는 메서드
	public UserVO getUser(int NO) {
		UserVO u = new UserVO();
		String sql = "select * from user_info where user_no = "+NO;
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				u.setNo(rs.getInt(1));
				u.setId(rs.getString(2));
				u.setPwd(rs.getString(3));
				u.setName(rs.getString(4));
				u.setJumin(rs.getString(5));
				u.setEmail(rs.getString(6));
				u.setInterest(rs.getString(7));
				u.setUpdate(rs.getDate(8));
				u.setPhone(rs.getString(9));
				u.setAddress(rs.getString(10));
			}
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {System.out.println("getUser() 예외 발생 : "+e.getMessage());}
		return u;
	}
	
	// user_no 가져오기
	public int getNO(String ID) {
		int re = -1;
		String sql = "select user_no from user_info where user_id = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				re=	rs.getInt(1);
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {System.out.println("getNO() 예외 발생 :"+e.getMessage());}
		return re;
	}

	// ID를 매개변수로 유저 이름을 반환하는 메서드
	public String getName(String ID) {
		String name = "";
		String sql = "select user_name from user_info where user_id = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				name =	rs.getString(1);
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {System.out.println("getNO() 예외 발생 :"+e.getMessage());}
		return name;
	}
	// ID를 매개변수로 유저 이름을 반환하는 메서드
	public String getName(int NO) {
		String name = "";
		String sql = "select user_name from user_info where user_no = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, NO);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				name =	rs.getString(1);
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {System.out.println("getNO() 예외 발생 :"+e.getMessage());}
		return name;
	}
	
	// 유저번호를 매개변수로 ID를 반환하는 메서드
	public String getID(int NO) {
		String name = "";
		String sql = "select user_id from user_info where user_no = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, NO);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				name =	rs.getString(1);
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {System.out.println("getNO() 예외 발생 :"+e.getMessage());}
		return name;
	}
	
	// 회원 가입 메서드
	public int signup(UserVO u) {
		int re = -1;
		String sql = "insert into user_info values(seq_uno.nextval, ?, ?, ?, ?, ?, ?, sysdate, ?, ?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getId());
			pstmt.setString(2, u.getPwd());
			pstmt.setString(3, u.getName());
			pstmt.setString(4, u.getJumin());
			pstmt.setString(5, u.getEmail());
			pstmt.setString(6, u.getInterest());
			pstmt.setString(7, u.getPhone());
			pstmt.setString(8, u.getAddress());
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("singup()예외발생 : "+e.getMessage());}
		
		return re;
	}
	
	// ID 중복을 확인하는 메서드
	public int checkID(String ID) {
		int re = -1;
		String sql = "select nvl(count(*),0) from user_info where user_id = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				re = rs.getInt(1);
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {System.out.println("checkID() 예외 발생 : "+e.getMessage());}
		return re;
	}
	
	// 비밀번호를 변경하는 메서드
	public int updatePWD(String PWD, int NO) {
		int re = -1;
		String sql = "update user_info set user_pwd=? where user_no=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, PWD);
			pstmt.setInt(2, NO);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("updatePWD()예외발생 : "+e.getMessage());}
		
		return re;
	}
	// 이메일을 변경하는 메서드
	public int updateEmail(String email, int NO) {
		int re = -1;
		String sql = "update user_info set email=? where user_no=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setInt(2, NO);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("updateEmail()예외발생 : "+e.getMessage());}
		
		return re;
	}
	// 관심사를 변경하는 메서드
	public int updateInterest(String interest, int NO) {
		int re = -1;
		String sql = "update user_info set interest=? where user_no=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, interest);
			pstmt.setInt(2, NO);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("updateInterest()예외발생 : "+e.getMessage());}
		
		return re;
	}
	// 전화번호를 변경하는 메서드
	public int updatePhone(String phone, int NO) {
		int re = -1;
		String sql = "update user_info set phone=? where user_no=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, phone);
			pstmt.setInt(2, NO);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("updatePhone()예외발생 : "+e.getMessage());}
		
		return re;
	}
	// 주소를 변경하는 메서드
	public int updateAddr(String address, int NO) {
		int re = -1;
		String sql = "update user_info set address=? where user_no=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, address);
			pstmt.setInt(2, NO);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("updateAddr()예외발생 : "+e.getMessage());}
		
		return re;
	}
}

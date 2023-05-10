package User_No;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import boards.Board;
import db.ConnectionProvider;

//선택된 게시글의 user정보를 가져오는 클래스
public class User_inform {
	
	public HashMap<String, Object> userInform() {
		HashMap<String, Object> map = new HashMap<String, Object>();
//		B_no b_no = new B_no();
//		int b_no_selected = b_no.boardNo();
		int b_no_selected = Board.postNum;
		String sql = "SELECT b_no, b.user_no, user_id "
				+ "FROM board b, user_info u "
				+ "WHERE b.user_no=u.user_no and b_no="+b_no_selected;
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				map.put("b_no", rs.getInt(1));
				map.put("user_no", rs.getInt(2));
				map.put("user_id", rs.getString(3));
			}
		}catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return map;
	}

}
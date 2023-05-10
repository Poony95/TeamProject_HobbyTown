package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import User_No.User_inform;
import boards.Board;
import db.ConnectionProvider;
import first.LogInPage;
import vo.commentsVO;

public class commentDAO {
	public int addComments(commentsVO c) {
		int re = -1;
		
		User_inform u_Infom = new User_inform();
		int user_log = LogInPage.getNO();	//로그인 유저 정보로 수정
		HashMap<String, Object> map_inform = u_Infom.userInform();
		String c_content = c.getC_content();
		
		String sql = "INSERT INTO comments "
				+ "VALUES(seq_comments.NEXTVAL, "
				+ "'"+c_content+"', TO_CHAR(SYSDATE,'YYYY-MM-DD HH:MI:SS'), "+user_log+", "+map_inform.get("b_no")+")";
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
	public int deleteComments(commentsVO c) {
		int re = -1;
		String sql = "DELETE FROM comments "
				+ "WHERE user_no="+c.getUser_no()
				+ " and c_content='"+c.getC_content()+"' "
				+ "and date_comment='"+c.getDate_comment()+"'";
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
	
	
	public ArrayList<Vector<String>> loadComments(){
		ArrayList<Vector<String>> list = new ArrayList<Vector<String>>();
		int b_no_selected = Board.postNum;
		
		String sql = "SELECT user_id, c_content, date_comment "
				+ "FROM user_info u, comments c, board b "
				+ "WHERE b.b_no=c.b_no and c.user_no=u.user_no and b.b_no="+b_no_selected
				+ "ORDER BY date_comment";
		String commentAll="";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Vector<String> v_comments = new Vector<String>();
				commentAll = rs.getString(1)+"  " + rs.getString(2) +" ("
						+ rs.getString(3)+")";
				v_comments.add(commentAll);
				list.add(v_comments);
			}
			ConnectionProvider.close(rs, stmt, conn);
		}catch (Exception e) {
			System.out.println("예외: "+e.getMessage());
		}
		return list;
	}
	
}

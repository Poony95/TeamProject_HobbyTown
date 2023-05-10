package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;

import User_No.User_inform;
import db.ConnectionProvider;
import vo.reportsVO;

public class reportsDAO {
	
	public int report(reportsVO r) {
		int re = -1;
		
		User_inform u_Infom = new User_inform();
		HashMap<String, Object> map_inform = u_Infom.userInform();
		String r_content = r.getR_content();
	
		String sql = "INSERT INTO reports "
				+ "VALUES(seq_reports.NEXTVAL, '"+r_content+"', "
				+ "default, null, default, "
				+ map_inform.get("user_no") +", "+map_inform.get("b_no")+")";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			re = stmt.executeUpdate(sql);
			ConnectionProvider.close(stmt, conn);		
			
		} catch (Exception e) {
			System.out.println("¿¹¿Ü:"+e.getMessage());
		}
		return re;		
	}
}
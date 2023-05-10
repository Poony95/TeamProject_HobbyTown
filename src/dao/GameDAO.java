package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import db.ConnectionProvider;

public class GameDAO { 
   
   // ����ȭ�� ������ ȯ���ϴ� �޼ҵ�
   public String getUserInfo(int NO) {
      String sql = "select user_name from user_info where user_no=?";
      String result = "";
      try {
         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, NO);
         ResultSet rs = pstmt.executeQuery();
         if(rs.next()) {
            result = rs.getString(1);
         }
         ConnectionProvider.close(rs, pstmt, conn);
      } catch (Exception e) {
         System.out.println("getUserInfo���ܹ߻�" +e.getMessage());
      }
      return result;
   }
   
   public int record(int user_no, double score){
      int re = -1;
      try {
         Connection conn 
         = ConnectionProvider.getConnection();
         CallableStatement cstmt 
         = conn.prepareCall("{call GameInsertOrUpdate(?,?)}");
         cstmt.setInt(1,user_no);
         cstmt.setDouble(2,score);
         re = cstmt.executeUpdate();
         System.out.println("re:"+re);
         ConnectionProvider.close(cstmt, conn);
         
      }catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return re;
   
   }

   // ��ŷ ������ ��� �޼ҵ�
   public ArrayList<HashMap<String, Object>> recordaddresslist(String address) {
       ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

          String sql = "SELECT rownum, a.user_name, a.address, To_CHAR(a.score, '000.00') AS score, a.user_no\r\n"
                + "FROM (\r\n"
                + "  SELECT i.user_no, i.user_name, i.address, CAST(g.score AS DECIMAL(6,2)) AS score\r\n"
                + "  FROM Game g\r\n"
                + "  INNER JOIN user_info i ON g.user_no = i.user_no\r\n"
                + "  WHERE address = ?"
                + "  ORDER BY g.score ASC\r\n"
                + ") a";
       try {
          
           Connection conn = ConnectionProvider.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, address);
           ResultSet rs = pstmt.executeQuery();
           while (rs.next()) {
           //ȸ����ȣ/�г���/������/�ð�   
           HashMap<String, Object>map = new HashMap<>();
           map.put("Ranking", rs.getInt(1));   
           map.put("name", rs.getString(2));   
           map.put("address", rs.getString(3));   
           map.put("score", rs.getDouble(4));   
           map.put("user_no", rs.getInt(5));   
           
           list.add(map);
           }
           ConnectionProvider.close(rs, pstmt, conn);
       } catch (Exception e) {
           System.out.println("���ܹ߻�:" + e.getMessage());
       }
       return list;
   }
   
   public ArrayList<HashMap<String, Object>> recordlist() {
       ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

       String sql = "SELECT rownum, a.user_name, a.address, To_CHAR(a.score, '000.00') AS score, a.user_no\r\n"
             + "    FROM (\r\n"
             + "    SELECT i.user_no, i.user_name, i.address, CAST(g.score AS DECIMAL(6,2)) AS score\r\n"
             + "    FROM Game g\r\n"
             + "    INNER JOIN user_info i ON g.user_no = i.user_no\r\n"
             + "    WHERE g.user_no != 0\r\n"
             + "    ORDER BY g.score ASC\r\n"
             + ") a";
       try {
           Connection conn = ConnectionProvider.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           while (rs.next()) {
           
           //ȸ����ȣ/�г���/������/�ð�   
           HashMap<String, Object>map = new HashMap<>();
           map.put("Ranking", rs.getInt(1));   
           map.put("name", rs.getString(2));   
           map.put("address", rs.getString(3));   
           map.put("score", rs.getDouble(4));   
           map.put("user_no", rs.getInt(5));   
           
           list.add(map);
           }
           ConnectionProvider.close(rs, stmt, conn);
       } catch (Exception e) {
           System.out.println("���ܹ߻�:" + e.getMessage());
       }
       return list;
   }
   
}
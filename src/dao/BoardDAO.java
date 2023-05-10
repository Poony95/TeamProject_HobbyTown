package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import db.ConnectionProvider;
import first.LogInPage;
import vo.BoardVO;

public class BoardDAO {
   
   //�Խñ� �ۼ�
   public int insertBoard(BoardVO b) {
       int re = -1;
       try {
           String sql = "insert into board (b_no, category, title, b_content, interest, date_board, application, user_no)"
                   + "values (seq_board.nextval, ?, ?, ?, ?, sysdate, ?, ?)";

           Connection conn = ConnectionProvider.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql);
           
           pstmt.setString(1, b.getCategory());
           pstmt.setString(2, b.getTitle());
           pstmt.setString(3, b.getB_content());
           pstmt.setString(4, b.getInterest());
           pstmt.setString(5, b.getAppilcation());
           pstmt.setInt(6, LogInPage.getNO());
           re = pstmt.executeUpdate();
           ConnectionProvider.close(pstmt, conn);
       } catch (Exception e) {
           System.out.println("���ܹ߻� : " + e.getMessage());
       }
       return re;
   }
   
   // ��ȸ�� ������Ʈ 
   public int updateHits(BoardVO b) {
       int re = -1;
       String sql = "UPDATE board SET b_cnt = b_cnt + 1 WHERE b_no = ?";
       try {
           Connection conn = ConnectionProvider.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, b.getB_no());
           re = pstmt.executeUpdate();
           ConnectionProvider.close(pstmt, conn);
       } catch (Exception e) {
           System.out.println("���ܹ߻�:" + e.getMessage());
       }
       return re;
   }
   
   // ��ü �Խñ� �� ��������
   public int getTotalCount() {
       int totalCount = 0;
       try {
           String sql = "SELECT COUNT(*) FROM board";
           Connection conn = ConnectionProvider.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql);
           ResultSet rs = pstmt.executeQuery();
           if (rs.next()) {
               totalCount = rs.getInt(1);
           }
           ConnectionProvider.close(rs, pstmt, conn);
       } catch (Exception e) {
           System.out.println("���� �߻�: " + e.getMessage());
       }
       return totalCount;
   }
   
   // ��� �Խñ� ��ȸ
   public ArrayList<BoardVO> viewAllList(int page){
      ArrayList<BoardVO> list = new ArrayList<>();
     
      
      try {
         String sql = "select a.* "
               + "from (select b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt, nvl(count(l.l_no), 0) l_cnt "
               + "from board b left outer join user_info u on u.user_no = b.user_no left outer join liked l on l.b_no=b.b_no "
               + "group by b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt "
               + "order by b.b_no desc, b.interest, date_board desc) a";
         
         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next()) {
            BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
         }
         ConnectionProvider.close(rs, pstmt, conn);
         } catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return list;
   }
   
   // �α�� ������ư ���� �� �α�� ��ȸ 
   public ArrayList<BoardVO> viewLikedList(){
      ArrayList<BoardVO> list = new ArrayList<>();
      try {
         String sql = "select a.* "
               + "from (select b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt, nvl(count(l.l_no), 0) l_no "
               + "from board b left outer join user_info u on u.user_no = b.user_no left outer join liked l on l.b_no=b.b_no "
               + "group by b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt "
               + "order by nvl(count(l.l_no), 0) desc, date_board desc) a where rownum <=10";
         
         Connection conn = ConnectionProvider.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         
         while(rs.next()) {
            
            BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
         }
         ConnectionProvider.close(rs, stmt, conn);
         } catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return list;
   }
   // �ֽż� ������ư ���� �� �α�� ��ȸ 
   public ArrayList<BoardVO> viewNewestList(){
      ArrayList<BoardVO> list = new ArrayList<>();
      try {
         String sql = "select a.* "
               + "from (select b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt, nvl(count(l.l_no), 0) l_no "
               + "from board b left outer join user_info u on u.user_no = b.user_no left outer join liked l on l.b_no=b.b_no "
               + "group by  b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt "
               + "order by date_board desc) a where rownum <=10";
         
         Connection conn = ConnectionProvider.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         
         while(rs.next()) {
            
            BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
         }
         ConnectionProvider.close(rs, stmt, conn);
         } catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return list;
   }
   
   // �˻� ��ȸ
   public ArrayList<BoardVO> SearchList(String search){
      ArrayList<BoardVO> list = new ArrayList<>();

      try {
         String sql = "select a.* "
               + "from (select b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt, nvl(count(l.l_no), 0) l_cnt "
               + "from board b left outer join user_info u on u.user_no = b.user_no left outer join liked l on l.b_no=b.b_no where lower(trim(b.title)) like ? "
               + "group by b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt "
               + "order by b.interest, date_board desc) a where rownum <=10";
         
         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%"+search+"%");
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next()) {
            
            BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
         }
         ConnectionProvider.close(rs, pstmt, conn);
         } catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return list;
   }
   
   // �α���� ���� �� �˻��ϴ� ����
   public ArrayList<BoardVO> likedSearchList(String search){
      ArrayList<BoardVO> list = new ArrayList<>();

      try {
         String sql = "select a.* "
               + "from (select b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt, nvl(count(l.l_no), 0) l_cnt "
               + "from board b left outer join user_info u on u.user_no = b.user_no left outer join liked l on l.b_no=b.b_no where lower(trim(b.title)) like ? "
               + "group by b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt "
               + "order by b.b_no desc, nvl(count(l.l_no), 0) desc, date_board desc) a where rownum <=10";

         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%"+search+"%");
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next()) {
         
            BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
         }
         ConnectionProvider.close(rs, pstmt, conn);
         } catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return list;
   }
   // �ֱټ��� ���� �� �˻��ϴ� ����
   public ArrayList<BoardVO> dateSearchList(String search){
      ArrayList<BoardVO> list = new ArrayList<>();

      try {
         String sql = "select a.* "
               + "from (select b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt, nvl(count(l.l_no), 0) l_cnt "
               + "from board b left outer join user_info u on u.user_no = b.user_no left outer join liked l on l.b_no=b.b_no where lower(trim(b.title)) like ? "
               + "group by b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt "
               + "order by b.b_no desc, date_board desc) a where rownum <=10";
         
         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%"+search+"%");
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next()) {
            
            BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
         }
         ConnectionProvider.close(rs, pstmt, conn);
         } catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return list;
   }
   // �����ɼǸ� ���� �� �˻��ϴ� ����
   public ArrayList<BoardVO> dateSearchList(String search, String addr){
      ArrayList<BoardVO> list = new ArrayList<>();

      try {
         String sql = "select a.* "
               + "from (select b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt, nvl(count(l.l_no), 0) l_cnt "
               + "from board b left outer join user_info u on u.user_no = b.user_no left outer join liked l on l.b_no=b.b_no where lower(trim(b.title)) like ? and u.address = ? "
               + "group by b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt "
               + "order by b.b_no desc, date_board desc) a where rownum <=10";
         
         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%"+search+"%");
         pstmt.setString(2, addr);
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next()) {
            
            BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
         }
         ConnectionProvider.close(rs, pstmt, conn);
         } catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return list;
   }
   //���ɻ� üũ�ڽ��� ���� �� �˻��ϴ� ����
   public ArrayList<BoardVO> interestList(String interest) {
       ArrayList<BoardVO> list = new ArrayList<>();
       
       try {
           String sql = "SELECT a.* " +
                        "FROM (SELECT b.b_no, u.address, b.category, b.interest, b.title, " +
                               "b.date_board, b.application, b.b_cnt, NVL(COUNT(l.l_no), 0) l_cnt " +
                               "FROM board b LEFT OUTER JOIN user_info u ON u.user_no = b.user_no " +
                               "LEFT OUTER JOIN liked l ON l.b_no=b.b_no WHERE b.interest LIKE ? " +
                               "GROUP BY b.b_no, u.address, b.category, b.interest, b.title, b.date_board, " +
                               "b.application, b.b_cnt " +
                               "ORDER BY b.b_no desc, date_board DESC) a " +
                        "WHERE ROWNUM <= 10";
           
           Connection conn = ConnectionProvider.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, "%" + String.join(",", interest) + "%");
           ResultSet rs = pstmt.executeQuery();
           
           while(rs.next()) {
              BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
           }
           
           ConnectionProvider.close(rs, pstmt, conn);
       } catch (Exception e) {
           System.out.println("���� �߻�: " + e.getMessage());
       }
       
       return list;
   }
   
   //ī�װ�(���׻�Ȱ/�Բ��ؿ�)�� ���� �� �˻��ϴ� ����
   public ArrayList<BoardVO> categorySearchList(String search, String categorys){
      ArrayList<BoardVO> list = new ArrayList<>();

      try {
         String sql = "select a.* "
               + "from (select b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt, nvl(count(l.l_no), 0) l_cnt "
               + "from board b left outer join user_info u on u.user_no = b.user_no left outer join liked l on l.b_no=b.b_no where lower(trim(b.title)) like ? and b.category = ? "
               + "group by b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt "
               + "order by b.b_no desc, date_board desc) a where rownum <=10";
         
         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%"+search+"%");
         pstmt.setString(2, categorys);
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next()) {
            
            BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
         }
         ConnectionProvider.close(rs, pstmt, conn);
         } catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return list;
   }

   //����, ī�װ�, ���ɻ�, �α�� ��� ���� �� �˻��ϴ� ����
   public ArrayList<BoardVO> AlloptionSearchList(String search, String categorys, String address, String interest){
      ArrayList<BoardVO> list = new ArrayList<>();
      
      try {
         String sql = "select a.* "
               + "from (select b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt, nvl(count(l.l_no), 0) l_cnt "
               + "from board b left outer join user_info u on u.user_no = b.user_no left outer join liked l on l.b_no=b.b_no "
               + "where lower(trim(b.title)) like ? and b.category = ? and u.address = ? and b.interest like ? "
               + "group by b.b_no, u.address, b.category, b.interest, b.title, b.date_board, b.application, b.b_cnt "
               + "order by b.b_no desc, nvl(count(l.l_no), 0) desc, date_board desc) a where rownum <=10";
         
      
         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%"+search+"%");
         pstmt.setString(2, categorys);
         pstmt.setString(3, address);
         pstmt.setString(4, "%"+interest+"%");
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next()) {
         
            BoardVO v = new BoardVO();
            v.setB_no(rs.getInt(1));
            v.setAddress(rs.getString(2));
            v.setCategory(rs.getString(3));
            v.setInterest(rs.getString(4));
            v.setTitle(rs.getString(5));
            v.setDate_board(rs.getDate(6));
            v.setAppilcation(rs.getString(7));
            v.setB_cnt(rs.getInt(8));
            v.setL_cnt(rs.getInt(9));
            list.add(v);
         }
         ConnectionProvider.close(rs, pstmt, conn);
         } catch (Exception e) {
         System.out.println("���ܹ߻�:"+e.getMessage());
      }
      return list;
   }
   
}
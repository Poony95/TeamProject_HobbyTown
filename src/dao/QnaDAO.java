package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import customer.Inner_qna;
import vo.QnAVo;

import db.ConnectionProvider;
import first.LogInPage;

public class QnaDAO {
	
	//유저가 QnA_list에서 삭제버튼을 눌렀을 때 answer, qna테이블에서도 데이터가 삭제되는 메소드
	public int deleteList(int no) {
		int re = -1;
		String sql = "delete qna where q_no = ? ";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		}catch(Exception e) {
			System.out.println("deleteList() 예외발생 :" + e.getMessage());
		}
		return re;
	}
	 
	
	//<관리자의 답변 등록 후 jtable의 처리상태가 변경되는 메소드>
	public int updateStatus(int no) {
		int re = -1;
		String sql = "update qna set q_status='답변완료' where q_no = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		}catch(Exception v) {
			System.out.println("updateStatus() 예외발생 : "+ v.getMessage());
		}
		return re;
	}
	
	
	//<답변을 JTextArea에서 조회하는 메소드>
		public ArrayList<QnAVo> searchAnswer(int q_no){
			ArrayList<QnAVo> list = new ArrayList<QnAVo>();
			String sql = "select a_content from answer where q_no=? ";
			try {
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, q_no);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					String content = rs.getString(1);
					QnAVo v = new QnAVo();
					v.setA_content(content);
					list.add(v);
				} ConnectionProvider.close(rs, pstmt, conn);
			}catch(Exception q) {
				System.out.println("searchAnswer() 예외발생" + q.getMessage());
			}
			return list;
		}
	
	
	//<관리자의 답변을 등록(이지만 이미 텅 빈 레코드가 있어 수정)하는 메서드 (ListPopUp창 등록 버튼 실행 시)>
		public int addAnswer(String a_content, int q_no, int u_no) {
			
			int re = -1;
			String sql = "update answer set a_date = default, a_content = ? where q_no = ? and user_no=?";
			try {
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, a_content);
				pstmt.setInt(2, q_no);
				pstmt.setInt(3, u_no);
				re=pstmt.executeUpdate();
				ConnectionProvider.close(pstmt, conn);
			}catch(Exception g) {
				System.out.println("addanswer() 예외발생 :" + g.getMessage());
			}
			return  re;
		}
	
	//<사용자가 문의글을 작성할 때 답변테이블에 공백이 추가되는 메소드> 답변이 없으면 rowData에 추가가 안돼서 넣음.
		public int blankAnswer() {
			int re = -1;
			String sql = "insert into answer values((SELECT NVL( MAX(a_no), 0) + 1 FROM answer ),(select max(q_no) from qna),null,null,0)";
			try {
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				re = pstmt.executeUpdate();
				ConnectionProvider.close(pstmt, conn);
			}catch(Exception t) {
				System.out.println("blankAnswer() 예외발생 :" +t.getMessage());
			}
			return re;
		}
	
	//<사용자가 입력한 내용을 등록하는 메소드>
	public int addData(String type, String mail, String title, String content, int no){
		String sql ="insert into qna values((SELECT NVL( MAX(q_no), 0) + 1 FROM qna),?,?,?,?,null,default,default,?)";
	
		if(type.equals("") || mail.equals("") || title.equals("") || content.equals("") ||Inner_qna.check_b.isSelected()==false) {
		        return 0;
		 }
		int count =-1;
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type);
			pstmt.setString(2, mail);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setInt(5, no);
			count = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		}catch(Exception d) {
			System.out.println("addData() 예러발생 :" +d.getMessage());
			d.printStackTrace();
		}
		return count;
	}

//<유저일 때 유저가 작성한 목록만 보이는 메소드>
		public ArrayList<QnAVo> addList(int no){
			ArrayList<QnAVo> list = new ArrayList<QnAVo>();
			String sql = "select q.q_no, q_type, q_title, q_inquirdate, q_status, a.a_date from qna q, answer a where q.q_no = a.q_no and q.user_no = ? order by q.q_no";
			try{
				Connection conn = db.ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, no);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					int q_no = rs.getInt(1);
					String q_type = rs.getString(2);
					String q_title = rs.getString(3);
					Date q_inquirdate = rs.getDate(4);
					String q_status = rs.getString(5);
					Date a_date = rs.getDate(6);
					QnAVo v =new QnAVo();
					v.setQ_no(q_no);
					v.setQ_type(q_type);
					v.setQ_title(q_title);
					v.setQ_inquirdate(q_inquirdate);
					v.setQ_status(q_status);
					v.setA_date(a_date);
					list.add(v);
				}db.ConnectionProvider.close(rs, pstmt, conn);
				
			}catch(Exception c) {
				System.out.println("addList(no) 예외발생 :" + c.getMessage());
			}
			return list;
			
		}

//<관리자일 때 문의내역의 JTable rowData에 값 출력하는 메소드>
	public ArrayList<QnAVo> addList(){
		ArrayList<QnAVo> list = new ArrayList<QnAVo>();
		String sql = "select q.q_no, q_type, q_title, q_inquirdate, q_status, a.a_date from qna q, answer a where q.q_no = a.q_no order by q.q_no";
		try{
			Connection conn = db.ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int q_no = rs.getInt(1);
				String q_type = rs.getString(2);
				String q_title = rs.getString(3);
				Date q_inquirdate = rs.getDate(4);
				String q_status = rs.getString(5);
				Date a_date = rs.getDate(6);
				QnAVo v =new QnAVo();
				v.setQ_no(q_no);
				v.setQ_type(q_type);
				v.setQ_title(q_title);
				v.setQ_inquirdate(q_inquirdate);
				v.setQ_status(q_status);
				v.setA_date(a_date);
				list.add(v);
			}db.ConnectionProvider.close(rs, pstmt, conn);
			
		}catch(Exception c) {
			System.out.println("addList() 예외발생 :" + c.getMessage());
		}
		return list;
		
	}

}

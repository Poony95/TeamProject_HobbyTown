package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.ConnectionProvider;
import messages.Message;
import vo.MessageVO;
 
public class MessageDAO {
	// 받은 쪽지 리스트를 불러오는 메서드
	public ArrayList<MessageVO> viewReceivedMessages(int recipient_no){
		ArrayList<MessageVO> list = new ArrayList<MessageVO>();
		String sql = "select m_content, date_sent,m_state, user_name recp_name, "
				+ "(select user_name from user_info where user_no=sender_no) sender_name,m_no, recipient_no, sender_no "
				+ "from messages m, user_info u "
				+ "where u.user_no=m.recipient_no and recipient_no=? order by date_sent desc";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recipient_no);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				MessageVO m = new MessageVO();
				m.setContent(rs.getString(1));
				m.setDate_sent(rs.getDate(2));
				m.setState(rs.getString(3));
				m.setRecipient_name(rs.getString(4));
				m.setSender_name(rs.getString(5));
				m.setM_no(rs.getInt(6));
				m.setRecipient_no(rs.getInt(7));
				m.setSender_no(rs.getInt(8));
				list.add(m);
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {System.out.println("viewMessages() 예외 발생 : "+e.getMessage());}
		return list;
	}
	// 보낸 쪽지 리스트를 불러오는 메서드
	public ArrayList<MessageVO> viewSentMessages(int recipient_no){
		ArrayList<MessageVO> list = new ArrayList<MessageVO>();
		String sql = "select m_content, date_sent,m_state, user_name recp_name, "
				+ "(select user_name from user_info where user_no=sender_no) sender_name,m_no, "
				+ "recipient_no, sender_no from messages m, user_info u "
				+ "where u.user_no=m.recipient_no and sender_no=? order by date_sent desc";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recipient_no);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				MessageVO m = new MessageVO();
				m.setContent(rs.getString(1));
				m.setDate_sent(rs.getDate(2));
				m.setState(rs.getString(3));
				m.setRecipient_name(rs.getString(4));
				m.setSender_name(rs.getString(5));
				m.setM_no(rs.getInt(6));
				m.setRecipient_no(rs.getInt(7));
				m.setSender_no(rs.getInt(8));
				list.add(m);
			}
			ConnectionProvider.close(rs, pstmt, conn);
		} catch (Exception e) {System.out.println("viewMessages() 예외 발생 : "+e.getMessage());}
		return list;
	}
	
	
	// 쪽지를 전송하는 메서드
	public int sendMessages(MessageVO m) {
		int re = -1;
		String sql = "insert into messages values(seq_msg.NEXTVAL, ?, sysdate, 'N', ?,?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getContent());
			pstmt.setInt(2, m.getSender_no());
			pstmt.setInt(3, m.getRecipient_no());
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("sendMessages() 예외 발생 : "+e.getMessage());}
		return re;
	}
	
	// 쪽지 상태를 읽음으로 변경하는 메서드
	public int changeState(MessageVO m) {
		int re = -1;
		String sql = "update messages set m_state = 'Y' where m_no=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m.getM_no());
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {System.out.println("viewDetailMessage() 예외 발생 : "+e.getMessage());}
		return re;
	}
	
	// 쪽지를 삭제하는 메서드
	public int deleteMessage(int m_no) {
		int re = -1;
		String sql = "delete from messages where m_no=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m_no);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(pstmt, conn);
		} catch (Exception e) {	System.out.println("deleteMessage() 예외 발생 : "+e.getMessage());}
		return re;
	}
	
	
}

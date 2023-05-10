package vo;

import java.util.Date;

public class MessageVO {
	private int m_no;
	private String content;
	private Date date_sent;
	private String state;
	private int sender_no;
	private int recipient_no;
	private String sender_name;
	private String recipient_name;
	public String getRecipient_name() {
		return recipient_name;
	}
	public void setRecipient_name(String recipient_name) {
		this.recipient_name = recipient_name;
	}
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate_sent() {
		return date_sent;
	}
	public void setDate_sent(Date date_sent) {
		this.date_sent = date_sent;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getSender_no() {
		return sender_no;
	}
	public void setSender_no(int sender_no) {
		this.sender_no = sender_no;
	}
	public int getRecipient_no() {
		return recipient_no;
	}
	public void setRecipient_no(int recipient_no) {
		this.recipient_no = recipient_no;
	}
	public String getSender_name() {
		return sender_name;
	}
	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}
	public MessageVO(int m_no, String content, Date date_sent, String state, int sender_no, int recipient_no,
			String sender_name) {
		super();
		this.m_no = m_no;
		this.content = content;
		this.date_sent = date_sent;
		this.state = state;
		this.sender_no = sender_no;
		this.recipient_no = recipient_no;
		this.sender_name = sender_name;
	}
	@Override
	public String toString() {
		return "MemoVO [m_no=" + m_no + ", content=" + content + ", date_sent=" + date_sent + ", state=" + state
				+ ", sender_no=" + sender_no + ", recipient_no=" + recipient_no + ", sender_name=" + sender_name + "]";
	}
	public MessageVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}

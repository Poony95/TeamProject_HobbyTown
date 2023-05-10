package vo;

import java.util.Date;

public class QnAVo {
	private int q_no;
	private String q_type;
	private String q_mail;
	private String q_title;
	private String q_content;
	private String q_attachedfile;
	private Date q_inquirdate;
	private String q_status;
	private int user_no;
	private Date a_date;
	private String a_content;
	public QnAVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getQ_no() {
		return q_no;
	}
	public void setQ_no(int q_no) {
		this.q_no = q_no;
	}
	public String getQ_type() {
		return q_type;
	}
	public void setQ_type(String q_type) {
		this.q_type = q_type;
	}
	public String getQ_mail() {
		return q_mail;
	}
	public void setQ_mail(String q_mail) {
		this.q_mail = q_mail;
	}
	public String getQ_title() {
		return q_title;
	}
	public void setQ_title(String q_title) {
		this.q_title = q_title;
	}
	public String getQ_content() {
		return q_content;
	}
	public void setQ_content(String q_content) {
		this.q_content = q_content;
	}
	public String getQ_attachedfile() {
		return q_attachedfile;
	}
	public void setQ_attachedfile(String q_attachedfile) {
		this.q_attachedfile = q_attachedfile;
	}
	public Date getQ_inquirdate() {
		return q_inquirdate;
	}
	public void setQ_inquirdate(Date q_inquirdate) {
		this.q_inquirdate = q_inquirdate;
	}
	public String getQ_status() {
		return q_status;
	}
	public void setQ_status(String q_status) {
		this.q_status = q_status;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public Date getA_date() {
		return a_date;
	}
	public void setA_date(Date a_date) {
		this.a_date = a_date;
	}
	public String getA_content() {
		return a_content;
	}
	public void setA_content(String a_content) {
		this.a_content = a_content;
	}
	
}
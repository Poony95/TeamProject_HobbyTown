package vo;

public class commentsVO {
	private int c_no;
	private String c_content;
	private String date_comment;
	private int user_no;
	private int b_no;

	public commentsVO(int c_no, String c_content, String date_comment, int user_no, int b_no) {
		super();
		this.c_no = c_no;
		this.c_content = c_content;
		this.date_comment = date_comment;
		this.user_no = user_no;
		this.b_no = b_no;
	}
	
	
	public commentsVO(String c_content) {
		super();
		this.c_content = c_content;
	}

	
	
	public commentsVO(int user_no, String c_content, String date_comment) {
		super();
		this.c_content = c_content;
		this.date_comment = date_comment;
		this.user_no = user_no;
	}


	public commentsVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getC_no() {
		return c_no;
	}
	public void setC_no(int c_no) {
		this.c_no = c_no;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public String getDate_comment() {
		return date_comment;
	}
	public void setDate_comment(String date_comment) {
		this.date_comment = date_comment;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}


	
	
}

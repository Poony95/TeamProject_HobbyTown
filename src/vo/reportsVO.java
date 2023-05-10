
package vo;

public class reportsVO {
	private int r_no;
	private String r_content;
	private String date_report;
	private String date_complete;
	private String r_state;
	private int user_no;
	private int b_no;
	public reportsVO(int r_no, String r_content, String date_report, String date_complete, String r_state, int user_no,
			int b_no) {
		super();
		this.r_no = r_no;
		this.r_content = r_content;
		this.date_report = date_report;
		this.date_complete = date_complete;
		this.r_state = r_state;
		this.user_no = user_no;
		this.b_no = b_no;
	}
	public reportsVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public reportsVO(String r_content) {
		super();
		this.r_content = r_content; 
	}
	public int getR_no() {
		return r_no;
	}
	public void setR_no(int r_no) {
		this.r_no = r_no;
	}
	public String getR_content() {
		return r_content;
	}
	public void setR_content(String r_content) {
		this.r_content = r_content;
	}
	public String getDate_report() {
		return date_report;
	}
	public void setDate_report(String date_report) {
		this.date_report = date_report;
	}
	public String getDate_complete() {
		return date_complete;
	}
	public void setDate_complete(String date_complete) {
		this.date_complete = date_complete;
	}
	public String getR_state() {
		return r_state;
	}
	public void setR_state(String r_state) {
		this.r_state = r_state;
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
package vo;

public class LikedVO {
	private int l_no;
	private String date_liked;
	private int b_no;
	private int user_no;
	public LikedVO(int l_no, String date_liked, int b_no, int user_no) {
		super();
		this.l_no = l_no;
		this.date_liked = date_liked;
		this.b_no = b_no;
		this.user_no = user_no;
	}
	public LikedVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getL_no() {
		return l_no;
	}
	public void setL_no(int l_no) {
		this.l_no = l_no;
	}
	public String getDate_liked() {
		return date_liked;
	}
	public void setDate_liked(String date_liked) {
		this.date_liked = date_liked;
	}
	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	
	
	
}

package vo;

public class PostVO {
	private int b_no;
	private String category;
	private String title;
	private String user_id;
	private String b_content;
	private String interest;
	private String date_board;
	private String application;
	private int b_cnt;
	private int user_no;
	public PostVO(int b_no, String category, String title, String user_id, String b_content, String interest,
			String date_board, String application, int b_cnt, int user_no) {
		super();
		this.b_no = b_no;
		this.category = category;
		this.title = title;
		this.user_id = user_id;
		this.b_content = b_content;
		this.interest = interest;
		this.date_board = date_board;
		this.application = application;
		this.b_cnt = b_cnt;
		this.user_no = user_no;
	}
	
	public PostVO(int b_no) {
		super();
		this.b_no = b_no;
	}

	public PostVO(int b_no, String category, String title, String user_id, String b_content, String interest,
			String date_board, String application, int b_cnt) {
		super();
		this.b_no = b_no;
		this.category = category;
		this.title = title;
		this.user_id = user_id;
		this.b_content = b_content;
		this.interest = interest;
		this.date_board = date_board;
		this.application = application;
		this.b_cnt = b_cnt;
	}


	public int getB_no() {
		return b_no;
	}

	public void setB_no(int b_no) {
		this.b_no = b_no;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getDate_board() {
		return date_board;
	}

	public void setDate_board(String date_board) {
		this.date_board = date_board;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public int getB_cnt() {
		return b_cnt;
	}

	public void setB_cnt(int b_cnt) {
		this.b_cnt = b_cnt;
	}

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public PostVO() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
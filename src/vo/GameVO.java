package vo;

import dao.UserDAO;

public class GameVO {
	
	private int no;
	private double score;
	private String name;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String address;
	private String id;
	
	
	public GameVO() {
		super();
		name = new UserDAO().getName(no);
		id = new UserDAO().getID(no);
		
		
		
	}
	public GameVO(int no, double score) {
		super();
		this.no = no;
		this.score = score;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	
}

package vo;

import java.sql.Date;

public class BoardVO {
   private int b_no = 0;
   private String address;
   private String category = "";
   private String interest = ""; 
   private String title = "";
   private String b_content = "";
   private Date date_board ;
   private String appilcation = "";
   private int b_cnt = 0;
   private int l_cnt= 0;
   private String ap_content = "";
   private String name = "";
   private String phone = "";
   private String gender = "";
   public BoardVO() {
      super();
   }
   public BoardVO(int b_no, String address, String category, String interest, String title, String b_content,
         Date date_board, String appilcation, int b_cnt, int l_cnt, String ap_content, String name, String phone,
         String gender) {
      super();
      this.b_no = b_no;
      this.address = address;
      this.category = category;
      this.interest = interest;
      this.title = title;
      this.b_content = b_content;
      this.date_board = date_board;
      this.appilcation = appilcation;
      this.b_cnt = b_cnt;
      this.l_cnt = l_cnt;
      this.ap_content = ap_content;
      this.name = name;
      this.phone = phone;
      this.gender = gender;
   }
   public int getB_no() {
      return b_no;
   }
   public void setB_no(int b_no) {
      this.b_no = b_no;
   }
   public String getAddress() {
      return address;
   }
   public void setAddress(String address) {
      this.address = address;
   }
   public String getCategory() {
      return category;
   }
   public void setCategory(String category) {
      this.category = category;
   }
   public String getInterest() {
      return interest;
   }
   public void setInterest(String interest) {
      this.interest = interest;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getB_content() {
      return b_content;
   }
   public void setB_content(String b_content) {
      this.b_content = b_content;
   }
   public Date getDate_board() {
      return date_board;
   }
   public void setDate_board(Date date_board) {
      this.date_board = date_board;
   }
   public String getAppilcation() {
      return appilcation;
   }
   public void setAppilcation(String appilcation) {
      this.appilcation = appilcation;
   }
   public int getB_cnt() {
      return b_cnt;
   }
   public void setB_cnt(int b_cnt) {
      this.b_cnt = b_cnt;
   }
   public int getL_cnt() {
      return l_cnt;
   }
   public void setL_cnt(int l_cnt) {
      this.l_cnt = l_cnt;
   }
   public String getAp_content() {
      return ap_content;
   }
   public void setAp_content(String ap_content) {
      this.ap_content = ap_content;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getPhone() {
      return phone;
   }
   public void setPhone(String phone) {
      this.phone = phone;
   }
   public String getGender() {
      return gender;
   }
   public void setGender(String gender) {
      this.gender = gender;
   }
   
}
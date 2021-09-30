package database;

public class RsvVO {

	private String user_id;//회원아이디
	private int center_code;//진료소 코드
	private String vc_type;//백신종류
	private String rsv_date;//날짜
	private String rsv_hour;//시간
	private String rsv_date2;

	// 은정 추가 210731
	// userData
	private String user_name; // 회원 이름
	private String user_num; // 회원 주민번호
	private String user_tel; // 회원 전화번호
	// locData
	private String loc1; // 시구 (진료소)
	private String loc2; // 시군구 (진료소)
	// centerData
	private String center_name; // 진료소명
	private String center_addr; //진료소 주소
	private String center_tel; //진료소 번호



	public RsvVO() {

	}


	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getCenter_code() {
		return center_code;
	}
	public void setCenter_code(int center_code) {
		this.center_code = center_code;
	}
	public String getVc_type() {
		return vc_type;
	}
	public void setVc_type(String vc_type) {
		this.vc_type = vc_type;
	}
	public String getRsv_date() {
		return rsv_date;
	}
	public void setRsv_date(String rsv_date) {
		this.rsv_date = rsv_date;
	}
	public String getRsv_hour() {
		return rsv_hour;
	}
	public void setRsv_hour(String rsv_hour) {
		this.rsv_hour = rsv_hour;
	}
	public String getRsv_date2() {
		return rsv_date2;
	}
	
	public void setRsv_date2(String rsv_date2) {
		this.rsv_date2 = rsv_date2;
	}	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_num() {
		return user_num;
	}
	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getLoc1() {
		return loc1;
	}
	public void setLoc1(String loc1) {
		this.loc1 = loc1;
	}
	public String getLoc2() {
		return loc2;
	}
	public void setLoc2(String loc2) {
		this.loc2 = loc2;
	}
	public String getCenter_name() {
		return center_name;
	}
	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}
	public String getCenter_addr() {
		return center_addr;
	}
	public void setCenter_addr(String center_addr) {
		this.center_addr = center_addr;
	}
	public String getCenter_tel() {
		return center_tel;
	}
	public void setCenter_tel(String center_tel) {
		this.center_tel = center_tel;
	}
}
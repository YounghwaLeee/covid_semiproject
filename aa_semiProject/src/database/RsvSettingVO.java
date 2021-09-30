package database;

public class RsvSettingVO {
	private int no;
	private String startDate;
	private String endDate;
	private int age1;
	private int age2;
	private String notice_title;
	private String notice;
	
	public RsvSettingVO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getAge1() {
		return age1;
	}

	public void setAge1(int age1) {
		this.age1 = age1;
	}

	public int getAge2() {
		return age2;
	}

	public void setAge2(int age2) {
		this.age2 = age2;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	public String getNotice_title() {
		return notice_title;
	}

	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}

}

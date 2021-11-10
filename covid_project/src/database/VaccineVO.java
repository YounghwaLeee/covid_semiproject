package database;

public class VaccineVO { // ÀºÁ¤
	private int center_code;
	private int jansen;
	private int az;
	private int pfizer;
	private int moderna;
	
	// locData
	private String loc1;
	private String loc2;
	// centerData
	private String center_name;
	private String center_tel;
	
	
	public VaccineVO() {
		
	}

	public int getCenter_code() {
		return center_code;
	}

	public void setCenter_code(int center_code) {
		this.center_code = center_code;
	}

	public int getJansen() {
		return jansen;
	}

	public void setJansen(int jansen) {
		this.jansen = jansen;
	}

	public int getAz() {
		return az;
	}

	public void setAz(int az) {
		this.az = az;
	}

	public int getPfizer() {
		return pfizer;
	}

	public void setPfizer(int pfizer) {
		this.pfizer = pfizer;
	}

	public int getModerna() {
		return moderna;
	}

	public void setModerna(int moderna) {
		this.moderna = moderna;
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

	public String getCenter_tel() {
		return center_tel;
	}

	public void setCenter_tel(String center_tel) {
		this.center_tel = center_tel;
	}
}

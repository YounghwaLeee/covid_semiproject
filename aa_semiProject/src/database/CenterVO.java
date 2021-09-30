package database;

public class CenterVO {
   
   private int center_code;
   private String center_name;
   private int loc_code;
   private String center_addr;
   private String center_time1;
   private String center_time2;
   private String center_time3;
   private String center_tel;
   
   // 은정 추가
   // locData
   private String loc1;
   private String loc2;

	
	public CenterVO() {
      
   }

   public int getCenter_code() {
      return center_code;
   }

   public void setCenter_code(int center_code) {
      this.center_code = center_code;
   }

   public String getCenter_name() {
      return center_name;
   }

   public void setCenter_name(String center_name) {
      this.center_name = center_name;
   }

   public int getLoc_code() {
      return loc_code;
   }

   public void setLoc_code(int loc_code) {
      this.loc_code = loc_code;
   }

   public String getCenter_addr() {
      return center_addr;
   }

   public void setCenter_addr(String center_addr) {
      this.center_addr = center_addr;
   }

   public String getCenter_time1() {
      return center_time1;
   }

   public void setCenter_time1(String center_time1) {
      this.center_time1 = center_time1;
   }

   public String getCenter_time2() {
      return center_time2;
   }

   public void setCenter_time2(String center_time2) {
      this.center_time2 = center_time2;
   }

   public String getCenter_time3() {
      return center_time3;
   }

   public void setCenter_time3(String center_time3) {
      this.center_time3 = center_time3;
   }

   public String getCenter_tel() {
      return center_tel;
   }

   public void setCenter_tel(String center_tel) {
      this.center_tel = center_tel;
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

}
package database;

public class RsvSettingDAO extends DBCON {

	public RsvSettingDAO() {
		// TODO Auto-generated constructor stub
	}
	
	
	// 은정 추가 -------------
   // 예약 설정값 불러오기
   public RsvSettingVO selectRsvSetting() {
	   RsvSettingVO vo= new RsvSettingVO();
      try {
         dbConn();
         
         String sql = "select no, to_char(startdate, 'yyyy/mm/dd'), to_char(enddate, 'yyyy/mm/dd'), age1, age2, notice_title, notice from rsvSettingData where no=1";
         
         pstmt=conn.prepareStatement(sql);
         
         rs=pstmt.executeQuery();
         while(rs.next()) {
            vo.setNo(rs.getInt(1));
            vo.setStartDate(rs.getString(2));
            vo.setEndDate(rs.getString(3));
            vo.setAge1(rs.getInt(4));
            vo.setAge2(rs.getInt(5));
            vo.setNotice_title(rs.getString(6));
            vo.setNotice(rs.getString(7));
         }
      } catch (Exception e) {
         System.out.println("예약 설정값 불러오기 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return vo;
   }
   
   
   // 은정 추가
   // 예약 설정 수정
   public int updateRsvSetting(RsvSettingVO vo) {
      int cnt = 0;
      try {
         dbConn();
         
         String sql = "update rsvSettingData set startdate=?, enddate=?, age1=?, age2=? where no=1";
         
         pstmt=conn.prepareStatement(sql);
         pstmt.setString(1, vo.getStartDate());
         pstmt.setString(2, vo.getEndDate());
         pstmt.setInt(3, vo.getAge1());
         pstmt.setInt(4, vo.getAge2());
         
         cnt = pstmt.executeUpdate();
      } catch (Exception e) {
         System.out.println("예약 설정 수정 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return cnt;
   }
   
   // 은정 추가
   // 예약 관련 공지사항 설정 수정
   public int updateRsvNotice(String notice_title, String notice) {
      int cnt = 0;
      try {
         dbConn();
         
         String sql = "update rsvSettingData set notice_title=?, notice=? where no=1";
         
         pstmt=conn.prepareStatement(sql);
         pstmt.setString(1, notice_title);
         pstmt.setString(2, notice);
         
         cnt = pstmt.executeUpdate();
      } catch (Exception e) {
         System.out.println("예약 공지사항 수정 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return cnt;
   }

}

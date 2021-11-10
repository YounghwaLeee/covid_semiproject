package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

public class LocDAO extends DBCON{

   public LocDAO() {
      
   }

   //희수
   //시도 불러오기
   public Vector<String> combo1() {
      Vector<String> list = new Vector<String>();
      
      try {
         dbConn();
         
         String sql = "select distinct(loc1) from locdata order by loc1";
         //나중에 지역 위치 조건 추가해야함
         //따라서 combo1에 매개변수 loc1, loc2가 들어가야함
         
         pstmt=conn.prepareStatement(sql);
           rs=pstmt.executeQuery();
           while(rs.next()) {
               //LocVO vo= new LocVO();
               String loc1 = rs.getString(1);
               list.add(loc1);
            }
      }
      catch (Exception e) {
         System.out.println("시도 정보 불러오기 에러");
         e.printStackTrace();
      }
      finally {
         dbClose(); 
      }
      return list;
   }
   
   
   //희수
   //시도 선택하면 시군구 불러오기
   public Vector<String> combo2(String loc1) {
      Vector<String> list = new Vector<String>();
      
      try {
         //System.out.println("테스트 : "+loc1);
         dbConn();
         
         String sql = "select loc2 from locdata where loc1=?";
         
         pstmt=conn.prepareStatement(sql);
         pstmt.setString(1, loc1);
           rs=pstmt.executeQuery();
           while(rs.next()) {
               //LocVO vo= new LocVO();
               String loc2 = rs.getString(1);            
               list.add(loc2);
            }
      }
      catch (Exception e) {
         System.out.println("시군구 정보 불러오기 에러");
         e.printStackTrace();
      }
      finally {
         dbClose();
      }
      return list;
   }
   	
	
	// 은정 추가
	// 주소코드로 해당 시도 시군구 정보 불러오기 // LocVO 반환
	public LocVO selectLocData(int loc_code) {
		LocVO vo= new LocVO();
		try {
			dbConn();
	         
			String sql = "select loc_code, loc1, loc2 from locdata where loc_code=?";
	         
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loc_code);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				vo.setLoc_code(rs.getInt(1));
				vo.setLoc1(rs.getString(2));
				vo.setLoc2(rs.getString(3));
			}
		} catch (Exception e) {
	         System.out.println("시군구 정보 불러오기 에러");
	         e.printStackTrace();
		} finally {
			dbClose();
		}
		return vo;
	}
	
	
	// 은정 추가
	// 해당 시도 시군구 정보로 주소코드 불러오기
	public int getLocCode(String loc1, String loc2) {
		int loc_code=0;
		try {
			dbConn();
	         
			String sql = "select loc_code from locdata where loc1=? and loc2=?";
	         
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, loc1);
			pstmt.setString(2, loc2);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				loc_code = rs.getInt(1);
			}
		} catch (Exception e) {
	         System.out.println("주소코드 불러오기 에러");
	         e.printStackTrace();
		} finally {
			dbClose();
		}
		return loc_code;
	}
	// ------------------
   

}
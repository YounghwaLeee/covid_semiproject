package database;

import java.util.ArrayList;
import java.util.List;

public class VaccineDAO extends DBCON{

	public VaccineDAO() {
		
	}
	
	// 은정 추가 -------------
	// 전체 vaccineData 백신 테이블 불러오기
	public List<VaccineVO> getAllVaccData(){
		List<VaccineVO> list = new ArrayList<VaccineVO>();
		try {
			dbConn();
			
			String sql = "select v.center_code, c.loc1, c.loc2, c.center_name, c.center_tel, v.jansen, v.az, v.pfizer, v.moderna"
					+ " from vaccinedata v"
					+ " join (select center_code, center_name, loc1, loc2, center_tel from centerData c join locData l on c.loc_code=l.loc_code) c"
					+ " on v.center_code=c.center_code"
					+ " order by c.center_code";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				VaccineVO vo = new VaccineVO();
				vo.setCenter_code(rs.getInt(1));
				vo.setLoc1(rs.getString(2));
				vo.setLoc2(rs.getString(3));
				vo.setCenter_name(rs.getString(4));
				vo.setCenter_tel(rs.getString(5));
				vo.setJansen(rs.getInt(6));
				vo.setAz(rs.getInt(7));
				vo.setPfizer(rs.getInt(8));
				vo.setModerna(rs.getInt(9));

				list.add(vo);
			}
		} catch(Exception e) {
			System.out.println("백신 전체 목록 불러오기 에러");
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return list;
	}

	
	// 은정 추가 -------------
	// 등록 --- 1개의 백신 데이터 등록 // 진료소 등록 시 자동 등록됨
	public int insertVaccData(int center_code){
		int cnt=0;
		try {
			dbConn();
			
			String sql = "insert into vaccinedata(center_code) values(?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, center_code);
		
			cnt = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("백신 데이터 등록 에러");
				e.printStackTrace();
			} finally {
				dbClose();
			}
		return cnt;
	}
	
	
	
	// 은정 추가 -------------
	// 수정 --- 1개의 vaccineData 백신 테이블 수정
	public int updateVaccData(VaccineVO vo){
		int cnt=0;
		try {
			dbConn();
			
			String sql = "update vaccinedata set jansen=?, az=?, pfizer=?, moderna=? where center_code=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getJansen());
			pstmt.setInt(2, vo.getAz());
			pstmt.setInt(3, vo.getPfizer());
			pstmt.setInt(4, vo.getModerna());
			pstmt.setInt(5, vo.getCenter_code());
		
			cnt = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("백신 수량 수정 에러");
				e.printStackTrace();
			} finally {
				dbClose();
			}
		return cnt;
	}
	
	
	// 은정 추가 ------------- 210801
	// 검색 // vaccineData 백신 부분 테이블 불러오기
	// 시도, 시군구 설정 후 검색 시
	public List<VaccineVO> getSearchVaccData(String loc1, String loc2, String searchTxt){
		List<VaccineVO> list = new ArrayList<VaccineVO>();
		try {
			dbConn();
			
			String sql = "select v.center_code, c.loc1, c.loc2, c.center_name, c.center_tel, v.jansen, v.az, v.pfizer, v.moderna"
					+ " from vaccinedata v"
					+ " join (select center_code, center_name, loc1, loc2, center_tel from centerData c join locData l on c.loc_code=l.loc_code) c"
					+ " on v.center_code=c.center_code"
					+ " where c.loc1=? and c.loc2=? and (c.center_name like ? or c.center_code like ? or c.center_tel like ?)"
					+ " order by c.center_code";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loc1);
			pstmt.setString(2, loc2);
			pstmt.setString(3, "%"+searchTxt+"%");
			pstmt.setString(4, "%"+searchTxt+"%");
			pstmt.setString(5, "%"+searchTxt+"%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				VaccineVO vo = new VaccineVO();
				vo.setCenter_code(rs.getInt(1));
				vo.setLoc1(rs.getString(2));
				vo.setLoc2(rs.getString(3));
				vo.setCenter_name(rs.getString(4));
				vo.setCenter_tel(rs.getString(5));
				vo.setJansen(rs.getInt(6));
				vo.setAz(rs.getInt(7));
				vo.setPfizer(rs.getInt(8));
				vo.setModerna(rs.getInt(9));

				list.add(vo);
			}
		} catch(Exception e) {
			System.out.println("백신 전체 목록 불러오기 에러");
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return list;
	}
	
	
	// 은정 추가 ------------- 210801
	// 검색 // vaccineData 백신 부분 테이블 불러오기
	// 시도, 시군구 설정 안 하고 검색 시
	public List<VaccineVO> getSearchVaccData(String searchTxt){
		List<VaccineVO> list = new ArrayList<VaccineVO>();
		try {
			dbConn();
			
			String sql = "select v.center_code, c.loc1, c.loc2, c.center_name, c.center_tel, v.jansen, v.az, v.pfizer, v.moderna"
					+ " from vaccinedata v"
					+ " join (select center_code, center_name, loc1, loc2, center_tel from centerData c join locData l on c.loc_code=l.loc_code) c"
					+ " on v.center_code=c.center_code"
					+ " where c.center_name like ? or c.center_code like ? or c.center_tel like ?"
					+ " order by c.center_code";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+searchTxt+"%");
			pstmt.setString(2, "%"+searchTxt+"%");
			pstmt.setString(3, "%"+searchTxt+"%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				VaccineVO vo = new VaccineVO();
				vo.setCenter_code(rs.getInt(1));
				vo.setLoc1(rs.getString(2));
				vo.setLoc2(rs.getString(3));
				vo.setCenter_name(rs.getString(4));
				vo.setCenter_tel(rs.getString(5));
				vo.setJansen(rs.getInt(6));
				vo.setAz(rs.getInt(7));
				vo.setPfizer(rs.getInt(8));
				vo.setModerna(rs.getInt(9));

				list.add(vo);
			}
		} catch(Exception e) {
			System.out.println("백신 전체 목록 불러오기 에러");
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return list;
	}
	
	
	// 은정 추가 ------------- 210801
	// 검색 // vaccineData 백신 부분 테이블 불러오기
	// 직원 관리자 접속 시 ----- (시도 설정 후 검색)
	public List<VaccineVO> getSearchVaccData(String loc1, String searchTxt){
		List<VaccineVO> list = new ArrayList<VaccineVO>();
		try {
			dbConn();
			
			String sql = "select v.center_code, c.loc1, c.loc2, c.center_name, c.center_tel, v.jansen, v.az, v.pfizer, v.moderna"
					+ " from vaccinedata v"
					+ " join (select center_code, center_name, loc1, loc2, center_tel from centerData c join locData l on c.loc_code=l.loc_code) c"
					+ " on v.center_code=c.center_code"
					+ " where c.loc1=? and (c.center_name like ? or c.center_code like ? or c.center_tel like ?)"
					+ " order by c.center_code";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loc1);
			pstmt.setString(2, "%"+searchTxt+"%");
			pstmt.setString(3, "%"+searchTxt+"%");
			pstmt.setString(4, "%"+searchTxt+"%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				VaccineVO vo = new VaccineVO();
				vo.setCenter_code(rs.getInt(1));
				vo.setLoc1(rs.getString(2));
				vo.setLoc2(rs.getString(3));
				vo.setCenter_name(rs.getString(4));
				vo.setCenter_tel(rs.getString(5));
				vo.setJansen(rs.getInt(6));
				vo.setAz(rs.getInt(7));
				vo.setPfizer(rs.getInt(8));
				vo.setModerna(rs.getInt(9));

				list.add(vo);
			}
		} catch(Exception e) {
			System.out.println("백신 전체 목록 불러오기 에러");
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return list;
	}
	
	
	// 은정 추가 ------------- 210802
	// 수정 --- 백신 예약 시 수량 차감 -1 하기
	public int minusVaccData(String vc_type, int center_code){
		int cnt=0;
		try {
			dbConn();
			
			String sql = "update vaccinedata set " + vc_type + "=" + vc_type +"-1 where center_code=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, center_code);

			cnt = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("예약 백신 수량 차감 에러");
				e.printStackTrace();
			} finally {
				dbClose();
			}
		return cnt;
	}
	
	
	//0802 성규 백신 수량 +1증가 
   public int plusVaccData(String vc_type, int center_code) {
      int cnt = 0;
      try{
         dbConn();
         String sql = "update vaccinedata set "+vc_type+"="+vc_type+"+1 where center_code=?";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, center_code);

         cnt = pstmt.executeUpdate();

      } catch(Exception e) {
         System.out.println("백신 수량 +1 증가 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return cnt;
   }
   
   //백신선택 21.08.02 히수가 원하는거
   public VaccineVO allvaccinedata(int center_code) {
      VaccineVO vo = new VaccineVO();
      try {
         //1. db연결
         dbConn();
         String sql = "select jansen,az,pfizer,moderna from vaccinedata where center_code=?";
         //2.prepareStatement 생성
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, center_code);

         rs = pstmt.executeQuery();
         while(rs.next()) {
            vo.setJansen(rs.getInt(1));
            vo.setAz(rs.getInt(2));
            vo.setPfizer(rs.getInt(3));
            vo.setModerna(rs.getInt(4));      

         }
      } catch (Exception e) {
         System.out.println("오류..");
         e.printStackTrace();
      }finally {
         dbClose();
      }
      return vo;
   }
   
}

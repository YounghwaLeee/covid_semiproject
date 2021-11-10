package database;

import java.util.ArrayList;
import java.util.List;


public class TestDAO extends DBCON { // 검사 예약 DAO

	public TestDAO() {
	}
	
	
	// 은정 추가 210731
	// 검사 예약 전체 목록 조회
	public List<TestVO> selectAllTestData() {
		List<TestVO> list = new ArrayList<TestVO>();
		try {
			dbConn();
			String sql = "select t.center_code, c.loc1, c.loc2, c.center_name, to_char(t.rsv_date, 'yyyy/mm/dd'), t.rsv_hour, t.user_id, u.user_name, u.user_num, u.user_tel"
					+ " from testdata t join userdata u on t.user_id=u.user_id"
					+ " join (select center_code, center_name, loc1, loc2 from centerData c join locData l on c.loc_code=l.loc_code) c"
					+ " on t.center_code=c.center_code"
					+ " order by t.center_code, t.rsv_date, t.rsv_hour, u.user_name, u.user_num";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TestVO vo = new TestVO();
				vo.setCenter_code(rs.getInt(1));
				vo.setLoc1(rs.getString(2));
				vo.setLoc2(rs.getString(3));
				vo.setCenter_name(rs.getString(4));
				vo.setRsv_date(rs.getString(5));
				vo.setRsv_hour(rs.getString(6));
				vo.setUser_id(rs.getString(7));
				vo.setUser_name(rs.getString(8));
				vo.setUser_num(rs.getString(9));
				vo.setUser_tel(rs.getString(10));
				
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("전체 검사 예약 조회 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	
	// 은정 추가 210731
	// 검사 예약 정보 등록 (추가)
	public int insertTestData(TestVO vo) {
		int cnt = 0;
		try {
			dbConn();

			String sql = "insert into testData values(?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getUser_id());
			pstmt.setInt(2, vo.getCenter_code());
			pstmt.setString(3, vo.getRsv_date());
			pstmt.setString(4, vo.getRsv_hour());	
			
			cnt = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("검사 예약 등록 에러");
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return cnt;
	}
	
	
	// 성규 추가
	// 검사 예약 전체선택
	public List<TestVO> TestRecord(){
		List<TestVO> list = new ArrayList<TestVO>();
		try {
			dbConn();
			String sql = "select user_id,center_code,to_char(rsv_date,'YYYY-MM-DD')rsv_date,rsv_hour from TESTDATA order by user_id asc";

			pstmt = conn.prepareStatement(sql);

			rs=pstmt.executeQuery();
			while (rs.next()) {
				TestVO vo = new TestVO();
				vo.setUser_id(rs.getString(1));
				vo.setCenter_code(rs.getInt(2));
				vo.setRsv_date(rs.getString(3));
				vo.setRsv_hour(rs.getString(4));
				list.add(vo);
			}
		}catch (Exception e) {
			System.out.println("검사 예약 전체 불러오기 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	// 성규 추가
	// 검사 예약 삭제
	public int deleteTestData(String user_id) {
		int cnt = 0;
		try {
			dbConn();
			//			System.out.println("실행");
			String sql = "delete from TESTDATA where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);

			cnt = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("검사 예약 삭제 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return cnt;
	}
	
	//희수 추가
   //아이디로 검사예약정보 불러오기
   public TestVO selectTestRsv(String user_id) {
      TestVO vo= new TestVO();
      try {
         dbConn();
         String sql = "select c.center_code, c.loc1, c.loc2, c.center_name, to_char(t.rsv_date, 'yyyy/mm/dd'), t.rsv_hour, u.user_id, u.user_name, u.user_num, u.user_tel, c.center_addr, c.center_tel "
               + "from testdata t join userdata u on t.user_id=u.user_id "
               + "join (select center_addr, center_tel, center_code, center_name, loc1, loc2 from centerData c "
               + "join locData l on c.loc_code=l.loc_code) c on t.center_code=c.center_code "
               + "where u.user_id=? "
               + "order by c.center_code, t.rsv_date, t.rsv_hour, u.user_name, u.user_num";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, user_id);
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
            vo.setCenter_code(rs.getInt(1));
            vo.setLoc1(rs.getString(2));
            vo.setLoc2(rs.getString(3));
            vo.setCenter_name(rs.getString(4));
            vo.setRsv_date(rs.getString(5));
            vo.setRsv_hour(rs.getString(6));
            vo.setUser_id(rs.getString(7));
            vo.setUser_name(rs.getString(8));
            vo.setUser_num(rs.getString(9));
            vo.setUser_tel(rs.getString(10));
            vo.setCenter_addr(rs.getString(11));
            vo.setCenter_tel(rs.getString(12));
         }
      } catch (Exception e) {
         System.out.println("회원 검사 예약 조회 에러 발생");
         e.printStackTrace();
      }finally {
         dbClose();
      }
      return vo;
   }
   
   
   	// 은정 추가 210801
	// (관리자용) 검사 예약 검색 목록 조회 ----텍스트필드만 검색
	public List<TestVO> getSearchTestData(String searchTxt) {
		List<TestVO> list = new ArrayList<TestVO>();
		try {
			dbConn();
			String sql = "select t.center_code, c.loc1, c.loc2, c.center_name, to_char(t.rsv_date, 'yyyy/mm/dd'), t.rsv_hour, t.user_id, u.user_name, u.user_num, u.user_tel"
					+ " from testdata t join userdata u on t.user_id=u.user_id"
					+ " join (select center_code, center_name, loc1, loc2, center_tel from centerData c join locData l on c.loc_code=l.loc_code) c"
					+ " on t.center_code=c.center_code"
					+ " where t.center_code like ? or center_name like ? or to_char(t.rsv_date, 'yyyy/mm/dd') like ? or t.user_id like ? or user_name like ? or user_num like ? or user_tel like ?"
					+ " order by t.center_code, t.rsv_date, t.rsv_hour, u.user_name, u.user_num";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+searchTxt+"%");
			pstmt.setString(2, "%"+searchTxt+"%");
			pstmt.setString(3, "%"+searchTxt+"%");
			pstmt.setString(4, "%"+searchTxt+"%");
			pstmt.setString(5, "%"+searchTxt+"%");
			pstmt.setString(6, "%"+searchTxt+"%");
			pstmt.setString(7, "%"+searchTxt+"%");

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TestVO vo = new TestVO();
				vo.setCenter_code(rs.getInt(1));
				vo.setLoc1(rs.getString(2));
				vo.setLoc2(rs.getString(3));
				vo.setCenter_name(rs.getString(4));
				vo.setRsv_date(rs.getString(5));
				vo.setRsv_hour(rs.getString(6));
				vo.setUser_id(rs.getString(7));
				vo.setUser_name(rs.getString(8));
				vo.setUser_num(rs.getString(9));
				vo.setUser_tel(rs.getString(10));
				
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("관리자용 검사 예약 관리 검색(텍스트필드) 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	
	// 은정 추가 210801
	// (관리자용) 검사 예약 검색 목록 조회 ---- 시도, 시군구, 텍스트필드 검색
	public List<TestVO> getSearchTestData(String loc1, String loc2, String searchTxt) {
		List<TestVO> list = new ArrayList<TestVO>();
		try {
			dbConn();
			String sql = "select t.center_code, c.loc1, c.loc2, c.center_name, to_char(t.rsv_date, 'yyyy/mm/dd'), t.rsv_hour, t.user_id, u.user_name, u.user_num, u.user_tel"
					+ " from testdata t join userdata u on t.user_id=u.user_id"
					+ " join (select center_code, center_name, loc1, loc2, center_tel from centerData c join locData l on c.loc_code=l.loc_code) c"
					+ " on t.center_code=c.center_code"
					+ " where loc1=? and loc2=?"
					+ " and (t.center_code like ? or center_name like ? or to_char(t.rsv_date, 'yyyy/mm/dd') like ? or t.user_id like ? or user_name like ? or user_num like ? or user_tel like ?)"
					+ " order by t.center_code, t.rsv_date, t.rsv_hour, u.user_name, u.user_num";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loc1);
			pstmt.setString(2, loc2);
			pstmt.setString(3, "%"+searchTxt+"%");
			pstmt.setString(4, "%"+searchTxt+"%");
			pstmt.setString(5, "%"+searchTxt+"%");
			pstmt.setString(6, "%"+searchTxt+"%");
			pstmt.setString(7, "%"+searchTxt+"%");
			pstmt.setString(8, "%"+searchTxt+"%");
			pstmt.setString(9, "%"+searchTxt+"%");

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TestVO vo = new TestVO();
				vo.setCenter_code(rs.getInt(1));
				vo.setLoc1(rs.getString(2));
				vo.setLoc2(rs.getString(3));
				vo.setCenter_name(rs.getString(4));
				vo.setRsv_date(rs.getString(5));
				vo.setRsv_hour(rs.getString(6));
				vo.setUser_id(rs.getString(7));
				vo.setUser_name(rs.getString(8));
				vo.setUser_num(rs.getString(9));
				vo.setUser_tel(rs.getString(10));
				
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("관리자용 검사 예약 관리 검색(시도, 시군구, 텍스트필드) 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	// 은정 추가 210801
	// (직원 관리자용) 검사 예약 검색 목록 조회 ---- 시도, 텍스트필드 검색
	public List<TestVO> getSearchTestData(String loc1, String searchTxt) {
		List<TestVO> list = new ArrayList<TestVO>();
		try {
			dbConn();
			String sql = "select t.center_code, c.loc1, c.loc2, c.center_name, to_char(t.rsv_date, 'yyyy/mm/dd'), t.rsv_hour, t.user_id, u.user_name, u.user_num, u.user_tel"
					+ " from testdata t join userdata u on t.user_id=u.user_id"
					+ " join (select center_code, center_name, loc1, loc2, center_tel from centerData c join locData l on c.loc_code=l.loc_code) c"
					+ " on t.center_code=c.center_code"
					+ " where loc1=?"
					+ " and (t.center_code like ? or center_name like ? or to_char(t.rsv_date, 'yyyy/mm/dd') like ? or t.user_id like ? or user_name like ? or user_num like ? or user_tel like ?)"
					+ " order by t.center_code, t.rsv_date, t.rsv_hour, u.user_name, u.user_num";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loc1);
			pstmt.setString(2, "%"+searchTxt+"%");
			pstmt.setString(3, "%"+searchTxt+"%");
			pstmt.setString(4, "%"+searchTxt+"%");
			pstmt.setString(5, "%"+searchTxt+"%");
			pstmt.setString(6, "%"+searchTxt+"%");
			pstmt.setString(7, "%"+searchTxt+"%");
			pstmt.setString(8, "%"+searchTxt+"%");

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TestVO vo = new TestVO();
				vo.setCenter_code(rs.getInt(1));
				vo.setLoc1(rs.getString(2));
				vo.setLoc2(rs.getString(3));
				vo.setCenter_name(rs.getString(4));
				vo.setRsv_date(rs.getString(5));
				vo.setRsv_hour(rs.getString(6));
				vo.setUser_id(rs.getString(7));
				vo.setUser_name(rs.getString(8));
				vo.setUser_num(rs.getString(9));
				vo.setUser_tel(rs.getString(10));
				
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("관리자용 검사 예약 관리 검색(시도, 텍스트필드) 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
}
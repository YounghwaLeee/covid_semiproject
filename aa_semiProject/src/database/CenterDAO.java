package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CenterDAO extends DBCON {

   public CenterDAO() {
      
   }
   
   // 은정 추가 ------------- //희수 수정
   // 진료소 테이블 전체 불러오기 // 오래 걸려서 일단은 조건절 추가했는데 빼줄 것임. (where center_code<1100)
   public List<CenterVO> selectAllCenterData(){
      List<CenterVO> list = new ArrayList<CenterVO>();
      try {
         dbConn();
         
         String sql = "select c.center_code, c.center_name, c.loc_code, c.center_addr, c.center_tel, c.center_time1, c.center_time2, c.center_time3, l.loc1, l.loc2 "
               + "from centerData c join locData l "
               + "on c.loc_code=l.loc_code order by center_code";
         
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         while(rs.next()) {
            CenterVO vo = new CenterVO();
            vo.setCenter_code(rs.getInt(1));
            vo.setCenter_name(rs.getString(2));
            vo.setLoc_code(rs.getInt(3));
            vo.setCenter_addr(rs.getString(4));
            vo.setCenter_tel(rs.getString(5));
            vo.setCenter_time1(rs.getString(6));
            vo.setCenter_time2(rs.getString(7));
            vo.setCenter_time3(rs.getString(8));
            vo.setLoc1(rs.getString(9));
            vo.setLoc2(rs.getString(10));
            
            list.add(vo);
         }
      } catch(Exception e) {
         System.out.println("진료소 전체 목록 불러오기 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return list;
   }
   
   
   // 은정 추가 -------------
   // 진료소 코드로 해당 진료소 1개 정보 전부 불러오기 ----- List 반환 -------------- selectCenter(int center_code)랑 중복이고 반환 타입이 다름
   public List<CenterVO> selectCenterData(int center_code) {
      List<CenterVO> list = new ArrayList<CenterVO>();
      
      try {
         dbConn();
         
         String sql = "select center_code, center_name, loc_code, center_addr, center_tel, center_time1, center_time2, center_time3 from centerdata where center_code=?";
         
         pstmt=conn.prepareStatement(sql);
         pstmt.setInt(1, center_code);
         
         rs=pstmt.executeQuery();
         while(rs.next()) {
            CenterVO vo= new CenterVO();
            vo.setCenter_code(rs.getInt(1));
            vo.setCenter_name(rs.getString(2));
            vo.setLoc_code(rs.getInt(3));
            vo.setCenter_addr(rs.getString(4));
            vo.setCenter_tel(rs.getString(5));
            vo.setCenter_time1(rs.getString(6));
            vo.setCenter_time2(rs.getString(7));
            vo.setCenter_time3(rs.getString(8));
            
            list.add(vo);
         }
      } catch (Exception e) {
         System.out.println("진료소 정보 불러오기 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return list;
   }
   // -----------------------
   
   
   
   // 은정 추가 -------------
   // 진료소 코드로 해당 진료소 1개 정보 전부 불러오기 ----- CenterVO 반환(웬만하면 이걸로 쓰기)
   public CenterVO selectCenter(int center_code) {
      CenterVO vo= new CenterVO();
      try {
         dbConn();
         
         String sql = "select center_code, center_name, loc_code, center_addr, center_tel, center_time1, center_time2, center_time3 from centerdata where center_code=?";
         
         pstmt=conn.prepareStatement(sql);
         pstmt.setInt(1, center_code);
         
         rs=pstmt.executeQuery();
         while(rs.next()) {
            vo.setCenter_code(rs.getInt(1));
            vo.setCenter_name(rs.getString(2));
            vo.setLoc_code(rs.getInt(3));
            vo.setCenter_addr(rs.getString(4));
            vo.setCenter_tel(rs.getString(5));
            vo.setCenter_time1(rs.getString(6));
            vo.setCenter_time2(rs.getString(7));
            vo.setCenter_time3(rs.getString(8));
         }
      } catch (Exception e) {
         System.out.println("진료소 정보 불러오기 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return vo;
   }
   // -----------------------
   
   
   // 은정 추가 -------------
   // 진료소 1개 조회 ----- 진료소 정보들로 진료소 코드 얻어오기 (백신 데이터 등록용)
   public int getCenterCordData(CenterVO vo) {
      int center_code=0;
      try {
         dbConn();
         
         String sql = "select center_code from centerdata where center_name=? and loc_code=? and center_addr=? and center_tel=?";
         
         pstmt=conn.prepareStatement(sql);
         pstmt.setString(1, vo.getCenter_name());
         pstmt.setInt(2, vo.getLoc_code());
         pstmt.setString(3, vo.getCenter_addr());
         pstmt.setString(4, vo.getCenter_tel());
         
         rs=pstmt.executeQuery();
         while(rs.next()) {
            center_code = rs.getInt(1);
         }
      } catch (Exception e) {
         System.out.println("진료소 정보 불러오기 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return center_code;
   }
   // -----------------------
   

   // 은정 추가
   // 진료소 정보 등록 (추가)
   public int insertCenterData(CenterVO vo) {
      int cnt = 0;
      try {
         dbConn();
         
         String sql = "insert into centerdata(center_name, loc_code, center_addr, center_tel, center_time1, center_time2, center_time3)"
                  + " values(?, ?, ?, ?, ?, ?, ?)";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, vo.getCenter_name());
         pstmt.setInt(2, vo.getLoc_code());
         pstmt.setString(3, vo.getCenter_addr());
         pstmt.setString(4, vo.getCenter_tel());
         pstmt.setString(5, vo.getCenter_time1());
         if(vo.getCenter_time2()==null || vo.getCenter_time2().equals("")) pstmt.setString(6, "미운영");
         else pstmt.setString(6, vo.getCenter_time2());
         if(vo.getCenter_time3()==null || vo.getCenter_time3().equals("")) pstmt.setString(7, "미운영");
         else pstmt.setString(7, vo.getCenter_time3());
         
         cnt = pstmt.executeUpdate();
         
      } catch(Exception e) {
         System.out.println("진료소 정보 등록 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return cnt;
   }
   
   
   // 은정 추가
   // 진료소 정보 수정
   public int updateCenterData(CenterVO vo) {
      int cnt = 0;
      try {
         dbConn();
         
         String sql = "update centerdata set center_name=?, loc_code=?, center_addr=?, center_tel=?, center_time1=?, center_time2=?, center_time3=? where center_code=?";
         
         pstmt=conn.prepareStatement(sql);
         pstmt.setString(1, vo.getCenter_name());
         pstmt.setInt(2, vo.getLoc_code());
         pstmt.setString(3, vo.getCenter_addr());
         pstmt.setString(4, vo.getCenter_tel());
         pstmt.setString(5, vo.getCenter_time1());
         if(vo.getCenter_time2()==null || vo.getCenter_time2().equals("")) pstmt.setString(6, "미운영");
         else pstmt.setString(6, vo.getCenter_time2());
         if(vo.getCenter_time3()==null || vo.getCenter_time3().equals("")) pstmt.setString(7, "미운영");
         else pstmt.setString(7, vo.getCenter_time3());
         pstmt.setInt(8, vo.getCenter_code());
         
         cnt = pstmt.executeUpdate();
      } catch (Exception e) {
         System.out.println("진료소 수정 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return cnt;
   }
   
   
   // 은정 추가
   // 진료소 정보 삭제
   public int deleteCenterData(int center_code) {
      int cnt = 0;
      try {
         dbConn();
         
         String sql = "delete from centerdata where center_code=?";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, center_code);
         
         cnt = pstmt.executeUpdate();
         
      } catch(Exception e) {
         System.out.println("진료소 정보 삭제 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return cnt;
   }
   
   
   //회원용! 진료소 검색21.08.01 영화추가  //시도 시군구 콤보박스 체크안하고 텍스트필드만 검색했을때 (쿼리문 실제 연결시 띄어쓰기 중요함)
   public List<CenterVO> memberCenterSearch(String centerName){
     List<CenterVO> list = new ArrayList<CenterVO>();
     try {
        dbConn();
      
        String sql = "select c.center_code, c.center_name, c.loc_code ,l.loc1,l.loc2, c.center_addr,c.center_time1,c.center_time2,c.center_time3,center_tel"
                 + " from centerdata c join locdata l on c.loc_code=l.loc_code where center_name like ? order by center_code";
        pstmt = conn.prepareStatement(sql);
        //?먼저 값을 넣어주고 밑에거를 실앵해야한다.
        pstmt.setString(1,"%"+centerName+"%"); // '%강남%' centername 검색어를 말하는거다.
        rs = pstmt.executeQuery();//결과테이블 담아주는데
        
        while(rs.next()) { //결과테이블을 푼거
           CenterVO vo = new CenterVO();
           vo.setCenter_code(rs.getInt(1));
           vo.setCenter_name(rs.getString(2));
           vo.setLoc_code(rs.getInt(3));
           vo.setLoc1(rs.getString(4));
           vo.setLoc2(rs.getString(5));
           vo.setCenter_addr(rs.getString(6));
           vo.setCenter_time1(rs.getString(7));
           vo.setCenter_time2(rs.getString(8));
           vo.setCenter_time3(rs.getString(9));
           vo.setCenter_tel(rs.getString(10));

           list.add(vo);
        }
     } catch(Exception e) {
        System.out.println("회원용 진료소 텍스트필드 검색 에러...");
        e.printStackTrace();
     } finally {
        dbClose();
     }
     return list;
   }
   
   
   //회원용! 진료소 검색21.08.01 영화추가  //시도 시군구 콤보박스만 선택했을때  (쿼리문 실제 연결시 띄어쓰기 중요함)
   public List<CenterVO> memberCenterSearch(String loc1, String loc2, String centerName){
     List<CenterVO> list = new ArrayList<CenterVO>();
     try {
        dbConn();
        //시도 시군구 콤보박스 체크안하고 텍스트필드만 검색했을때 (쿼리문 실제 연결시 띄어쓰기 중요함)
        String sql = "select c.center_code, c.center_name, c.loc_code ,l.loc1,l.loc2, c.center_addr,c.center_time1,c.center_time2,c.center_time3,center_tel"
              + " from centerdata c join locdata l on c.loc_code=l.loc_code where loc1=? and loc2=? and center_name like ? order by center_code";
        pstmt = conn.prepareStatement(sql);
        //?먼저 값을 넣어주고 밑에거를 실앵해야한다.
       
        pstmt.setString(1,loc1);
        pstmt.setString(2,loc2);
        pstmt.setString(3,"%"+centerName+"%"); // '%강남%' centername 검색어를 말하는거다.
        rs = pstmt.executeQuery();//결과테이블 담아주는데
        
        while(rs.next()) { //결과테이블을 푼거
           CenterVO vo = new CenterVO();
           vo.setCenter_code(rs.getInt(1));
           vo.setCenter_name(rs.getString(2));
           vo.setLoc_code(rs.getInt(3));
           vo.setLoc1(rs.getString(4));
           vo.setLoc2(rs.getString(5));
           vo.setCenter_addr(rs.getString(6));
           vo.setCenter_time1(rs.getString(7));
           vo.setCenter_time2(rs.getString(8));
           vo.setCenter_time3(rs.getString(9));
           vo.setCenter_tel(rs.getString(10));

           list.add(vo);
        }
     } catch(Exception e) {
        System.out.println("회원용 진료소 시도, 시군구, 텍스트필드 검색 에러...");
        e.printStackTrace();
     } finally {
        dbClose();
     }
     return list;
  }
   
   
   // 은정 추가 -------------
   // (관리자용) 진료소 관리 --- 진료소 검색
   // 텍스트필드만 검색했을 때
   public List<CenterVO> getSearchCenterData(String searchTxt){
      List<CenterVO> list = new ArrayList<CenterVO>();
      try {
         dbConn();
         
         String sql = "select center_code, center_name, c.loc_code, loc1, loc2, center_addr, center_tel, center_time1, center_time2, center_time3"
         		+ " from centerData c join locData l"
         		+ " on c.loc_code=l.loc_code"
         		+ " where center_code like ? or center_name like ? or center_addr like ? or center_tel like ?"
         		+ " order by center_code";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%"+searchTxt+"%");
         pstmt.setString(2, "%"+searchTxt+"%");
         pstmt.setString(3, "%"+searchTxt+"%");
         pstmt.setString(4, "%"+searchTxt+"%");
         
         rs = pstmt.executeQuery();
         while(rs.next()) {
            CenterVO vo = new CenterVO();
            vo.setCenter_code(rs.getInt(1));
            vo.setCenter_name(rs.getString(2));
            vo.setLoc_code(rs.getInt(3));
            vo.setLoc1(rs.getString(4));
            vo.setLoc2(rs.getString(5));
            vo.setCenter_addr(rs.getString(6));
            vo.setCenter_tel(rs.getString(7));
            vo.setCenter_time1(rs.getString(8));
            vo.setCenter_time2(rs.getString(9));
            vo.setCenter_time3(rs.getString(10));
            
            list.add(vo);
         }
      } catch(Exception e) {
         System.out.println("관리자용 진료소 관리 텍스트필드 검색 불러오기 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return list;
   }
   
   
   // 은정 추가 -------------
   // (관리자용) 진료소 관리 --- 진료소 검색
   // 시도, 시군구, 텍스트필드 검색했을 때
   public List<CenterVO> getSearchCenterData(String loc1, String loc2, String searchTxt){
      List<CenterVO> list = new ArrayList<CenterVO>();
      try {
         dbConn();
         
         String sql = "select center_code, center_name, c.loc_code, loc1, loc2, center_addr, center_tel, center_time1, center_time2, center_time3"
         		+ " from centerData c join locData l"
         		+ " on c.loc_code=l.loc_code"
         		+ " where loc1=? and loc2=? and (center_code like ? or center_name like ? or center_addr like ? or center_tel like ?)"
         		+ " order by center_code";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, loc1);
         pstmt.setString(2, loc2);
         pstmt.setString(3, "%"+searchTxt+"%");
         pstmt.setString(4, "%"+searchTxt+"%");
         pstmt.setString(5, "%"+searchTxt+"%");
         pstmt.setString(6, "%"+searchTxt+"%");
         
         rs = pstmt.executeQuery();
         while(rs.next()) {
            CenterVO vo = new CenterVO();
            vo.setCenter_code(rs.getInt(1));
            vo.setCenter_name(rs.getString(2));
            vo.setLoc_code(rs.getInt(3));
            vo.setLoc1(rs.getString(4));
            vo.setLoc2(rs.getString(5));
            vo.setCenter_addr(rs.getString(6));
            vo.setCenter_tel(rs.getString(7));
            vo.setCenter_time1(rs.getString(8));
            vo.setCenter_time2(rs.getString(9));
            vo.setCenter_time3(rs.getString(10));
            
            list.add(vo);
         }
      } catch(Exception e) {
         System.out.println("관리자용 진료소 관리 시도, 시군구, 텍스트필드 검색 불러오기 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return list;
   }
   
   
   // 은정 추가 -------------
   // (직원 관리자용) 진료소 관리 --- 진료소 검색
   // 시도, 텍스트필드 검색했을 때
   public List<CenterVO> getSearchCenterData(String loc1, String searchTxt){
      List<CenterVO> list = new ArrayList<CenterVO>();
      try {
         dbConn();
         
         String sql = "select center_code, center_name, c.loc_code, loc1, loc2, center_addr, center_tel, center_time1, center_time2, center_time3"
         		+ " from centerData c join locData l"
         		+ " on c.loc_code=l.loc_code"
         		+ " where loc1=? and (center_code like ? or center_name like ? or center_addr like ? or center_tel like ?)"
         		+ " order by center_code";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, loc1);
         pstmt.setString(2, "%"+searchTxt+"%");
         pstmt.setString(3, "%"+searchTxt+"%");
         pstmt.setString(4, "%"+searchTxt+"%");
         pstmt.setString(5, "%"+searchTxt+"%");
         
         rs = pstmt.executeQuery();
         while(rs.next()) {
            CenterVO vo = new CenterVO();
            vo.setCenter_code(rs.getInt(1));
            vo.setCenter_name(rs.getString(2));
            vo.setLoc_code(rs.getInt(3));
            vo.setLoc1(rs.getString(4));
            vo.setLoc2(rs.getString(5));
            vo.setCenter_addr(rs.getString(6));
            vo.setCenter_tel(rs.getString(7));
            vo.setCenter_time1(rs.getString(8));
            vo.setCenter_time2(rs.getString(9));
            vo.setCenter_time3(rs.getString(10));
            
            list.add(vo);
         }
      } catch(Exception e) {
         System.out.println("관리자용 진료소 관리 시도, 텍스트필드 검색 불러오기 에러");
         e.printStackTrace();
      } finally {
         dbClose();
      }
      return list;
   }
   
}
package database;

import java.util.ArrayList;
import java.util.List;

public class AdminDAO extends DBCON {

	public AdminDAO() {
	}
	//직원전체선택
	public List<AdminVO> adminallRecord() {
		List<AdminVO> list = new ArrayList<AdminVO>();
		try {
			//1. db연결
			dbConn();
			String sql = "select admin_id,admin_pw,admin_name,admin_num,admin_tel,admin_local,admin_grade from ADMINDATA order by admin_id asc";
			//2.prepareStatement 생성
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				AdminVO vo = new AdminVO();
				vo.setAdmin_id(rs.getString(1));
				vo.setAdmin_pw(rs.getString(2));
				vo.setAdmin_name(rs.getString(3));
				vo.setAdmin_num(rs.getString(4));
				vo.setAdmin_tel(rs.getString(5));
				vo.setAdmin_local(rs.getString(6));
				vo.setAdmin_grade(rs.getInt(7));
				list.add(vo);
			};
		} catch (Exception e) {
			System.out.println("전체회원 불러오기 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	
	//관리자검색
	public List<AdminVO> searchadmindata(String fieldName,String search) {
		List<AdminVO>list = new ArrayList<AdminVO>();
		try {
			dbConn();

			String sql = "select admin_id , admin_name , admin_num , admin_tel ,admin_local from admindata where "+fieldName+" like ? order by admin_id asc";

			//			String sql = "select admin_id , admin_name , admin_num , admin_tel ,admin_local from admindata where ? like ? order by admin_id asc";

			System.out.println("sql->"+sql);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+search+"%");// "%김%"
			//			pstmt.setString(1,fieldName);
			//			pstmt.setString(2,"%"+search+"%");
			rs = pstmt.executeQuery();
//			System.out.println(fieldName+"필드명"+search);
			while(rs.next()) {
				AdminVO vo = new AdminVO();
				vo.setAdmin_id(rs.getString(1));
				vo.setAdmin_name(rs.getString(2));
				vo.setAdmin_num(rs.getString(3));
				vo.setAdmin_tel(rs.getString(4));
				vo.setAdmin_local(rs.getString(5));

//				System.out.println(vo.getAdmin_id());
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("관리자검색에러 발생.......");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	
	public List<AdminVO> admingrade0() {//관리자 승인 대기 왼쪽JList
		List<AdminVO> list = new ArrayList<AdminVO>();
		try {
			//1. db연결
			dbConn();
			String sql = "select admin_id,admin_pw,admin_name,admin_num,admin_tel,admin_local,admin_grade from ADMINDATA where admin_grade =0 order by admin_id asc";
			//2.prepareStatement 생성
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				AdminVO vo = new AdminVO();
				vo.setAdmin_id(rs.getString(1));
				vo.setAdmin_pw(rs.getString(2));
				vo.setAdmin_name(rs.getString(3));
				vo.setAdmin_num(rs.getString(4));
				vo.setAdmin_tel(rs.getString(5));
				vo.setAdmin_local(rs.getString(6));
				vo.setAdmin_grade(rs.getInt(7));
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("관리자대기직원 불러오기 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	
	public List<AdminVO> admingrade1() {//관리자 승인 오른쪽JList
		List<AdminVO> list = new ArrayList<AdminVO>();
		try {
			//1. db연결
			dbConn();
			String sql = "select admin_id,admin_pw,admin_name,admin_num,admin_tel,admin_local,admin_grade from ADMINDATA where admin_grade =1 order by admin_id asc";
			//2.prepareStatement 생성
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				AdminVO vo = new AdminVO();
				vo.setAdmin_id(rs.getString(1));
				vo.setAdmin_pw(rs.getString(2));
				vo.setAdmin_name(rs.getString(3));
				vo.setAdmin_num(rs.getString(4));
				vo.setAdmin_tel(rs.getString(5));
				vo.setAdmin_local(rs.getString(6));
				vo.setAdmin_grade(rs.getInt(7));
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("관리자직원 불러오기 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	
	//관리자삭제
	public int deleteAdmin(String admin_id) {
		int cnt = 0;
		try {
			dbConn();
			String sql = "delete from ADMINDATA where admin_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, admin_id);

			cnt = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("관리자삭제 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return cnt;
	}
	
	//관리자수정
	public int updateGrade(int admin_grade,String admin_id) {
		int cnt = 0;
		try {
			dbConn();
			String sql = "UPDATE admindata SET admin_grade = ? WHERE admin_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,admin_grade);
			pstmt.setString(2,admin_id);

			cnt=pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("관리자 권한 수정 에러 발생...");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return cnt;
	}
	
	
	
	//--- 희수 파일 추가
	
	// 관리자 회원가입
	public int adminSignUp(AdminVO vo) {
      int cnt=0;
      try {
         dbConn();
         String sql = "insert into admindata(admin_id,admin_pw,admin_name,admin_num,admin_tel,admin_local,admin_grade) values(?,?,?,?,?,?,?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, vo.getAdmin_id());
         pstmt.setString(2, vo.getAdmin_pw());
         pstmt.setString(3, vo.getAdmin_name());
         pstmt.setString(4, vo.getAdmin_num());
         pstmt.setString(5, vo.getAdmin_tel());
         pstmt.setString(6, vo.getAdmin_local());
         pstmt.setInt(7, vo.getAdmin_grade());
         cnt = pstmt.executeUpdate();
      }catch(Exception e) {
         System.out.println("관리자 추가 에러 발생..");
         e.printStackTrace();
      }finally{
         dbClose();
      }
      return cnt;
   }
	
	//관리자 휴대폰 번호 중복 체크
	public int telNumCheck(String admin_tel) {
      int telApprove =0;
      String tel="";
      try {
         dbConn();
         String sql = "select admin_tel from admindata where admin_tel=?";
         pstmt=conn.prepareStatement(sql);
         pstmt.setString(1, admin_tel);
          rs = pstmt.executeQuery();
          while(rs.next()) {
             tel=rs.getString(1);    
          }
          if(tel.equals("")) {//중복이 안된거니깐0,1로 바꿔주고 넘겨주는거
             telApprove=1;
          }
          System.out.println(telApprove);
      }catch(Exception e) {
         System.out.println("휴대폰 번호 에러 발생");
         e.printStackTrace();
      }finally {
         dbClose();
      }
      return telApprove;
   }
	
	
	//관리자 정보 불러오기. 21.07.31 콤보박스랑 같이불러옴. 
   public AdminVO setMyInfo(String admin_id) {
      AdminVO vo= new AdminVO();

      //list는 vo형태를 담아줬다. 
      try {
         dbConn();//db연결을 할건데 이 값들이 필요하다. 
         String sql="select admin_id,admin_pw,admin_name,admin_num,admin_tel, admin_local, admin_grade from admindata where admin_id=?";
         pstmt=conn.prepareStatement(sql);
         pstmt.setString(1, admin_id);
         rs=pstmt.executeQuery();
         while(rs.next()) { //내 테이블에 있는 값들을 세팅을 해주면 vo에 담아서 세팅을 해줄겁니다. 
            vo.setAdmin_id(rs.getString(1));//메일 내용을 쓰는거
            vo.setAdmin_pw(rs.getString(2));
            vo.setAdmin_name(rs.getString(3));
            vo.setAdmin_num(rs.getString(4));
            vo.setAdmin_tel(rs.getString(5));
            vo.setAdmin_local(rs.getString(6));
            vo.setAdmin_grade(rs.getInt(7));
         }
      }catch(Exception ea) {
         System.out.println("관리자 아이디로 관리자 모든 정보 불러오기 오류");
         ea.printStackTrace();
      }finally {
         dbClose();
      }
      return vo;
   }

   
   // 영화 추가
   //관리자 정보 수정 21.07.31
   public int updateInfo(AdminVO adminvo) {
      int cnt=0;
      AdminVO vo= adminvo;
      System.out.println(vo.getAdmin_id());
      System.out.println(vo.getAdmin_pw());
      System.out.println(vo.getAdmin_tel());
      
      try {
         dbConn();
         String sql= "update admindata set admin_pw=?,admin_tel=? where admin_id=?";
     pstmt =conn.prepareStatement(sql);

     pstmt.setString(1, vo.getAdmin_pw());
     pstmt.setString(2, vo.getAdmin_tel());
     pstmt.setString(3, vo.getAdmin_id());
     
     cnt=pstmt.executeUpdate();
     if(cnt >0)
     {
        System.out.println("잘해결됨!!");
     }
        
  }catch(Exception ea) {
     System.out.println("나의 정보 수정 에러");
         ea.printStackTrace();
      }finally {
         dbClose();
      }
      return cnt;
   }
   
   
   
   //관리자 아이디 찾기
   public String searchId(String name, String num ,String tel){
      //List<UserVO> list= new ArrayList<UserVO>();
      String id ="";
      try {
         dbConn();
         String sql="select admin_id from adminData where admin_name=? and admin_num=? and admin_tel=?";//?대신 걔네가 입력한 값을 넣어라 !!!! 

         pstmt=conn.prepareStatement(sql);//생성 레코드 전체를 선택하는 쿼리문
         
         pstmt.setString(1, name);
         pstmt.setString(2, num);
         pstmt.setString(3, tel);
         rs=pstmt.executeQuery();
         while(rs.next()) {
            id=rs.getString(1);
         }
         
      }catch(Exception e) {
         System.out.println("관리자 ID 찾기 오류 발생");
         e.printStackTrace();
      }finally {
         dbClose();
      }
      return id;//무조건 반환해야함
   }
   
   
   // 희수 추가
   //비밀번호 찾기. (회원정보 일치하는지? 일치하면 아이디 반환)
   public String searchAdminPw(AdminVO vo) { 
      String id="";
      try {
         dbConn();
         String sql="select admin_id from adminData where admin_id=? and admin_name=? and admin_num=? and admin_tel=? ";
         
         pstmt=conn.prepareStatement(sql);
         
         pstmt.setString(1, vo.getAdmin_id());
         pstmt.setString(2, vo.getAdmin_name());
         pstmt.setString(3, vo.getAdmin_num());
         pstmt.setString(4, vo.getAdmin_tel());
         
         rs=pstmt.executeQuery();
         while(rs.next()){
            id=rs.getString(1);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }finally {
         dbClose();
      }
      return id;
   }
   
   //관리자 비밀번호 변경
   public int passWordModify(String Admin_pw, String Admin_id) {
   int cnt=0;
   try {
      dbConn();
      String sql = "update userdata set user_pw=? where user_id=?"; //쿼리문 문제 없음 정상임
      pstmt = conn.prepareStatement(sql);
    System.out.println(Admin_pw+Admin_id);
      pstmt.setString(1, Admin_pw);
      pstmt.setString(2, Admin_id);
      cnt = pstmt.executeUpdate();
   }catch(Exception e) {
      System.out.println("비밀번호 변경 에러발생");
      e.printStackTrace();
   }finally{
      dbClose();
   }
   System.out.println(cnt);
   return cnt;
}

   
}
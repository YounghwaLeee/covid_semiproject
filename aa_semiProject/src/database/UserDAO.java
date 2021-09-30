package database;

import java.util.ArrayList;
import java.util.List;


public class UserDAO extends DBCON {//회원 DAO

	public UserDAO() {
	}
	
	// 성규 추가
	// 회원 전체선택
	public List<UserVO> UserRecord() {
		List<UserVO> list = new ArrayList<UserVO>();
		try {
			dbConn();
			String sql = "select User_id,user_pw,User_name,User_num,User_tel,user_date from USERDATA order by USER_id asc";
			
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				UserVO vo = new UserVO();
				vo.setUser_id(rs.getString(1));
				vo.setUser_pw(rs.getString(2));
				vo.setUser_name(rs.getString(3));
				vo.setUser_num(rs.getString(4));
				vo.setUser_tel(rs.getString(5));
				vo.setUser_date(rs.getString(6));		

				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("전체회원 불러오기 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	// 성규 추가
	// 회원검색
	public List<UserVO> searchuserdata(String fieldName,String search) {
		List<UserVO>list = new ArrayList<UserVO>();
		try {
			dbConn();

			String sql = "select user_id , user_name , user_num , user_tel from userdata where "+fieldName+" like ? order by user_id asc"; 

			//"select user_id , user_name , user_num , user_tel from userdata where "+fieldName+" like ? order by user_id asc";

			//String sql = "select admin_id , admin_name , admin_num , admin_tel ,admin_local from admindata where ? like ? order by admin_id asc";



			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+search+"%");// "%김%"
			//			pstmt.setString(1,fieldName);ㅁ
			rs = pstmt.executeQuery();
			while(rs.next()) {

				UserVO vo = new UserVO();
				vo.setUser_id(rs.getString(1));
				vo.setUser_name(rs.getString(2));
				vo.setUser_num(rs.getString(3));
				vo.setUser_tel(rs.getString(4));

				//System.out.println(vo.getAdmin_id());
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("회원검색에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	
	// 성규 추가
	// 회원 삭제
	public int deleteUser(String user_id) {
		int cnt = 0;
		try {
			dbConn();
			//			System.out.println("실행");
			String sql = "delete from USERDATA where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);

			cnt = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("회원삭제 에러 발생");
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return cnt;
	}
	
	
	//영화 추가
	//비밀번호 수정하기 PasswordSetting
    public int passWordModify(String User_pw, String User_id) {
	     int cnt=0;
	     try {
	        dbConn();
	        String sql = "update userdata set user_pw=? where user_id=?"; //쿼리문 문제 없음 정상임
	        pstmt = conn.prepareStatement(sql);
	      System.out.println(User_pw+User_id);
	        pstmt.setString(1, User_pw);
	        pstmt.setString(2, User_id);
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
    
    
    // 영화 추가
    //나의 정보 불러오기
    public UserVO setMyInfo(String user_id) {
       UserVO vo= new UserVO();

       //list는 vo형태를 담아줬다. 
       try {
          dbConn();//db연결을 할건데 이 값들이 필요하다. 
          String sql="select user_id,user_pw,user_name,user_num,user_tel, user_date from userdata where user_id=?";
          pstmt=conn.prepareStatement(sql);
          pstmt.setString(1, user_id);

          rs=pstmt.executeQuery();
          while(rs.next()) { //내 테이블에 있는 값들을 세팅을 해주면 vo에 담아서 세팅을 해줄겁니다. 
             vo.setUser_id(rs.getString(1));//메일 내용을 쓰는거
             vo.setUser_pw(rs.getString(2));
             vo.setUser_name(rs.getString(3));
             vo.setUser_num(rs.getString(4));
             vo.setUser_tel(rs.getString(5));
             vo.setUser_date(rs.getString(6));
          }
       }catch(Exception ea) {
          ea.printStackTrace();
       }finally {
          dbClose();
       }
       return vo;
    }
    
    // 영화 추가
    //정보 불러온걸 수정
    public int updateInfo(UserVO uservo) {
       int cnt=0;
       try {
          dbConn();
          String sql= "update userdata set user_pw=?,user_tel=? where user_id=?";
          pstmt =conn.prepareStatement(sql);
          
          pstmt.setString(1, uservo.getUser_pw());
          pstmt.setString(2, uservo.getUser_tel());
          pstmt.setString(3, uservo.getUser_id());
          
          cnt= pstmt.executeUpdate();

       }catch(Exception ea) {
          System.out.println("나의 정보 수정 에러");
          ea.printStackTrace();
       }finally {
          dbClose();
       }
       return cnt;
    }
  	
    
    // 영화 추가
	//비밀번호 찾기. 아이디를 반환해야 하니깐  
	public String searchPw(UserVO uservo) {//uservo 를 담아주겠다. 
		String id="";
		try {
			dbConn();
			String sql="select user_id from userData where user_id=? and user_name=? and user_num=? and user_tel=? ";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, uservo.getUser_id());
			pstmt.setString(2, uservo.getUser_name());
			pstmt.setString(3, uservo.getUser_num());
			pstmt.setString(4, uservo.getUser_tel());
			
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

	
  	//아이디 찾기
  	public String searchId(String name, String num ,String tel){
  		//List<UserVO> list= new ArrayList<UserVO>();
  		String id ="";
  		try {
  			dbConn();
  			String sql="select user_id from userData where user_name=? and user_num=? and user_tel=?";//?대신 걔네가 입력한 값을 넣어라 !!!! 

  			pstmt=conn.prepareStatement(sql);//생성 레코드 전체를 선택하는 쿼리문
  			
  			pstmt.setString(1, name);
  			pstmt.setString(2, num);
  			pstmt.setString(3, tel);
  			rs=pstmt.executeQuery();
  			while(rs.next()) {
  				id=rs.getString(1);
  			}
  			
  		}catch(Exception e) {
  			System.out.println("id 찾기 오류발생...");
  			e.printStackTrace();
  		}finally {
  			dbClose();
  		}
  		System.out.println(name+num+tel);
  		System.out.println(id);
  		return id;//무조건 반환해야함
  	}
  	
  	
  	//희수추가
    //회원 회원가입
    public int userSignUp(UserVO vo) {
       int cnt=0;
       try {
          dbConn();
          String sql = "insert into userdata(user_id,user_pw,user_name,user_num,user_tel) values(?,?,?,?,?)";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, vo.getUser_id());
          pstmt.setString(2, vo.getUser_pw());
          pstmt.setString(3, vo.getUser_name());
          pstmt.setString(4, vo.getUser_num());
          pstmt.setString(5, vo.getUser_tel());
          cnt = pstmt.executeUpdate();
       }
       catch(Exception e) {
          System.out.println("회원 추가 에러 발생");
          e.printStackTrace();
       }
       finally{
          dbClose();
       }
       return cnt;
    }
    
    
    //희수추가
    //회원 휴대폰 번호 중복 체크
    public int telNumCheck(String user_tel) {
       int telApprove =0;
       String tel="";
       try {
          dbConn();
          String sql = "select user_tel from userdata where user_tel=?";
          pstmt=conn.prepareStatement(sql);
          pstmt.setString(1, user_tel);
           rs = pstmt.executeQuery();
           while(rs.next()) {
              tel=rs.getString(1);    
           }
           if(tel.equals("")) {//중복이 안된거니깐0,1로 바꿔주고 넘겨주는거
              telApprove=1;
           }
           System.out.println(telApprove);
       }catch(Exception e) {
          System.out.println("휴대폰번호 중복여부 검사 에러");
          e.printStackTrace();
       }finally {
          dbClose();
       }
       return telApprove;
    }

   
}
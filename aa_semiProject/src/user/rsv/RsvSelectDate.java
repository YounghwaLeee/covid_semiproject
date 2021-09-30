package user.rsv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.json.simple.JSONObject;

import database.CenterDAO;
import database.CenterVO;
import database.RsvDAO;
import database.RsvSettingDAO;
import database.RsvSettingVO;
import database.RsvVO;
import database.TestDAO;
import database.TestVO;
import database.UserDAO;
import database.UserVO;
import database.VaccineDAO;
import database.VaccineVO;
import net.nurigo.java_sdk.api.Message;
import user.check.TabCheckReservationinformation;
import user.check.TestRsvCheck;
import user.check.VaccineRsvCheck;
import user.home.MainTitle;
import user.home.SetStyle;
import user.home.UserTabMenu;
import user.searchcenter.SearchCenterMain;
import user.searchcenter.SearchCenterSplit;

public class RsvSelectDate extends JPanel implements ActionListener{ // 은정 // 검사+백신 예약 날짜시간 선택 화면
   // ---- 받아와야 할 값
   private String user_id = MainTitle.user_id; // 로그인 회원 아이디 저장
   private int center_code; // 진료소 코드
   private String vc_type; // 백신 코드
   // ----------------------------------
   private String center_name;
   private String center_time1;
   private String center_time2;
   private String center_time3;
   
   String rsvDate = ""; // 선택된 날짜(일)
   String rsvTime = ""; // 선택된 시간
   
   Calendar now = Calendar.getInstance();
   // 예약 시작일
   int startYear = now.get(Calendar.YEAR);
   int startMonth = now.get(Calendar.MONTH)+1;
   int startDay = now.get(Calendar.DAY_OF_MONTH)+1;
   // 예약 종료일
   int endYear = now.get(Calendar.YEAR); 
   int endMonth = now.get(Calendar.MONTH)+1; 
   int endDay = now.get(Calendar.DAY_OF_MONTH)+1;
   
   int lastday; int week;
   int year = startYear; int month = startMonth;// year, month: 년월 콤보박스 선택값
   
   String vaccStr;
   String testStr;
   
   // -------- 화면 구현 컨테이너+컴포넌트 ---------
   JPanel centerPane = new JPanel(null);
      // 날짜 선택
      JPanel pane1 = new JPanel(new BorderLayout());
         JPanel dateTopPane = new JPanel(new BorderLayout());
            JLabel dateLbl = new JLabel("날짜 선택", JLabel.LEFT);
         JPanel datePane = new JPanel(new BorderLayout());
            JPanel ymPane = new JPanel();
               Integer[] yearArr = {startYear-1, startYear, startYear+1};
               Integer[] monthArr = {1,2,3,4,5,6,7,8,9,10,11,12};
               JComboBox<Integer> yearCB = new JComboBox<Integer>(yearArr);
               JComboBox<Integer> monthCB = new JComboBox<Integer>(monthArr);
               JButton leftBtn = new JButton("<");
               JButton rightBtn = new JButton(">");
            JPanel dPane = new JPanel(new BorderLayout());
               JPanel weekPane = new JPanel(new GridLayout(1, 7));
                  String weekStr = "일월화수목금토";
               JPanel dayPane = new JPanel(new GridLayout(0, 7));
                  JButton[] dayBtn = new JButton[38];
      // 시간 선택
      JPanel pane2 = new JPanel(new BorderLayout());
         JPanel timeTopPane = new JPanel(new BorderLayout());
            JLabel timeLbl = new JLabel("시간 선택", JLabel.LEFT);
         JPanel timePane = new JPanel();
            JButton[] timeBtn = new JButton[10];;
            JLabel timeTxt = new JLabel("예약할 날짜를 선택해주세요.");
         
      // 안내문구 + 취소/예약완료 버튼
      JPanel pane3 = new JPanel(new BorderLayout());
         JEditorPane rsvTxt = new JEditorPane();
         JPanel btnPane = new JPanel();
            JButton cancleBtn = new JButton("취소");
            JButton rsvBtn = new JButton("예약완료");
   
   // 폰트 // 컬러
   SetStyle st = new SetStyle();
         
   
   public RsvSelectDate() {}
   
   // [검사 예약]일 경우
   public RsvSelectDate(int center_code) { // 진료소 코드
      this.center_code = center_code;
      
      // ----------- 검사 예약 기간 설정 (기본 2달로 설정함) ------------
//      // 예약 시작일
//      startYear = now.get(Calendar.YEAR);
//      startMonth = now.get(Calendar.MONTH)+1;
//      startDay = now.get(Calendar.DAY_OF_MONTH)+1;
      
      // 예약 종료일 (2달 후)
      endYear = now.get(Calendar.YEAR); 
      if(startMonth+2>12) { // 2달 후가 12월보다 크면
         endMonth = startMonth+2-12;   // 다음 연도, 월로 바꾸기
         endYear = now.get(Calendar.YEAR)+1;
      } else {
         endMonth = startMonth+2;
      }
      endDay = now.get(Calendar.DAY_OF_MONTH)+1;
      //--------------------------------------------------------
      
      start();
   }
   
   // [백신 예약]일 경우
   public RsvSelectDate(int center_code, String vc_type) { // 진료소 코드, 백신 종류
      this.center_code = center_code;
      this.vc_type = vc_type;
      
      RsvSettingDAO dao = new RsvSettingDAO();
      RsvSettingVO vo = dao.selectRsvSetting();
      
      String startDate = vo.getStartDate();
      String endDate = vo.getEndDate();
      System.out.println(startDate+","+endDate);
      
      //----------- 백신 예약 기간 설정 ----------
      // 예약 시작일
      startYear = Integer.parseInt(startDate.substring(0,4));
      startMonth = Integer.parseInt(startDate.substring(5,7));
      startDay = Integer.parseInt(startDate.substring(8));
      // 예약 종료일
      endYear = Integer.parseInt(endDate.substring(0,4));
      endMonth = Integer.parseInt(endDate.substring(5,7));
      endDay = Integer.parseInt(endDate.substring(8));
      //--------------------------------------
      
      start();
   }
   
   public void start() {
//      setBackground(Color.WHITE);
      getCenterInfo(); // 해당 진료소 정보 가져오기 (진료소명, 운영시간 3가지)
      
      // ----- pane1 ------ 날짜 선택 패널
      dateLbl.setFont(st.fnt1);
      dateTopPane.add(dateLbl);
      dateTopPane.setBorder(new EmptyBorder(10,10,10,0)); // 여백 북서남동 순서
      dateTopPane.setOpaque(false);
      //dateTopPane.setBackground(Color.WHITE); //
      
      leftBtn.setFont(st.fnt2);
      leftBtn.setBackground(Color.WHITE);
      leftBtn.setBorderPainted(false);
      rightBtn.setFont(st.fnt2);
      rightBtn.setBackground(Color.WHITE);
      rightBtn.setBorderPainted(false);
      yearCB.setSelectedItem(year);
      yearCB.setFont(st.fnt1);
      yearCB.setBackground(Color.WHITE);
      monthCB.setSelectedItem(month);
      monthCB.setFont(st.fnt1);
      monthCB.setBackground(Color.WHITE);
      
      ymPane.add(leftBtn);
      ymPane.add(yearCB);
      ymPane.add(monthCB);
      ymPane.add(rightBtn);
      ymPane.setBackground(Color.WHITE); //
      
      setWeek(); // 요일 세팅
      setDefaultDate(); // 날짜 세팅
      
      weekPane.setBackground(Color.WHITE);
      dayPane.setBackground(Color.WHITE);
      dPane.add(weekPane, BorderLayout.NORTH);
      dPane.add(dayPane);
      
      datePane.add(ymPane, BorderLayout.NORTH);
      datePane.add(dPane);
      
      pane1.add(dateTopPane, BorderLayout.NORTH);
      pane1.add(datePane);
      
      pane1.setBounds(0, 20, 400, 290);
      pane1.setBackground(Color.WHITE); //
      
      // ----- pane2 ------ 시간 선택 패널
      timeLbl.setFont(st.fnt1);
      timeTopPane.setBorder(new EmptyBorder(10,10,10,0)); // 여백 북서남동 순서
      timeTopPane.setBackground(Color.WHITE);
      timeTopPane.add(timeLbl);
      setDefaultTimeBtn(); // 시간 버튼 세팅
      timePane.setBorder(new EmptyBorder(10,10,10,10)); // 여백 북서남동 순서
      timePane.setBackground(Color.WHITE);
      pane2.add(timeTopPane, BorderLayout.NORTH);
      pane2.add(timePane, BorderLayout.CENTER);
      pane2.setBounds(0, 330, 400, 140);
      
      // ----- pane3 ------ 안내 텍스트 + 취소/예약완료 패널
      rsvTxt.setText("예약 날짜와 시간을 선택해 주세요.");
      rsvTxt.setFont(st.fnt2);
      rsvTxt.setOpaque(false);
      rsvTxt.setAlignmentX(JEditorPane.CENTER_ALIGNMENT);
      rsvTxt.setFocusable(false);
      rsvTxt.setBorder(new EmptyBorder(10,10,10,10));
      cancleBtn.setFont(st.fnt1);
      cancleBtn.setForeground(Color.WHITE);
      cancleBtn.setBackground(st.clr1);
      cancleBtn.setBorderPainted(false);
      rsvBtn.setFont(st.fnt1);
      rsvBtn.setForeground(Color.WHITE);
      rsvBtn.setBackground(st.clr1);
      rsvBtn.setBorderPainted(false);
      btnPane.add(cancleBtn);   btnPane.add(rsvBtn);
      pane3.add(rsvTxt);
      pane3.add(btnPane, BorderLayout.SOUTH);
      pane3.setBounds(0, 480, 400, 110);
      
      centerPane.setPreferredSize(new Dimension(400,600));
//      centerPane.setBackground(Color.WHITE);
      centerPane.add(pane1);
      centerPane.add(pane2);
      centerPane.add(pane3);
      
      add(centerPane);
      
      // 이벤트 발생 등록
      yearCB.addActionListener(this);
      monthCB.addActionListener(this);
      leftBtn.addActionListener(this);
      rightBtn.addActionListener(this);
      cancleBtn.addActionListener(this);
      rsvBtn.addActionListener(this);
   }
   
   // 해당 진료소 정보 가져오기 (진료소명, 운영시간 3가지)
   public void getCenterInfo() {
      CenterDAO dao = new CenterDAO();
      CenterVO vo = dao.selectCenter(center_code);
      center_name = vo.getCenter_name();
      center_time1 = vo.getCenter_time1();
      center_time2 = vo.getCenter_time2();
      center_time3 = vo.getCenter_time3();
   }
   
   // 요일 세팅
   public void setWeek() {
      for(int i=0; i<weekStr.length(); i++) {
         JLabel weekLbl = new JLabel(weekStr.substring(i, i+1));
         if(i==0) weekLbl.setForeground(Color.RED);
         if(i==6) weekLbl.setForeground(Color.BLUE);
         weekLbl.setFont(st.fnt1);
         weekLbl.setHorizontalAlignment(JLabel.CENTER);
         weekPane.add(weekLbl);
      }
   }
   
   // 현재 날짜 세팅
   public void setDefaultDate() {
      now.set(year, month-1, 1);
      week = now.get(Calendar.DAY_OF_WEEK);
      lastday = now.getActualMaximum(Calendar.DAY_OF_MONTH);

      for(int i=0; i<dayBtn.length; i++) {
         if(i<week-1) {   // 공백
            dayBtn[i] = new JButton("");
            dayBtn[i].setBackground(Color.WHITE);
            dayBtn[i].setBorderPainted(false);
            dayBtn[i].setEnabled(false);
            dayPane.add(dayBtn[i]);
         } else if(i<lastday+week-1) { // 날짜 버튼
            dayBtn[i] = new JButton((i+1)-(week-1)+"");
            
            if((i+1)%7==1) { // 일요일
               dayBtn[i].setForeground(Color.RED);
               if(center_time3.equals("미운영")) dayBtn[i].setEnabled(false);
            }
            else if((i+1)%7==0) { // 토요일
               dayBtn[i].setForeground(Color.BLUE);
               if(center_time2.equals("미운영")) dayBtn[i].setEnabled(false);
            }
            dayBtn[i].setFont(st.fnt1);
            dayBtn[i].setHorizontalAlignment(JLabel.CENTER);
            dayBtn[i].setBackground(Color.WHITE);
            dayBtn[i].setBorderPainted(false);
            // 날짜 비활성화
            if(year==endYear && month==endMonth) { // ----- 예약 종료일 이후 비활성화
               if((i+1)-(week-1) > endDay) dayBtn[i].setEnabled(false);
               if(year==startYear && month==startMonth) { // ----- 예약 시작일 이전 비활성화
                  if((i+1)-(week-1) < startDay) dayBtn[i].setEnabled(false);
               } 
            } else if(year>endYear || (year==endYear && month>endMonth)) {
               dayBtn[i].setEnabled(false);
            } else if(year<startYear || (year==startYear && month<startMonth)) { // ----- 예약 시작일 이전 비활성화
               dayBtn[i].setEnabled(false);
            }

            // 버튼 등록 + 이벤트 등록
            dayBtn[i].addActionListener(this);
            dayPane.add(dayBtn[i]);
         } else { // 삽입 안 하고 생성만 한 버튼 // 배열값 오류 때문에 나머지 버튼 생성만 함. dayPane에는 안 넣었음
            dayBtn[i] = new JButton(""); 
         }
      }
   }
   
   // 날짜 선택 이벤트 발생 후 날짜 다시 세팅 --- 선택된 것 보여주기 위함
   public void setDate() { //
      dayPane.removeAll();
      dayPane.setVisible(false);
      setDefaultDate();
      dayPane.setVisible(true);
   }
   
   // 이전 버튼 눌렀을 때
   public void setPrev() {
      int yearIdx = (Integer) yearCB.getSelectedItem();
      int monthIdx = (Integer) monthCB.getSelectedItem();
      if(monthIdx==1) {
         yearCB.setSelectedItem(yearIdx-1);
         monthCB.setSelectedItem(12);
         if(yearIdx==startYear-1) {
            yearCB.setSelectedItem(yearIdx);
            monthCB.setSelectedItem(monthIdx);
         }
      } else {
         monthCB.setSelectedItem(monthIdx-1);
      }
   }
   
   // 다음 버튼 눌렀을 때
   public void setNext() {
      int yearIdx = (Integer) yearCB.getSelectedItem();
      int monthIdx = (Integer) monthCB.getSelectedItem();
      if(monthIdx==12) {
         yearCB.setSelectedItem(yearIdx+1);
         monthCB.setSelectedItem(1);
         if(yearIdx==startYear+1) {
            yearCB.setSelectedItem(yearIdx);
            monthCB.setSelectedItem(monthIdx);
         }
      } else {
         monthCB.setSelectedItem(monthIdx+1);
      }
   }
   
   // 처음 시간 선택 Pane 내용 설정 --- 날짜 선택하라는 안내 메세지
   public void setDefaultTimeBtn() {
      timeTxt.setFont(st.fnt2);
      timePane.add(timeTxt);
   }
   
   // 날짜 선택 후 시간 선택 세팅
   public void setTimeBtn() {
      timePane.removeAll();
      timePane.setVisible(false);
      timePane.setLayout(new GridLayout(2, 5, 12, 12));   // 5열 // 간격 위아래 12

      String center_time="";
      
      // 선택 날짜 가져오기
      now.set(year, month-1, Integer.parseInt(rsvDate));
      week = now.get(Calendar.DAY_OF_WEEK);
      // 요일에 따른 운영시간 가져오기
      switch(week) {
         case 7: center_time = center_time2; break; // 토요일 운영시간
         case 1: center_time = center_time3; break; // 일요일 운영시간
         default: center_time = center_time1; // 평일 운영시간
      }
      
      if(center_time.equals("미운영")) {
         timePane.setLayout(new FlowLayout());
         timeTxt.setText("진료소 미운영");
         timeTxt.setFont(st.fnt2);
         timePane.add(timeTxt);
      } else {
         int openTime = Integer.parseInt(center_time.substring(0,2));
         int closeTime = Integer.parseInt(center_time.substring(8,10));

         // 00:00~24:00 같은 시간에 대해 예약 시간 변경
         if(openTime<8) openTime=9;
         if(closeTime==0 || closeTime>8) closeTime=19;
         //-------------------------------------------
         
         for(int i=0; i<10; i++) {
            if(i+openTime<10) timeBtn[i] = new JButton("0"+(i+openTime)+":00");
            else timeBtn[i] = new JButton(i+openTime+":00");   // 버튼 10개만 생성
            if(i<closeTime-openTime) {   // 운영시간 버튼 10개보다 적게 나오면 그만큼만 패널에 삽입하기
               timeBtn[i].setFont(st.fnt2);
               timeBtn[i].setBorder(new LineBorder(Color.GRAY, 1));
               timeBtn[i].setBackground(new Color(255,255,255)); // 버튼 비활성화 시 주석 처리
               // 시간 버튼 비활성화 시 아래 주석 내용 사용
//               timeBtn[i].setBackground(new Color(220,220,220));
//               timeBtn.setBorderPainted(false);
//               timeBtn.setEnabled(false);
               timeBtn[i].addActionListener(this);
               timePane.add(timeBtn[i]);
            } else { // 나머지는 빈패널로 채워서 레이아웃 흐트러지지 않게 함.
               JPanel emptyPane = new JPanel();
               emptyPane.setOpaque(false); // 투명하게
               timePane.add(emptyPane);
            }
         } // for
      }
      timePane.setVisible(true);
   }
   
   // 날짜 선택하면 실행
   public void selectDate() {
      for(int i=0; i<dayBtn.length; i++) {   // 선택 안 된 나머지 버튼 색상 원래대로 돌려놓기
         if((i+1)%7==1) dayBtn[i].setForeground(Color.RED);
         else if((i+1)%7==0) dayBtn[i].setForeground(Color.BLUE);
         else dayBtn[i].setForeground(Color.BLACK);
         dayBtn[i].setBackground(Color.WHITE);
      }
      setTimeBtn();   // 시간 버튼 생성
      rsvTxt.setText("예약 시간을 선택해 주세요.");
   }
   
   
   // [코로나 검사 예약] 시간 선택하면 실행 --- 검사 예약 문구 출력
   public void selectTime() {
      for(int i=0; i<timeBtn.length; i++) {   // 선택 안 된 나머지 버튼 색상 원래대로 돌려놓기
         timeBtn[i].setForeground(Color.BLACK);
         timeBtn[i].setBackground(Color.WHITE);
         timeBtn[i].setBorderPainted(true);
      }
      // 검사 예약일 경우
      testStr = year + "년 " + month + "월 " + rsvDate + "일 " + rsvTime + "에 " + center_name + "에서 코로나 검사 예정입니다.";
		rsvTxt.setText(testStr);
   }
   
   // [백신 예약] 시간 선택하면 실행 --- 백신 예약 문구 출력
   public void selectTime(String vc_type) {
      for(int i=0; i<timeBtn.length; i++) {   // 선택 안 된 나머지 버튼 색상 원래대로 돌려놓기
         timeBtn[i].setForeground(Color.BLACK);
         timeBtn[i].setBackground(Color.WHITE);
         timeBtn[i].setBorderPainted(true);
      }
      // 백신 예약일 경우
      vaccStr = year + "년 " + month + "월 " + rsvDate + "일 " + rsvTime + "에 " + center_name + "에서 " + vc_type
				+ " 백신 예방 접종 예정입니다.";
		rsvTxt.setText(vaccStr);
   }
   
   
   
   // [코로나 검사 예약] 등록
   public void insertTest() {
      TestVO vo = new TestVO();
      TestDAO dao = new TestDAO();
      
      vo.setUser_id(user_id);
      vo.setCenter_code(center_code);
      vo.setRsv_date(year+"/"+month+"/"+rsvDate);
      vo.setRsv_hour(rsvTime);

      int cnt = dao.insertTestData(vo);
      if(cnt>0) {
         JOptionPane.showMessageDialog(this, "코로나 검사 예약이 완료되었습니다.");
			UserTabMenu.centerpane.removeAll();
			TabCheckReservationinformation rsvCheck = new TabCheckReservationinformation();
			UserTabMenu.centerpane.add(rsvCheck);
			TestRsvCheck ct = new TestRsvCheck(MainTitle.user_id);
			rsvCheck.inData.removeAll();
			rsvCheck.inData.add(ct);

			// 문자-----------------------------------------------------------------------------------------문자
			sendMessage(testStr);
      } else {
         JOptionPane.showMessageDialog(this, "코로나 검사 예약을 실패하였습니다. 다시 시도해 주세요.");
      }
   }
   
   // [백신 예약] 등록
   public void insertRsv() {
      RsvVO vo = new RsvVO();
      RsvDAO dao = new RsvDAO();
      
      vo.setUser_id(user_id);
      vo.setCenter_code(center_code);
      vo.setVc_type(vc_type);
      vo.setRsv_date(year+"/"+month+"/"+rsvDate);
      vo.setRsv_hour(rsvTime);

      int cnt = dao.insertRsvData(vo);
      if(cnt>0) { // 예약 성공 시
         JOptionPane.showMessageDialog(this, "백신 접종 예약이 완료되었습니다.");
         UserTabMenu.centerpane.removeAll();
			TabCheckReservationinformation rsvCheck = new TabCheckReservationinformation();
			UserTabMenu.centerpane.add(rsvCheck);
			VaccineRsvCheck cr = new VaccineRsvCheck(MainTitle.user_id);
			rsvCheck.inData.removeAll();
			rsvCheck.inData.add(cr);
			// 문자-----------------------------------------------------------------------------------------문자
			sendMessage(vaccStr);
      } else { // 예약 실패 시
         // 차감된 백신 수량 다시 더하기
         String vc_col=getVaccCol();
         VaccineDAO vdao = new VaccineDAO();
         int cnt2 = vdao.plusVaccData(vc_col, center_code);
         if(cnt>0) { // 수량 차감 성공 시
            JOptionPane.showMessageDialog(this, "백신 접종 예약을 실패했습니다. 다시 시도해 주세요.");
         } else { // 수량 차감 실패 시
            JOptionPane.showMessageDialog(this, "백신 접종 예약을 실패했습니다. 관리자에게 문의해주세요.");
         }
      }
   }
   
   // 예약 완료 후 해당 진료소의 해당 백신 수량 -1 차감하기
   public void minusVaccCount() {
      String vc_col=getVaccCol();
      
      // 백신 수량 차감 하기
      VaccineDAO dao = new VaccineDAO();
      int cnt = dao.minusVaccData(vc_col, center_code);
      if(cnt>0) { // 수량 차감 성공 시
         insertRsv(); // [백신 예약] 등록하기
      } else { // 수량 차감 실패 시
         JOptionPane.showMessageDialog(this, "백신 접종 예약을 실패했습니다. 관리자에게 문의해주세요.");
      }
   }
   
   
   // 해당 백신 데이터 컬럼명 얻어오기
   public String getVaccCol() {
      String vc_col = "";
      if(vc_type.equals("얀센")) vc_col = "jansen";
      else if(vc_type.equals("아스트라제네카")) vc_col = "az";
      else if(vc_type.equals("화이자")) vc_col = "pfizer";
      else if(vc_type.equals("모더나")) vc_col = "moderna";
      
      return vc_col;
   }
   
   
   //
   public void cancleRsv() {
      int result = JOptionPane.showConfirmDialog(this, "정말 취소하시겠습니까?", "예약 취소", JOptionPane.OK_CANCEL_OPTION);
      if(result==JOptionPane.OK_OPTION) {
         if(vc_type==null || vc_type.equals("")) { // 검사 예약 취소
        	 MainTitle.lbl.setText("검사 예약");
				UserTabMenu.centerpane.removeAll();
				SearchCenterSplit.centerName = null;
				SearchCenterMain sCenter = new SearchCenterMain();
				RsvSouthBtn sBtn = new RsvSouthBtn();
				UserTabMenu.centerpane.add(sCenter);
				sBtn.rsvBtn.setText("코로나 검사 예약하기");
				UserTabMenu.centerpane.add(BorderLayout.SOUTH, sBtn);
				UserTabMenu.centerpane.updateUI();
         } else { // 백신 예약 취소
        	 MainTitle.lbl.setText("백신 접종 예약");
				UserTabMenu.centerpane.removeAll();
				SearchCenterSplit.centerName = null;
				RsvVaccineRadio.vc_type = null;
				SearchCenterMain sCenter = new SearchCenterMain();
				UserTabMenu.centerpane.removeAll();
				RsvVaccineRadio vRadio = new RsvVaccineRadio();
				UserTabMenu.centerpane.add(BorderLayout.NORTH, vRadio);
				vRadio.rBtn0.setVisible(false);
				UserTabMenu.centerpane.add(sCenter);

				RsvSouthBtn sBtn = new RsvSouthBtn();
				sBtn.rsvBtn.setText("백신 접종 예약하기");
				UserTabMenu.centerpane.add(BorderLayout.SOUTH, sBtn);
				UserTabMenu.centerpane.updateUI();
         }
      }
   }
   

   @Override
   public void actionPerformed(ActionEvent ae) {
      Object evt = ae.getSource();
      String evtStr = ae.getActionCommand();
      if(evt instanceof JComboBox) {   // 콤보박스 이벤트가 발생하면
         year = (Integer)yearCB.getSelectedItem();
         month = (Integer)monthCB.getSelectedItem();
         setDate();   // 캘린더 재설정
      } else if(evt instanceof JButton) {   // 버튼 이벤트가 발생하면
         JButton evtBtn = (JButton) ae.getSource();
         if(evt==leftBtn) {   // < 캘린더 이전 버튼
            setPrev();
            
         } else if(evt==rightBtn) {   // > 캘린더 다음 버튼
            setNext();
            
         } else if(evtStr.equals("취소")) {
            cancleRsv(); // 취소
            
         } else if(evtStr.equals("예약완료")) {
            if(vc_type==null || vc_type.equals("")) insertTest(); // [검사 예약]
            else minusVaccCount(); // [백신 예약] --- 백신 재고 차감부터 함
            
         } else if(evtStr.indexOf(":")!=-1){ // 시간 선택하면
            rsvTime = evtStr;   // 선택된 시간을 저장
            if(vc_type==null || vc_type.equals("")) selectTime();   // 시간 선택 // 검사 예약 시--- 백신 문구 출력
            else selectTime(vc_type); // 시간 선택 // 백신 예약 시--- 백신 예약 문구 출력
            evtBtn.setForeground(Color.WHITE);   // 선택된 시간 버튼 색 변경
            evtBtn.setBackground(st.clr1);
            evtBtn.setBorderPainted(false);
            
         } else {   // 날짜 선택하면
            rsvDate = evtStr; // 선택된 날짜(일)를 저장
            selectDate();   // 날짜 선택 메서드
            evtBtn.setForeground(Color.WHITE);   // 선택된 날짜 버튼 색 변경
            evtBtn.setBackground(st.clr1);
         }
      }
   }
   
	public void sendMessage(String str) {
		String api_key = "NCSFF6HVTATSPNV2";
		String api_secret = "EXFTHIJS8DYJOSKBYDNINSDO65MSW3UM";
		Message coolsms = new Message(api_key, api_secret);

		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();
		String text = str;

		UserDAO userdao = new UserDAO();
		UserVO uservo = userdao.setMyInfo(MainTitle.user_id);
		params.put("to", uservo.getUser_tel());
		params.put("from", "01087885202");
		params.put("type", "SMS");
		params.put("text", text);
		params.put("app_version", "test app 1.2"); // application name and version

		try {
			JSONObject obj = (JSONObject) coolsms.send(params);
			System.out.println(obj.toString());

		} catch (Exception e) {
			System.out.println("문자 메시지 발송 오류");
		}
	}
}
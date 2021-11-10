package admin.rsv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import user.home.SetStyle;

public class RsvCalendar extends JPanel implements ActionListener{
	String rsvDate = ""; // 선택된 날짜(일)
	String rsvTime = ""; // 선택된 시간
	
	Calendar now = Calendar.getInstance();
	int nowYear = now.get(Calendar.YEAR); // nowYear는 yearArr 생성하기 위함
	int nowMonth = now.get(Calendar.MONTH)+1;
	int lastday; int week;
	int year = nowYear; int month = nowMonth;// year, month: 년월 콤보박스 선택값
	int rsvStartDay = now.get(Calendar.DAY_OF_MONTH)+1; // 예약 가능 시작일
	
	// -------- 화면 구현 컨테이너+컴포넌트 ---------
		// 날짜 선택
		JPanel pane1 = new JPanel(new BorderLayout());
			JPanel dateTopPane = new JPanel(new BorderLayout());
				JLabel dateLbl = new JLabel("날짜 선택", JLabel.LEFT);
			JPanel datePane = new JPanel(new BorderLayout());
				JPanel ymPane = new JPanel();
					Integer[] yearArr = {nowYear-1, nowYear, nowYear+1};
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
						
	// 폰트 // 컬러
	SetStyle st = new SetStyle();
	
	public RsvCalendar() {
//		setBackground(Color.WHITE);
		
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


		
		add(pane1);
		
		// 이벤트 발생 등록
		yearCB.addActionListener(this);
		monthCB.addActionListener(this);
		leftBtn.addActionListener(this);
		rightBtn.addActionListener(this);

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
			if(i<week-1) {	// 공백
				dayBtn[i] = new JButton("");
				dayBtn[i].setBackground(Color.WHITE);
				dayBtn[i].setBorderPainted(false);
				dayBtn[i].setEnabled(false);
				dayPane.add(dayBtn[i]);
			} else if(i<lastday+week-1) { // 날짜 버튼
				dayBtn[i] = new JButton((i+1)-(week-1)+"");
				
				if((i+1)%7==1) { // 일요일
					dayBtn[i].setForeground(Color.RED);
//					if(center_time3.equals("미운영")) dayBtn[i].setEnabled(false);
				}
				else if((i+1)%7==0) { // 토요일
					dayBtn[i].setForeground(Color.BLUE);
//					if(center_time2.equals("미운영")) dayBtn[i].setEnabled(false);
				}
				dayBtn[i].setFont(st.fnt1);
				dayBtn[i].setHorizontalAlignment(JLabel.CENTER);
				dayBtn[i].setBackground(Color.WHITE);
				dayBtn[i].setBorderPainted(false);
				// 날짜 비활성화
				if(year<nowYear || (year==nowYear && month<nowMonth)) { // 현재 날짜 이전 달부터는 예약 비활성화
					dayBtn[i].setEnabled(false);
				} else if(year==nowYear && month==nowMonth) {
					if((i+1)-(week-1) < rsvStartDay) dayBtn[i].setEnabled(false);	// 예약 가능 시작일부터 날짜 활성화
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
			if(yearIdx==nowYear-1) {
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
			if(yearIdx==nowYear+1) {
				yearCB.setSelectedItem(yearIdx);
				monthCB.setSelectedItem(monthIdx);
			}
		} else {
			monthCB.setSelectedItem(monthIdx+1);
		}
	}
	
	// 날짜 선택하면 실행
	public void selectDate() {
		for(int i=0; i<dayBtn.length; i++) {	// 선택 안 된 나머지 버튼 색상 원래대로 돌려놓기
			if((i+1)%7==1) dayBtn[i].setForeground(Color.RED);
			else if((i+1)%7==0) dayBtn[i].setForeground(Color.BLUE);
			else dayBtn[i].setForeground(Color.BLACK);
			dayBtn[i].setBackground(Color.WHITE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		String evtStr = e.getActionCommand();
		if(evt instanceof JComboBox) {	// 콤보박스 이벤트가 발생하면
			year = (Integer)yearCB.getSelectedItem();
			month = (Integer)monthCB.getSelectedItem();
			setDate();	// 캘린더 재설정
		} else if(evt instanceof JButton) {	// 버튼 이벤트가 발생하면
			JButton evtBtn = (JButton) e.getSource();
			if(evt==leftBtn) {	// < 캘린더 이전 버튼
				setPrev();
				
			} else if(evt==rightBtn) {	// > 캘린더 다음 버튼
				setNext();
				
			} else if(evtStr.equals("취소")) {//------------------ 취소 버튼 눌렀을 때
				//
				
			} else {	// 날짜 선택하면
				rsvDate = evtStr; // 선택된 날짜(일)를 저장
				selectDate();	// 날짜 선택 메서드
				evtBtn.setForeground(Color.WHITE);	// 선택된 날짜 버튼 색 변경
				evtBtn.setBackground(st.clr1);				
			}
		}
		
	}

}

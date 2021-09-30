package admin.rsv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import database.RsvSettingDAO;
import database.RsvSettingVO;
import user.home.SetStyle;

public class RsvSetting extends JDialog implements ActionListener{
	public int startY, startM, startD;
	public int endY, endM, endD;
	public String startDate = "";
	public String endDate = "";
	public int age1, age2;

	JPanel centerPane = new JPanel(new BorderLayout());
	JPanel pane = new JPanel(new BorderLayout());
	// 백신 예약 기간 설정
	JPanel pane1 = new JPanel(null);
	JLabel lbl1 = new JLabel("백신 예약 기간 설정");
	JLabel startLbl = new JLabel("예약 시작일");
	JLabel endLbl = new JLabel("예약 마지막날");
	JTextField startTf = new JTextField();
	JTextField endTf = new JTextField();
	JButton startBtn = new JButton("선택");
	JButton endBtn = new JButton("선택");

	JDialog dialog1 = new JDialog();
	RsvCalendar startCal = new RsvCalendar();
	JPanel sBtnPane = new JPanel(new FlowLayout());
	JButton sCancleBtn = new JButton("취소");
	JButton sChkBtn = new JButton("확인");
	JDialog dialog2 = new JDialog();
	RsvCalendar endCal = new RsvCalendar();
	JPanel eBtnPane = new JPanel(new FlowLayout());
	JButton eCancleBtn = new JButton("취소");
	JButton eChkBtn = new JButton("확인");

	// 백신 예약 대상 설정
	JPanel pane2 = new JPanel(null);
	JLabel lbl2 = new JLabel("백신 예약 대상 설정");
	JComboBox<Integer> age1CB;
	JComboBox<Integer> age2CB;
	DefaultComboBoxModel<Integer> model1;
	DefaultComboBoxModel<Integer> model2;
	Object[] obj1;
	Object[] obj2;
	JLabel age1Lbl = new JLabel("년생 ~ ");
	JLabel age2Lbl = new JLabel("년생");
	// 백신 예약 활성화 ---- 빼놓기
	//			JPanel pane3 = new JPanel(null);
	//				JCheckBox chkBox = new JCheckBox("백신 예약 활성화");
	JPanel btnPane = new JPanel(new GridLayout(1,2,10,0));
	JButton cancleBtn = new JButton("취소");
	JButton chkBtn = new JButton("확인");

	SetStyle st = new SetStyle();

	public RsvSetting() {
		//		add(startRsv);
		setLayout(new FlowLayout());

		model1 = new DefaultComboBoxModel<Integer>();
		for(int i=2021; i>=1922; i--) {
			model1.addElement(i);
		}
		age1CB = new JComboBox<Integer>(model1);


		model2 = new DefaultComboBoxModel<Integer>();
		for(int i=2021; i>=1922; i--) {
			model2.addElement(i);
		}
		age2CB = new JComboBox<Integer>(model2);

		// 컴포넌트 세팅
		lbl1.setBounds(0, 0, 200, 30);
		startLbl.setBounds(0,40,140,30);
		startTf.setBounds(150,40,170,30);
		startBtn.setBounds(320,40,80,30);
		endLbl.setBounds(0,80,140,30);
		endTf.setBounds(150,80,170,30);
		endBtn.setBounds(320,80,80,30);

		lbl2.setBounds(0, 0, 200, 30);
		age1CB.setBounds(0, 40, 140, 30);
		age1Lbl.setBounds(150,40,50,30);
		age2CB.setBounds(210, 40, 140, 30);
		age2Lbl.setBounds(360,40,30,30);


		// 폰트, 컬러 설정
		lbl1.setFont(st.fnt1); lbl1.setForeground(st.clr1);
		startLbl.setFont(st.fnt2);
		startTf.setFont(st.fnt3);
		startBtn.setFont(st.fnt2);
		endLbl.setFont(st.fnt2);
		endTf.setFont(st.fnt3);
		endBtn.setFont(st.fnt2);

		lbl2.setFont(st.fnt1); lbl2.setForeground(st.clr1);
		age1CB.setFont(st.fnt3); age1CB.setBackground(Color.WHITE);
		age1Lbl.setFont(st.fnt2);
		age2CB.setFont(st.fnt3); age2CB.setBackground(Color.WHITE);
		age2Lbl.setFont(st.fnt2);

		startTf.setEditable(false);
		endTf.setEditable(false);
		startTf.setBackground(Color.WHITE);
		endTf.setBackground(Color.WHITE);


		pane1.setPreferredSize(new Dimension(300,150));
		pane1.add(lbl1);
		pane1.add(startLbl);
		pane1.add(startTf);
		pane1.add(startBtn);
		pane1.add(endLbl);
		pane1.add(endTf);
		pane1.add(endBtn);

		pane2.setPreferredSize(new Dimension(300,70));
		pane2.add(lbl2);
		pane2.add(age1CB);
		pane2.add(age1Lbl);
		pane2.add(age2CB);
		pane2.add(age2Lbl);



		pane.setPreferredSize(new Dimension(400,280));
		pane.add(pane1, BorderLayout.NORTH);
		pane.add(pane2);

		// 백신 예약 활성화 ---- 주석 처리
		//		chkBox.setBounds(0, 0, 400, 30);
		//		chkBox.setFont(st.fnt2);
		//		pane3.setPreferredSize(new Dimension(300,40));
		//		pane3.add(chkBox);
		//		pane.add(pane3, BorderLayout.SOUTH);


		// 하단 취소/확인 버튼
		cancleBtn.setFont(st.fnt1);
		chkBtn.setFont(st.fnt1);
		cancleBtn.setBackground(st.clr1);
		chkBtn.setBackground(st.clr1);
		cancleBtn.setForeground(Color.WHITE);
		chkBtn.setForeground(Color.WHITE);
		cancleBtn.setBorderPainted(false);
		chkBtn.setBorderPainted(false);
		btnPane.setPreferredSize(new Dimension(400,40));
		btnPane.add(cancleBtn);
		btnPane.add(chkBtn);

		centerPane.add(pane);
		centerPane.add(btnPane, BorderLayout.SOUTH);
		centerPane.setBorder(new EmptyBorder(30,0,0,0));

		add(centerPane);

		basicRsvVal(); // -------------기존 예약 설정값 세팅

		setBounds(300,150,540,440);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);


		startBtn.addActionListener(this);
		endBtn.addActionListener(this);
		cancleBtn.addActionListener(this);
		chkBtn.addActionListener(this);
	}


	public void basicRsvVal() {
		RsvSettingDAO dao = new RsvSettingDAO();
		RsvSettingVO vo = dao.selectRsvSetting();

		startTf.setText(vo.getStartDate());
		endTf.setText(vo.getEndDate());
		age1CB.setSelectedItem(vo.getAge1());
		age2CB.setSelectedItem(vo.getAge2());
	}


	// 예약 시작일 달력 띄우기
	public void showStartCalendar() {
		sCancleBtn.setFont(st.fnt1);
		sChkBtn.setFont(st.fnt1);
		sCancleBtn.setBackground(st.clr1);
		sChkBtn.setBackground(st.clr1);
		sCancleBtn.setForeground(Color.WHITE);
		sChkBtn.setForeground(Color.WHITE);
		sCancleBtn.setBorderPainted(false);
		sChkBtn.setBorderPainted(false);
		//		sBtnPane.setPreferredSize(new Dimension(200,40));
		sBtnPane.add(sCancleBtn);
		sBtnPane.add(sChkBtn);
		sBtnPane.setBorder(new EmptyBorder(0,0,15,0));

		//		cal.setSize(400,300);
		dialog1.add(startCal);
		dialog1.add(sBtnPane, BorderLayout.SOUTH);
		dialog1.setBounds(100,100, 400, 400);
		dialog1.setVisible(true);
		dialog1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		sCancleBtn.addActionListener(this);
		sChkBtn.addActionListener(this);
	}

	// 예약 마지막날 달력 띄우기
	public void showEndCalendar() {
		eCancleBtn.setFont(st.fnt1);
		eChkBtn.setFont(st.fnt1);
		eCancleBtn.setBackground(st.clr1);
		eChkBtn.setBackground(st.clr1);
		eCancleBtn.setForeground(Color.WHITE);
		eChkBtn.setForeground(Color.WHITE);
		eCancleBtn.setBorderPainted(false);
		eChkBtn.setBorderPainted(false);
		//		eBtnPane.setPreferredSize(new Dimension(400,60));
		eBtnPane.add(eCancleBtn);
		eBtnPane.add(eChkBtn);
		eBtnPane.setBorder(new EmptyBorder(0,0,15,0));

		//		cal.setSize(400,300);
		dialog2.add(endCal);
		dialog2.add(eBtnPane, BorderLayout.SOUTH);
		dialog2.setBounds(100,100, 400, 400);
		dialog2.setVisible(true);
		dialog2.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		eCancleBtn.addActionListener(this);
		eChkBtn.addActionListener(this);
	}


	// 예약 시작일 저장
	public void saveStartDate() {
		startY = startCal.year;
		startM = startCal.month;
		if(startCal.rsvDate==null || startCal.rsvDate.equals("")) {
			JOptionPane.showMessageDialog(this, "날짜를 선택해주세요.");
		} else {
			startD = Integer.parseInt(startCal.rsvDate);
			if(startM<10) startDate = startY+"/0"+startM+"/"+startD;
			else startDate = startY+"/"+startM+"/"+startD;
			startTf.setText(startDate);
			dialog1.dispose();
		}
	}

	// 예약 마지막날 저장
	public void saveEndDate() {
		endY = endCal.year;
		endM = endCal.month;
		if(endCal.rsvDate==null || endCal.rsvDate.equals("")) {
			JOptionPane.showMessageDialog(this, "날짜를 선택해주세요.");
		} else {
			endD = Integer.parseInt(endCal.rsvDate);
			if(endM<10) endDate = endY+"/0"+endM+"/"+endD;
			else endDate = endY+"/"+endM+"/"+endD;
			endTf.setText(endDate);
			dialog2.dispose();
		}

	}

	// 대상 나이 기준 저장
	public void saveTargetAge() {
		age1 = (Integer) age1CB.getSelectedItem();
		age2 = (Integer) age2CB.getSelectedItem();
	}

	// 입력 검사
	public void checkSetting() {
		if(startTf.getText()==null || startTf.getText().equals("")){
			JOptionPane.showMessageDialog(this, "예약 시작일을 선택해주세요.");
		} else if(endTf.getText()==null || endTf.getText().equals("")){
			JOptionPane.showMessageDialog(this, "예약 마지막날을 선택해주세요.");
		} else {
			saveTargetAge();
			if(age1>age2) {
				int tmp = age1;
				age1 = age2;
				age2 = tmp;
			}
			setRsvVal(); // 예약 설정 수정
		}
	}

	// 예약 설정 수정
	public void setRsvVal() {
		RsvSettingVO vo = new RsvSettingVO();
		RsvSettingDAO dao = new RsvSettingDAO();
		vo.setStartDate(startTf.getText());
		vo.setEndDate(endTf.getText());
		vo.setAge1(age1);
		vo.setAge2(age2);

		int cnt = dao.updateRsvSetting(vo);
		if(cnt>0) {
			JOptionPane.showMessageDialog(this, "예약 설정이 완료되었습니다.");
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "예약 설정을 실패하였습니다.");
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		if(evt == startBtn) { // 예약 시작일
			showStartCalendar();
		} else if(evt == endBtn) { // 예약 마지막날
			showEndCalendar();
		} else if(evt == sCancleBtn) { // 예약시작팝업 취소 버튼
			dialog1.dispose();
		} else if(evt == eCancleBtn) { // 예약마지막팝업 취소 버튼
			dialog2.dispose();
		} else if(evt == cancleBtn) { // 취소 버튼
			dispose();
		} else if(evt == sChkBtn) { // 예약시작팝업 확인 버튼
			saveStartDate();
			dialog1.dispose();
		} else if(evt == eChkBtn) { // 예약마지막팝업 확인 버튼
			saveEndDate();
			dialog2.dispose();
		} else if(evt == chkBtn) { // 확인 버튼
			checkSetting();
		}
	}


	public static void main(String[] args) {
		new RsvSetting();
	}


}

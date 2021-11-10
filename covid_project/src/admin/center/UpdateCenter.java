package admin.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import database.CenterDAO;
import database.CenterVO;
import database.LocDAO;
import database.LocVO;
import database.VaccineDAO;
import user.home.SetStyle;
import user.home.UserTabMenu;

public class UpdateCenter extends JPanel implements FocusListener, ActionListener, ItemListener{ // 은정
	/* 진료소 등록 시 기본 생성자로 객체 생성 */
	/* 진료소 수정 시 생성자(진료소코드) 객체 생성 */
	
	JPanel centerPane = new JPanel(new BorderLayout());	// 가운데 정렬 패널
		// 1. 입력창 패널
		JPanel inputPane = new JPanel(null);
			JLabel[] lbl = new JLabel[6];
				String[] colName = {"진료소명", "주소", "대표 전화번호", "평일 운영시간", "토요일 운영시간", "일요일/공휴일 운영시간"};
			JTextField[] tf = new JTextField[3];
			DefaultComboBoxModel<String> model1;
			JComboBox<String> loc1CB, loc2CB;
			JComboBox<String>[] timeCB = new JComboBox[12];
				String[] hour = new String[24];
				String[] minute = new String[12];
			JLabel[] timeLbl = new JLabel[15];
			JCheckBox[] timeChk = {new JCheckBox("미운영"), new JCheckBox("미운영")};
			
		// 2. 버튼 패널
		JPanel btnPane = new JPanel(null);			
			JButton cancleBtn = new JButton("취소");
			JButton insertBtn = new JButton("등록");

	// 폰트 // 컬러
	SetStyle st = new SetStyle();
	
	ManageCenter mCenter;

	private int center_code; // 수정 시 진료소 코드 얻어와야 함
	
	// ----- 진료소 등록 시 실행 -----
	public UpdateCenter() {		
		// 주소 콤보박스(시도, 시군구) 기본 세팅
		setDefaultLocCB();
		// 운영시간 콤보박스 기본 세팅
		setDefaultTime();
	
		// 1. 입력창 세팅 // 라벨, 텍스트필드
		for(int i=0; i<lbl.length; i++) {
			lbl[i] = new JLabel(colName[i]);
			lbl[i].setFont(st.fnt2);
			lbl[i].setHorizontalAlignment(JLabel.CENTER);
			// 삽입 부분 위치 설정
			if(i<=1) {
				tf[i] = new JTextField();
				tf[i].setFont(st.fnt3);
				lbl[i].setBounds(0,38*i,170,30);
				if(i==0) {
					tf[i].setBounds(174,38*i,300,30);
				} else if(i==1){
					tf[i].setBounds(174,38*(i+1),300,30);
					loc1CB.setBounds(174,38*i,115,30);
					loc2CB.setBounds(294,38*i,180,30);
				}
				inputPane.add(tf[i]);
			} else if(i>=2) {
				lbl[i].setBounds(0,38*(i+1),170,30);
				if(i==2) {
					tf[i] = new JTextField();
					tf[i].setFont(st.fnt3);
					tf[i].setBounds(174,38*(i+1),300,30);
					inputPane.add(tf[i]);
				} else if(i>=3){
					timeCB[(i-3)*4+0].setBounds(174,38*(i+1),50,30); // 00(시)
					timeLbl[(i-3)*5+0].setBounds(224,38*(i+1),22,30); // 시
					timeCB[(i-3)*4+1].setBounds(246,38*(i+1),50,30); // 00(분)
					timeLbl[(i-3)*5+1].setBounds(296,38*(i+1),22,30); // 분
					
					timeLbl[(i-3)*5+4].setBounds(318,38*(i+1),16,30); // ~ // 라벨배열 인덱스 4,9,14번째
					
					timeCB[((i-3)*4)+2].setBounds(334,38*(i+1),50,30); // 00(시)
					timeLbl[(i-3)*5+2].setBounds(384,38*(i+1),22,30); // 시
					timeCB[(i-3)*4+3].setBounds(406,38*(i+1),50,30); // 00(분)
					timeLbl[(i-3)*5+3].setBounds(456,38*(i+1),22,30); // 분
					
					inputPane.add(timeCB[(i-3)*4+0]);
					inputPane.add(timeCB[(i-3)*4+1]);
					inputPane.add(timeCB[(i-3)*4+2]);
					inputPane.add(timeCB[(i-3)*4+3]);
					inputPane.add(timeLbl[(i-3)*5+0]);
					inputPane.add(timeLbl[(i-3)*5+1]);
					inputPane.add(timeLbl[(i-3)*5+2]);
					inputPane.add(timeLbl[(i-3)*5+3]);
					inputPane.add(timeLbl[(i-3)*5+4]);
					if(i>=4) {
						timeChk[i-4].setBounds(488,38*(i+1),80,30);
						timeChk[i-4].setFont(st.fnt3);
						inputPane.add(timeChk[i-4]);
					}
				}
			}
			inputPane.add(lbl[i]);
		}
		
		tf[1].setText("상세주소"); // 상세주소 글씨 미리 세팅
		tf[1].setForeground(Color.GRAY);
		
		loc1CB.setFont(st.fnt3);
		loc2CB.setFont(st.fnt3);
		loc1CB.setBackground(Color.WHITE);
		loc2CB.setBackground(Color.WHITE);
		inputPane.add(loc1CB);
		inputPane.add(loc2CB);
		
		
		// 2. 버튼 세팅
		cancleBtn.setFont(st.fnt1);
		cancleBtn.setForeground(Color.WHITE);
		cancleBtn.setBackground(st.clr1);
		cancleBtn.setBorderPainted(false);
		cancleBtn.setBounds(90,0,150,40);
		insertBtn.setFont(st.fnt1);
		insertBtn.setForeground(Color.WHITE);
		insertBtn.setBackground(st.clr1);
		insertBtn.setBorderPainted(false);
		insertBtn.setBounds(260,0,150,40);
		btnPane.add(cancleBtn);
		btnPane.add(insertBtn);
		btnPane.setPreferredSize(new Dimension(500,40));
		btnPane.setBorder(new EmptyBorder(150,0,0,0));
		
		// 가운데 정렬 패널에 1,2 패널 넣어주기
		centerPane.add(inputPane);
		centerPane.add(btnPane, BorderLayout.SOUTH);
		centerPane.setBorder(new EmptyBorder(0,100,0,0));
		centerPane.setPreferredSize(new Dimension(700,350)); //500, 350

		add(centerPane);
		setBorder(new EmptyBorder(100,0,0,0));	// 위쪽 여백
		
		// 이벤트 발생 -- 텍스트 필드 "상세주소"란
		tf[1].addFocusListener(this);
		cancleBtn.addActionListener(this);
		insertBtn.addActionListener(this);
		timeChk[0].addItemListener(this);
		timeChk[1].addItemListener(this);
	}
	
	// ----- 진료소 수정 시 실행 -----
	public UpdateCenter(int center_code) {
		this();
		this.center_code = center_code;
		
		CenterDAO dao = new CenterDAO();
		CenterVO vo = dao.selectCenter(center_code);
		
		tf[0].setText(vo.getCenter_name());
		selectLocCB(vo.getLoc_code());
		tf[1].setText(vo.getCenter_addr());
		tf[1].setForeground(Color.BLACK);
		tf[2].setText(vo.getCenter_tel());
		
		// 운영시간 불러오기
		String[] timeTxt = {vo.getCenter_time1(), vo.getCenter_time2(), vo.getCenter_time3()};
		for(int i=0; i<timeTxt.length; i++) {
			if(i==0) {
				timeCB[(i*4)+0].setSelectedItem(timeTxt[i].substring(0, 2));
				timeCB[(i*4)+1].setSelectedItem(timeTxt[i].substring(3, 5));
				timeCB[(i*4)+2].setSelectedItem(timeTxt[i].substring(8, 10));
				timeCB[(i*4)+3].setSelectedItem(timeTxt[i].substring(11));
				
			} else {
				if(timeTxt[i].equals("미운영")) {
					timeChk[i-1].setSelected(true);
				} else {
					timeCB[(i*4)+0].setSelectedItem(timeTxt[i].substring(0, 2));
					timeCB[(i*4)+1].setSelectedItem(timeTxt[i].substring(3, 5));
					timeCB[(i*4)+2].setSelectedItem(timeTxt[i].substring(8, 10));
					timeCB[(i*4)+3].setSelectedItem(timeTxt[i].substring(11));
				}
			}
		}		
		insertBtn.setText("수정 완료");
	}
	
	
	
	// 주소(시도, 시군구) 콤보박스 기본 세팅
	public void setDefaultLocCB(){
		LocDAO dao = new LocDAO();
		Vector<String> list1 = dao.combo1();
		model1 = new DefaultComboBoxModel<String>(list1);
		loc1CB = new JComboBox<String>(model1);
		loc1CB.insertItemAt("시·도", 0);
		loc1CB.setSelectedIndex(0);
		
		loc2CB = new JComboBox<String>();
		loc2CB.addItem("시·군·구");
		
		// loc1 콤보박스 이벤트 발생 등록
		loc1CB.addActionListener(this);
	}
	
	// 시도(loc1) 콤보박스 선택 이벤트 발생 ---> 시군구(loc2) 콤보박스 바뀜
	public void setLoc2CB(){
		LocDAO dao = new LocDAO();
		if(loc1CB.getSelectedItem().equals("시·도")) {
			loc2CB.removeAllItems();
			loc2CB.addItem("시·군·구");
		} else {
			Vector<String> list2 = dao.combo2((String)loc1CB.getSelectedItem());
			DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<String>(list2);
			loc2CB.setModel(model2);
		}
		loc2CB.updateUI();	// loc2 (시군구) 콤보박스 내용 업데이트
	}
	
	// 진료소 수정시 --- 해당 진료소 시도/시군구 정보 세팅하기
	public void selectLocCB(int loc_code) {
		LocDAO dao = new LocDAO();
		LocVO vo = new LocVO();
		vo = dao.selectLocData(loc_code);	//

		loc1CB.setSelectedItem(vo.getLoc1()); // loc1(시도) 선택값 기본 설정
		loc2CB.setSelectedItem(vo.getLoc2()); // loc2(시군구) 선택값 기본 설정
		
		// loc1 콤보박스 이벤트 발생 등록
		loc1CB.addActionListener(this);
	}
	
	
	
	// 운영시간 콤보박스 기본세팅
	public void setDefaultTime(){
		// 시 만들기
		for(int i=0; i<hour.length; i++) {
			if(i<10) hour[i]="0"+i;
			else hour[i]=""+i;
		}
		
		// 분 만들기
		for(int i=0; i<minute.length; i++) {
			if(i*5<10) minute[i]="0"+(i*5);
			else minute[i]=""+(i*5);
		}
		
		// 시/분 콤보박스 만들기
		for(int i=0; i<timeCB.length; i++) {
			if(i%2==0) timeCB[i] = new JComboBox<String>(hour);
			else timeCB[i] = new JComboBox<String>(minute);

			timeCB[i].insertItemAt("--", 0);
			timeCB[i].setSelectedIndex(0);
			timeCB[i].setFont(st.fnt3);
			timeCB[i].setBackground(Color.WHITE);
		}
		
		// 시/분 라벨
		for(int i=0; i<timeLbl.length; i++) {
			if(i%5==4) timeLbl[i] = new JLabel("~");
			else if(i%5==0 || i%5==2) timeLbl[i] = new JLabel("시");
			else timeLbl[i] = new JLabel("분");
			
			if(i%5==4) timeLbl[i].setFont(st.fnt2);
			else timeLbl[i].setFont(st.fnt3);
			
			timeLbl[i].setBorder(new EmptyBorder(0,2,0,0));
		}
	}
	
	
	// 입력된 진료소 정보 CenterVO에 저장하기 ---> 등록, 수정 메서드에 공통 실행
	public CenterVO setCenterVO() {
		LocDAO ldao = new LocDAO();
		CenterVO cvo = new CenterVO();
		CenterDAO cdao = new CenterDAO();
		
		String loc1 = (String)loc1CB.getSelectedItem();
		String loc2 = (String)loc2CB.getSelectedItem();
		int loc_code = ldao.getLocCode(loc1, loc2); // 선택된 시도/시군구 정보로 주소 코드 얻어오기
		
		cvo.setCenter_name(tf[0].getText());
		cvo.setLoc_code(loc_code);
		cvo.setCenter_addr(tf[1].getText());
		cvo.setCenter_tel(tf[2].getText());
		String[] timeTxt = new String[3];
		for(int i=0; i<timeTxt.length; i++) {
			if(i==0) {
				timeTxt[i] = (String)timeCB[(i*4)+0].getSelectedItem()+":"+timeCB[(i*4)+1].getSelectedItem()+" ~ "+timeCB[(i*4)+2].getSelectedItem()+":"+timeCB[(i*4)+3].getSelectedItem();
			} else {
				if(timeChk[i-1].isSelected()) timeTxt[i] = "미운영";
				else timeTxt[i] = (String)timeCB[(i*4)+0].getSelectedItem()+":"+timeCB[(i*4)+1].getSelectedItem()+" ~ "+timeCB[(i*4)+2].getSelectedItem()+":"+timeCB[(i*4)+3].getSelectedItem();
			}
		}
		cvo.setCenter_time1(timeTxt[0]);
		cvo.setCenter_time2(timeTxt[1]);
		cvo.setCenter_time3(timeTxt[2]);
		
		return cvo;
	}
	
	// 진료소 등록 ----> 등록 버튼 이벤트
	public void insertCenter() {
		CenterVO cvo = setCenterVO(); // 등록은 center_code 시퀀스 자동 등록이라 필요 없음
		CenterDAO cdao = new CenterDAO();
		
		int cnt = cdao.insertCenterData(cvo);	
		if(cnt>0) { // 진료소 등록 성공
			insertVacc(cvo); // 백신 데이터 등록하기
		} else { // 진료소 등록 실패
			JOptionPane.showMessageDialog(this, "진료소 정보 등록을 실패했습니다. 다시 시도해 주세요.");
		}
	}
	
	// 백신 데이터 등록 ----> 진료소 등록 성공하면 실행 // 백신 수량은 모두 기본 0
	public void insertVacc(CenterVO cvo) {
		CenterDAO cdao = new CenterDAO();
		VaccineDAO vdao = new VaccineDAO();
	
		int center_code = cdao.getCenterCordData(cvo); // 방금 등록된 진료소 정보로 진료소 코드 가져오기
		int cnt = vdao.insertVaccData(center_code); // 백신 데이터 등록하기
		if(cnt>0) { // 백신 데이터 등록 성공
			JOptionPane.showMessageDialog(this, "진료소 정보가 등록되었습니다.");
			returnManageCenter(); //------------------------------------------------------------------------------------------ 화면 전환
		} else { // 백신 데이터 등록 실패 시
			int cnt2 = cdao.deleteCenterData(center_code); // 방금 등록된 진료소 정보 삭제하기
			if(cnt2>0) { // 진료소, 백신 등록 모두X
				JOptionPane.showMessageDialog(this, "진료소 정보 등록을 실패했습니다. 다시 시도해 주세요.");
			} else { // 진료소만 등록되고 백신 데이터 등록 실패 시 (진료소 삭제 실패 시)
				JOptionPane.showMessageDialog(this, "진료소 정보가 등록되었습니다. 백신 수량을 등록하려면 관리자에게 문의 바랍니다.");
				returnManageCenter(); //-------------------------------------------------------------------------------------- 화면 전환
			}
		}
	}
	
	
	// 진료소 수정 ----> 수정 완료 버튼 이벤트
	public void updateCenter() {
		CenterVO cvo = setCenterVO();
		CenterDAO cdao = new CenterDAO();
		
		cvo.setCenter_code(center_code);	// 수정은 기존 center_code 필요함
		
		int cnt = cdao.updateCenterData(cvo);
		if(cnt>0) {
			JOptionPane.showMessageDialog(this, "진료소 정보 수정이 완료되었습니다.");
			returnManageCenter(); //------------------------------------------------------------------------------------------- 화면 전환
		} else {
			JOptionPane.showMessageDialog(this, "진료소 정보 수정을 실패했습니다. 다시 시도해 주세요.");
		}
	}
	
	// 등록/수정 전 입력 내용 검사
	public boolean checkInfo() {
		boolean chk = true;
		if(tf[0].getText()==null || tf[0].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "진료소명을 입력해 주세요.");
			chk = false;
		} else if(loc1CB.getSelectedItem().equals("시·도")) {
			JOptionPane.showMessageDialog(this, "지역을 선택해 주세요.");
			chk = false;
		} else if(tf[1].getText()==null || tf[1].getText().equals("") || tf[1].getText().equals("상세주소")) {
			JOptionPane.showMessageDialog(this, "상세주소를 입력해 주세요.");
			chk = false;
		} else if(tf[2].getText()==null || tf[2].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "대표 전화번호를 입력해 주세요.");
			chk = false;
		} else {
			// 운영시간 선택 검사
//			boolean chk = true;
			for(int i=0; i<timeCB.length; i++) {
				if(i<=3) { // 0,1,2,3
					if(timeCB[i].getSelectedItem().equals("--")) {	// 평일 운영시간
						JOptionPane.showMessageDialog(this, "운영시간을 모두 선택해주세요.");
						chk = false;
						break;
					}
				} else if(i>=4 && i<8){ // 4,5,6,7
					if(timeCB[i].getSelectedItem().equals("--") && timeChk[0].isSelected()==false) { // 토요일 운영시간
						JOptionPane.showMessageDialog(this, "운영시간을 모두 선택해주세요.");
						chk = false;
						break;
					}
				} else { // 8,9,10,11
					if(timeCB[i].getSelectedItem().equals("--") && timeChk[1].isSelected()==false) { // 일요일/공휴일 운영시간
						JOptionPane.showMessageDialog(this, "운영시간을 모두 선택해주세요.");
						chk = false;
						break;
					}
				}
			} //for
		} // if
		return chk;
	}
	
	
	// 화면전환 ----> [진료소 관리]로 이동
	public void returnManageCenter() {
		UserTabMenu.centerpane.removeAll();
		mCenter = new ManageCenter();
		UserTabMenu.centerpane.add(mCenter);
		UserTabMenu.centerpane.updateUI();
	}
	
	
	// --------------- 이벤트 등록 ---------------------
	
	// 버튼/콤보박스 선택 이벤트 // 다음 화면 전환도 여기서
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		String evtStr = e.getActionCommand();
		if(evt instanceof JComboBox) {
			setLoc2CB();
		} else if(evt instanceof JButton) {
			if(evtStr.equals("등록")) {
				boolean chk = checkInfo(); // 내용 잘 입력했나 체크
				if(chk) insertCenter(); // 진료소 등록
			} else if(evtStr.equals("수정 완료")) {
				boolean chk = checkInfo(); // 내용 잘 입력했나 체크
				if(chk) updateCenter(); // 진료소 수정
			} else if(evtStr.equals("취소")) {
				returnManageCenter(); //------------------------------------------------------------------------------------- 화면 전환
			}
		}
		
	}
	
	// 미운영 체크박스 이벤트
	public void itemStateChanged(ItemEvent e) {
		Object evt = e.getSource();
		if(evt==timeChk[0]) {
			if(e.getStateChange()==ItemEvent.SELECTED) {
				for(int i=4; i<=7; i++) {
					timeCB[i].setEnabled(false);
					timeCB[i].setSelectedIndex(0);
				}
			} else {
				for(int i=4; i<=7; i++) {
					timeCB[i].setEnabled(true);
				}
			}
		} else if(evt==timeChk[1]) {
			if(e.getStateChange()==ItemEvent.SELECTED) {
				for(int i=8; i<=11; i++) {
					timeCB[i].setEnabled(false);
					timeCB[i].setSelectedIndex(0);
				}
			} else {
				for(int i=8; i<=11; i++) {
					timeCB[i].setEnabled(true);
				}
			}
		}
		
	}
	
	
	// 상세주소란 포커스 시 이벤트
	public void focusGained(FocusEvent e) {
		if(tf[1].getText().equals("상세주소")) {
			tf[1].setText("");
			tf[1].setForeground(Color.BLACK);
		}
	}

	// 상세주소란 포커스 해제 시 이벤트
	public void focusLost(FocusEvent e) {
		if(tf[1].getText()==null || tf[1].getText().equals("")) {
			tf[1].setText("상세주소");
			tf[1].setForeground(Color.GRAY);
		}
	}

}

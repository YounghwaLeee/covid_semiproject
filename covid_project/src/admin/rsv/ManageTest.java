package admin.rsv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.CenterDAO;
import database.CenterVO;
import database.RsvDAO;
import database.RsvVO;
import database.TestDAO;
import database.TestVO;
import user.home.SetStyle;
import user.searchcenter.SearchCenterCombo;

public class ManageTest extends JPanel implements ActionListener { // 은정 // 검사 예약 관리 (관리자용)
	private String loc1 =""; // 관리자 소속
	
	// NORTH
	SearchCenterCombo search; // 검색
	
	// CENTER
	JPanel pane = new JPanel(new BorderLayout());
		JScrollPane tbPane; // center
			JTable table = new JTable();
				DefaultTableModel model;
					String[] col = {"진료소 코드", "시도", "시군구", "진료소명", "예약날짜", "예약시간", "예약자 아이디", "예약자 성명", "예약자 주민등록번호", "예약자 휴대폰번호"}; // 컬럼명
					int[] colSize = {50,40,100,100,100,60,90,60,100,100}; // 컬럼 최소 사이즈
		JPanel bottomPane = new JPanel(new BorderLayout()); // south
			JPanel btnPane = new JPanel();
				JPanel btnInnerPane = new JPanel(null);
					JButton[] btn = {new JButton("전체목록"), new JButton("예약 삭제")};
				
	// 폰트 // 컬러
	SetStyle st = new SetStyle();
	
	public ManageTest() {
		start();
	}
	
	
	public ManageTest(String loc1) {
		this.loc1 = loc1;
		start();
	}
	
	
	
	// 화면 구현
	public void start() {
		setLayout(new BorderLayout());
		
		// 모델 생성하면서 수정 불가하게 설정
		model = new DefaultTableModel(col, 0) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		setManageTestBtn(); // 전체목록/삭제 버튼 세팅
		
		if(loc1==null || loc1.equals("")) {	// 슈퍼 관리자 화면
			search = new SearchCenterCombo(); // 검색창 세팅
			setAllTestList();	// 전체 진료소 목록 불러오기
		} else {	// 직원 화면
			search = new SearchCenterCombo(loc1); // 검색창 세팅 (시도 콤보박스 없음)
			setSearchList(); // 해당 소속 목록
		}
		
		
		// 테이블 설정 -- 컬럼 사이즈 설정
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 정해진 컬럼 사이즈가 있고 스크롤 생김
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // 스크롤 없이 컬럼이 창 사이즈에 맞게 늘어남
		for(int i=0; i<col.length; i++) {
			table.getColumnModel().getColumn(i).setMinWidth(colSize[i]);
		}
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		tbPane = new JScrollPane(table);

		btnPane.setPreferredSize(new Dimension(500,75));
		bottomPane.setBorder(new EmptyBorder(15,0,0,0));
		bottomPane.add(btnPane, BorderLayout.SOUTH);
		
		search.setBorder(new EmptyBorder(0,0,15,0));
		pane.add(tbPane);
		pane.add(bottomPane, BorderLayout.SOUTH);
		pane.setBorder(new EmptyBorder(0,20,0,20));
		
		add(search, BorderLayout.NORTH);
		add(pane);
		
		// 이벤트 등록
		search.btn.addActionListener(this);
		search.tf.addActionListener(this);
	}
	
	
	// 검사 예약 관리 --------- 전체 검사 예약 목록
	public void setAllTestList(){
		TestDAO dao = new TestDAO();
		List<TestVO> list = dao.selectAllTestData();
		
		setTestList(list);
	}
	
	// 목록 불러오기 // 전체목록 or 검색 결과 목록
	public void setTestList(List<TestVO> list) {
		model.setRowCount(0);

		for(int i=0; i<list.size(); i++) {
			TestVO vo = list.get(i);
			String user_num = vo.getUser_num().substring(0,6) + "-" + vo.getUser_num().substring(6,7).concat("******"); // 주민등록번호 000000-0****** 형식으로 바꾸기
			String user_tel = vo.getUser_tel().substring(0,3) + "-" + vo.getUser_tel().substring(3,7) + "-" + vo.getUser_tel().substring(7); // 휴대폰번호 000-0000-0000 형식으로 바꾸기
			Object[] record = {vo.getCenter_code(), vo.getLoc1(), vo.getLoc2(), vo.getCenter_name(), vo.getRsv_date(), vo.getRsv_hour(), vo.getUser_id(), vo.getUser_name(), user_num, user_tel};
			model.addRow(record);
		}
		table.setModel(model);
		table.updateUI();
	}

	
	// 전체목록/삭제 버튼 세팅
	public void setManageTestBtn() {
		for(int i=0; i<btn.length; i++) {
			btn[i].setFont(st.fnt1);
			btn[i].setBounds(165*i, 0, 150, 40);
			btn[i].setBackground(st.clr1);
			btn[i].setForeground(Color.WHITE);
			btn[i].setBorderPainted(false);
			btnInnerPane.add(btn[i]);
			
			btn[i].addActionListener(this);
		}
		btnInnerPane.setPreferredSize(new Dimension(315,75));
		btnPane.add(btnInnerPane);
	}
	
	
	// [삭제] 버튼 이벤트 발생 시 ------- 예약 선택 여부 검사 / 선택 예약의 회원 아이디 저장
	public void selectDelTest(){
		int row = table.getSelectedRow();
		if(row==-1) { // 행이 선택되지 않았을 때
			JOptionPane.showMessageDialog(this, "삭제할 검사 예약 정보를 선택해 주세요.");
		} else {
			int result = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION) {
				String user_id = (String) model.getValueAt(row, 7); // 진료소 코드 얻어오기
				deleteTest(user_id);
			}
		}		
	}
	
	// 검사 예약 삭제 실행
	public void deleteTest(String user_id) {
		TestDAO dao = new TestDAO();
		int cnt = dao.deleteTestData(user_id);
		if(cnt>0) {
			JOptionPane.showMessageDialog(this, "해당 검사 예약 정보가 삭제되었습니다.");
			setAllTestList();
		} else {
			JOptionPane.showMessageDialog(this, "검사 예약 정보 삭제를 실패하였습니다. 다시 시도해 주세요.");
		}
	}
	
	// 검색 버튼 눌렀을 때 --- 검색 목록 출력하게
	public void setSearchList() {
		String loc1 = (String) search.cb1.getSelectedItem();
		String loc2 = (String) search.cb2.getSelectedItem();
		String searchTxt = search.tf.getText();
		
		TestDAO dao = new TestDAO();
		List<TestVO> list;
		
		if(loc1.equals("시·도") && loc2.equals("시·군·구")) { // 
			list = dao.getSearchTestData(searchTxt);
		} else if(!loc1.equals("시·도") && loc2.equals("시·군·구")) { // 
			list = dao.getSearchTestData(loc1, searchTxt);
		} else {
			list = dao.getSearchTestData(loc1, loc2, searchTxt);
		}
		setTestList(list);  // 해당 목록 출력
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		String evtStr= e.getActionCommand();
		if(evtStr.equals("전체목록")) {
			search.tf.setText("");			// 검색창 초기화
			if(loc1==null || loc1.equals("")) { // 슈퍼 관리자
				search.cb1.setSelectedIndex(0); // 시도 콤보박스 초기화
				setAllTestList(); // 전체 목록
			} else {							// 직원 관리자
				search.cb2.setSelectedIndex(0); // 시군구 콤보박스 초기화
				setSearchList();	// 해당 소속 전체 목록
			}
		} else if(evtStr.equals("예약 삭제")) {
			selectDelTest();
		} else if(evt == search.tf || evtStr.equals("검색")) {
			setSearchList();
		}
	}
}

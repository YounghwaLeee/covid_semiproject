package admin.center;

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
import database.LocDAO;
import database.LocVO;
import database.VaccineDAO;
import database.VaccineVO;
import user.home.SetStyle;
import user.home.UserTabMenu;
import user.searchcenter.SearchCenterCombo;

public class ManageCenter extends JPanel implements ActionListener { // 은정 // 진료소 관리 (관리자용)
	private String loc1 ="";  // 관리자 소속
	
	// NORTH
	SearchCenterCombo search; // 검색
	
	// CENTER
	JPanel pane = new JPanel(new BorderLayout());
		JScrollPane tbPane; // center
			JTable table = new JTable();
				DefaultTableModel model;
					String[] col = {"진료소 코드", "진료소명", "시도", "시군구", "상세주소", "대표 전화번호", "평일 운영시간", "토요일 운영시간", "일요일/공휴일 운영시간"};
					int[] colSize = {100,200,100,100,300,100,100,100,100}; // 컬럼 최소 사이즈
		JPanel bottomPane = new JPanel(new BorderLayout()); // south
			JPanel btnPane = new JPanel();
				JPanel btnInnerPane = new JPanel(null);
					JButton[] btn = {new JButton("전체목록"), new JButton("추가"), new JButton("수정"), new JButton("삭제")};
				
	// 폰트 // 컬러
	SetStyle st = new SetStyle();
	
	UpdateCenter uCenter;
	
	public ManageCenter() {
		start();
	}
	
	
	public ManageCenter(String loc1) {
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
		
		setManageCenterBtn(); // 전체목록/추가/수정/삭제 버튼 세팅
		
		if(loc1==null || loc1.equals("")) {	// 슈퍼 관리자 화면
			search = new SearchCenterCombo(); // 검색창 세팅
			setAllCenterList();	// 전체 진료소 목록 불러오기
		} else {	// 직원 화면
			search = new SearchCenterCombo(loc1); // 검색창 세팅 (시도 콤보박스 없음)
			setSearchList(); // 해당 소속 목록
		}
		
		// 테이블 설정 -- 컬럼 사이즈 설정
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		centerTb.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		for(int i=0; i<col.length; i++) {
			table.getColumnModel().getColumn(i).setMinWidth(colSize[i]);
		}
//		System.out.println(centerTb.getColumnModel().getColumn(0).getPreferredWidth());
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
	
	
	
	// 진료소 관리 --- 전체 진료소 목록 불러오기
	public void setAllCenterList() {
		CenterDAO dao = new CenterDAO();
		List<CenterVO> list = dao.selectAllCenterData();
		
		setCenterList(list);
	}
	
	
	// 목록 불러오기 // 전체목록 or 검색 결과 목록
	public void setCenterList(List<CenterVO> list) {
		model.setRowCount(0);

		for(int i=0; i<list.size(); i++) {
			CenterVO vo = list.get(i);
			Object[] record = {vo.getCenter_code(), vo.getCenter_name(), vo.getLoc1(), vo.getLoc2(), vo.getCenter_addr(), vo.getCenter_tel(), vo.getCenter_time1(), vo.getCenter_time2(), vo.getCenter_time3()};
			model.addRow(record);
		}
		table.setModel(model);
		table.updateUI();
	}

	
	// 전체목록/추가/수정/삭제 버튼 세팅
	public void setManageCenterBtn() {
		for(int i=0; i<btn.length; i++) {
			btn[i].setFont(st.fnt1);
			btn[i].setBounds(165*i, 0, 150, 40);
			btn[i].setBackground(st.clr1);
			btn[i].setForeground(Color.WHITE);
			btn[i].setBorderPainted(false);
			btnInnerPane.add(btn[i]);
			
			btn[i].addActionListener(this);
		}
		btnInnerPane.setPreferredSize(new Dimension(645,75));
		btnPane.add(btnInnerPane);
	}
	
	// [삭제] 버튼 이벤트 발생 시 실행 ------- 진료소 선택 여부 검사 / 선택 진료소 코드 저장
	public void selectDelCenter(){
		int row = table.getSelectedRow();
		if(row==-1) { // 행이 선택되지 않았을 때
			JOptionPane.showMessageDialog(this, "삭제할 진료소를 선택해 주세요.");
		} else {
			int result = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION) {
				int center_code = (Integer) model.getValueAt(row, 0); // 진료소 코드 얻어오기
				deleteCenter(center_code);
			}
		}		
	}
	
	// 진료소 삭제
	public void deleteCenter(int center_code) {
		CenterDAO dao = new CenterDAO();
		int cnt = dao.deleteCenterData(center_code);
		if(cnt>0) {
			JOptionPane.showMessageDialog(this, "해당 진료소가 삭제되었습니다.");
			setAllCenterList();
		} else {
			JOptionPane.showMessageDialog(this, "진료소 삭제를 실패하였습니다. 다시 시도해 주세요.");
		}
	}

	
	// [수정] 버튼 이벤트 발생 시 실행 ------- 진료소 선택 여부 검사 / 선택 진료소 코드 저장
	public void selectEditCenter(){
		int row = table.getSelectedRow();
		if(row==-1) { // 행이 선택되지 않았을 때
			JOptionPane.showMessageDialog(this, "수정할 진료소를 선택해 주세요.");
		} else {
			int center_code = (Integer) model.getValueAt(row, 0); // 진료소 코드 얻어오기
			uCenter = new UpdateCenter(center_code);
			
			UserTabMenu.centerpane.removeAll();
			UserTabMenu.centerpane.add(uCenter);
			UserTabMenu.centerpane.updateUI();
		}		
	}
	
	// 검색 버튼 눌렀을 때 --- 검색 목록 출력하게
	public void setSearchList() {
		String loc1 = (String) search.cb1.getSelectedItem();
		String loc2 = (String) search.cb2.getSelectedItem();
		String searchTxt = search.tf.getText();
		
		CenterDAO dao = new CenterDAO();
		List<CenterVO> list;
		
		if(loc1.equals("시·도") && loc2.equals("시·군·구")) { // 
			list = dao.getSearchCenterData(searchTxt);
		} else if(!loc1.equals("시·도") && loc2.equals("시·군·구")) { // 
			list = dao.getSearchCenterData(loc1, searchTxt);
		} else {
			list = dao.getSearchCenterData(loc1, loc2, searchTxt);
		}
		setCenterList(list);  // 해당 목록 출력
	}

	
	
	// --- 버튼 클릭 이벤트
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		String evtStr= e.getActionCommand();
		if(evtStr.equals("전체목록")) {
			search.tf.setText("");			// 검색창 초기화
			if(loc1==null || loc1.equals("")) { // 슈퍼 관리자
				search.cb1.setSelectedIndex(0); // 시도 콤보박스 초기화
				setAllCenterList(); // 전체 목록
			} else {							// 직원 관리자
				search.cb2.setSelectedIndex(0); // 시군구 콤보박스 초기화
				setSearchList();	// 해당 소속 전체 목록
			}
		} else if(evtStr.equals("추가")) {
			uCenter  = new UpdateCenter();
			UserTabMenu.centerpane.removeAll();
			UserTabMenu.centerpane.add(uCenter);
			UserTabMenu.centerpane.updateUI();
		} else if(evtStr.equals("수정")) {
			selectEditCenter(); // 이 메서드 안에서 화면 전환 필요
		} else if(evtStr.equals("삭제")) {
			selectDelCenter();
		} else if(evt == search.tf || evtStr.equals("검색")) {
			setSearchList();
		}
	}
}

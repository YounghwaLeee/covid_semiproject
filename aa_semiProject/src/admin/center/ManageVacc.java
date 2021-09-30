package admin.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.VaccineDAO;
import database.VaccineVO;
import user.home.SetStyle;
import user.searchcenter.SearchCenterCombo;

public class ManageVacc extends JPanel implements ActionListener{ // 은정	 // 백신 수량 관리 (관리자용)
	private String loc1 ="";  // 관리자 소속
	
	// NORTH
	SearchCenterCombo search;
	
	// CENTER
	JPanel pane = new JPanel(new BorderLayout());
		JScrollPane tbPane; // center
			JTable table = new JTable();
				DefaultTableModel model;
					String[] col = {"진료소 코드", "시도", "시군구", "진료소명", "대표 전화번호", "얀센", "아스트라제네카", "화이자", "모더나"};
					int[] colSize = {60,10,100,240,130,40,40,40,40}; // 컬럼 최소 사이즈
		JPanel bottomPane = new JPanel(new BorderLayout()); // south
			JPanel editPane = new JPanel();
				JPanel editInnerPane = new JPanel(null);
					JLabel[] vaccLbl = new JLabel[4];
						String[] vaccStr = {"얀센","아스트라제네카","화이자","모더나"};
					JTextField[] vaccTf = new JTextField[4];
					JLabel[] minusLbl = new JLabel[4];
					JLabel[] plusLbl = new JLabel[4];
			JPanel btnPane = new JPanel();
				JPanel btnInnerPane = new JPanel(null);
					JButton[] btn = {new JButton("전체목록"), new JButton("수정 완료")};
				
	// 폰트 // 컬러
	SetStyle st = new SetStyle();
	
	
	// 슈퍼 관리자용
	public ManageVacc() {
		start();
	}
	
	
	// 직원 관리자용
	public ManageVacc(String loc1) {
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
		
		setEditVaccArea(); // 수량 입력 부분 세팅
		setManageVaccBtn(); // 전체목록, 수정완료 버튼 세팅
		
		if(loc1==null || loc1.equals("")) {	// 슈퍼 관리자 화면
			search = new SearchCenterCombo(); // 검색창 세팅
			setAllVaccCountList(); // 전체 목록
		} else {	// 직원 화면
			search = new SearchCenterCombo(loc1); // 검색창 세팅 (시도 콤보박스 없음)
			setSearchList(); // 해당 소속 목록
		}
		
		// 테이블 설정 -- 컬럼 사이즈 설정
		for(int i=0; i<col.length; i++) {
			table.getColumnModel().getColumn(i).setMinWidth(colSize[i]);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		tbPane = new JScrollPane(table);

		btnPane.setPreferredSize(new Dimension(500,75));
		bottomPane.setBorder(new EmptyBorder(15,0,0,0));
		bottomPane.add(editPane);
		bottomPane.add(btnPane, BorderLayout.SOUTH);
		
		search.setBorder(new EmptyBorder(0,0,15,0));
		pane.add(tbPane);
		pane.add(bottomPane, BorderLayout.SOUTH);
		pane.setBorder(new EmptyBorder(0,20,0,20));
		
		add(search, BorderLayout.NORTH);
		add(pane);
		
		// 이벤트 등록
		table.addMouseListener(new MyMouseEvent());
		table.addKeyListener(new MyKeyEvent());
		search.btn.addActionListener(this);
		search.tf.addActionListener(this);
	}
	
	
	// 백신관리 --- 전체 백신 수량 목록 불러오기
	public void setAllVaccCountList() {		
		VaccineDAO dao = new VaccineDAO();
		List<VaccineVO> list = dao.getAllVaccData();;
		
		setVaccList(list);
	}
	
	// 목록 불러오기 // 전체목록 or 검색 결과 목록
	public void setVaccList(List<VaccineVO> list) {
		model.setRowCount(0);
		
		for(int i=0; i<list.size(); i++) {
			VaccineVO vo = list.get(i);
			Object[] record = {vo.getCenter_code(), vo.getLoc1(), vo.getLoc2(), vo.getCenter_name(), vo.getCenter_tel(), vo.getJansen(), vo.getAz(), vo.getPfizer(), vo.getModerna()};
			model.addRow(record);
		}
		table.setModel(model);
		table.updateUI();
		
		for(int i=0; i<vaccTf.length; i++){ // 수량 입력 초기화
			vaccTf[i].setText("");
		}
	}

	
	// 수정 완료 버튼 세팅
	public void setManageVaccBtn() {
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

	// 백신 수량 수정 텍스트필드 + 라벨(-,+)
	public void setEditVaccArea() {
		for(int i=0; i<vaccLbl.length; i++) {
			vaccLbl[i] = new JLabel(vaccStr[i]);
			vaccLbl[i].setBounds(150*i, 0, 150, 20);
			vaccLbl[i].setFont(st.fnt2);
			vaccLbl[i].setHorizontalAlignment(JLabel.CENTER);
			editInnerPane.add(vaccLbl[i]);
			
			minusLbl[i] = new JLabel("-");
			minusLbl[i].setBounds(150*i+15, 30, 30, 35);
			minusLbl[i].setFont(st.fnt1);
			minusLbl[i].setHorizontalAlignment(JLabel.CENTER);
			editInnerPane.add(minusLbl[i]);
			minusLbl[i].addMouseListener(new MyMouseEvent());
			
			vaccTf[i] = new JTextField();
			vaccTf[i].setBounds(150*i+45, 30, 60, 35);
			vaccTf[i].setFont(st.fnt3);
			vaccTf[i].setHorizontalAlignment(JLabel.RIGHT);
			editInnerPane.add(vaccTf[i]);
			
			plusLbl[i] = new JLabel("+");
			plusLbl[i].setBounds(150*i+105, 30, 30, 35);
			plusLbl[i].setFont(st.fnt1);
			plusLbl[i].setHorizontalAlignment(JLabel.CENTER);
			editInnerPane.add(plusLbl[i]);
			plusLbl[i].addMouseListener(new MyMouseEvent());
		}
		editInnerPane.setPreferredSize(new Dimension(600,85));
		editPane.add(editInnerPane);
	}
	
	// 테이블에서 진료소 하나 선택(클릭)하면 발생
	public void setVaccineCount() {
		int row = table.getSelectedRow();
		int jansenCnt = (Integer) model.getValueAt(row, 5);
		int azCnt = (Integer) model.getValueAt(row, 6);
		int pfizerCnt = (Integer) model.getValueAt(row, 7);
		int modernaCnt = (Integer) model.getValueAt(row, 8);
		
		vaccTf[0].setText(Integer.toString(jansenCnt));
		vaccTf[1].setText(Integer.toString(azCnt));
		vaccTf[2].setText(Integer.toString(pfizerCnt));
		vaccTf[3].setText(Integer.toString(modernaCnt));
	}
	
	// - 라벨 클릭했을 때 (백신 수량 -1 하기)
	public void clickMinus(int i) {
		if(vaccTf[i].getText()==null || vaccTf[i].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "진료소를 선택해 주세요.");
		} else {
			int vaccCnt = Integer.parseInt(vaccTf[i].getText());
			if(vaccCnt>0) {
				vaccTf[i].setText(Integer.toString(vaccCnt-1));
			} else {
				JOptionPane.showMessageDialog(this, "0 이상의 수만 입력해 주세요.");
			}
		}
	}
	
	// + 라벨 클릭했을 때 (백신 수량 +1 하기)
	public void clickPlus(int i) {
		if(vaccTf[i].getText()==null || vaccTf[i].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "진료소를 선택해 주세요.");
		} else {
			int vaccCnt = Integer.parseInt(vaccTf[i].getText());
			if(vaccCnt<=1000) {
				vaccTf[i].setText(Integer.toString(vaccCnt+1));
			} else {
				JOptionPane.showMessageDialog(this, "1000 이하의 수만 입력해 주세요.");
			}
		}
	}
	
	// 수정 버튼 이벤트 발생 시 ---------- 진료소 선택 여부 검사 / 선택 진료소 코드 저장
	public void clickModifyBtn() {
		int row = table.getSelectedRow();
		if(row==-1) { // 행이 선택되지 않았을 때
			JOptionPane.showMessageDialog(this, "백신 수량을 수정할 진료소를 선택해 주세요.");
		} else {
			int result = JOptionPane.showConfirmDialog(this, "수정하시겠습니까?", "백신 수량 수정", JOptionPane.OK_CANCEL_OPTION);
			if(result==JOptionPane.OK_OPTION) {
				int center_code = (Integer) model.getValueAt(row, 0); // 진료소 코드 얻어오기
				updateVacc(center_code);
			}
		}
	}

	// 백신 데이터 수정 실행
	public void updateVacc(int center_code) {
		int jansenCnt = Integer.parseInt(vaccTf[0].getText());
		int azCnt = Integer.parseInt(vaccTf[1].getText());
		int pfizerCnt = Integer.parseInt(vaccTf[2].getText());
		int modernaCnt = Integer.parseInt(vaccTf[3].getText());
		
		VaccineVO vo = new VaccineVO();
		VaccineDAO dao = new VaccineDAO();
		vo.setCenter_code(center_code);
		vo.setJansen(jansenCnt);
		vo.setAz(azCnt);
		vo.setPfizer(pfizerCnt);
		vo.setModerna(modernaCnt);
		
		int cnt = dao.updateVaccData(vo);
		
		if(cnt>0) { // 수정 성공
			JOptionPane.showMessageDialog(this, "해당 진료소의 백신 수량이 수정되었습니다.");
			setAllVaccCountList();
		} else { // 수정 실패
			JOptionPane.showMessageDialog(this, "백신 수량 수정을 실패하였습니다. 다시 시도해 주세요.");
		}
	}
	
	// 검색 버튼 눌렀을 때 --- 검색 목록 출력하게
	public void setSearchList() {
		String loc1 = (String) search.cb1.getSelectedItem();
		String loc2 = (String) search.cb2.getSelectedItem();
		String searchTxt = search.tf.getText();
		
		VaccineDAO dao = new VaccineDAO();
		List<VaccineVO> list;

		if(loc1.equals("시·도") && loc2.equals("시·군·구")) { // 
			list = dao.getSearchVaccData(searchTxt);
		} else if(!loc1.equals("시·도") && loc2.equals("시·군·구")) { // 
			list = dao.getSearchVaccData(loc1, searchTxt);
		} else {
			list = dao.getSearchVaccData(loc1, loc2, searchTxt);
		}
		setVaccList(list); // 해당 목록 출력
	}

	
	
	
	// --- 버튼 클릭 이벤트
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		String evtStr= e.getActionCommand();
		if(evtStr.equals("전체목록")) {
			search.tf.setText("");			// 검색창 초기화
			if(loc1==null || loc1.equals("")) { // 슈퍼 관리자
				search.cb1.setSelectedIndex(0); // 시도 콤보박스 초기화
				setAllVaccCountList(); // 전체 목록
			} else {							// 직원 관리자
				search.cb2.setSelectedIndex(0); // 시군구 콤보박스 초기화
				setSearchList();	// 해당 소속 전체 목록
			}
		} else if(evtStr.equals("수정 완료")) {
			clickModifyBtn();
		} else if(evt == search.tf || evtStr.equals("검색")) {
			setSearchList();
		}
	}
	
	// --- 테이블 마우스 이벤트
	public class MyMouseEvent extends MouseAdapter{
		public MyMouseEvent() {}
		
		// 테이블 레코드 선택했을 때 ---- Mouse 이벤트 발생
		public void mouseReleased(MouseEvent e) {
			Object evt = e.getSource();
			if(evt instanceof JTable) {
				setVaccineCount();
			} else if(evt instanceof JLabel) {
				for(int i=0; i<minusLbl.length; i++) {
					if(evt == minusLbl[i]) clickMinus(i);
					else if(evt == plusLbl[i]) clickPlus(i);
				}
			}
		}
	}
	
	// --- 테이블 키 이벤트
	public class MyKeyEvent extends KeyAdapter{
		public MyKeyEvent() {}
		
		// 테이블 레코드 선택했을 때 ---- Mouse 이벤트 발생
		public void keyReleased(KeyEvent e) {
			Object evt = e.getSource();
			if(evt == table) setVaccineCount();
		}
	}
	
}

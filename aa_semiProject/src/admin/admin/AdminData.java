package admin.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import database.AdminDAO;
import database.AdminVO;
import user.home.SetStyle;

public class AdminData extends JPanel implements ActionListener {//관리자 페이지
	//성규
	//희수 수정 : 검색어 없을 시, 팝업창 뜨기
	//홈 대신 전체보기 버튼 넣음 / 이벤트 구현 해둠
	//그밖에 살짝 수정함
	
	AdminDAO dao = new AdminDAO();
	JTable table;
	JScrollPane sp;
	DefaultTableModel model;
	//	JTable 에서 제목으로 사용할 배열
	String title[] = {"아이디","이름","주민등록번호","휴대폰번호","소속"};
	//홈으로 삭제버튼
	JPanel click = new JPanel(new GridLayout(2,1));
	JPanel cen = new JPanel();
	JPanel clickbtn = new JPanel();

	JPanel checkpanel = new JPanel(new GridLayout(2,1));
	JButton allbtn = new JButton("전체 목록");
	JButton deletebtn = new JButton("삭제");

	String id;

	//관리자검색
	JPanel northPane = new JPanel();
	DefaultComboBoxModel<String> searchModel = new DefaultComboBoxModel<String>();
	JComboBox<String> searchKey = new JComboBox<String>(searchModel);	
	JTextField searchadmin = new JTextField(15);
	JButton searchBtn = new JButton("검색");
    
	SetStyle st = new SetStyle();
	
	public AdminData() {
		
		setLayout(new BorderLayout());

		model = new DefaultTableModel(title,0);
		table = new JTable(model);
		sp = new JScrollPane(table);

		add(sp);
		add(BorderLayout.SOUTH,click);
		cen.add(checkpanel);
		click.add(cen);
		click.add(clickbtn);
		clickbtn.add(allbtn);
		allbtn.setFont(st.fnt1);
		allbtn.setBackground(st.clr1);
		allbtn.setForeground(Color.WHITE);
		allbtn.setBorderPainted(false);
		clickbtn.add(deletebtn);
		deletebtn.setFont(st.fnt1);
		deletebtn.setBackground(st.clr1);
		deletebtn.setForeground(Color.WHITE);
		deletebtn.setBorderPainted(false);
		deletebtn.addActionListener(this);
		searchadmin.addActionListener(this);
		
		AdminAllList();
		setSearchForm();
		
//		setSize(1200,800);		
//		setVisible(true);
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	//관리자 검색 폼
	public void setSearchForm() {
		add(BorderLayout.NORTH,northPane);

		searchModel.addElement("아이디");
		searchModel.addElement("이름");
		searchModel.addElement("휴대폰번호");
		searchModel.addElement("소속");
		northPane.add(searchKey);//콤보박스

		northPane.add(searchadmin);
		northPane.add(searchBtn);
		searchBtn.setFont(st.fnt1);
		searchBtn.setBackground(st.clr1);
		searchBtn.setForeground(Color.WHITE);
		searchBtn.setBorderPainted(false);
		searchBtn.addActionListener(this);
		allbtn.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		String eventBtn = ae.getActionCommand();
		Object obj = ae.getSource();
		if(eventBtn.equals("삭제")) {
			selectDelAdmin();
		}else if(eventBtn.equals("검색")) {
			adminSearch();
		}else if(obj==searchadmin) {
			adminSearch();
		}
		//희수 추가
		else if(obj==allbtn) {
			//System.out.println("전체보기 클릭");
			AdminAllList();
		}
	}
	//삭제할 직원
	public void AdminDelete(){

		int row = table.getSelectedRow();
		id = String.valueOf(table.getValueAt(row, 0));
		//		System.out.println("확인용");
		dao.deleteAdmin(id);
		AdminAllList();
	}
	public void selectDelAdmin() {

		int resurt =JOptionPane.showConfirmDialog(this, "관리자를 정말 삭제하시겠습니까?", "삭제", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(resurt==JOptionPane.OK_OPTION) {
			AdminDelete();
		}
	}
	//데이터 베이스에서 직원전체 목록(이름으로) 가져오기 - JTable목록을 보여준다.
	public void AdminAllList() {
		List<AdminVO> list = dao.admingrade1();
		setAdminTable(list);
	}
	public void setAdminTable(List<AdminVO> list ) {
		model.setRowCount(0);
		for(int i=0;i<list.size();i++) {
			AdminVO vo = list.get(i);
			Object[] obj = {vo.getAdmin_id(),vo.getAdmin_name(),vo.getAdmin_num(),vo.getAdmin_tel(),vo.getAdmin_local()};
			model.addRow(obj);
		}
	}
	//검색
	public void adminSearch() {
		String search = searchadmin.getText();
		if(search!=null && !search.equals("")) {//검색어가있다.
			String searchField = (String)searchKey.getSelectedItem();
			//검색키 "아이디","이름",,"휴대폰번호","소속"

			String fieldName = "";
			if(searchField.equals("아이디")) {
				fieldName = "admin_id";
			}else if(searchField.equals("이름")) {
				fieldName = "admin_name";
			}else if(searchField.equals("휴대폰번호")) {
				fieldName = "admin_tel";
			}else if(searchField.equals("소속")) {
				fieldName = "admin_local";
			}

			AdminDAO dao = new AdminDAO();
			List<AdminVO>list = dao.searchadmindata(fieldName,search);
			setAdminTable(list);
			searchadmin.setText("");
		}
		else {
			JOptionPane.showMessageDialog(this, "검색어를 입력하세요.");
		}
	}
	public static void main(String[] args) {
//		new AdminData();
	}
}
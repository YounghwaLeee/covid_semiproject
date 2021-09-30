package admin.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
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
import database.UserDAO;
import database.UserVO;
import oracle.net.aso.v;
import user.home.SetStyle;
public class UserData extends JPanel implements ActionListener{ //성규
	//희수 수정 : 검색어 없을 시, 팝업창 뜨기
	//홈 대신 전체보기 버튼 넣음 / 이벤트 구현 해둠
	//그밖에 살짝 수정함
	
	UserDAO dao = new UserDAO();
	JTable table;
	JScrollPane sp;
	DefaultTableModel model;
	//	JTable 에서 제목으로 사용할 배열
	String title[] = {"아이디","이름","주민등록번호","전화번호"};
	//홈으로 삭제버튼
	JPanel click = new JPanel(new GridLayout(2,1));
	JPanel cen = new JPanel();
	JPanel clickbtn = new JPanel();

	JPanel checkpanel = new JPanel(new GridLayout(2,1));
	JButton allbtn = new JButton("전체 목록");
	JButton deletebtn = new JButton("삭제");

	String id;
	//회원검색
	JPanel northPane = new JPanel();
	DefaultComboBoxModel<String> searchModel = new DefaultComboBoxModel<String>();
	JComboBox<String> searchKey = new JComboBox<String>(searchModel);	
	JTextField searchuser = new JTextField(15);
	JButton searchBtn = new JButton("검색");
	SetStyle st = new SetStyle();
	public UserData() {
		
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
		searchuser.addActionListener(this);
		allbtn.addActionListener(this);
		
		userAllList();
		setSearchForm();
		
//		setSize(1200,800);		
//		setVisible(true);
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	//회원 검색 폼
	public void setSearchForm() {
		add(BorderLayout.NORTH,northPane);

		searchModel.addElement("아이디");
		searchModel.addElement("이름");
		searchModel.addElement("휴대폰번호");
		northPane.add(searchKey);//콤보박스

		northPane.add(searchuser);
		northPane.add(searchBtn);
		searchBtn.setFont(st.fnt1);
		searchBtn.setBackground(st.clr1);
		searchBtn.setForeground(Color.WHITE);
		searchBtn.setBorderPainted(false);
		searchBtn.addActionListener(this);
	}
	
	////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		String eventBtn = e.getActionCommand();
		Object obj = e.getSource();
		if(eventBtn.equals("삭제")) {
			selectDelUser();
		}else if(eventBtn.equals("검색")) {
			userSearch();
		}else if(obj==searchuser) {
			userSearch();
		}
		//희수 추가
		else if(obj==allbtn) {
			//System.out.println("전체보기 클릭");
			userAllList();
		}
	}
	//////////////////////////////////////////////
	
	public void UserDelete(){
		//삭제 할 직원
		int row = table.getSelectedRow();
		id = String.valueOf(table.getValueAt(row, 0));
		//		System.out.println("확인용");
		dao.deleteUser(id);
		userAllList();
		
	}
	public void selectDelUser() {

		int resurt =JOptionPane.showConfirmDialog(this, "회원을 정말 삭제하시겠습니까?", "삭제", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(resurt==JOptionPane.OK_OPTION) {
			UserDelete();
		}
	}
	//데이터 베이스에서 회원전체 목록(이름으로) 가져오기 - JTable목록을 보여준다.
	public void userAllList() {
		List<UserVO> list = dao.UserRecord();
		setUserTable(list);
	}
	public void setUserTable(List<UserVO> list ) {
		model.setRowCount(0);
		for(int i=0;i<list.size();i++) {
			UserVO vo = list.get(i);
			Object[] obj = {vo.getUser_id(),vo.getUser_name(),vo.getUser_num(),vo.getUser_tel()};
			model.addRow(obj);
		}
	}
	public void userSearch() {
		String search = searchuser.getText();
		if(search!=null && !search.equals("")) {//검색어가있다.
			String searchField = (String)searchKey.getSelectedItem();
			//검색키 "아이디","이름","휴대폰번호"

			String fieldName = "";
			if(searchField.equals("아이디")) {
				fieldName = "user_id";
			}else if(searchField.equals("이름")) {
				fieldName = "user_name";
			}else if(searchField.equals("휴대폰번호")) {
				fieldName = "user_tel";
			}

			UserDAO dao = new UserDAO();
			List<UserVO>list = dao.searchuserdata(fieldName,search);
			setUserTable(list);
			searchuser.setText("");
		}
		else {
			JOptionPane.showMessageDialog(this, "검색어를 입력하세요.");
		}
	}
	public static void main(String[] args) {
//		new UserData();
	}
}
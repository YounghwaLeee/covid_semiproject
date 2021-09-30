package admin.rsv;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import database.AdminDAO;
import database.AdminVO;
import user.home.MainTitle;
import user.home.SetStyle;

public class RsvTab extends JPanel{
	SetStyle style = new SetStyle();
	JTabbedPane northTab = new JTabbedPane(JTabbedPane.NORTH);
	
	ManageRsv vacRsv;
	ManageTest testRsv = new ManageTest();
	
	public RsvTab() {
		setLayout(new BorderLayout());
		add(northTab);
    	northTab.setFont(style.fnt16);
    	
    	AdminDAO dao = new AdminDAO();
    	AdminVO vo = dao.setMyInfo(MainTitle.admin_id);
    	
    	if(vo.getAdmin_local().equals("시·도")) { // 슈퍼관리자
    		vacRsv = new ManageRsv();
    		testRsv = new ManageTest();
    	}
    	else { //일반 관리자
    		vacRsv = new ManageRsv(vo.getAdmin_local());
    		testRsv = new ManageTest(vo.getAdmin_local());
    	}
    	
    	northTab.addTab("코로나 검사 예약", testRsv);
    	northTab.addTab("백신 접종 예약", vacRsv);
		
	}

	
//	SetStyle style = new SetStyle();
//	JTabbedPane northTab = new JTabbedPane(JTabbedPane.NORTH);
//
//	AdminList apprvList = new AdminList();
//	AdminData adminData = new AdminData();
//	
//	public AdminTab() {
//		setLayout(new BorderLayout());
//		add(northTab);
//    	northTab.setFont(style.fnt16);
//    	northTab.addTab("직원 목록", adminData);
//    	northTab.addTab("직원 권한 관리", apprvList);
}

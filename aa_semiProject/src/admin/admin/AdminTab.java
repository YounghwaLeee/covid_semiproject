package admin.admin;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import database.AdminDAO;
import database.AdminVO;
import user.home.MainTitle;
import user.home.SetStyle;

public class AdminTab extends JPanel { //희수
	
	SetStyle style = new SetStyle();
	JTabbedPane northTab = new JTabbedPane(JTabbedPane.NORTH);

	AdminList apprvList = new AdminList();
	AdminData adminData = new AdminData();
	
	public AdminTab() {
		setLayout(new BorderLayout());
		add(northTab);
    	northTab.setFont(style.fnt16);
    	northTab.addTab("직원 목록", adminData);
    	northTab.addTab("직원 권한 관리", apprvList);
			
    	
    	
//		setSize(1000,800);
//		setVisible(true);
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	
    	
	}
	
	public static void main(String[] args) {
//		new AdminTab();
	}

}

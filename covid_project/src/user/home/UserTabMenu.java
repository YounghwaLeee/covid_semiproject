package user.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import admin.admin.AdminTab;
import admin.center.ManageCenter;
import admin.center.ManageVacc;
import admin.home.AdminInfo;
import admin.home.SignUpMain;
import admin.rsv.RsvTab;
import admin.user.UserData;
import database.AdminDAO;
import database.AdminVO;
import database.RsvSettingDAO;
import database.RsvSettingVO;
import user.check.TabCheckReservationinformation;
import user.rsv.RsvSouthBtn;
import user.rsv.RsvVaccineRadio;
import user.searchcenter.SearchCenterMain;
import user.searchcenter.SearchCenterSplit;

public class UserTabMenu extends JFrame implements MouseListener {// 탭에들어가는정보

	// 가장 기본 탭메뉴
	JSplitPane allsp;
	JTabbedPane maintab = new JTabbedPane(JTabbedPane.LEFT);

	JPanel leftpane = new JPanel(new BorderLayout());
	JPanel userpane = new JPanel(new BorderLayout());
	JPanel pane = new JPanel(new GridLayout(0, 1));
	JButton[] btn = { new JButton("홈"), new JButton("검사 예약"), new JButton("백신 접종 예약"), new JButton("예약 조회"),
			new JButton("진료소 찾기"), new JButton("관리자 모드") };

	JPanel adminpane = new JPanel(new BorderLayout());
	JPanel pane2 = new JPanel(new GridLayout(0, 1));
	JButton[] btn2 = { new JButton("홈"), new JButton("진료소 관리"), new JButton("백신 관리"), new JButton("예약 관리"),
			new JButton("회원 관리"), new JButton("직원 관리"), new JButton("회원 모드") };

	JPanel rightpane = new JPanel(new BorderLayout());
	JScrollPane sp = new JScrollPane(rightpane);
	public static JPanel centerpane = new JPanel(new BorderLayout());

	SetStyle style = new SetStyle();
	MainTitle title = new MainTitle();

	Firstpage firstmain;
	SignUpMain signUp;
	Login login;
	AdminInfo info;

	UserData userdata;
	AdminTab admintab;
	TabCheckReservationinformation rsvCheck;
	SearchCenterMain sCenter;
	RsvVaccineRadio vRadio;
	ManageCenter mCenter;
	ManageVacc mVacc;

	public UserTabMenu() {

		setResizable(false);

		allsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftpane, sp);
		leftpane.add(userpane);

		for (int i = 0; i < btn.length; i++) {
			JPanel btnpane = new JPanel();
			if (i != btn.length - 1) {
				btnpane.add(btn[i]);
				pane.add(btnpane);
				userpane.add(BorderLayout.NORTH, pane);
			} else {
				btnpane.add(btn[i]);
				userpane.add(BorderLayout.SOUTH, btnpane);
			}

			// userpane.add(BorderLayout.NORTH, pane);

			btn[i].setFont(style.fnt1);
			btn[i].setPreferredSize(new Dimension(180, 40));
			btn[i].setBackground(style.clr1);
			btn[i].setForeground(Color.white);
			btn[i].setFocusPainted(false);
			btn[i].addMouseListener(this);
		}

		for (int i = 0; i < btn2.length; i++) {
			JPanel btnpane = new JPanel();
			if (i != btn2.length - 1) {
				btnpane.add(btn2[i]);
				pane2.add(btnpane);
				adminpane.add(BorderLayout.NORTH, pane2);
			} else {
				btnpane.add(btn2[i]);
				adminpane.add(BorderLayout.SOUTH, btnpane);
			}

			// adminpane.add(BorderLayout.NORTH,pane2);

			btn2[i].setFont(style.fnt1);
			btn2[i].setPreferredSize(new Dimension(180, 40));
			btn2[i].setBackground(Color.white);
			btn2[i].setForeground(style.clr1);
			btn2[i].setFocusPainted(false);
			btn2[i].addMouseListener(this);
		}

		MainTitle.lbl.setText("　");

		rightpane.add(BorderLayout.NORTH, title);
		rightpane.add(centerpane);

		firstmain = new Firstpage(MainTitle.mode);
		centerpane.add(firstmain);
		rightpane.setBackground(Color.white);
		add(allsp);

		setTitle("코로나 검사/백신 예약 시스템");
		setSize(1200, 800);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		MainTitle.lbl2.addMouseListener(this);
		MainTitle.lbl3.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object event = e.getSource();
		// 라벨 이벤트
		if (event == MainTitle.lbl2) {
			if (MainTitle.lbl2.getText().equals("회원가입")) {
				MainTitle.lbl.setText("회원가입");
				signUp = new SignUpMain();
				centerpane.removeAll();
				centerpane.add(signUp);
				if (MainTitle.mode == 1) {
					signUp.cb1.setVisible(false);
					signUp.lbl[6].setVisible(false);
				}
				// centerpane.updateUI();
			} else if (MainTitle.lbl2.getText().equals("나의 정보")) {
				MainTitle.lbl.setText("나의 정보");
				info = new AdminInfo();
				centerpane.removeAll();
				centerpane.add(info);
				if (MainTitle.mode == 1) {
					info.affiliationlbl.setVisible(false);
					info.affiliation.setVisible(false);
				}
			}
			centerpane.updateUI();
		} else if (event == MainTitle.lbl3) {
			if (MainTitle.lbl3.getText().equals("로그인")) {
				MainTitle.lbl.setText("로그인");
				login = new Login();
				centerpane.removeAll();
				centerpane.add(login);
				// centerpane.updateUI();

				// 테스트용 문구 출력
				if (MainTitle.mode == 1) {
					System.out.println("회원모드 로그인");
				} else
					System.out.println("관리자모드 로그인");
			} else if (MainTitle.lbl3.getText().equals("로그아웃")) {
				MainTitle.user_id = null;
				MainTitle.admin_id = null;
				JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
				firstmain = new Firstpage(MainTitle.mode);
				centerpane.removeAll();
				centerpane.add(firstmain);
				// centerpane.updateUI();

				MainTitle.lbl2.setText("회원가입");
				MainTitle.lbl3.setText("로그인");
			}
			centerpane.updateUI();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Object event = e.getSource();
		// 버튼 이벤트 (회원모드)
		if (event == btn[0]) {
			MainTitle.lbl.setText("　");
			// System.out.println("홈 클릭");
			firstmain = new Firstpage(MainTitle.mode);
			centerpane.removeAll();
			centerpane.add(firstmain);
			centerpane.updateUI();
		} else if (event == btn[1]) {
			MainTitle.lbl.setText("검사 예약");
			firstmain = new Firstpage(MainTitle.mode);
			centerpane.removeAll();
			SearchCenterSplit.centerName = null;
			if (MainTitle.user_id != null) {

				sCenter = new SearchCenterMain();
				RsvSouthBtn sBtn = new RsvSouthBtn();
				centerpane.add(sCenter);
				sBtn.rsvBtn.setText("코로나 검사 예약하기");
				centerpane.add(BorderLayout.SOUTH, sBtn);
			} else {
				loginfirstUser();
			}
			centerpane.updateUI();
		} else if (event == btn[2]) {
			MainTitle.lbl.setText("백신 접종 예약");
			centerpane.removeAll();
			SearchCenterSplit.centerName = null;
			RsvVaccineRadio.vc_type = null;
			if (MainTitle.user_id != null) {
				// 은정 추가 --------- 접종 대상 생년에 해당되면 예약 가능, 아니면 알림창
				RsvSettingDAO dao = new RsvSettingDAO();
				RsvSettingVO vo = dao.selectRsvSetting();
				if(MainTitle.user_year>=vo.getAge1() && MainTitle.user_year<=vo.getAge2()) {
					sCenter = new SearchCenterMain();
					centerpane.removeAll();
					vRadio = new RsvVaccineRadio();
					centerpane.add(BorderLayout.NORTH, vRadio);
					vRadio.rBtn0.setVisible(false);
					centerpane.add(sCenter);

					RsvSouthBtn sBtn = new RsvSouthBtn();
					sBtn.rsvBtn.setText("백신 접종 예약하기");
					centerpane.add(BorderLayout.SOUTH, sBtn);
				} else {
					JOptionPane.showMessageDialog(this, "해당 백신 접종 기간의 예약 대상이 아닙니다.");
					firstmain = new Firstpage(MainTitle.mode);
					centerpane.removeAll();
					centerpane.add(firstmain);
				} //-----------------
			} else {
				loginfirstUser();
			}
			centerpane.updateUI();
		} else if (event == btn[3]) {
			MainTitle.lbl.setText("예약 조회");
			centerpane.removeAll();

			if (MainTitle.user_id != null) {
				rsvCheck = new TabCheckReservationinformation();
				centerpane.add(rsvCheck);
			} else {
				loginfirstUser();
			}
			centerpane.updateUI();
		} else if (event == btn[4]) {
			MainTitle.lbl.setText("진료소 찾기");
			centerpane.removeAll();
			sCenter = new SearchCenterMain();
			centerpane.add(sCenter);
			centerpane.updateUI();
		} else if (event == btn[5]) {
			MainTitle.lbl.setText("관리자 모드 HOME");
			MainTitle.mode = 2;
			MainTitle.user_id = null;
			firstmain = new Firstpage(MainTitle.mode);
			login = new Login();

			MainTitle.lbl2.setText("회원가입");
			MainTitle.lbl3.setText("로그인");

			centerpane.removeAll();
			centerpane.add(login);
			centerpane.updateUI();

			leftpane.removeAll();
			leftpane.add(adminpane);
			leftpane.updateUI();
		}

		// 버튼 이벤트 (관리자모드)
		else if (event == btn2[0]) {
			MainTitle.lbl.setText("관리자 모드 HOME");
			firstmain = new Firstpage(MainTitle.mode);
			MainTitle.mode = 2;
			centerpane.removeAll();
			if (MainTitle.admin_id != null) {
				centerpane.add(firstmain);
			} else
				loginfirstAdmin();
			centerpane.updateUI();
		} else if (event == btn2[1]) {
			MainTitle.lbl.setText("진료소 관리");

			centerpane.removeAll();

			if (MainTitle.admin_id != null) {
				AdminDAO dao = new AdminDAO();
				AdminVO vo = dao.setMyInfo(MainTitle.admin_id);
				String locname = vo.getAdmin_local();

				if (locname.equals("시·도")) {
					mCenter = new ManageCenter();
				} else {
					mCenter = new ManageCenter(locname);
				}
				centerpane.add(mCenter);
			} else {
				loginfirstAdmin();
			}
			centerpane.updateUI();
		} else if (event == btn2[2]) {
			MainTitle.lbl.setText("백신 관리");
			centerpane.removeAll();
			
			AdminDAO admindao = new AdminDAO();
			AdminVO adminvo = admindao.setMyInfo(MainTitle.admin_id);
			
			if (MainTitle.admin_id != null) {
				if(adminvo.getAdmin_local().equals("시·도")) {
					mVacc = new ManageVacc();
				}
				else {
					mVacc = new ManageVacc(adminvo.getAdmin_local());
				}
				centerpane.add(mVacc);
			} else {
				loginfirstAdmin();
			}
			centerpane.updateUI();
		} else if (event == btn2[3]) {
			MainTitle.lbl.setText("예약 관리");
			RsvTab rsvtab = new RsvTab();
			centerpane.removeAll();
			if (MainTitle.admin_id != null) {
				centerpane.add(rsvtab);
			} else {
				loginfirstAdmin();
			}
			centerpane.updateUI();
		} else if (event == btn2[4]) {		
			MainTitle.lbl.setText("회원 관리");
			userdata = new UserData();
			centerpane.removeAll();
			if (MainTitle.admin_id != null) {
				centerpane.add(userdata);
			} else {
				loginfirstAdmin();
			}
			centerpane.updateUI();
		} else if (event == btn2[5]) {
			MainTitle.lbl.setText("직원 관리");
			admintab = new AdminTab();
			centerpane.removeAll();

			AdminDAO dao = new AdminDAO();
			AdminVO vo = dao.setMyInfo(MainTitle.admin_id);

			if (MainTitle.admin_id == null) { // 로그인X
				loginfirstAdmin();
			}

			else if (vo.getAdmin_local().equals("시·도")) { // 슈퍼관리자
				centerpane.add(admintab);
			}

			else if (MainTitle.admin_id != null) { // 로그인O
				JOptionPane.showMessageDialog(null, "슈퍼 관리자만 접근할 수 있습니다.");
				firstmain = new Firstpage(MainTitle.mode);
				centerpane.add(firstmain);
			}
			centerpane.updateUI();
		} else if (event == btn2[6]) {
			MainTitle.lbl.setText("　");
			MainTitle.mode = 1;
			firstmain = new Firstpage(MainTitle.mode);
			MainTitle.admin_id = null;
			MainTitle.lbl2.setText("회원가입");
			MainTitle.lbl3.setText("로그인");

			centerpane.removeAll();
			centerpane.add(firstmain);
			centerpane.updateUI();

			leftpane.removeAll();
			leftpane.add(userpane);
			leftpane.updateUI();
		}

	}

	// 로그인 경고창
	public void loginfirstUser() {
		JOptionPane.showMessageDialog(null, "먼저 로그인해주세요.");
		MainTitle.lbl.setText("로그인");
		login = new Login();
		centerpane.add(login);
	}

	// 관리자 로그인 경고창
	public void loginfirstAdmin() {
		JOptionPane.showMessageDialog(null, "먼저 관리자 로그인을 해주세요.");
		MainTitle.lbl.setText("로그인");
		login = new Login();
		centerpane.add(login);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

//	public static void main(String[] args) {
//		new UserTabMenu();
//
//	}

}
package user.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.RsvSettingDAO;
import database.RsvSettingVO;

public class Firstpage extends JPanel implements ActionListener {//은정
	/* 미구현 상태 -> 공지사항 텍스트가 화면이 바뀌어도 저장되어야 하고, 관리자페이지든 일반회원페이지든 바뀐 내용이 적용되어야 합니다. */
	
	// 가운데 정렬 패널
	JPanel centerPane = new JPanel(null);
		// 타이틀
		JLabel mainTitle = new JLabel();
		Font mainFnt = new Font("맑은 고딕", Font.BOLD, 38);	
		//String.valueOf()
		// 확진자 현황
		JPanel notice1 = new JPanel();
		CoronaAPI api = new CoronaAPI();
		
		// 공지사항 (안내)
		JPanel notice2 = new JPanel(new BorderLayout());
			JEditorPane subPane = new JEditorPane();
				String txt1; // 공지사항 제목
			JEditorPane txtPane = new JEditorPane();
				String txt2; // 공지사항 본문
			JPanel editPane = new JPanel(new GridLayout(1, 1, 10, 0));
				JButton cancleBtn = new JButton("취소");
				JButton editBtn = new JButton();
		
	// 폰트 // 컬러
	// 폰트 // 컬러
	SetStyle st = new SetStyle();
	//Font fnt1 = new Font("맑은 고딕", Font.BOLD, 20);
	
	public Firstpage(int mode) {
		
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);

		// 타이틀
		mainTitle.setText("코로나 검사/백신 예약 시스템");
		mainTitle.setFont(mainFnt);
		mainTitle.setBounds(0, 50, 660, 50);
		mainTitle.setHorizontalAlignment(JLabel.CENTER);
		mainTitle.setVerticalAlignment(JLabel.CENTER);
		
		
		notice1.setBorder(new EmptyBorder(20,20,20,20)); // 패널 안에 여백 //북서남동 순서
		notice1.setBackground(Color.DARK_GRAY);
		notice1.setBounds(0, 150, 320, 400);
		notice1.add(api);

		
		subPane.setFont(st.fnt20b); // 제목 폰트
		txtPane.setFont(st.fnt3); // 본문 폰트
		txtPane.setBorder(new EmptyBorder(10,0,0,0)); // 패널 안에 여백 //북서남동 순서
		
		setNotice2(); // 공지사항 제목, 본문 내용 세팅
		
		if(mode == 2) {
			setDefaultEditBtn(); // 수정 버튼 세팅 - <관리자용>에서만 필요함
		}
		
		notice2.setBorder(new EmptyBorder(20,20,20,20)); // 패널 안에 여백 //북서남동 순서
		notice2.setBackground(st.clr1);
		notice2.setBounds(340, 150, 320, 400);
		
		notice2.add(subPane, BorderLayout.NORTH);
		notice2.add(txtPane, BorderLayout.CENTER);
		notice2.add(editPane, BorderLayout.SOUTH);
		
		// 전체 세팅
		centerPane.setPreferredSize(new Dimension(660,580));
		centerPane.setBackground(Color.WHITE);
		centerPane.add(mainTitle);
		centerPane.add(notice1);
		centerPane.add(notice2);
		
		add(centerPane);
		
//		setSize(1200,800);
//		setVisible(true);
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		editBtn.addActionListener(this); // 수정하기, 수정완료 이벤트 발생
	}
	
	
	// 공지사항 제목, 본문 세팅
	public void setNotice2() {
		// 공지사항 내용 불러오기
		RsvSettingDAO dao = new RsvSettingDAO();
		RsvSettingVO vo = dao.selectRsvSetting();
		txt1 = vo.getNotice_title();
		txt2 = vo.getNotice();
		
		subPane.setText(txt1);
		subPane.setForeground(Color.YELLOW);
		subPane.setOpaque(false);  // 본문 패널 투명하게
		subPane.setFocusable(false); // 수정 비활성화
		
		txtPane.setText(txt2);
		txtPane.setForeground(Color.WHITE);
		txtPane.setOpaque(false); // 본문 패널 투명하게
		txtPane.setFocusable(false); // 수정 비활성화
	}
	
	// 수정 버튼 세팅
	public void setDefaultEditBtn() {
		editBtn.setFont(st.fnt2);
		editBtn.setText("수정하기");
		editBtn.setForeground(st.clr1);
		editBtn.setBackground(Color.WHITE);
		editBtn.setBorderPainted(false);
		editPane.setOpaque(false); // 버튼 패널 투명하게
		editPane.setBorder(new EmptyBorder(10,0,0,0));
		editPane.add(editBtn);
	}
	
	// 수정하기 버튼 누르면 실행
	public void editNotice() {
		txt1 = subPane.getText(); // 수정 전 제목 텍스트 담아두기
		subPane.setOpaque(true);
		subPane.setForeground(Color.BLACK);
		subPane.setBackground(new Color(225, 225, 225));
		subPane.setFocusable(true);
		
		txt2 = txtPane.getText(); // 수정 전 본문 텍스트 담아두기
		txtPane.setOpaque(true);
		txtPane.setForeground(Color.BLACK);
		txtPane.setBackground(new Color(225, 225, 225));
		txtPane.setFocusable(true);
		
		cancleBtn.setFont(st.fnt2);
		cancleBtn.setForeground(st.clr1);
		cancleBtn.setBackground(Color.WHITE);
		cancleBtn.setBorderPainted(false);
		editPane.add(cancleBtn);
		editBtn.setText("수정완료");
		
		cancleBtn.addActionListener(this);	// 취소 버튼 이벤트 발생
	}

	// 수정완료 버튼 누르면 실행 ---- 공지사항 제목, 본문 수정하기
	public void editCheck() {
		txt1 = subPane.getText(); // 수정된 제목 텍스트 담기
		txt2 = txtPane.getText(); // 수정된 본문 텍스트 담기
		
		RsvSettingDAO dao = new RsvSettingDAO();
		int cnt = dao.updateRsvNotice(txt1, txt2);
		if(cnt>0) {
			JOptionPane.showMessageDialog(this, "수정이 완료되었습니다.");
			editPane.remove(cancleBtn); // 취소 버튼 없애기
			setNotice2(); // 공지사항 제목, 본문 세팅
			setDefaultEditBtn(); //수정 버튼 세팅
		} else {
			JOptionPane.showMessageDialog(this, "수정을 실패하였습니다. 다시 시도해 주세요.");
		}
	}
	
	// 취소 버튼 누르면 실행
	public void editCancle() {
		editPane.remove(cancleBtn); // 취소 버튼 없애기
		setNotice2(); // 공지사항 제목, 본문 세팅 --> 수정 전 텍스트들도 다시 세팅됨
		setDefaultEditBtn(); //수정 버튼 세팅
	}
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String evtTxt = ae.getActionCommand();
		if(evtTxt.equals("수정하기")) {
			editNotice();
		} else if(evtTxt.equals("수정완료")) {
			editCheck();
		} else if(evtTxt.equals("취소")) {
			editCancle();
		}
	}
	
	
	public static void main(String[] args) {
//		new Firstpage_user();

	}
}

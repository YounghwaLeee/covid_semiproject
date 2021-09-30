package user.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class MainTitle extends JPanel { //희수 
	//모든화면 상단 타이틀(메뉴명/로그인/회원가입)
	
	public static String user_id = null;
	public static String admin_id = null;
	public static int mode = 1; //1은 사용자, 2는 관리자
	// 은정 추가
	public static int user_year; // 생년 4자리
	
	//JPanel northPane = new JPanel(new BorderLayout());
    public static JLabel lbl = new JLabel("");//임시
    JPanel pane = new JPanel(new GridLayout(1,2,20,0));
    public static JLabel lbl2 = new JLabel("회원가입");
    public static JLabel lbl3 = new JLabel("로그인");
       
    SetStyle style = new SetStyle();
       
	public MainTitle() {
		
		setBorder(BorderFactory.createEmptyBorder(25,25,35,25));
		
		setLayout(new BorderLayout());
		   //기본 상단 라벨
		//add(BorderLayout.NORTH,northPane);
		add(BorderLayout.WEST,lbl);
		add(BorderLayout.EAST,pane);
		   
		pane.add(lbl2);
		pane.add(lbl3);
		
		lbl2.setFont(style.fnt16);
		lbl3.setFont(style.fnt16);
		
		if(lbl.getText().equals("관리자 페이지")) {
			lbl.setFont(style.fnt16);
		}
		else {
			lbl.setFont(style.fnt30b);
		}
		
		   
//		setSize(1200,800);
//		setVisible(true);
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	     
	
	}
	
	
	public static void main(String[] args) {
		//new MainTitle();

	}


}

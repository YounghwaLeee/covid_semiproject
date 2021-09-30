package user.home;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

public class SetStyle { // 은정
   // 지정 폰트------------
   public Font fnt1 = new Font("맑은 고딕", Font.BOLD, 16); // 버튼
   public Font fnt2 = new Font("맑은 고딕", Font.BOLD, 14); // 라벨
   public Font fnt3 = new Font("맑은 고딕", Font.PLAIN, 14); // 본문
   
   public Font fnt20b = new Font("맑은 고딕", Font.BOLD, 20);
   public Font fnt20 = new Font("맑은 고딕",0, 20);
   public Font fnt16 = new Font("맑은 고딕",0, 16);
   public Font fnt30b = new Font("맑은 고딕",1, 30);
   
   // 지정 컬러 ------------
   public Color clr1 = new Color(38,80,150); // 파란색 ----- 현재 메인 컬러 
   
   // ----- 웬만하면 위에 지정된 거 쓰세요 ------------
   // SetStyle st = new SetStyle();
   // ex) st.fnt1
   
   
   // 바꿔서 써도 되는 폰트
   public Font fnt4 = new Font("맑은 고딕", Font.BOLD, 26);
   public Font fnt5 = new Font("맑은 고딕", Font.BOLD, 30);
   public Font fnt6 = new Font("맑은 고딕", Font.PLAIN,12);
   // 바꿔서 써도 되는 컬러
   public Color clr2 = new Color(220,220,220); // 회색
   public Color clr3 = new Color(38,100,150); //
   public Color clr4 = new Color(225,225,150);
   public Color clr5 = new Color(220,100,100);
   public Color clr6 = new Color(100,100,100);
   
   public SetStyle() {
      /*
      // 버튼 디자인 예시
      btn.setFont(st.fnt1); // 폰트 설정
      btn.setBackground(st.clr1); // 배경색
      btn.setForeground(Color.WHITE); // 글씨색
      btn.setBorderPainted(false); // 테두리 없애기
      
      */
      
      // JOptionPane 폰트 설정
      UIManager.put("OptionPane.messageFont", fnt3); // 메세지 내용
      UIManager.put("OptionPane.buttonFont", fnt2); // 버튼
   }
}
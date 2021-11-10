package user.check;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import user.home.MainTitle;
//import user.home.MainTitle;
import user.home.SetStyle;

public class TabCheckReservationinformation extends JPanel implements ActionListener{

   //버튼을 눌렀을때 검사 예약 정보와 백신 예약 정보 출력 구현 클래스

   SetStyle st = new SetStyle();//폰트설정 클래스

   TestRsvCheck ct; //검사 예약 정보 클래스
   VaccineRsvCheck cr; //백신 예약 정보 클래스

   JPanel checkbtnpane = new JPanel();//검사예약정보 백신예약정보 버튼 담을 패널
   JButton checkTestbtn = new JButton("검사 예약 정보");
   JButton checkRsvbtn = new JButton("백신 예약 정보");

   public static JPanel inData = new JPanel();//불러온 값을 담아줄 패널 공간 
   static JLabel lbl = new JLabel("검사 예약 정보가 없습니다.");
   static JLabel lbl2 = new JLabel("백신 예약 정보가 없습니다.");

   public TabCheckReservationinformation() {


      setLayout(new BorderLayout());

      add(BorderLayout.NORTH,checkbtnpane); //북쪽으로 버튼 넣어줌
      checkbtnpane.add(checkTestbtn);
      checkTestbtn.setFont(st.fnt1);
      checkTestbtn.setBackground(st.clr1);
      checkTestbtn.setForeground(Color.WHITE);
      checkTestbtn.setBorderPainted(false);
      checkbtnpane.add(checkRsvbtn);
      checkRsvbtn.setFont(st.fnt1);
      checkRsvbtn.setBackground(st.clr1);
      checkRsvbtn.setForeground(Color.WHITE);
      checkRsvbtn.setBorderPainted(false);
      lbl.setFont(st.fnt2);
      lbl2.setFont(st.fnt2);

      add(BorderLayout.CENTER,inData);   //북쪽버튼 클릭시 발생하는 이벤트 값을 넣어주는공간


      checkTestbtn.addActionListener(this);   //검사 예약정보창 출력 이벤트실행
      checkRsvbtn.addActionListener(this);   //백신 예약정보창 출력 이벤트 실행

//      setSize(1200,900);
//      setVisible(true);
//      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String eventBtn = e.getActionCommand();
      if(eventBtn.equals("검사 예약 정보")) {
         inData.removeAll();
         ct = new TestRsvCheck(MainTitle.user_id);
         if(ct.strdata!=null) { 	 
            inData.add(ct);            
         }else inData.add(lbl);
         inData.updateUI();
      }
      else if(eventBtn.equals("백신 예약 정보")) {
         inData.removeAll();
         cr = new VaccineRsvCheck(MainTitle.user_id);
         if(cr.strdata!=null) {     	 
            inData.add(cr);            
         }else inData.add(lbl2);
         inData.updateUI();
      }
   }

   public static void main(String[] args) {
//      new TabCheckReservationinformation();
   }
}
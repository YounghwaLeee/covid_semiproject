package user.check;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import database.RsvVO;
import database.TestDAO;
import database.TestVO;
import database.UserDAO;
import user.home.SetStyle;

public class TestRsvCheck extends JPanel implements ActionListener{//탭에들어가는정보
   //20210801김성규
   //코로나 검사 예약 정보코로나 검사 예약 정보코로나 검사 예약 정보코로나 검사 예약 정보
   //중간에 패널 넣을거임
   SetStyle st = new SetStyle();
   JPanel centerPanel= new JPanel(new BorderLayout());//가운데정렬 센터페널
   JPanel inPanel = new JPanel(null);

   //예약자정보
   JLabel reserveiplbl= new JLabel("<예약자 정보>");
   JLabel reservenamelbl= new JLabel("예약자 이름");
   JLabel reservenumlbl= new JLabel("예약자 주민등록번호");
   JLabel reservetellbl= new JLabel("예약자 휴대폰번호");

   //예약정보
   JLabel resimlbl2= new JLabel("<예약 정보>");
   JLabel redatalbl= new JLabel("검사일시");
   JLabel readdr1= new JLabel("검사 진료소");
   JLabel readdr2= new JLabel("진료소 주소");
   JLabel readdrtel= new JLabel("진료소 번호");

   //이름 선택 흰투명 라벨 
   JLabel pure1= new JLabel();
   JLabel pure2= new JLabel();
   JLabel pure3= new JLabel();
   JLabel pure4= new JLabel();
   JLabel pure5= new JLabel();
   JEditorPane pure6= new JEditorPane();
   JLabel pure7= new JLabel();

   JButton cancelbtn= new JButton("예약 취소하기");

   Font font3 = new Font("맑은 고딕",0, 20);
   Font fontidpw = new Font("맑은 고딕",Font.BOLD, 20);
   Font searchft = new Font("맑은 고딕",0, 16);//찾기폰트
   Font rightlblft = new Font("고딕",0, 12);//8~16자 영문 
   EmptyBorder empty= new EmptyBorder(0,30,0,30);// 라벨 이름 간격들

   String  strdata;
   
   String id = "";
   RsvVO vo;
   public  TestRsvCheck(String id){
      this.id =id;
      setLayout(new FlowLayout());

      TestDAO dao = new TestDAO();
      TestVO vo = dao.selectTestRsv(id);
      pure1.setText(vo.getUser_name());
      pure2.setText(vo.getUser_num());
      pure3.setText(vo.getUser_tel());

      pure4.setText(vo.getRsv_date()+" "+vo.getRsv_hour());
      pure5.setText(vo.getCenter_name());
      pure6.setText(vo.getLoc1()+" "+vo.getLoc2()+" "+vo.getCenter_addr());
      pure7.setText(vo.getCenter_tel());

      strdata = vo.getRsv_date();

      centerPanel.setPreferredSize(new Dimension(800,800));
      centerPanel.add(inPanel);
      add(centerPanel);

      //예약자 정보
      inPanel.add(reserveiplbl);//예약자 정보
      reserveiplbl.setBounds(0,0,140,40);
      reserveiplbl.setFont(fontidpw);

      //예약자이름
      inPanel.add(reservenamelbl).setBounds(0,40,230,50);
      reservenamelbl.setFont(fontidpw);
      reservenamelbl.setOpaque(true);//라벨배경색
      reservenamelbl.setBackground(Color.LIGHT_GRAY);
      reservenamelbl.setHorizontalAlignment(JLabel.CENTER);

      //예약자 주민등록번호
      inPanel.add(reservenumlbl).setBounds(0,92,230,50);
      reservenumlbl.setFont(fontidpw);
      reservenumlbl.setOpaque(true);//라벨배경색
      reservenumlbl.setBackground(Color.LIGHT_GRAY);
      reservenumlbl.setHorizontalAlignment(JLabel.CENTER);

      //예약자 휴대폰번호
      inPanel.add(reservetellbl).setBounds(0,144,230,50);
      reservetellbl.setFont(fontidpw);
      reservetellbl.setOpaque(true);//라벨배경색
      reservetellbl.setBackground(Color.LIGHT_GRAY);
      reservetellbl.setHorizontalAlignment(JLabel.CENTER); 

      //예약정보
      inPanel.add(resimlbl2).setBounds(0,190,140,40);
      resimlbl2.setFont(fontidpw);

      //검사일시
      inPanel.add(redatalbl).setBounds(0,235,230,50);
      redatalbl.setFont(fontidpw);
      redatalbl.setOpaque(true);//라벨배경색
      redatalbl.setBackground(Color.LIGHT_GRAY);
      redatalbl.setHorizontalAlignment(JLabel.CENTER);

      //검사 진료소 
      inPanel.add(readdr1).setBounds(0,287,230,50);
      readdr1.setFont(fontidpw);
      readdr1.setOpaque(true);//라벨배경색
      readdr1.setBackground(Color.LIGHT_GRAY);
      readdr1.setHorizontalAlignment(JLabel.CENTER);

      //진료소 주소
      inPanel.add(readdr2).setBounds(0,339,230,90);
      readdr2.setFont(fontidpw);
      readdr2.setOpaque(true);//라벨배경색
      readdr2.setBackground(Color.LIGHT_GRAY);
      readdr2.setHorizontalAlignment(JLabel.CENTER);

      //진료소 번호
      inPanel.add(readdrtel).setBounds(0,431,230,50);
      readdrtel.setFont(fontidpw);
      readdrtel.setOpaque(true);//라벨배경색
      readdrtel.setBackground(Color.LIGHT_GRAY);
      readdrtel.setHorizontalAlignment(JLabel.CENTER);

      //임의의 라벨 입력칸 (이회원 등등)
      inPanel.add(pure1).setBounds(230,40,500,50);
      pure1.setFont(font3);
      pure1.setBorder(empty);//라벨 간격
      pure1.setOpaque(true);//라벨배경색
      pure1.setBackground(Color.white);

      //예약자주민등록번호 
      inPanel.add(pure2).setBounds(230,92,500,50);
      pure2.setFont(font3);
      pure2.setBorder(empty);
      pure2.setOpaque(true);//라벨배경색
      pure2.setBackground(Color.white);

      //예약자 휴대폰 번호 
      inPanel.add(pure3).setBounds(230,144,500,50);
      pure3.setFont(font3);
      pure3.setBorder(empty);
      pure3.setOpaque(true);//라벨배경색
      pure3.setBackground(Color.white);

      //검사일시부터 
      inPanel.add(pure4).setBounds(230,235,500,50);
      pure4.setFont(font3);
      pure4.setBorder(empty);//
      pure4.setOpaque(true);//라벨배경색
      pure4.setBackground(Color.white);
      //검사진료소
      inPanel.add(pure5).setBounds(230,287,500,50);
      pure5.setFont(font3);
      pure5.setBorder(empty);
      pure5.setOpaque(true);//라벨배경색
      pure5.setBackground(Color.white);
      //진료소주소
      inPanel.add(pure6).setBounds(230,339,500,90);
      pure6.setBorder(new EmptyBorder(15,30,0,30));//얘는 바꾸면안됌
      pure6.setFont(font3);
      pure6.setOpaque(true);//라벨배경색
      pure6.setBackground(Color.white);

      //진료소번호
      inPanel.add(pure7).setBounds(230,431,500,50);
      pure7.setFont(font3);
      pure7.setBorder(empty);
      pure7.setOpaque(true);//라벨배경색
      pure7.setBackground(Color.white);

      // 예약취소하기 버튼
      inPanel.add(cancelbtn);
      cancelbtn.setBounds(280,500,280,40);
      cancelbtn.setBackground(Color.LIGHT_GRAY);
      cancelbtn.setFocusPainted(false);
      cancelbtn.setFont(st.fnt1);
      cancelbtn.setBackground(st.clr1);
      cancelbtn.setForeground(Color.WHITE);
      cancelbtn.setBorderPainted(false);
      cancelbtn.addActionListener(this);
      //      setSize(1200,800);
      //      setVisible(true);
      //      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      String eventBtn = e.getActionCommand();
      if(eventBtn.equals("예약 취소하기")) {
         delTestData();
      }

   }
   //검사예약 취소 메소드
   public void delTestData() {
      TestDAO dao = new TestDAO();
      
      int cnt = dao.deleteTestData(id);
      if(cnt>0) {
         JOptionPane.showMessageDialog(this, "검사 예약을 취소 하였습니다.");
         TabCheckReservationinformation.inData.removeAll();
         TabCheckReservationinformation.inData.add(TabCheckReservationinformation.lbl);
         TabCheckReservationinformation.inData.updateUI();
      }else {
         JOptionPane.showMessageDialog(this, "검사 예약을 취소를 실패 하였습니다.");
      }
   }
   public static void main(String[] args) {
      //      new TestRsvCheck();
   }
}
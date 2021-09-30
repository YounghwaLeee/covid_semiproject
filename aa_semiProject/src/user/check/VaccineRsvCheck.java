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

import database.RsvDAO;
import database.RsvVO;
import database.TestDAO;
import database.TestVO;
import database.VaccineDAO;
import user.home.SetStyle;

public class VaccineRsvCheck extends JPanel implements ActionListener {//탭에들어가는정보
   //20210801김성규
   //백신 예약 정보백신 예약 정보백신 예약 정보백신 예약 정보백신 예약 정보백신 예약 정보
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
   JLabel reservationNum= new JLabel("백신 종류");
   JLabel inoculate1= new JLabel("1차 접종일시");
   JLabel inoculate2= new JLabel("2차 접종일시");
   JLabel clinic= new JLabel("접종 진료소");
   JLabel clinicAdr= new JLabel("진료소 주소");
   JLabel clinicNum= new JLabel("진료소 번호");

   //이름 선택 흰투명 라벨 
   JLabel pure1= new JLabel();
   JLabel pure2= new JLabel();
   JLabel pure3= new JLabel();

   JLabel pure4= new JLabel();
   JLabel pure5= new JLabel();
   JLabel pure6= new JLabel();
   JLabel pure7= new JLabel();
   JEditorPane pure8= new JEditorPane();
   JLabel pure9= new JLabel();

   JButton cancelbtn= new JButton("예약 취소하기");

   Font font3 = new Font("맑은 고딕",0, 20);
   Font fontidpw = new Font("맑은 고딕",Font.BOLD, 20);
   Font searchft = new Font("맑은 고딕",0, 16);//찾기폰트
   Font rightlblft = new Font("고딕",0, 12);//8~16자 영문 
   EmptyBorder empty= new EmptyBorder(0,30,0,30);// 라벨 이름 간격들
   String  strdata;

   String id = "";
   RsvVO vo;
   public VaccineRsvCheck(String id){
      this.id = id;
      setLayout(new FlowLayout());
      RsvDAO dao = new RsvDAO();
      vo = dao.selectRsv(id);
      pure1.setText(vo.getUser_name());
      pure2.setText(vo.getUser_num());
      pure3.setText(vo.getUser_tel());

      pure4.setText(vo.getVc_type());
      pure5.setText(vo.getRsv_date()+" "+vo.getRsv_hour());
      pure6.setText(vo.getRsv_date2()+" "+vo.getRsv_hour());
      pure7.setText(vo.getCenter_name());
      pure8.setText(vo.getLoc1()+" "+vo.getLoc2()+" "+vo.getCenter_addr());
      pure9.setText(vo.getCenter_tel());

      strdata = vo.getRsv_date();

      centerPanel.setPreferredSize(new Dimension(800,800));
      centerPanel.add(inPanel);
      add(centerPanel);

      ///////라벨들

      //2.예약자 정보
      inPanel.add(reserveiplbl);//예약자 정보
      reserveiplbl.setBounds(0,0,140,40);
      reserveiplbl.setFont(fontidpw);

      //2-1.예약자이름
      inPanel.add(reservenamelbl).setBounds(0,40,230,50);
      reservenamelbl.setFont(fontidpw);
      reservenamelbl.setOpaque(true);//라벨배경색
      reservenamelbl.setBackground(Color.LIGHT_GRAY);
      reservenamelbl.setHorizontalAlignment(JLabel.CENTER);

      //2-2.예약자 주민등록번호
      inPanel.add(reservenumlbl).setBounds(0,92,230,50);
      reservenumlbl.setFont(fontidpw);
      reservenumlbl.setOpaque(true);//라벨배경색
      reservenumlbl.setBackground(Color.LIGHT_GRAY);
      reservenumlbl.setHorizontalAlignment(JLabel.CENTER);

      //2-3.예약자 휴대폰번호
      inPanel.add(reservetellbl).setBounds(0,144,230,50);
      reservetellbl.setFont(fontidpw);
      reservetellbl.setOpaque(true);//라벨배경색
      reservetellbl.setBackground(Color.LIGHT_GRAY);
      reservetellbl.setHorizontalAlignment(JLabel.CENTER); 

      //3.예약정보
      inPanel.add(resimlbl2).setBounds(0,190,140,40);
      resimlbl2.setFont(fontidpw);

      //예약 번호
      inPanel.add(reservationNum).setBounds(0,235,230,50);
      reservationNum.setFont(fontidpw);
      reservationNum.setOpaque(true);//라벨배경색
      reservationNum.setBackground(Color.LIGHT_GRAY);
      reservationNum.setHorizontalAlignment(JLabel.CENTER); 

      //1차 접종 일시
      inPanel.add(inoculate1).setBounds(0,287,230,50);
      inoculate1.setFont(fontidpw);
      inoculate1.setOpaque(true);//라벨배경색
      inoculate1.setBackground(Color.LIGHT_GRAY);
      inoculate1.setHorizontalAlignment(JLabel.CENTER); 

      //2차 접종 일시
      inPanel.add(inoculate2).setBounds(0,339,230,50);
      inoculate2.setFont(fontidpw);
      inoculate2.setOpaque(true);//라벨배경색
      inoculate2.setBackground(Color.LIGHT_GRAY);
      inoculate2.setHorizontalAlignment(JLabel.CENTER);

      //접종 진료소 clinic
      inPanel.add(clinic).setBounds(0,390,230,50);
      clinic.setFont(fontidpw);
      clinic.setOpaque(true);//라벨배경색
      clinic.setBackground(Color.LIGHT_GRAY);
      clinic.setHorizontalAlignment(JLabel.CENTER);

      //진료소 주소
      inPanel.add(clinicAdr).setBounds(0,442,230,80);
      clinicAdr.setFont(fontidpw);
      clinicAdr.setOpaque(true);//라벨배경색
      clinicAdr.setBackground(Color.LIGHT_GRAY);
      clinicAdr.setHorizontalAlignment(JLabel.CENTER);

      //진료소 번호
      inPanel.add(clinicNum).setBounds(0,523,230,50);
      clinicNum.setFont(fontidpw);
      clinicNum.setOpaque(true);//라벨배경색
      clinicNum.setBackground(Color.LIGHT_GRAY);
      clinicNum.setHorizontalAlignment(JLabel.CENTER);

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

      //예약번호
      inPanel.add(pure4).setBounds(230,235,500,50);
      pure4.setFont(font3);
      pure4.setBorder(empty);//
      pure4.setOpaque(true);//라벨배경색
      pure4.setBackground(Color.white);

      //1차 접종일시
      inPanel.add(pure5).setBounds(230,287,500,50);
      pure5.setFont(font3);
      pure5.setBorder(empty);
      pure5.setOpaque(true);//라벨배경색
      pure5.setBackground(Color.white);

      //2차 접종일시
      inPanel.add(pure6).setBounds(230,339,500,50);
      pure6.setBorder(new EmptyBorder(15,30,0,30));//얘는 바꾸면안됌
      pure6.setFont(font3);
      pure6.setOpaque(true);//라벨배경색
      pure6.setBackground(Color.white);

      //접종 진료소
      inPanel.add(pure7).setBounds(230,390,500,50);
      pure7.setFont(font3);
      pure7.setBorder(empty);
      pure7.setOpaque(true);//라벨배경색
      pure7.setBackground(Color.white);

      //진료소 주소
      inPanel.add(pure8).setBounds(230,442,500,80);
      pure8.setBorder(new EmptyBorder(15,30,0,30));//얘는 바꾸면안됌
      pure8.setFont(font3);
      pure8.setOpaque(true);//라벨배경색
      pure8.setBackground(Color.white);

      //진료소 번호
      inPanel.add(pure9).setBounds(230,523,500,50);
      pure9.setFont(font3);
      pure9.setBorder(empty);
      pure9.setOpaque(true);//라벨배경색
      pure9.setBackground(Color.white);

      //예약취소하기 버튼
      inPanel.add(cancelbtn);
      cancelbtn.setBounds(280,600,280,40);
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
         rsvDelete();
      }
   }
   //20210802 김성규 수정 이벤트 발생하기위해 메소드 
   public void rsvDelete() {// 화면 전환 해주셔야합니다
      RsvDAO dao = new RsvDAO();

      int cnt = dao.deleteRsvData(id);
      if(cnt>0) {//예약 삭제를 성공
         vaccineAdd();//백신수량 +1
         //         JOptionPane.showMessageDialog(this,"예약이 취소가 되었습니다");
      }else {//예약삭제 실패
         JOptionPane.showMessageDialog(this,"예약 취소가 실패하였습니다");
      }
   }
   public void vaccineAdd() {//백신수량+1메소드
      VaccineDAO dao2 = new VaccineDAO();
      String type = "";
      if(vo.getVc_type().equals("화이자")) {
         type = "PFIZER";
      }else if(vo.getVc_type().equals("모더나")) {
         type = "MODERNA";
      }else if(vo.getVc_type().equals("얀센")) {
         type = "Jansen";
      }else if(vo.getVc_type().equals("아스트라제네카")) {
         type = "AZ";
      }
      dao2.plusVaccData(type, vo.getCenter_code());
      JOptionPane.showMessageDialog(this,"예약이 취소가 되었습니다");
      TabCheckReservationinformation.inData.removeAll();
      TabCheckReservationinformation.inData.add(TabCheckReservationinformation.lbl2);
      TabCheckReservationinformation.inData.updateUI();
      //화면전환자리
   }
   public static void main(String[] args) {
      //      new CheckRsv();
   }
}
package admin.home;

//나의정보에서 변경하는거 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONObject;

import database.AdminDAO;
import database.AdminVO;
import database.LocDAO;
import database.UserDAO;
import database.UserVO;
import net.nurigo.java_sdk.api.Message;
import user.home.Firstpage;
import user.home.MainTitle;
import user.home.SetStyle;
import user.home.UserTabMenu;

public class AdminInfo extends JPanel implements ActionListener {// 탭에들어가는정보

   JPanel centerPanel = new JPanel(new BorderLayout());// 가운데정렬 센터페널
   JPanel inPanel = new JPanel(null);
   JLabel idlbl = new JLabel("아이디");
   JLabel idlbl2 = new JLabel();
   // 비번확인
   JLabel pwlbl = new JLabel("비밀번호");
   JPasswordField pwfd = new JPasswordField(25);// 비밀번호 텍스트 필드
   JLabel pwlbl2 = new JLabel("비밀번호 확인");
   JPasswordField pwfd2 = new JPasswordField(25);// 아이디텍스트필드

   // 이름
   JLabel namelbl = new JLabel("이름");
   JLabel namelbl2 = new JLabel();
   // 주민
   JLabel numlbl = new JLabel("주민등록번호");
   JLabel numlbl2 = new JLabel();
   // 휴대폰번호
   JLabel tellbl = new JLabel("휴대폰번호");
   JTextField telfd = new JTextField(25);
   JButton telbtn = new JButton("인증하기");

   int approval =0;

   public JLabel affiliationlbl = new JLabel("소속");

   JButton homebtn[] = new JButton[3];
   String[] col = { "취소", "수정 완료", "회원 탈퇴" };

   public JComboBox<String> affiliation;

   // 버튼패널
   JPanel btnPanel = new JPanel(new GridLayout(1, 3, 10, 10));
   // 스타일객체생성
   SetStyle st = new SetStyle();
   AdminVO adminvo;
   UserVO uservo;

   int aprroval =0;

   public AdminInfo() {
      // JFrame을 flow로 선언함
      setLayout(new FlowLayout());

      centerPanel.setPreferredSize(new Dimension(600, 540));
      centerPanel.setBackground(Color.yellow);
      centerPanel.add(inPanel);
      centerPanel.add(BorderLayout.SOUTH, btnPanel);
      add(centerPanel);

      inPanel.add(idlbl);// 아이디라벨
      idlbl.setBounds(0, 30, 200, 200);
      idlbl.setFont(st.fnt2);
      inPanel.add(idlbl2); // 아이디 member1 라벨
      idlbl2.setBounds(165, 109, 280, 40);
      idlbl2.setFont(st.fnt2);

      // 비밀번호
      inPanel.add(pwlbl);// 비밀번호
      pwlbl.setBounds(0, 80, 200, 200);
      pwlbl.setFont(st.fnt2);
      inPanel.add(pwfd); // 비번 텍스트 필드
      pwfd.setBounds(165, 160, 280, 40);
      pwfd.setFont(st.fnt2);

      inPanel.add(pwlbl2);// 비밀번호확인
      pwlbl2.setBounds(0, 130, 200, 200);
      pwlbl2.setFont(st.fnt2);
      inPanel.add(pwfd2); // 비번확인 텍스트 필드
      pwfd2.setBounds(165, 210, 280, 40);
      pwfd2.setFont(st.fnt2);

      inPanel.add(namelbl); // 이름 라벨
      namelbl.setBounds(0, 210, 200, 200);
      namelbl.setFont(st.fnt2);
      inPanel.add(namelbl2);
      namelbl2.setBounds(165, 290, 280, 40);
      namelbl2.setFont(st.fnt2);

      inPanel.add(numlbl);// 주민등록번호 라벨
      numlbl.setBounds(0, 260, 200, 200);
      numlbl.setFont(st.fnt2);
      inPanel.add(numlbl2);// 주민등록번호 숫자라벨
      numlbl2.setBounds(165, 340, 280, 40);
      numlbl2.setFont(st.fnt2);

      inPanel.add(tellbl);// 휴대폰 번호 라벨
      tellbl.setBounds(0, 310, 200, 200);
      tellbl.setFont(st.fnt2);
      inPanel.add(telfd);// 휴대폰 번호 텍스트필드
      telfd.setBounds(165, 390, 280, 40);
      // 콤보박스

      // locdao 콤보박스 소속 추가
      LocDAO locdao = new LocDAO();
      Vector<String> list1 = locdao.combo1();
      affiliation = new JComboBox<String>(list1);
      // 콤보박스명
      affiliation.insertItemAt("소속", 0);
      affiliation.setSelectedIndex(0);
      affiliation.setBounds(165, 450, 100, 30);
      affiliation.setBackground(Color.white);
      affiliation.setFont(st.fnt3);
      inPanel.add(affiliationlbl);
      affiliationlbl.setBounds(0, 370, 200, 200);
      affiliationlbl.setFont(st.fnt2);

      inPanel.add(affiliation);

      inPanel.add(telbtn);// 인증하기 버튼
      telbtn.setBounds(470, 390, 130, 40);
      telbtn.setFont(st.fnt1);
      telbtn.setBackground(st.clr1);// 버튼색깔 입히기.
      telbtn.setForeground(Color.WHITE); 
      telbtn.setFocusPainted(false);
      // 홈으로 , 수정완료, 회원탈퇴 버튼
      for (int i = 0; i < col.length; i++) {
         homebtn[i] = new JButton(col[i]);
         btnPanel.add(homebtn[i]);
         homebtn[i].setFont(st.fnt1);
         homebtn[i].setBackground(st.clr1);
         homebtn[i].setForeground(Color.WHITE); 
         homebtn[i].setFocusPainted(false);

         //         setSize(1000, 660);
         //        setVisible(true);
         //         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
      setDefaultInfo();
      homebtn[0].addActionListener(this);
      homebtn[1].addActionListener(this);
      homebtn[2].addActionListener(this);
      telbtn.addActionListener(this);
   }

   // 관리자정보불러오기.
   public void setDefaultInfo() {
      if(MainTitle.mode==1) {
         UserDAO userdao = new UserDAO();// 다오객채를 만들어서 다오한테 아이디 값을 전달하면됌.
         uservo = userdao.setMyInfo(MainTitle.user_id); // 다오야 아이디 줄게 //////////////아이디넣는곳
         idlbl2.setText(uservo.getUser_id());// 아이디라벨
         pwfd.setText(uservo.getUser_pw());// 패스워드텍스트

         namelbl2.setText(uservo.getUser_name());
         // 문자편집하는 작업
         String num1 = uservo.getUser_num().substring(0, 6);
         String num2 = uservo.getUser_num().substring(6, 7);
         numlbl2.setText(num1+" - "+num2+"******");// 주민번호받는곳 //희수수정
         telfd.setText(uservo.getUser_tel());
      }
      else if(MainTitle.mode==2) {
         AdminDAO admindao = new AdminDAO();// 다오객채를 만들어서 다오한테 아이디 값을 전달하면됌.
         adminvo = admindao.setMyInfo(MainTitle.admin_id); // 다오야 아이디 줄게 //////////////아이디넣는곳
         idlbl2.setText(adminvo.getAdmin_id());// 아이디라벨
         pwfd.setText(adminvo.getAdmin_pw());// 패스워드텍스트

         namelbl2.setText(adminvo.getAdmin_name());
         // 문자편집하는 작업
         String num1 = adminvo.getAdmin_num().substring(0, 6);
         String num2 = adminvo.getAdmin_num().substring(6, 7);
         numlbl2.setText(num1+" - "+num2+"******");// 주민번호받는곳 //희수수정
         telfd.setText(adminvo.getAdmin_tel());
         affiliation.setSelectedItem(adminvo.getAdmin_local());
      }


   }
   // 관리자 정보 불러온거 수정21.07.31

   public void modifyDefaultInfo() {
      // 여기 검사메서드를 해야한다. 메서드를 하나 더 만들어서 안에 사용해야함.
      int cnt = 0;

      if(MainTitle.mode==1) {
         UserDAO userdao = new UserDAO();
         uservo = new UserVO();
         //텍스트필드패스워드랑 데이터베이스 패스워드랑 확인
         // if(pwlbl.getText().equals(String.valueOf(pwfd2.getPassword()))){

         if (String.valueOf(pwfd.getPassword()).equals(String.valueOf(pwfd2.getPassword()))) {

            uservo.setUser_id(idlbl2.getText());
            uservo.setUser_pw(String.valueOf(pwfd2.getPassword()));
            uservo.setUser_tel(telfd.getText());
            cnt = userdao.updateInfo(uservo);
            JOptionPane.showMessageDialog(this, "나의정보 수정성공");
            returnfirst();
            //System.out.println(cnt);
         }
         else if(MainTitle.mode==2) {
            AdminDAO admindao = new AdminDAO();
            adminvo = new AdminVO();
            //텍스트필드패스워드랑 데이터베이스 패스워드랑 확인
            // if(pwlbl.getText().equals(String.valueOf(pwfd2.getPassword()))){

            if (String.valueOf(pwfd.getPassword()).equals(String.valueOf(pwfd2.getPassword()))) {

               adminvo.setAdmin_id(idlbl2.getText());
               adminvo.setAdmin_pw(String.valueOf(pwfd2.getPassword()));
               adminvo.setAdmin_tel(telfd.getText());
               cnt = admindao.updateInfo(adminvo);
               JOptionPane.showMessageDialog(this, "나의정보 수정성공");
               returnfirst();
               //System.out.println(cnt);
            } else {
               JOptionPane.showMessageDialog(this, "실패");
            }
         }
      }
   }


   public void modifyCheck() {
      System.out.println("실행...");
      if (telfd.getText().equals("") || telfd.getText() == null) { // 그 안에 있는 내용을 비교해줘야한다
         JOptionPane.showMessageDialog(this, "전화번호를 옳바르게 입력하세요");
      } else {
         modifyDefaultInfo();
      }

   }// else(telfd.equals(""){
   // JOptionPane.showMessageDialog(this, "전화번호를 옳바르게 입력하세요");
   // 회원탈퇴 버튼을 누르면

   // }\

   // 회원탈퇴기능
   public void drop() {
      if(MainTitle.mode==1) {
         UserDAO userdao = new UserDAO();
         // 저장된 vo에서 아이디를 받아와서 삭제 메서드를 실행
         int result = userdao.deleteUser(uservo.getUser_id());
         if (result > 0) {// 회원삭제됨
            JOptionPane.showMessageDialog(this, " 탈퇴하셨습니다.");
            
            MainTitle.lbl2.setText("회원가입");
            MainTitle.lbl3.setText("로그인");
            MainTitle.user_id=null;
            UserTabMenu.centerpane.removeAll();
            Firstpage first = new Firstpage(1);
            UserTabMenu.centerpane.add(first);
            UserTabMenu.centerpane.updateUI();
         } else {// 회원삭제실패함
            JOptionPane.showMessageDialog(this, "삭제를 실패하였습니다.");
         } // result!>0
      }

      else if(MainTitle.mode==2) {
         AdminDAO admindao = new AdminDAO();
         // 저장된 vo에서 아이디를 받아와서 삭제 메서드를 실행
         int result = admindao.deleteAdmin(adminvo.getAdmin_id());
         if (result > 0) {// 회원삭제됨
            JOptionPane.showMessageDialog(this, " 탈퇴하셨습니다.");
            MainTitle.lbl2.setText("회원가입");
            MainTitle.lbl3.setText("로그인");
            MainTitle.admin_id=null;
            UserTabMenu.centerpane.removeAll();
            Firstpage first = new Firstpage(2);
            UserTabMenu.centerpane.add(first);
            UserTabMenu.centerpane.updateUI();
         } else {// 회원삭제실패함
            JOptionPane.showMessageDialog(this, "탈퇴을 실패하였습니다.");
         } // result!>0
      }

   }
   ////////////////////////////////////////////////////////////
   @Override
   public void actionPerformed(ActionEvent e) {
      Object event = e.getSource();
      if(event == homebtn[0]) {
         returnfirst();
      }
      //인증하기
      else if (event == homebtn[1]&&approval==1&&String.valueOf(pwfd.getPassword()).equals(String.valueOf(pwfd2.getPassword()))) {
         modifyCheck();
         //returnfirst();
      } else if (event == homebtn[2]&&approval==1) { // 회원탈퇴//
         System.out.println("회원탈퇴ㅠㅠ");
         int msg;
         if (String.valueOf(pwfd.getPassword()).equals(String.valueOf(pwfd2.getPassword()))) {
        	 if(MainTitle.mode==1) {
        		 msg = JOptionPane.showConfirmDialog(null, "정말로 탈퇴 하시겠습니까? 기존 예약내역은 취소됩니다.","탈퇴 안내", JOptionPane.OK_CANCEL_OPTION);
        	 }
        	 else {
        		 msg = JOptionPane.showConfirmDialog(null, "정말로 탈퇴 하시겠습니까?", "탈퇴 안내", JOptionPane.OK_CANCEL_OPTION);
        	 }
            
            if (msg == 0)
               drop();
            else if (msg == 1) {
            }
         }else{
            JOptionPane.showMessageDialog(this, "비밀번호가 맞지 않습니다");
         }
      }else if(event==telbtn){
         phone();   
      }else if(!String.valueOf(pwfd.getPassword()).equals(String.valueOf(pwfd2.getPassword()))) {
         JOptionPane.showMessageDialog(this, "비밀번호가 맞지 않습니다");
      }
      else if(approval==0){//다른버튼 누를시에
         JOptionPane.showMessageDialog(this, "인증을 해주세요");
      }

   }
   ////////////////////////////////////////////////////////////////////////  
   
   public void returnfirst() {
      Firstpage firstmain = new Firstpage(MainTitle.mode);
      UserTabMenu.centerpane.removeAll();
      UserTabMenu.centerpane.add(firstmain);
      UserTabMenu.centerpane.updateUI();
   }
   
   public void phone() {
      //공백인지 아닌지 확인
      if(telfd.getText()==null || telfd.getText().equals("") || telfd.getText().length()!=11) {
         JOptionPane.showMessageDialog(null, "휴대폰 번호를 정확하게 입력해주세요.");
      }
      else {//비어있지 않은 경우 중에 실행되는 if문들

         AdminDAO dao = new AdminDAO();
         UserDAO dao2 = new UserDAO();
         int check;

         if(MainTitle.mode==1) {
            check = dao.telNumCheck(telfd.getText());
         } else {
            check = dao2.telNumCheck(telfd.getText());
         }

         checkPhoneNumeber(check);
      }
   }

   //휴대폰 번호로 가입 여부 확인
   public void checkPhoneNumeber(int check) {
      if(check==0){//기존에 있으면 이미 가입된 사람이다.
         JOptionPane.showMessageDialog(null, "이미 가입된 번호입니다.");

      }
      else {
         //비어 있지 않으면서 조건에 충족한 경우//데이터랑 맞지 않을경우 기존에 가입되어 있지 않아야 조건에 충족되는거.
         int ran=(int)Math.round(Math.random()*(9999-1000+1))+1000;    //1000~9999번까지.  
         System.out.println(ran);
         sendMessage(ran,telfd.getText()); //-----------------------------------------------------------------문자
         String msg=JOptionPane.showInputDialog(null, "인증번호를 입력해주세요");
         if(msg == null) 
            JOptionPane.showMessageDialog(null, "인증번호를 제대로 입력해주세요.");
         else if(msg.equals(""+ran)){
            // String으로 변환해줌
            JOptionPane.showMessageDialog(null, "인증되었습니다.");
            approval = 1;// 1은 인증이 되어서 넘어옴,.
            telfd.setEnabled(false);   
         }
         else 
            JOptionPane.showMessageDialog(null, "인증 실패");   
      }
   }
   
   //메시지 발송
   public void sendMessage(int ran,String fulltel) {
      String api_key = "NCSFF6HVTATSPNV2";
      String api_secret = "EXFTHIJS8DYJOSKBYDNINSDO65MSW3UM";
      Message coolsms = new Message(api_key, api_secret);

      // 4 params(to, from, type, text) are mandatory. must be filled
      HashMap<String, String> params = new HashMap<String, String>();
      String text = "인증번호를 입력해주세요 ["+String.valueOf(ran)+"]";
      params.put("to", fulltel);
      params.put("from", "01087885202");
      params.put("type", "SMS");
      params.put("text", text);
      params.put("app_version", "test app 1.2"); // application name and version

      try {
         JSONObject obj = (JSONObject) coolsms.send(params);
         System.out.println(obj.toString());

      } catch (Exception e) {
         System.out.println("문자 메시지 발송 오류");
      }
   }

   public static void main(String[] args) {
      //new AdminInfo();
   }
}
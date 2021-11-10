package admin.home;
//회원가입<영화> 희수파일 받은거. 데이터 등록완료 admindao에서 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

public class SignUpMain extends JPanel implements ActionListener ,MouseListener { // 희수

   // 회원가입 (관리자)

   JPanel mainpane = new JPanel(new BorderLayout());

   // 남쪽 (체크박스, 버튼)
   JPanel southpane = new JPanel(new BorderLayout());
   JPanel sNorthpane = new JPanel(new GridLayout(2, 1));
   JPanel cPane1 = new JPanel();
   JPanel cPane2 = new JPanel();
   JCheckBox ch1 = new JCheckBox("(필수)이용약관 동의"); //폰트바꾸기
   JCheckBox ch2 = new JCheckBox("(필수)개인정보 수집 및 이용 동의");//폰트바꾸기

   JLabel chlbl1 = new JLabel(">[자세히 보기]");
   JLabel chlbl2 = new JLabel(">[자세히 보기]");
   JPanel sSouthpane = new JPanel();
   JButton btn1 = new JButton("취소");
   JButton btn2 = new JButton("확인");

   // 센터(폼)
   // String[] title = {"아이디 ","비밀번호 ","비밀번호 확인 ","이름 ","주민등록번호 ","휴대폰번호 ","소속 "};
   public JLabel[] lbl = {new JLabel("아이디"), new JLabel("비밀번호"), new JLabel("비밀번호 확인"), new JLabel("이름"), new JLabel("주민등록번호"), new JLabel("휴대폰번호"), new JLabel("소속")};
   Font font = new Font("맑은 고딕", 1, 20);
   JPanel formpane = new JPanel(new GridLayout(lbl.length, 1));
   JTextField idformTf = new JTextField(30);
   JPasswordField pwTf = new JPasswordField(30);
   JPasswordField pwTf2 = new JPasswordField(30);
   JTextField nameformTf = new JTextField(30);

   JTextField formTf2 = new JTextField(14); 
   JPasswordField formTf21 = new JPasswordField(14);//주민뒤자리


   JTextField[] formTf3 = { new JTextField(6), new JTextField(7), new JTextField(7) };
   JButton telbtn = new JButton("인증");

   public JComboBox<String> cb1;
   LocDAO locdao = new LocDAO();

   SetStyle style = new SetStyle();
   Firstpage firstmain = new Firstpage(MainTitle.mode);

   int approval =0;
   int mode;
   int check;
   
   String fulltel;

   public SignUpMain() {
      MainTitle.lbl.setText("회원가입");
      add(BorderLayout.NORTH, mainpane);
      // 남쪽 (체크박스, 버튼)
      mainpane.add(BorderLayout.SOUTH, southpane);
      southpane.add(BorderLayout.NORTH, sNorthpane);
      southpane.add(BorderLayout.SOUTH, sSouthpane);
      sNorthpane.add(cPane1);
      sNorthpane.add(cPane2);
      cPane1.add(ch1);
      cPane1.add(chlbl1);
      cPane2.add(ch2);
      cPane2.add(chlbl2);
      sSouthpane.add(btn1);
      sSouthpane.add(btn2);

      // 센터(폼)
      mainpane.add(BorderLayout.NORTH, formpane);

      for (int i = 0; i < lbl.length; i++) {
         JPanel pane = new JPanel();
         JPanel tfpane = new JPanel();
         JPanel lblpane = new JPanel();

         formpane.add(pane);

         pane.add(lblpane);
         pane.add(tfpane);

         lblpane.setOpaque(true);
         //lblpane.setBackground(Color.LIGHT_GRAY);

         lblpane.add(lbl[i]);
         lbl[i].setFont(font);

         Dimension size1 = new Dimension(200, 40);
         lblpane.setPreferredSize(size1);

         Dimension size2 = new Dimension(350, 40);
         tfpane.setPreferredSize(size2);

         if (i == 0) {
            tfpane.add(idformTf);
            //idformTf.setText("");
         }

         else if (i == 1) {
            tfpane.add(pwTf);
            //pwTf.setText("");
         }

         else if (i == 2) {
            tfpane.add(pwTf2);
            // pwTf2.setText("");
         }

         else if (i == 3) {
            tfpane.add(nameformTf);
            //nameformTf.setText("");
         }

         else if (i == 4) { // 주민등록번호
            JLabel lb1 = new JLabel("-");
            tfpane.add(formTf2);
            //formTf2[0].setText("");
            tfpane.add(lb1);
            tfpane.add(formTf21);
            //formTf2[1].setText("");
         } else if (i == 5) { // 휴대폰 번호
            JLabel lb1 = new JLabel("-");
            JLabel lb2 = new JLabel("-");
            tfpane.add(formTf3[0]);
            //formTf3[0].setText("");
            tfpane.add(lb1);
            tfpane.add(formTf3[1]);
            //formTf3[1].setText("");
            tfpane.add(lb2);
            tfpane.add(formTf3[2]);
            //formTf3[2].setText("");
            tfpane.add(telbtn);
            telbtn.setFont(style.fnt1);
            telbtn.setBackground(style.clr1);
            telbtn.setForeground(Color.white);
         } else if (i == 6) {// 소속
            Vector<String> list1 = locdao.combo1();
            cb1 = new JComboBox(list1);
            cb1.insertItemAt("시·도", 0);
            cb1.setSelectedIndex(0);
            cb1.setFont(style.fnt16);
            cb1.setBackground(Color.WHITE);
            tfpane.add(cb1);
         }

      }

      btn1.setBackground(style.clr1);
      btn2.setBackground(style.clr1);
      btn1.setForeground(Color.white);
      btn2.setForeground(Color.white);
      btn1.setFont(style.fnt1);
      btn2.setFont(style.fnt1);
      cb1.setBackground(Color.white);
      //////////////////콤보박스글씨변경
      ch1.setFont(style.fnt1);
      ch2.setFont(style.fnt1);
      chlbl1.setFont(style.fnt1);//자세히보기 라벨폰트
      chlbl2.setFont(style.fnt1);

      //      setSize(1200, 800);
      //      setVisible(true);
      //      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

      btn1.addActionListener(this);
      btn2.addActionListener(this);
      telbtn.addActionListener(this);
      chlbl1.addMouseListener(this);
      chlbl2.addMouseListener(this);

   }


   ////////////////////////////////////////////////////////////

   //모든 조건 다 만족하면 비로소 회원가입! DB로 데이터 보냄!
   public void adminSign() {// 회원가입
      int cnt=0;
      if(MainTitle.mode==1) { //사용자
         System.out.println("사용자 회원가입");
         UserVO vo = new UserVO();
         vo.setUser_id(idformTf.getText());
         vo.setUser_pw(String.valueOf(pwTf.getPassword()));// 비밀번호

         vo.setUser_name(nameformTf.getText());// 이름
         vo.setUser_num(formTf2.getText() + (String.valueOf(formTf21.getPassword())));// 주민등록번호
         vo.setUser_tel(formTf3[0].getText() + formTf3[1].getText() + formTf3[2].getText());

         UserDAO dao = new UserDAO();
         cnt = dao.userSignUp(vo);

         if (cnt > 0) {
            JOptionPane.showMessageDialog(this, "회원가입 완료.");
            //회원가입 완료 후 다시 홈으로
            returnHome();
         }
      }
      else if(MainTitle.mode==2) { //관리자
         System.out.println("관리자 회원가입");
         AdminVO vo = new AdminVO();
         //System.out.println(String.valueOf(pwTf.getPassword()));
         vo.setAdmin_id(idformTf.getText());
         vo.setAdmin_pw(String.valueOf(pwTf.getPassword()));// 비밀번호

         vo.setAdmin_name(nameformTf.getText());// 이름
         vo.setAdmin_num(formTf2.getText() + (String.valueOf(formTf21.getPassword())));// 주민등록번호
         vo.setAdmin_tel(formTf3[0].getText() + formTf3[1].getText() + formTf3[2].getText());
         // 겟텍스트까지가 string 값이라는 거다.
         vo.setAdmin_local((String) cb1.getSelectedItem());// 소속 콤보박스
         AdminDAO dao = new AdminDAO();
         cnt = dao.adminSignUp(vo);

         if (cnt > 0) {
            JOptionPane.showMessageDialog(this, "회원가입 완료.");
            //회원가입 완료 후 다시 홈으로
            returnHome();
         }
      }

   }

   //가입 완료 버튼 누를시에 이벤트 발생 //영화
   public void checkSign() {// 회원가입 다일로그 문구
      if((idformTf.getText().length()<5 ||idformTf.getText().length()>15)) {
         JOptionPane.showMessageDialog(this, "아이디는 6자리이상 15자리 이하만 가능합니다.");
      } else if (String.valueOf(pwTf.getPassword()) == null || (String.valueOf(pwTf.getPassword()).equals(""))) {
         JOptionPane.showMessageDialog(this, "비밀번호를 정확하게 입력해주세요");
      } else if (nameformTf.getText() == null || nameformTf.getText().equals("")) {
         JOptionPane.showMessageDialog(this, "이름을 정확하게 입력해주세요");
      } else if (formTf2.getText() == null || (String.valueOf(formTf21.getPassword())) == null || formTf2.getText().equals("")
            ||(String.valueOf(formTf21.getPassword())).equals("")) {
         JOptionPane.showMessageDialog(this, "주민등록 번호를 정확하게 입력해주세요");
      } else if (formTf3[0].getText() == null || formTf3[1].getText() == null || formTf3[2].getText() == null
            || formTf3[0].getText().equals("") || formTf3[1].getText().equals("")
            || formTf3[2].getText().equals("")) {
         JOptionPane.showMessageDialog(this, "전화번호를 정확하게 입력해주세요");
      } else if (!ch1.isSelected() || !ch2.isSelected()) {
         JOptionPane.showMessageDialog(this, "약관에 동의해주세요");
      }else if(approval==0) {
         JOptionPane.showMessageDialog(this, "휴대폰 번호 인증을 해주세요");
      } else if (check==2 && ((String) cb1.getSelectedItem()).equals("시·도")) {
         JOptionPane.showMessageDialog(this, "시·도 를 선택해주세요");
      } else {
         checkPassWord();
      }
   }

   //비밀번호=비밀번호확인 일치하는지 확인
   public void checkPassWord() {
      // System.out.println(String.valueOf(pwTf.getPassword())+"/"+String.valueOf(pwTf2.getPassword()));
      if (String.valueOf(pwTf.getPassword()).equals(String.valueOf(pwTf2.getPassword()))) {
         adminSign();
      } else {
         JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
      }

   }

   //휴대폰 인증 버튼 눌렀을 때 공백 여부 확인
   public void confirmFail() {

      //공백인지 아닌지 확인
      if(formTf3[0].getText()==null||formTf3[0].getText().equals("")||formTf3[1].getText()==null||formTf3[1].getText().equals("")||formTf3[2].getText()==null||formTf3[2].getText().equals("")) {
         JOptionPane.showMessageDialog(null, "휴대폰 번호를 정확하게 입력해주세요.");
      }

      else {//비어있지 않은 경우 중에 실행되는 if문들
         List<AdminVO> telList = new ArrayList<AdminVO>();

         String tel1,tel2,tel3;
         tel1= formTf3[0].getText();
         tel2= formTf3[1].getText();
         tel3= formTf3[2].getText();
         fulltel=tel1+tel2+tel3;

         //3개의 텍스트필드를 선언을 해놓고
         //if 개연결한거가 fulltel이 핸드폰 번호들과 다 일치하지 않으면은 변수를 줘서 0 ,1 1 고정시키고 0~1로바꿔
         //System.out.println("메인어프로발"+approval); //출력문

         AdminDAO dao = new AdminDAO();
         UserDAO dao2 = new UserDAO();

         if(mode==1) {
            check = dao.telNumCheck(fulltel);
         } else {
            check = dao2.telNumCheck(fulltel);
         }

         checkPhoneNumeber(check);
      }
   }

   //휴대폰 번호로 가입 여부 확인
   public void checkPhoneNumeber(int check) {
      if(check==0){//기존에 있으면 이미 가입된 사람이다.
         JOptionPane.showMessageDialog(null, "이미 가입된 사람입니다.");

      }else {

         //비어 있지 않으면서 조건에 충족한 경우//데이터랑 맞지 않을경우 기존에 가입되어 있지 않아야 조건에 충족되는거.
         int ran=(int)Math.round(Math.random()*(9999-1000+1))+1000;    //1000~9999번까지.  
         System.out.println(ran);
         sendMessage(ran,fulltel); //--------------------------------------------------------------------------문자발송
         String msg=JOptionPane.showInputDialog(null, "인증번호를 입력해주세요");
         if(msg == null) 
            JOptionPane.showMessageDialog(null, "인증번호를 제대로 입력해주세요.");
         if(msg.equals(""+ran)){
            // String으로 변환해줌
            approval = 1;// 1은 인증이 되어서 넘어옴,.
            JOptionPane.showMessageDialog(null, "인증되었습니다.");
            formTf3[0].setEnabled(false);
            formTf3[1].setEnabled(false);
            formTf3[2].setEnabled(false);

         }
         else 
            JOptionPane.showMessageDialog(null, "인증실패");   
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

   //홈으로 돌아가는 메소드
   public void returnHome() {
      MainTitle.lbl.setText("　");
      UserTabMenu.centerpane.removeAll();
      UserTabMenu.centerpane.add(firstmain);
      UserTabMenu.centerpane.updateUI();
   }

   //////////////////////////////////////액션
   public void actionPerformed(ActionEvent e) {
      Object event = e.getSource();
      //String event = e.getActionCommand();
      if (event==(telbtn)) {
         confirmFail();
      }
      else if(event==(btn1)) { //취소
         //희수추가
         returnHome();
      }
      else if(event==btn2) {
         checkSign();
      }
   }

   ////////////////////////////////////////////////////////

   @Override
   public void mouseClicked(MouseEvent e) {
      Object lblevent=e.getSource();
      if(lblevent==chlbl1) {
         JOptionPane.showMessageDialog(this, "1. 이 약관은 전기통신사업법 제 31 조, 동 법 시행규칙 제 21조의 2에 따라 공시절차를 거친 후 홈페이지를 통하여 \n이를 공지하거나 전자우편 기타의 방법으로 이용자에게 통지함으로써 효력을 발생합니다.\r\n"
               + "2. 회사는 본 약관을 사전 고지 없이 개정할 수 있으며, 개정된 약관은 제9조에 정한 방법으로 공지합니다.\n 회원은 개정된 약관에 동의하지 아니하는 경우 본인의 회원등록을 취소(회원탈퇴)할 수 있으며,\n 계속 사용의 경우는 약관 개정에 대한 동의로 간주됩니다. 개정된 약관은 공지와 동시에 그 효력이 발생됩니다.");
      }else if(lblevent==chlbl2) {
         JOptionPane.showMessageDialog(this, "해당 사이트는 관계법령이 정하는 바에 따라 회원 등록정보를 포함한 회원의 개인정보를 보호하기 위해 노력합니다.\n"
               + "회원 개인정보의 보호 및 사용에 대해서는 관련법령 및 당 사이트의 개인정보 보호정책이 적용됩니다.\n"
               + "다만, 당 사이트 이외에 링크된 사이트에서는 당 사이트의 개인정보 보호정책이 적용되지 않습니다.");
      }

   }

   @Override
   public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub

   }


   public static void main(String[] args) {
      //            new SignUpMain();
   }
}
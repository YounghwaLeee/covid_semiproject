package user.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import admin.home.SignUpMain;
import database.AdminDAO;
import database.AdminVO;
import database.UserDAO;
import database.UserVO;



public class Login extends JPanel implements ActionListener, MouseListener{//영화
   //로그인 첫 화면 (아이디 비번)
   
    JPanel centerPanel= new JPanel(new BorderLayout());//가운데정렬 센터페널
    JPanel inPanel = new JPanel(null);
        
          JLabel idlbl= new JLabel("아이디");
          JLabel pwlbl= new JLabel("비밀번호");
          JTextField idfd= new JTextField(25);
          JPasswordField pwfd= new JPasswordField(25);
          JButton loginbtn = new JButton("로그인");
          
          JLabel searchidlbl= new JLabel("아이디 찾기");
          JLabel searchpwlbl= new JLabel("비밀번호 찾기");
          JLabel memberlbl= new JLabel("회원가입");
          
     SetStyle style = new SetStyle();   
     Firstpage firstmain = new Firstpage(MainTitle.mode);
     SearchID sID;
     SearchPW sPW;
     SignUpMain sign;
          
       public Login() {
          
          setLayout(new FlowLayout());
       
          centerPanel.setPreferredSize(new Dimension(600,540));
          centerPanel.add(inPanel);
          add(centerPanel);
         
         
          inPanel.add(idlbl); //아이디 라벨
          idlbl.setBounds(0,120,200,200);
          idlbl.setFont(style.fnt20b);
          inPanel.add(idfd); //아이디 텍스트필드
          idfd.setBounds(165,200,280,40);
        
          inPanel.add(pwlbl); //비번 라벨
          pwlbl.setBounds(0,180,200,200);
          pwlbl.setFont(style.fnt20b);
          inPanel.add(pwfd); //비밀번호 텍스트필드
          pwfd.setBounds(165,260,280,40);
        
          //로그인 버튼
          inPanel.add(loginbtn);
          loginbtn.setBounds(165,320,280,40);
          loginbtn.setBackground(style.clr1);
          loginbtn.setForeground(Color.white);
          loginbtn.setFocusPainted(false);
          loginbtn.setFont(style.fnt20b);
        
        
          inPanel.add(searchidlbl);//아이디찾기
          inPanel.add(searchpwlbl);//비밀번호찾기
          inPanel.add(memberlbl);//회원가입
        
          searchidlbl.setBounds(140,370,100,40);//아이디찾기
          searchidlbl.setFont(style.fnt16);
        
          searchpwlbl.setBounds(260,370,120,40);//비밀번호찾기
          searchpwlbl.setFont(style.fnt16);
        
          memberlbl.setBounds(390,370,100,40);//회원가입
          memberlbl.setFont(style.fnt16); 
        
        
//          setSize(1000,660);
//          setVisible(true);
//          setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          
          loginbtn.addActionListener(this);
          searchidlbl.addMouseListener(this);
          searchpwlbl.addMouseListener(this);
          memberlbl.addMouseListener(this);
          pwfd.addActionListener(this);
   }
       
       @Override
       public void actionPerformed(ActionEvent e) {
          Object event = e.getSource();
        
          //로그인 버튼 클릭 시
          if(event==loginbtn || event==pwfd) {
             String insertid = idfd.getText();
             String insertpw = String.valueOf(pwfd.getPassword());
             
             //빈칸 -> 로그인 실패
             if(insertid.equals("") || insertpw.equals("")) {
                JOptionPane.showMessageDialog(null,"아이디와 비밀번호를 입력하세요.","로그인 실패",JOptionPane.ERROR_MESSAGE);
             }
             
             //로그인 성공
             else {
                
                if(MainTitle.mode == 1) { //회원모드 로그인
                   UserDAO dao = new UserDAO();
                    UserVO vo = dao.setMyInfo(insertid);
                    if(insertpw.equals(vo.getUser_pw())) {
                       JOptionPane.showMessageDialog(null,vo.getUser_name()+"님 로그인 성공");
                       MainTitle.user_id= insertid;
                       //System.out.println("로그인한 아이디 : "+ MainTitle.user_id);
                       // 은정 추가 ---- 회원 주민번호 받아와서 생년 4자리 구하기
                       if(Integer.parseInt(vo.getUser_num().substring(0,2)) < 22) { // 00~21로 주민번호 시작할 때
                    	   MainTitle.user_year = Integer.parseInt("20"+vo.getUser_num().substring(0,2)); // 20XX
                       } else { // 22~99로 주민번호 시작할 때
                    	   MainTitle.user_year = Integer.parseInt("19"+vo.getUser_num().substring(0,2)); //19XX
                       }
                       // ------------------------------------------
                       MainTitle.lbl.setText("　");
                       UserTabMenu.centerpane.removeAll();
                       UserTabMenu.centerpane.add(firstmain);
                       UserTabMenu.centerpane.updateUI();
                       
                       MainTitle.lbl2.setText("나의 정보");
                       MainTitle.lbl3.setText("로그아웃");
                    }
                    else {
                       JOptionPane.showMessageDialog(null,"가입되지 않은 정보입니다.");
                    }
                }
                else { //관리자모드 로그인
                   AdminDAO dao2 = new AdminDAO();
                   AdminVO vo2 = dao2.setMyInfo(insertid);
                   if(insertpw.equals(vo2.getAdmin_pw()) && vo2.getAdmin_grade()==1) {
                       JOptionPane.showMessageDialog(null,"관리자 "+vo2.getAdmin_name()+" 로그인 성공");
                       MainTitle.admin_id= insertid;
                       
                       MainTitle.lbl.setText("　");
                       UserTabMenu.centerpane.removeAll();
                       UserTabMenu.centerpane.add(firstmain);
                       UserTabMenu.centerpane.updateUI();
                       
                       MainTitle.lbl2.setText("나의 정보");
                       MainTitle.lbl3.setText("로그아웃");
                    }
                   
                    else if(insertpw.equals(vo2.getAdmin_pw()) && vo2.getAdmin_grade()==0){
                       JOptionPane.showMessageDialog(null,"관리자 승인을 받아야 로그인이 가능합니다.");
                    }
                   
                    else {
                       JOptionPane.showMessageDialog(null,"가입되지 않은 정보입니다.");
                    }
                }
             }
          }
       }
       
   
   @Override
   public void mouseClicked(MouseEvent e) {
      Object event = e.getSource();
      if(event==searchidlbl && searchidlbl.getText().equals("아이디 찾기")) {
         MainTitle.lbl.setText("아이디 찾기");
         sID = new SearchID();
         
         UserTabMenu.centerpane.removeAll();
         UserTabMenu.centerpane.add(sID);
         UserTabMenu.centerpane.updateUI();
      }
      else if(event==searchpwlbl && searchpwlbl.getText().equals("비밀번호 찾기")) {
         MainTitle.lbl.setText("비밀번호 찾기");
         sPW = new SearchPW();
         
         UserTabMenu.centerpane.removeAll();
         UserTabMenu.centerpane.add(sPW);
         UserTabMenu.centerpane.updateUI();
      }
      else if (event==memberlbl && memberlbl.getText().equals("회원가입")) {
         MainTitle.lbl.setText("회원가입");
         sign = new SignUpMain();
         if (MainTitle.mode == 1) {
            sign.cb1.setVisible(false);
            sign.lbl[6].setVisible(false);
         }
         UserTabMenu.centerpane.removeAll();
         UserTabMenu.centerpane.add(sign);
         UserTabMenu.centerpane.updateUI();
      }
   }

   @Override
   public void mousePressed(MouseEvent e) {
   }

   @Override
   public void mouseReleased(MouseEvent e) {   
   }
   
   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }


   public static void main(String[] args) {
//      new Login();
   }


}
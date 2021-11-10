package user.home;

//로그인1-1
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import database.AdminDAO;
import database.UserDAO;


public class Passwordsetting extends JPanel implements ActionListener{//탭에들어가는정보
	//영화

    

       //null 레이아웃 잡을 패널
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel inPanel= new JPanel(null);
       JLabel newpwlbl= new JLabel("새 비밀번호");
    JLabel newpwSelectlbl= new JLabel("새 비밀번호 확인");
    //JLabel drawlbl= new JLabel("8~16자 영문 대 소문자,숫자,특수문자를 사용하세요.");
    //JLabel drawlbl2= new JLabel("비밀번호가 일치합니다.");
    JPasswordField newPwfd= new JPasswordField(25);
    JPasswordField newPwSelfd= new JPasswordField(25);
    
    JButton selectbtn = new JButton("확인");
    //임의로 데이터베이스 연결 잘되었는지 보는거 !
    JPasswordField id= new JPasswordField(25);
 
    Firstpage firstmain;
    

       Font font3 = new Font("맑은 고딕",0, 20);
       Font fontidpw = new Font("맑은 고딕",Font.BOLD, 20);
       Font searchft = new Font("맑은 고딕",0, 16);//찾기폰트
       Font rightlblft = new Font("고딕",0, 12);//8~16자 영문 

       
       String pw= null;
       int cnt;

       public  Passwordsetting() {
       
       setLayout(new FlowLayout());
 
       centerPanel.setPreferredSize(new Dimension(600,540));
       centerPanel.add(inPanel);
       add(centerPanel);


      inPanel.add(newpwlbl); //새비밀번호 라벨
       newpwlbl.setBounds(10,118,200,200);
       newpwlbl.setFont(fontidpw);
       inPanel.add(newPwfd); //새비밀번호 텍스트필드
       newPwfd.setBounds(180,200,280,40);
       //inPanel.add(drawlbl);//8~6문자 ...라벨
     //  drawlbl.setFont(rightlblft);
      // drawlbl.setBounds(470,200,300,40);
       
       inPanel.add(newpwSelectlbl); //새비밀번호 확인
       newpwSelectlbl.setBounds(0,175,200,200);
       newpwSelectlbl.setFont(fontidpw);
       inPanel.add(newPwSelfd); //새비밀번호 확인필드
       newPwSelfd.setBounds(180,255,280,40);
       //inPanel.add(drawlbl2);
       //drawlbl2.setFont(rightlblft);
       //drawlbl2.setBounds(290,255,300,40);
       
       inPanel.add(selectbtn);
       selectbtn.setBounds(180,315,280,40);
       selectbtn.setBackground(Color.LIGHT_GRAY);
       selectbtn.setFocusPainted(false);
       selectbtn.setFont(font3);
       
//       setSize(1000,660);
//       setVisible(true);
//       setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       
       selectbtn.addActionListener(this);
       
       
    }
    
    public void modify() {
    String user_id = SearchPW.id;
    if(MainTitle.mode == 1) {
    	UserDAO dao = new UserDAO();
    	cnt= dao.passWordModify(String.valueOf(newPwfd.getPassword()),user_id);
    }
    else if(MainTitle.mode==2){
    	AdminDAO dao2 = new AdminDAO();
    	cnt= dao2.passWordModify(String.valueOf(newPwfd.getPassword()),user_id);
    }

    if(cnt>0) {
       JOptionPane.showMessageDialog(this, "비밀번호가 변경되었습니다.");
       firstmain = new Firstpage(MainTitle.mode);
       
       UserTabMenu.centerpane.removeAll();
       UserTabMenu.centerpane.add(firstmain);
       UserTabMenu.centerpane.updateUI();
    }      
 }
    
    
    public void checkSign() {
     if(newPwfd.getPassword()==null || newPwfd.getPassword().equals("")){
        JOptionPane.showMessageDialog(this, "비밀번호를 정확하게 입력해주세요");
     }else {
        checkPassWord();
     }
  }
    public void checkPassWord() {
     if(String.valueOf(newPwfd.getPassword()).equals(String.valueOf(newPwSelfd.getPassword()))) {
        modify();
     }else {
        JOptionPane.showMessageDialog(this,"비밀번호가 일치하지 않습니다." );
        }
     
  }
    public void actionPerformed(ActionEvent e) {
    String event = e.getActionCommand();
    if(event.equals("확인")) {
       checkSign();
    }
 }   



 public static void main(String[] args) {
//    new PasswordSetting();

 }

}

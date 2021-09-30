package user.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.AdminDAO;
import database.UserDAO;
public class SearchID extends JPanel implements ActionListener{//탭에들어가는정보
   //영화
   //희수 수정

   JPanel centerPanel= new JPanel(new BorderLayout());//가운데정렬 센터페널
   JPanel inPanel = new JPanel(null);
   JLabel namelbl= new JLabel("이름");
   JLabel numlbl= new JLabel("주민등록번호");
   JLabel tellbl= new JLabel("휴대폰번호");
   JLabel underballbl= new JLabel("―");
   JTextField namefd= new JTextField(25);//이름텍스트필드
   JTextField numfd= new JTextField(15);//주민앞에텍스트필드

   JPasswordField numfd2= new JPasswordField(15);//-뒤에텍스트필드
   JTextField telfd= new JTextField(25);

   JButton selectbtn = new JButton("확인");

   //폰트
   //       Font font3 = new Font("맑은 고딕",0, 20);
   //       Font fontidpw = new Font("맑은 고딕",Font.BOLD, 20);
   //       Font searchft = new Font("맑은 고딕",0, 16);//찾기폰트
   Font rightlblft = new Font("고딕",0, 12);//8~16자 영문 
   SetStyle st = new SetStyle();

   Firstpage firstmain;

   public  SearchID() {
      setLayout(new FlowLayout());

      centerPanel.setPreferredSize(new Dimension(600,540));
      centerPanel.add(inPanel);
      add(centerPanel);

      inPanel.add(namelbl); //이름 라벨
      namelbl.setBounds(0,120,200,200);
      namelbl.setFont(st.fnt20b);
      inPanel.add(namefd); //이름 텍스트필드
      namefd.setBounds(145,200,280,40);

      inPanel.add(numlbl); //주민등록번호라벨
      numlbl.setBounds(0,178,200,200);
      numlbl.setFont(st.fnt20b);
      inPanel.add(numfd); //주민등록번호 첫번째텍스트필드
      numfd.setBounds(145,260,135,40);
      inPanel.add(underballbl);//주민등록번호 - 라벨

      underballbl.setBounds(285,260,132,40);//-라벨 크기
      inPanel.add(numfd2);
      numfd2.setBounds(297,260,132,40);//-주민등록번호 두번째칸텍스트필드

      inPanel.add(tellbl);
      tellbl.setBounds(0,235,200,200);//휴대폰번호라벨
      tellbl.setFont(st.fnt20b);
      inPanel.add(telfd);
      telfd.setBounds(145,316,280,40);//휴대폰 텍스트필드

      //          setSize(1000,660);
      //          setVisible(true);
      //          setDefaultCloseOperation(DISPOSE_ON_CLOSE);

      //확인버튼 /////////수
      inPanel.add(selectbtn);
      selectbtn.setBounds(145,370,280,40);
      selectbtn.setForeground(Color.white);
      selectbtn.setBackground(st.clr1); 
      selectbtn.setFocusPainted(false);
      selectbtn.setFont(st.fnt1);
      selectbtn.addActionListener(this);

   }         @Override
   public void actionPerformed(ActionEvent e) {
      Object event =  e.getSource(); //이벤트발생인
      if(event==selectbtn) {//콘솔문에 실행시켜라
         String name, num,num1,num2,tel;
         name= namefd.getText();//네임필드 안에 들어있는 내용을 들고오는거
         num1= numfd.getText();
         num2= (String.valueOf(numfd2.getPassword()));
         num=num1.concat(num2);//연결시켜주는 메서드

         tel = telfd.getText();
         //System.out.println(name+num+tel);

         if(MainTitle.mode == 1) {//회원 비밀찾기
            UserDAO dao=new UserDAO();
            String searchid= dao.searchId(name, num, tel);
            if(searchid==null|| searchid.equals("")) {
               JOptionPane.showMessageDialog(this, "일치하는 정보가 없습니다");
               namefd.setText(""); //일치하는 정보다 없으면 자동으로 지워진다.
               numfd.setText("");
               numfd2.setText("");
               telfd.setText("");
            }else {
               JOptionPane.showMessageDialog(this,"< "+name+" > 님의 아이디는 < "+searchid+" > 입니다.");

               firstmain = new Firstpage(MainTitle.mode);
               UserTabMenu.centerpane.removeAll();
               UserTabMenu.centerpane.add(firstmain);
               UserTabMenu.centerpane.updateUI();
            }
         }
         else {
            AdminDAO dao2=new AdminDAO();
            String searchid= dao2.searchId(name, num, tel);
            if(searchid==null|| searchid.equals("")) {
               JOptionPane.showMessageDialog(this, "일치하는 정보가 없습니다");
               namefd.setText(""); //일치하는 정보다 없으면 자동으로 지워진다.
               numfd.setText("");
               numfd2.setText("");
               telfd.setText("");
            }else {
               JOptionPane.showMessageDialog(this,"< "+name+" > 님의 아이디는 < "+searchid+" > 입니다.");

               firstmain = new Firstpage(MainTitle.mode);
               UserTabMenu.centerpane.removeAll();
               UserTabMenu.centerpane.add(firstmain);
               UserTabMenu.centerpane.updateUI();
            }
         }
      }
   }

   public static void main(String[] args) {
      //      new SearchID();

   }

}
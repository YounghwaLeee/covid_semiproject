package user.searchcenter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.LocDAO;
import user.home.SetStyle;



public class SearchCenterCombo extends JPanel implements ItemListener { //희수 // 은정 변경
   //지역 시도/시군구 콤보박스
   
   public JComboBox<String> cb1;
   public JComboBox<String> cb2;
   
   public JTextField tf = new JTextField(20);
   public JButton btn = new JButton("검색");
   public LocDAO locdao = new LocDAO();
   public LocDAO locdao2 = new LocDAO();
   
   SetStyle st = new SetStyle();
   
   public SearchCenterCombo() {
      //시도 콤보박스 설정
      Vector<String> list1 = locdao.combo1();
      cb1= new JComboBox<String>(list1);
      cb1.insertItemAt("시·도", 0);
      cb1.setSelectedIndex(0);
       
//      String sido = (String)cb1.getSelectedItem();
//      Vector<String> list2 = locdao.combo2(sido);
//      cb2 = new JComboBox(list2);
      
      cb2 = new JComboBox<String>();
      cb2.addItem("시·군·구");
      
      add(cb1);
      add(cb2);
      add(tf);
      add(btn);
      
      searchBasic();
      
      cb1.addItemListener(this);
   }
   
   
   public SearchCenterCombo(String loc1) { // 은정
      //시도 콤보박스 설정
      Vector<String> list1 = locdao.combo1();
      cb1= new JComboBox<String>(list1);
      cb1.insertItemAt("시·도", 0);
      cb1.setSelectedItem(loc1);
      
      // 시군구 콤보박스 설정
      cb2 = new JComboBox<String>();
      Vector<String> list2 = locdao2.combo2(loc1);               
     DefaultComboBoxModel<String> cb2model = new DefaultComboBoxModel<String>(list2);
     cb2.setModel(cb2model);
     cb2.insertItemAt("시·군·구", 0);
     cb2.setSelectedIndex(0);
      
//     add(cb1); // 존재는 하되 화면에 등록X
      add(cb2);
      add(tf);
      add(btn);
      
      searchBasic();
   }
   
   
   public void searchBasic() {
    //---- 컴포넌트 사이즈 고정+ 버튼 테두리 없앰 //은정 추가 ----
      cb1.setPreferredSize(new Dimension(70,30));
      cb2.setPreferredSize(new Dimension(130,30));
      tf.setPreferredSize(new Dimension(210,30));
      btn.setPreferredSize(new Dimension(70,30));
      btn.setBorderPainted(false);
      // ------------------------------------
      
      cb1.setFont(st.fnt3);
      cb2.setFont(st.fnt3);
      btn.setFont(st.fnt1);
      tf.setFont(st.fnt3);
      cb1.setBackground(Color.white);
      cb2.setBackground(Color.white);
      btn.setBackground(st.clr1);
      btn.setForeground(Color.white);
      btn.setFocusable(false);//searchcenter.combo영화수정
   }
   

   @Override
   public void itemStateChanged(ItemEvent ie) {
      if(ie.getStateChange()==ItemEvent.SELECTED) {
         cb2.removeAllItems();
         //System.out.println("테스트");
         String sido = (String)cb1.getSelectedItem();
         if(sido.equals("시·도")) {
            //cb2.removeAllItems();
            cb2.addItem("시·군·구");
         }
         else {
            Vector<String> list2 = locdao2.combo2(sido);               
             DefaultComboBoxModel<String> cb2model = new DefaultComboBoxModel<String>(list2);
             cb2.setModel(cb2model);
         }
        
      }
   }

}
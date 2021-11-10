package admin.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.AdminDAO;
import database.AdminVO;
import user.home.SetStyle;



public class AdminList extends JPanel implements ActionListener{
   AdminDAO dao = new AdminDAO();

   JSplitPane sp1,sp2;

   JPanel allPane = new JPanel(new GridLayout(1,3));

   JPanel adminPane1 = new JPanel();

   DefaultListModel<String> adminModel0 = new DefaultListModel<String>();
   JList<String> adminList0 = new JList<String>(adminModel0);
   JScrollPane adminSp = new JScrollPane(adminList0);

   JPanel centerPane = new JPanel();
   JPanel inPane = new JPanel();
   JButton lbtn = new JButton("◀");
   JButton rbtn = new JButton("▶");

   JPanel adminPane2 = new JPanel();

   DefaultListModel<String> adminModel1 = new DefaultListModel<String>();
   JList<String> adminList1 = new JList<String>(adminModel1);
   JScrollPane adminsp2 = new JScrollPane(adminList1);

   JPanel btnPane = new JPanel();
   JButton cancel = new JButton("취소");
   JButton modify = new JButton("수정완료");

   SetStyle st = new SetStyle();
   HashMap<String,Integer> modifyRoom = new HashMap<String,Integer>();//0과1이 되려고 하는 친구들의모임
   public AdminList() {
      setLayout(new BorderLayout());

      add(adminSp);
      inPane.add(lbtn);
      lbtn.setFont(st.fnt1); 
      lbtn.setBackground(st.clr1);
      lbtn.setForeground(Color.WHITE);
      lbtn.setBorderPainted(false);
      inPane.add(rbtn);
      rbtn.setFont(st.fnt1);
      rbtn.setBackground(st.clr1);
      rbtn.setForeground(Color.WHITE);
      rbtn.setBorderPainted(false);
      //      inPane.setBackground(Color.red);  버튼위치 알아보려고 넣은겁니다.
      inPane.setPreferredSize(new Dimension(100,100));
      centerPane.setBorder(new EmptyBorder(300,0,0,0));
      inPane.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
      centerPane.add(inPane);
      allPane.add(centerPane);      

      allPane.add(adminsp2);

      btnPane.add(cancel);
      cancel.setFont(st.fnt1);
      cancel.setBackground(st.clr1);
      cancel.setForeground(Color.WHITE);
      cancel.setBorderPainted(false);

      btnPane.add(modify);
      modify.setBackground(st.clr1);
      modify.setForeground(Color.white);
      modify.setFont(st.fnt1);
      add(BorderLayout.SOUTH,btnPane);
      sp1= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,adminSp,centerPane);
      sp2= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sp1,adminsp2);
      sp1.setDividerLocation(320);//위쪽이 350 픽셀이 된다.
      sp2.setDividerLocation(630);//왼쪽이 800 픽셀이 된다.
      sp1.setDividerSize(0);//px
      sp2.setDividerSize(0);//px
      add(allPane);
      allPane.add(sp2);
      adminList0.setFont(st.fnt16);
      adminList1.setFont(st.fnt16);
      
//      setSize(1200,800);
//      setVisible(true);
//      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      
      admingrade0List();
      admingrade1List();
      lbtn.addActionListener(this);
      rbtn.addActionListener(this);
      modify.addActionListener(this);
   }
   @Override
   public void actionPerformed(ActionEvent ae) {
      Object event = ae.getSource();
      if(event==rbtn) {  
         listDataChanged(adminList0,adminList1,1);// leftList-->rightList
      }else if(event==lbtn) {
         listDataChanged(adminList1,adminList0,0);// leftList <-- rightList
      }else if(event==modify) {
         gardeupdata();
         JOptionPane.showMessageDialog(this,"수정이완료되었습니다");
      }
   }
   public void listDataChanged(JList outList , JList inList, int grade) {
      DefaultListModel<String> outModel = (DefaultListModel)outList.getModel(); //remove
      DefaultListModel<String> inModel = (DefaultListModel)inList.getModel(); //add
      List<String> list = outList.getSelectedValuesList();
      //      System.out.println(outList.getSelectedValuesList());
      for(int i=0;i<list.size();i++) {
         inModel.addElement(list.get(i));// 추가
         outModel.removeElement(list.get(i));//삭제
         String id = list.get(i);
         int divide = id.indexOf("/");
         String word = id.substring(0,divide);
         System.out.println(word);
         modifyRoom.put(word, grade);
      }
   }
   public void admingrade0List() {//관리자 권한이 없는 직원목록
      List<AdminVO> list = dao.admingrade0();

      for(int i=0;i<list.size();i++) {
         AdminVO vo = list.get(i);
         String str = vo.getAdmin_id()+"/"+vo.getAdmin_name()+"/"+vo.getAdmin_local();
         adminModel0.addElement(str);
      }
   }
   public void admingrade1List() {//관리자 권한이 있는 직원목록
      List<AdminVO> list = dao.admingrade1();
      for(int i=0;i<list.size();i++) {
         AdminVO vo = list.get(i);
         String str = vo.getAdmin_id()+"/"+vo.getAdmin_name()+"/"+vo.getAdmin_local();
         adminModel1.addElement(str);
      }
   }
   //등급업데이트
   public void gardeupdata() {
      AdminDAO dao = new AdminDAO();
      Set<String> keyList = modifyRoom.keySet();
      Iterator<String> ii=keyList.iterator();
      while (ii.hasNext()) {
         String key = ii.next();
         int value = modifyRoom.get(key);
         dao.updateGrade(value,key);
      }
   }
   public static void main(String[] args) {
//      new AdminList();
   }
}
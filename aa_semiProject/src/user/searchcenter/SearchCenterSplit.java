package user.searchcenter;

import javax.swing.table.DefaultTableModel;

import database.CenterDAO;
import database.CenterVO;
import database.LocDAO;
import database.LocVO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.*;

public class SearchCenterSplit extends JPanel implements MouseListener, KeyListener{
   //진료소찾기 (리스트-상세정보) //희수
   
   JSplitPane splitpane;
   
   //왼쪽 (진료소명단 테이블)
   String title[] = {"지역","진료소명"};
   //String title[] = {"진료소명"};
   DefaultTableModel model;
   JTable centerlist;
   JScrollPane sp;
   
   //미리 다 갖고오는 데이터들
   String title2[] = {"시도","시군구","주소","전화번호","시간1","시간2","시간3","코드"};
   DefaultTableModel model2;
   JTable codelist;
   
   Font font = new Font("맑은 고딕",1, 25);
   Font font2 = new Font("맑은 고딕",0, 15);
   Font font3 = new Font("맑은 고딕",1, 15);
   Font font4 = new Font("맑은 고딕",0, 15);
   
   JPanel mainpane = new JPanel(new BorderLayout()); //오른쪽 패널
   JPanel allpane = new JPanel(new BorderLayout());
   JPanel northpane = new JPanel(new GridLayout(2,1));
      JPanel namepane = new JPanel(new BorderLayout());
         JLabel namelbl = new JLabel("진료소명");
         JPanel btnpane = new JPanel();
            JButton mapbtn = new JButton("지도");
      JLabel addrlbl = new JLabel("주소");
   JPanel centerpane = new JPanel(new GridLayout(1,2,5,5));
      JPanel titlepane = new JPanel(new GridLayout(4,1,10,10));
         JLabel titlelbl = new JLabel();
         String[] infotitle = {"전화번호","평일 운영시간","토요일 운영시간","일요일/공휴일 운영시간"};
      JPanel infopane = new JPanel (new GridLayout(4,1,10,10));
         JLabel infolbl[] = new JLabel[4];
         String[] infonull = {"-","~","~","~"};
         
      Color clr1 = new Color(38,80,150); // 파란색
         
      int row;
      public static String centerName;
      public static int code;
      
      CenterDAO dao = new CenterDAO();
      
      String centerAddr;

   public SearchCenterSplit() {  
      mainpane.setBorder(BorderFactory.createEmptyBorder(20,0,0,0)); //여백
      mainpane.add(BorderLayout.NORTH,allpane);
      Dimension dd = new Dimension(400,500);
      mainpane.setPreferredSize(dd);
      allpane.add(BorderLayout.NORTH,northpane);
         northpane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20)); //여백
         northpane.add(namepane);
            namepane.setBorder(BorderFactory.createEmptyBorder(20,0,20,0)); //여백
            namepane.add(BorderLayout.WEST,namelbl);
            namepane.add(BorderLayout.EAST,btnpane);
            btnpane.add(mapbtn);
         northpane.add(addrlbl);
         
     allpane.add(centerpane);
        centerpane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20)); //여백
        centerpane.add(titlepane);
        centerpane.add(infopane);
        
        for(int i=0; i<infotitle.length;i++) {
           titlelbl = new JLabel(infotitle[i]);
           titlelbl.setFont(font3);
           titlepane.add(titlelbl);
        }
        for(int i=0; i<infonull.length;i++) {
           infolbl[i] = new JLabel(infonull[i]);
           infolbl[i].setFont(font4);
           infopane.add(infolbl[i]);
        }
         
      //폰트 설정
      namelbl.setFont(font);
      addrlbl.setFont(font2);
      mapbtn.setBackground(clr1);
      mapbtn.setForeground(Color.white);
  
      //테이블
      setTable();   
      centerAllList();
      centerlist.getColumnModel().getColumn(0).setPreferredWidth(10);
      centerlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

          
       //분할화면 설정
       splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sp,mainpane);
       add(splitpane);
       splitpane.setDividerLocation(300);

//       setSize(1200,800);
//       setVisible(true);
//       setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       
       centerlist.addMouseListener(this);
       centerlist.addKeyListener(this);
       mapbtn.addMouseListener(this);
   }
   
   //테이블 목록 갖고오기
   public void centerAllList() {  
      List<CenterVO> list = dao.selectAllCenterData();
      setCenterTable(list);
      
   }
   
   //테이블 목록 셋팅하기
   public void setCenterTable(List<CenterVO> list) {
      model.setRowCount(0);
      
      for(int i=0;i<list.size();i++) {
         CenterVO vo = list.get(i);   
         
         Object[] obj = {vo.getLoc1()+" "+vo.getLoc2(),vo.getCenter_name()};
         model.addRow(obj);
         
         Object[] obj0 = {vo.getLoc1(),vo.getLoc2(),vo.getCenter_addr(),vo.getCenter_tel(),vo.getCenter_time1(),vo.getCenter_time2(),vo.getCenter_time3(), vo.getCenter_code()};
         model2.addRow(obj0);

      }
      
      centerlist.setRowHeight(20);
      centerlist.setFont(font4);

   }
   
   //테이블 설정
   public void setTable() {
      model = new DefaultTableModel(title,0);
      centerlist = new JTable();
      centerlist.setModel(model);//21.08.01수정영화
      sp = new JScrollPane(centerlist);
      
      model2 = new DefaultTableModel(title2,0);
      codelist = new JTable(model2);
   }


@Override
public void mouseClicked(MouseEvent e) {
   if(e.getSource()==mapbtn) {
	      APIMain mapimg = new APIMain(centerAddr);
   }
   
}

@Override
public void mousePressed(MouseEvent e) {
   // TODO Auto-generated method stub
   
}

@Override
public void mouseReleased(MouseEvent e) {
   if(e.getButton()==1) {
      //
	   
      row = centerlist.getSelectedRow();
      eventsection(row);
   }
}

//이벤트 메서드
public void eventsection(int row) {
    //진료소 명 설정
    centerName = String.valueOf(centerlist.getValueAt(row, 1));
    namelbl.setText(centerName);
    
    String addr = String.valueOf(codelist.getValueAt(row, 2));
    String tel = String.valueOf(codelist.getValueAt(row, 3));
    String time1 = String.valueOf(codelist.getValueAt(row, 4));
    String time2 = String.valueOf(codelist.getValueAt(row, 5));
    String time3 = String.valueOf(codelist.getValueAt(row, 6));
        
    String sido = String.valueOf(codelist.getValueAt(row, 0));
    String sigungu = String.valueOf(codelist.getValueAt(row, 1));
    centerAddr = addr;
    addrlbl.setText(sido+" "+sigungu+" "+centerAddr);
    
    code = (Integer)codelist.getValueAt(row, 7);
    centerAddr = addr;
    
    String infonull2[] = {tel,time1,time1,time3};
      for(int i=0; i<infonull.length;i++) {
         infolbl[i].setText(infonull2[i]);
         infolbl[i].setFont(font4);
      }
}

   @Override
   public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
      
   }
   
   @Override
   public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub
      
   }
   
   @Override
   public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub
      
   }
   
   @Override
   public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_UP||e.getKeyCode()==KeyEvent.VK_DOWN) {
         row = centerlist.getSelectedRow();
         eventsection(row);
      }
   }
   
   @Override
   public void keyReleased(KeyEvent e) {
   
   }
   
   
   public static void main(String[] args) {
//      new SearchCenterSplit();
   
   }
   
   }
//키이벤트 엔터 방향키 조정시에 되는거 07.30 (o)
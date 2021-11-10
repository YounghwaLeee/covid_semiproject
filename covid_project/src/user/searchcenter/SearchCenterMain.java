package user.searchcenter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

import database.AdminDAO;
import database.AdminVO;
import database.CenterDAO;
import database.CenterVO;
import user.home.MainTitle;
import user.home.SetStyle;

public class SearchCenterMain extends JPanel implements ActionListener, KeyListener{
   //진료소 찾기 탭메뉴 //희수

   SetStyle style = new SetStyle();

   //탭
   SearchCenterCombo cb = new SearchCenterCombo();
   SearchCenterSplit sp = new SearchCenterSplit();


   //탭1 (목록보기)
   public SearchCenterMain() {
      setLayout(new BorderLayout());


      //탭1 (목록보기)
      add(BorderLayout.NORTH, cb);
      add(sp);
      sp.setPreferredSize(new Dimension(880,500));

//      setSize(1020,800);
//      setVisible(true);
//      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

      cb.btn.addActionListener(this);
      cb.tf.addActionListener(this);
      
   }
   //  //회원용! 진료소 검색21.08.01 영화추가  //시도 시군구 콤보박스 체크안하고 텍스트필드만 검색했을때 
   public void centerSearch() {
      String loc1=(String)cb.cb1.getSelectedItem();//콤보박스값 불러오는거 
      String loc2=(String)cb.cb2.getSelectedItem();
      String center=cb.tf.getText();
      CenterDAO centerdao = new CenterDAO();
      //만약 검색어를 입력했을떄 콤보박스만 선택했을때 dao 한테 검색하라는 일을 시켜야됌. 
      if(loc1.equals("시·도")&&loc2.equals("시·군·구")) {
         // 다오객채를 만들어서 다오한테 아이디 값을 전달하면됌.
         List<CenterVO>list = centerdao.memberCenterSearch(center);//검색어알려줌 그테이블리스트담음
         memberCenter(list);//검색된 결과만 리스트만 가져오는 거.
      }else {
         List<CenterVO>list = centerdao.memberCenterSearch(loc1,loc2,center);//검색어알려줌 그테이블리스트담음1
         memberCenter(list);
      }
   }
   public void memberCenter( List<CenterVO> list) { //list 가 배열이라고 생각하면됌그 타입이 memberVO 타입
      sp.model.setRowCount(0);//맴버센터 전체 출력해주는기능
      sp.model2.setRowCount(0);
      for (int i=0; i<list.size();i++) {// for 문은테이블에 있는 정보 다풀어서 보여줄게 ~
         CenterVO vo= list.get(i);//하나가져온거를 담아줌 
         Object[]obj = {vo.getLoc1()+" "+vo.getLoc2(),vo.getCenter_name()};
         sp.model.addRow(obj);

         Object[] obj0 = {vo.getLoc1(),vo.getLoc2(),vo.getCenter_addr(),vo.getCenter_tel(),vo.getCenter_time1(),vo.getCenter_time2(),vo.getCenter_time3()};
         sp.model2.addRow(obj0);

      }
      sp.centerlist.setModel(sp.model);//다시한번 모델을 세팅해주는거.
      sp.centerlist.updateUI();
   }


   @Override
   public void actionPerformed(ActionEvent e) {
      String event= e.getActionCommand();
      Object evr=e.getSource();
      if(event.equals("검색")|| evr==cb.tf) {
         centerSearch();
      }
      
      
//      
//   }
}
   @Override
   public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub
      
   }
   @Override
   public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()==KeyEvent.VK_ENTER) {
         centerSearch();
      }
   }
   @Override
   public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
      
   }public static void main(String[] args) {
//      new SearchCenterMain();   
   }
}
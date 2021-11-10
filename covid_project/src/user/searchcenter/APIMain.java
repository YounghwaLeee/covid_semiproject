package user.searchcenter;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class APIMain extends JFrame{
	
//	private JTextField tf = new JTextField(30);
//	private JButton btn = new JButton("검색");
//	private JPanel pane = new JPanel();
	
	private GoogleAPI goo = new GoogleAPI();
	private String location;
	private JLabel gooMap = new JLabel();
	
	public APIMain() {
		
	}
	
	public APIMain(String location) {
		goo.downloadMap(location);
		gooMap.setIcon((goo.getMap(location)));
		goo.fileDelete(location);
		
		add(BorderLayout.SOUTH,gooMap);
		
		setTitle("진료소 위치");
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pack();
	}
	
//	public class Event implements MouseListener {
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			setMap(tf.getText());
//			
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
//	public APIMain() {
//		setLayout(new BorderLayout());
////		setTitle("Google map");
////		setVisible(true);
////		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		
////		pane.add(tf);
////		pane.add(btn);
//		SearchCenterSplit.mapbtn.addMouseListener(new Event());
//		
////		goo.downloadMap(location);
////		gooMap = new JLabel(goo.getMap(location));
////		goo.fileDelete(location);
//		
//		add(BorderLayout.NORTH,pane);
//		//pack();
//		
//	}
	
	public static void main(String[] args) {
		new APIMain();

	}


}

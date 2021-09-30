package user.rsv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.DataBufferInt;

import database.RsvDAO;
import database.RsvVO;
import database.TestDAO;
import database.TestVO;
import database.VaccineDAO;
import user.home.MainTitle;
import user.home.SetStyle;
import user.home.UserTabMenu;
import user.searchcenter.SearchCenterSplit;

import javax.swing.*;

public class RsvSouthBtn extends JPanel implements ActionListener{
	JPanel rsvPane = new JPanel();
	public JButton rsvBtn = new JButton();
	
	SetStyle st = new SetStyle();
	
	public RsvSouthBtn() {
		
		rsvBtn.setPreferredSize(new Dimension(200,50));
		rsvBtn.setBackground(st.clr1);
		rsvBtn.setForeground(Color.white);
		rsvBtn.setFont(st.fnt1);
		
		rsvPane.add(rsvBtn);
		add(rsvPane);
		rsvBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object event = e.getSource();
		
		if(event==rsvBtn && rsvBtn.getText().equals("코로나 검사 예약하기")) {
			TestDAO dao = new TestDAO();
			TestVO vo = dao.selectTestRsv(MainTitle.user_id);
			if(vo.getRsv_date()!=null){
				JOptionPane.showMessageDialog(this, "이미 예약된 내역이 있습니다.");
			}
			
			else if(SearchCenterSplit.centerName==null) {
				JOptionPane.showMessageDialog(this, "진료소를 선택하세요.");
			}
			else {
				int centerCode = SearchCenterSplit.code;
				RsvSelectDate rsv1 = new RsvSelectDate(centerCode);
				UserTabMenu.centerpane.removeAll();
				UserTabMenu.centerpane.add(rsv1);
				UserTabMenu.centerpane.updateUI();
			}
			
		}
		
		
		else if(event==rsvBtn && rsvBtn.getText().equals("백신 접종 예약하기")) {
			RsvDAO dao2 = new RsvDAO();
			RsvVO vo2 = dao2.selectRsv(MainTitle.user_id);
			System.out.println(RsvVaccineRadio.vc_type);
			
			if(RsvVaccineRadio.vc_type==null || RsvVaccineRadio.vc_type.equals("전체 백신")) {
				JOptionPane.showMessageDialog(this, "백신 종류를 선택해주세요.");
			}

			else if(vo2.getRsv_date()!=null) {
				JOptionPane.showMessageDialog(this, "이미 백신 접종 예약된 내역이 있습니다.");
			}
			else if(SearchCenterSplit.centerName==null) {
				JOptionPane.showMessageDialog(this, "진료소를 선택하세요.");
			}
			
			else {
				int centerCode = SearchCenterSplit.code;
				RsvSelectDate rsv2 = new RsvSelectDate(centerCode,RsvVaccineRadio.vc_type);
				UserTabMenu.centerpane.removeAll();
				UserTabMenu.centerpane.add(rsv2);
				UserTabMenu.centerpane.updateUI();
			}
//			VaccineDAO dao3 = new VaccineDAO();
//			VaccineVO vo3 = dao3.
//			else if() {
//				
//			}
		}
	}

}

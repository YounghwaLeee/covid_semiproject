package user.home;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;

public class CoronaAPI extends JPanel {

	Calendar now = Calendar.getInstance();
	Calendar now2 = Calendar.getInstance();
	String year = String.valueOf(now.get(Calendar.YEAR)); // 2021
	String month = String.valueOf(now.get(Calendar.MONTH) + 1);
	String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH)); // 10

	String yyear;
	String ymonth;
	String yday;

	String today;
	int clearCnt; // 격리해제수
	int deathCnt; // 사망자수
	int decideCnt; // 확진자수
	int examCnt; // 검사진행수

	int clearCnt2; // 격리해제수
	int deathCnt2; // 사망자수
	int decideCnt2; // 확진자수
	int examCnt2; // 검사진행수

	SetStyle st = new SetStyle();

	public CoronaAPI() {

		try {

			setLayout(new BorderLayout());
			setOpaque(false);

			now2.add(Calendar.DATE, -1);
			yyear = String.valueOf(now2.get(Calendar.YEAR));
			ymonth = String.valueOf(now2.get(Calendar.MONTH) + 1);
			yday = String.valueOf(now2.get(Calendar.DAY_OF_MONTH));

			// System.out.println(month.length());
			if (month.length() == 1) {
				month = "0" + month;
			}

			if (day.length() == 1) {
				day = "0" + day;
			}

			if (ymonth.length() == 1) {
				ymonth = "0" + ymonth;
			}
			if (yday.length() == 1) {
				yday = "0" + yday;
			}

			today = year + month + day;
			String yesterday = yyear + ymonth + yday;
			//System.out.println(today);
			//System.out.println(yesterday);

			StringBuilder urlBuilder = new StringBuilder(
					"http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /* URL */
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
					+ "=IYA4VjaxNAd2GApj1fRFqvtQqIHaKphJU4jZH5%2BC%2B%2B5CZjKxJQju4alJosVgE21HjwpFLcksDv18kWuzsw0jEg%3D%3D"); /*
																																 * Service
																																 * Key
																																 */
			// urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" +
			// URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
			urlBuilder.append(
					"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
					+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
			urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "="
					+ URLEncoder.encode(yesterday, "UTF-8")); /* 검색할 생성일 범위의 시작 */
			urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "="
					+ URLEncoder.encode(today, "UTF-8")); /* 검색할 생성일 범위의 종료 */
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			// System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			String data = sb.toString();
			//System.out.println(data);

//    	int clearCnt; //격리해제수
//    	int deathCnt; //사망자수
//    	int decideCnt; //확진자수
//    	int examCnt; //검사진행수

			int idx3 = data.indexOf("<clearCnt>") + 10;
			int idx4 = data.indexOf("</clearCnt>");
			clearCnt = Integer.valueOf(data.substring(idx3, idx4));
			//System.out.println("누적 격리해제 수 : " + clearCnt);

			int idxx3 = data.indexOf("<clearCnt>", idx4 + 10) + 10;
			int idxx4 = data.indexOf("</clearCnt>", idx4 + 10);
			clearCnt2 = Integer.valueOf(data.substring(idxx3, idxx4));
			//System.out.println("격리해제 수 : " + (clearCnt - clearCnt2));
			int clearnum = clearCnt - clearCnt2;

			//////////////////////////////////

			int idx5 = data.indexOf("<deathCnt>") + 10;
			int idx6 = data.indexOf("</deathCnt>");
			deathCnt = Integer.valueOf(data.substring(idx5, idx6));
			//System.out.println("누적 사망자 수 : " + deathCnt);

			int idxx5 = data.indexOf("<deathCnt>", idx6 + 10) + 10;
			int idxx6 = data.indexOf("</deathCnt>", idx6 + 10);
			deathCnt2 = Integer.valueOf(data.substring(idxx5, idxx6));
			//System.out.println("사망자 수 : " + (deathCnt - deathCnt2));
			int deathnum = deathCnt - deathCnt2;

			////////////////////////////////////////////////////

			int idx7 = data.indexOf("<decideCnt>") + 11;
			int idx8 = data.indexOf("</decideCnt>");
			decideCnt = Integer.valueOf(data.substring(idx7, idx8));
			//System.out.println("누적 확진자 수 : " + decideCnt);

			int idxx7 = data.indexOf("<decideCnt>", idx8 + 10) + 11;
			int idxx8 = data.indexOf("</decideCnt>", idx8 + 10);
			decideCnt2 = Integer.valueOf(data.substring(idxx7, idxx8));
			//System.out.println("확진자 수 : " + (decideCnt - decideCnt2));
			int decidenum = decideCnt - decideCnt2;

			////////////////////////////////////////////////////

			int idx9 = data.indexOf("<examCnt>") + 9;
			int idx10 = data.indexOf("</examCnt>");
			examCnt = Integer.valueOf(data.substring(idx9, idx10));
			//System.out.println("누적 검사진행 수 : " + examCnt);

			int idxx9 = data.indexOf("<examCnt>", idx10 + 10) + 9;
			int idxx10 = data.indexOf("</examCnt>", idx10 + 10);
			examCnt2 = Integer.valueOf(data.substring(idxx9, idxx10));
			//System.out.println("검사진행 수 : " + (examCnt - examCnt2));
			int examnum = examCnt - examCnt2;
			
			String examstr = "(▲ ";
			if (examnum<0) {
				examnum = examnum * -1;
				examstr = "(▼ ";
			}
			else if(examnum==0) {
				examstr = "((-) ";
			}

			JLabel title = new JLabel("코로나 현황");
			
			String[] coronainfo = {"확진 환자", String.valueOf(decideCnt),
					"(▲ " + decidenum + " )", "격리 해제", String.valueOf(clearCnt), "(▲ " + clearnum + " )", "사망자",
					String.valueOf(deathCnt), "(▲ " + deathnum + " )", "검사 진행", String.valueOf(examCnt),
					examstr + examnum + " )" };

			JPanel pane = new JPanel(new GridLayout(2,2,30,30));
			pane.setOpaque(false);

			title.setFont(st.fnt20b);
			title.setForeground(Color.white);
			
			JPanel titlepane = new JPanel();
			titlepane.add(title,FlowLayout.LEFT);
			titlepane.setOpaque(false);
			
			add(BorderLayout.NORTH,titlepane);
			add(pane);
			pane.setBorder(new EmptyBorder(30,30,30,30));
			
			JPanel pane1 = new JPanel(new GridLayout(3,1));
			JPanel pane2 = new JPanel(new GridLayout(3,1));
			JPanel pane3 = new JPanel(new GridLayout(3,1));
			JPanel pane4 = new JPanel(new GridLayout(3,1));
			
			for(int i=0;i<coronainfo.length;i++) {
				if(i<3) {
					JLabel lbl = new JLabel(coronainfo[i]);
					lbl.setForeground(Color.white);
					lbl.setOpaque(false);
					if(i%3==1) {
						lbl.setFont(st.fnt2);
						lbl.setForeground(new Color(137,245,245));
					}
					if(i%3==2) {
						lbl.setFont(st.fnt3);
					}
					else {
						lbl.setFont(st.fnt1);
					}
					pane1.add(lbl);
				}
				else if(i<6) {
					JLabel lbl = new JLabel(coronainfo[i]);
					lbl.setForeground(Color.white);
					if(i%3==1) {
						lbl.setFont(st.fnt2);
						lbl.setForeground(new Color(137,245,245));
					}
					if(i%3==2) {
						lbl.setFont(st.fnt3);
					}
					else {
						lbl.setFont(st.fnt1);
					}
					pane2.add(lbl);
				}
				else if (i<9) {
					JLabel lbl = new JLabel(coronainfo[i]);
					lbl.setForeground(Color.white);
					if(i%3==1) {
						lbl.setFont(st.fnt2);
						lbl.setForeground(new Color(137,245,245));
					}
					if(i%3==2) {
						lbl.setFont(st.fnt3);
					}
					else {
						lbl.setFont(st.fnt1);
					}
					pane3.add(lbl);
				}
				else {
					JLabel lbl = new JLabel(coronainfo[i]);
					lbl.setForeground(Color.white);
					if(i%3==1) {
						lbl.setFont(st.fnt2);
						lbl.setForeground(new Color(137,245,245));
					}
					if(i%3==2) {
						lbl.setFont(st.fnt3);
					}
					else {
						lbl.setFont(st.fnt1);
					}
					pane4.add(lbl);
				}
			}
			
			pane.add(pane1);
			pane.add(pane2);
			pane.add(pane3);
			pane.add(pane4);
			pane1.setOpaque(false);
			pane2.setOpaque(false);
			pane3.setOpaque(false);
			pane4.setOpaque(false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.println(sb.toString());
	}
//    public static void main(String[] args) throws IOException {
//    	new CoronaAPI();
//    }
}

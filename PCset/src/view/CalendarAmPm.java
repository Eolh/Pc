package view;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarAmPm {
	public void testName() throws Exception {
		int i = Calendar.getInstance().get(Calendar.AM_PM);
		String[] ampm = {"AM","PM"};
		System.out.println("지금 오전 오후의 값 : "+i +","+ampm[i]);
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh");
	}
}

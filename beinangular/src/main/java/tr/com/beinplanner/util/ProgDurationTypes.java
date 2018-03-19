package tr.com.beinplanner.util;

import java.util.Calendar;
import java.util.Date;

public class ProgDurationTypes {

	public static final int DURATION_TYPE_DAILY=0;
	
	public static final int DURATION_TYPE_WEEKLY=1;
	
	public static final int DURATION_TYPE_MONTHLY=2;
	
	
	public static Date getDateForNextDate(Date date, int dayDuration) {
		if (date == null)
			return new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(((Date)(date.clone())).getTime());
		
		cal.set(Calendar.DATE, cal.get(Calendar.DATE)+dayDuration);
		
		Date returnDate=new Date(cal.getTimeInMillis());
		
		return returnDate;
	}
	
	public static Date getDateForNextMonth(Date date, int monthDuration) {
		if (date == null)
			return new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(((Date)(date.clone())).getTime());
		
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+monthDuration);
		
		Date returnDate=new Date(cal.getTimeInMillis());
		
		return returnDate;
	}
	
	
	
 	
}

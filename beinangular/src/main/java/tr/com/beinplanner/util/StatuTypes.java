package tr.com.beinplanner.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class StatuTypes {

	public static final int ALL=-1;
	public static final int ACTIVE=0;
	public static final int PASSIVE=1;
	public static final int FREEZE=2;
	public static final int NO_STARTED=3;
	public static final int FINISHED=4;
	
	public static final int TIMEPLAN_CANCEL=1;
	public static final int TIMEPLAN_NORMAL=0;
	public static final int TIMEPLAN_POSTPONE=2;
	
	
	public static String getStatuTypes(int statu){
		switch (statu) {
		case 0:
			return "active";
		case 1:
			return "passive";
		case 2:
			return "freeze";
		case 3:
			return "noStarted";
		case 4:
			return "finished";
		default:
			return "none";
		}
	}
	
	public static String getStatuByDate(Date startDate,Date endDate,String ptTz){
		
		Date currentDate=getCurrentDateByTimeZone(ptTz);
		
		if(startDate.before(currentDate) && endDate.after(currentDate)){
			return getStatuTypes(ACTIVE);
		}else if(startDate.after(currentDate)){
			return getStatuTypes(NO_STARTED);
		}else if(endDate.before(currentDate)){
			return getStatuTypes(FINISHED);
		}else{
			return getStatuTypes(ALL);
		}
	}
	
   public static Date getCurrentDateByTimeZone(String ptTz){
		
		
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = TimeZone.getTimeZone(ptTz);
		calendar.setTimeZone(timeZone);
		String timeOfCome=calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
		Date currentDate=OhbeUtil.getThatDayFormatNotNull(timeOfCome, "dd/MM/yyyy HH:mm");
	    return currentDate;
	}
	
	public static String getCurrentDateByTimeZoneStr(String ptTz){
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = TimeZone.getTimeZone(ptTz);
		calendar.setTimeZone(timeZone);
		String timeOfCome=calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
		return timeOfCome;
	}
	
	
}

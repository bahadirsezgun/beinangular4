package tr.com.beinplanner.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeTypes {

	public static final int TIME_TYPE_NO_PERIOD  = 1;
	
	public static final int TIME_TYPE_PERIOD  = 2;
	
	public static String TIME_Pzt="Mon";
	public static String TIME_Sal="Tue";
	public static String TIME_Car="Wed";
	public static String TIME_Per="Thu";
	public static String TIME_Cum="Fri";
	public static String TIME_Cmt="Sat";
	public static String TIME_Paz="Sun";
	
	
	public static String getDateStrByFormatEEEByTurkish(Date date) {
		if (date == null)
			return "";
		java.util.Date thatDay = new java.util.Date(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMMM", new Locale("en-EN"));//LangUtil.firmam.getFirmLang()));
		sdf.applyPattern("EEE");
		return sdf.format(thatDay);
	}
}

package tr.com.beinplanner.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tr.com.beinplanner.schedule.businessEntity.ScheduleDayObj;
import tr.com.beinplanner.settings.dao.PtGlobal;

public class DateTimeUtil {

	
	public static void main(String[] args) {
		
		Calendar cal=Calendar.getInstance();
		int day=cal.get(Calendar.DAY_OF_WEEK);
		
		
		////System.out.println(getWeekStartDate());
		////System.out.println(getWeekEndDate());
		
		
		////System.out.println(getNextWeekStartDate());
		////System.out.println(getNextWeekEndDate());
		
	}
	
	
	public static Date getWeekStartDate(){
		Calendar cal=Calendar.getInstance();
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		
		Date weekStartDate=new Date();
		weekStartDate=OhbeUtil.getDateForNextDate(weekStartDate, (-1*dayOfWeek+1));
		weekStartDate=OhbeUtil.setTime00(weekStartDate);
		return weekStartDate;
	}
	
	public static Date getWeekEndDate(){
		Calendar cal=Calendar.getInstance();
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		
		Date weekEndDate=new Date();
		weekEndDate=OhbeUtil.getDateForNextDate(weekEndDate, (9-dayOfWeek));
		weekEndDate=OhbeUtil.setTime00(weekEndDate);
		return weekEndDate;
	}
	
	public static Date getNextWeekStartDate(){
		Calendar cal=Calendar.getInstance();
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		
		Date weekStartDate=new Date();
		weekStartDate=OhbeUtil.getDateForNextDate(weekStartDate, (8-dayOfWeek));
		weekStartDate=OhbeUtil.setTime00(weekStartDate);
		return weekStartDate;
	}
	
	public static Date getNextWeekEndDate(){
		Calendar cal=Calendar.getInstance();
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		
		Date weekEndDate=new Date();
		weekEndDate=OhbeUtil.getDateForNextDate(weekEndDate, (16-dayOfWeek));
		weekEndDate=OhbeUtil.setTime00(weekEndDate);
		return weekEndDate;
	}
	
	
	
	public static Date setHourMinute(Date day,String time){
		Calendar cal=Calendar.getInstance();
		cal.setTime(day);
		
		String[] t=time.split(":");
		
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(t[1]));
		
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return new Date(cal.getTimeInMillis());
	}
		
	public static Date setMillisecondTo0(Date day){
		Calendar cal=Calendar.getInstance();
		cal.setTime(day);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());
	}
	
	public static String getHourMinute(Date day){
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(day);
		String time="";
		
		
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		String timeHour=""+hour;
		if(hour<10){
			timeHour="0"+timeHour;
		}
		
		int minute=cal.get(Calendar.MINUTE);
		String timeMinute=""+minute;
		if(minute<10){
			timeMinute="0"+minute;
		}
		time=timeHour+":"+timeMinute;
		
		return time;
		
	}
	
	public static String getHourMinuteToNumber(Date day){
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(day);
		String time="";
		
		
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		String timeHour=""+hour;
		if(hour<10){
			timeHour="0"+timeHour;
		}
		
		int minute=cal.get(Calendar.MINUTE);
		String timeMinute=""+minute;
		if(minute<10){
			timeMinute="0"+minute;
		}
		time=timeHour+timeMinute;
		
		return time;
		
	}
	
	public static String getDayNames(Date day){
		Calendar cal=Calendar.getInstance();
		cal.setTime(day);
		
		int daySeq=cal.get(Calendar.DAY_OF_WEEK);
		
		String dayName="";
		switch (daySeq) {
		case 1:
			dayName = "sunday";
			break;
		case 2:
			dayName = "monday";
			break;
		case 3:
			dayName = "tuesday";
			break;
		case 4:
			dayName = "wednesday";
			break;
		case 5:
			dayName = "thursday";
			break;
		case 6:
			dayName = "friday";
			break;
		case 7:
			dayName = "saturday";
			break;
		
		}
		return dayName;
		
		
	}
	
	public static String getMonthNames(Date day){
		Calendar cal=Calendar.getInstance();
		cal.setTime(day);
		
		int monthSeq=cal.get(Calendar.MONTH)+1;
		
		String monthName="";
		
		switch (monthSeq) {
		case 1:
			monthName = "january";
			break;
		case 2:
			monthName = "february";
			break;
		case 3:
			monthName = "march";
			break;
		case 4:
			monthName = "april";
			break;
		case 5:
			monthName = "may";
			break;
		case 6:
			monthName = "june";
			break;
		case 7:
			monthName = "july";
			break;
		case 8:
			monthName = "august";
			break;
		case 9:
			monthName = "september";
			break;
		case 10:
			monthName = "october";
			break;
		case 11:
			monthName = "november";
			break;
		case 12:
			monthName = "december";
			break;
		}
		return monthName;
		
		
	}
	
	public static String getDateStrByFormat(Date date, String format) {
		if (date == null)
			return "";
		java.util.Date thatDay = new java.util.Date(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.applyPattern(format);
		return sdf.format(thatDay);
	}
	
	public static String convertMobileDateToSystemDate(String mobileDateStr,int firmId,String format){
		
		mobileDateStr=mobileDateStr.replaceAll("\\.", "/");
		Date mobileDate=getThatDayFormatNotNull(mobileDateStr, "dd/MM/yyyy");
		return getDateStrByFormat(mobileDate, format);
	}
			
			
	
	public static Date getThatDayFormatNotNull(String dateString,
			String format) {
		Date date=null;
		try {
			date = new Date();
			if (!dateString.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				date = sdf.parse(dateString);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getDateNamesFromLocale(Date date,int firmId,String ptLang) {
		////System.out.println("GlobalUtil.global.getPtLang() is "+GlobalUtil.global.getPtLang());
		SimpleDateFormat sdf = new SimpleDateFormat("EEE", new Locale(ptLang));
		java.util.Date thatDay = new java.util.Date(date.getTime());
		sdf.applyPattern("EEE");
		return sdf.format(thatDay);
	}
	
	public static int getYearOfDate(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int getMonthOfDate(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH)+1;
	}
	
	public static List<ScheduleDayObj> getDayList(Date startDate,int dayDuration,PtGlobal ptGlobal){
		List<ScheduleDayObj> dayLists=new ArrayList<ScheduleDayObj>();
		for (int i = 0; i < dayDuration; i++) {
			ScheduleDayObj dayList=new ScheduleDayObj();
			
			
			SimpleDateFormat date_format = new SimpleDateFormat("EEE, dd MMMM", new Locale(ptGlobal.getPtLang()));
			
			String dateFormat=ptGlobal.getPtScrDateFormat();
			String dateTimeFormat=ptGlobal.getPtScrDateFormat()+" HH:mm";
					
			Date date=OhbeUtil.getDateForNextDate(startDate, i);
			dayList.setDayName(date_format.format(date));
			dayList.setDayDate(OhbeUtil.getThatDateForNight(OhbeUtil.getDateStrByFormat( date, dateTimeFormat) , dateTimeFormat) );
			
			SimpleDateFormat date_format1 = new SimpleDateFormat(dateFormat);
			
			Date date1=OhbeUtil.getDateForNextDate(startDate, i);
			dayList.setDayTime(date_format1.format(date1));
			dayLists.add(dayList);
		}
		
		return dayLists;
		
	}
	
	
	public static String getMonthNamesBySequence(int monthSeq){
		String monthName="";
		switch (monthSeq) {
		case 1:
			monthName = "january";
			break;
		case 2:
			monthName = "february";
			break;
		case 3:
			monthName = "march";
			break;
		case 4:
			monthName = "april";
			break;
		case 5:
			monthName = "may";
			break;
		case 6:
			monthName = "june";
			break;
		case 7:
			monthName = "july";
			break;
		case 8:
			monthName = "august";
			break;
		case 9:
			monthName = "september";
			break;
		case 10:
			monthName = "october";
			break;
		case 11:
			monthName = "november";
			break;
		case 12:
			monthName = "december";
			break;
		}
		return monthName;
		
		
	}
	
	
}

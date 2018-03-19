package tr.com.beinplanner.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class OhbeUtil {

	public static final boolean DEVELOPMENT_MODE=false;
	
	public static final int USER_PRIM_TYPE_ORAN=0;
	public static final int USER_PRIM_TYPE_SABIT=1;
	public static final int USER_PRIM_TYPE_SABIT_ORAN=2;
	
	/* HIZMET DURUMLARI */

	public static String OHBE_OK_FLAG = "processOk";
	public static String OHBE_NOK_FLAG = "processNok";
	
	public static String OHBE_ISLEM_BASARILI = "İşlem Başarılı";
	public static String OHBE_ISLEM_BASARISIZ = "İşlem Başarısız";
	
	public static String OHBE_ISLEM_BASARISIZ_NUMERIC = "-1";
	
	public static String OHBE_STUDIO_PLAN_MEMBER_HAVE_CLASS = "studioPlanMemberHaveClass";
	
	
	
	public static String OHBE_STUDIO_PLAN_TEACHER_HAVE_CLASS = "studioPlanTeacherHaveClass";
	public static String OHBE_STUDIO_PLAN_ALREADY_CREATED = "studioPlanAlreadyCreated";
	public static String OHBE_STUDIO_PLAN_CANNOT_DELETE = "studioPlanCannotDelete";
	public static String OHBE_STUDIO_ALREADY_USED = "studioAlreadyUsed";
	public static String OHBE_STUDIO_PLAN_CANNOT_DELETE_FOR_CONFIRMED_PAYMENT = "studioPlanCannotDeleteForConfirmPayment";
	public static String LICENCE_DB_IP = "";
	

	public static final int ROLTYPE_MANAGER=1;
	public static final int ROLTYPE_TEACHER=2;
	public static final int ROLTYPE_ADMIN=5;
	public static final int ROLTYPE_PERSONEL=4;
	public static final int ROLTYPE_STUDENT=0;
	public static final int ROLTYPE_DEMO=6;
	
	public static final int ACTIVE_PARAMATER=0;
	public static final int PASSIVE_PARAMATER=1;
	
	
	
	public static  String DERS_STATU_SUCCESS="BAŞARILI";
	public static  String DERS_STATU_WARNING="UYARI";
	public static  String DERS_STATU_FAIL="BAŞARISIZ";
	public static  String DERS_STATU_NOTHING="TAMAM";
	
	
	
	
	public static final int DERS_STATU_FAIL_NUMERIC=1;
	public static final int DERS_STATU_SUCCESS_NUMERIC=0;
	
	public static final int CREDIT_CONFIRMED=1;
	public static final int CREDIT_NOT_CONFIRMED=0;
	
	
	public static final int DEVELOPER=1;
	public static final int CLOUD=2;
	public static final int AMAZON=3;
	public static int PLACE=-1;
		
	
	static{PLACE=DEVELOPER;} 
	
	
	
	
		
	private void createRemoteConnetion(){
		//ScreenLicence screenLicence=new ScreenLicence();
		// PRODUCTION ICIN DEGISTIRLMELI
		//screenLicence.createRemoteConnetion();
	}
	
	/////////////////////SMS MESSAGES//////////////////
	
	public static String SMS_UNDEFINED_USER = "undefinedSmsUser";
	public static String UNDEFINED_USER = "undefinedUser";
	
	
	//////////////////////////////////
	
	public static String BODYSYS_RULE_ONE_TEACHER = "1050";
	public static String BODYSYS_RULE_MULTI_TEACHER = "2150";
	public static String BODYSYS_RULE_FIVE_TEACHER = "2550";		
	
	
	public static String BODYSYS_RULE_MEMBER_PERSONEL = "1";		
	public static String BODYSYS_RULE_MEMBER_GRUP = "2";		
	public static String BODYSYS_RULE_MEMBER_FITNESS = "3";		
	public static String BODYSYS_RULE_MEMBER_PERSONEL_GRUP = "12";		
	public static String BODYSYS_RULE_MEMBER_PERSONEL_FITNESS = "13";		
	public static String BODYSYS_RULE_MEMBER_GRUP_FITNESS = "23";		
	public static String BODYSYS_RULE_MEMBER_PERSONEL_GRUP_FITNESS = "123";		
	
	
	
	public static final String PTBOSS_MYPASS="canan3367sagali7611";
	
	
	public static String ROOT_STOCK_FOLDER="";
	public static String ROOT_PROFILE_FOLDER="";
	public static String ROOT_FIRM_FOLDER="";
	public static String ABAX_BID_SCHEMA="";
	public static String ABAX_BID_LOGIN_SCHEMA="";
	public static String ROOT_FIRM_ROOT_URL="";
	
	public static String FIRM_LANG="";
	public static String FIRM_ZONE="";
	
	
	static{
		if(PLACE==DEVELOPER){
			ROOT_STOCK_FOLDER="C:/uploadedFiles/IMG/";
			ROOT_PROFILE_FOLDER="C:/uploadedFiles/IMG/";
			ROOT_FIRM_FOLDER="C:/uploadedFiles/";
			ABAX_BID_SCHEMA="ptboss";
			ABAX_BID_LOGIN_SCHEMA="ptboss";
			ROOT_FIRM_ROOT_URL="C:/uploadedFiles/IMG/FIRM.jpg";
		}else if(PLACE==AMAZON){
			ROOT_STOCK_FOLDER="/mnt/my-data/stock";
			ROOT_PROFILE_FOLDER="/mnt/my-data/";
			ROOT_FIRM_FOLDER="/mnt/my-data/";
			ABAX_BID_SCHEMA="ptboss";
			ABAX_BID_LOGIN_SCHEMA="ptboss";
			ROOT_FIRM_ROOT_URL="/mnt/my-data/FIRM.jpg";
		}
		
	}
	
	
	

	public static String getDateStrByFormat(Date date, String format) {
		if (date == null)
			return "";
		
		java.util.Date thatDay = new java.util.Date(date.getTime());
		/*SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat
				.getInstance();*/
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMMM", new Locale("tr-TR"));//LangUtil.firmam.getFirmLang()));
		
		
		sdf.applyPattern(format);
		return sdf.format(thatDay);
	}
	public static Date getTodayDate() {
		Date date=new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.DATE, cal.get(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		date.setTime(cal.getTimeInMillis());
		
		return date;
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
	
	public static String getRolName(int rolId) {

		String rolName;
		switch (rolId) {
		case 1:
			rolName = "ADMIN";
			break;
		case 2:
			rolName = "YETKILI";
			break;
		case 3:
			rolName = "CALIŞAN";
			break;
		case 4:
			rolName = "VERİ GİRİŞ";
			break;
		default:
			rolName =  "VERİ GİRİŞ";
			break;
		}
		return rolName;
	}
	
	/*
	 * 	public static int WALK_WAIT_CALL=1;
	public static int WALK_CALLED=2;
	public static int WALK_CALLED_NO_REACH=2;
	public static int WALK_WILL_RECALL=3;
	public static int WALK_IS_BEEN_MEMBER=4;
	 */
	
	
	/*LISTE MAKSIMUM UZUNLUGU*/
	public static int OHBE_MOBILE_HIZMET_LISTE_UZUNLUGU=10;
	public static int OHBE_HIZMET_LISTE_UZUNLUGU=50;
	
	
	
	
   
  
	
	/*UTILMethods*/
	public static String getNullValues(String value){
		if(value==null){
			value="";
		}else
		if(value.equals(null)){
			value="";
		}
		return value;
	}
	
	public static String getTimeByFormat(Timestamp date, String format) {
		if (date == null)
			return "";
		java.util.Date thatDay = new java.util.Date(date.getTime());
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat
				.getInstance();
		sdf.applyPattern(format);
		return sdf.format(thatDay);
	}
	
	
	// case 3: // 'd' - DATE
    // case 5: // 'H' - HOUR_OF_DAY:0-based.  eg, 23:59 + 1 hour =>> 00:59
    // case 6: // 'm' - MINUTE
    // case 7: // 's' - SECOND
    // case 8: // 'S' - MILLISECOND
    // case 10: // 'D' - DAY_OF_YEAR
    // case 11: // 'F' - DAY_OF_WEEK_IN_MONTH
    // case 12: // 'w' - WEEK_OF_YEAR
    // case 13: // 'W' - WEEK_OF_MONTH
    // case 16: // 'K' - HOUR: 0-based.  eg, 11PM + 1 hour =>> 0 AM
	
	public static Date getThatDayFormatNullifNull(String dateString,
			String format) throws Exception {
		Date date = new Date();
		if (!dateString.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(dateString);
			
		}
		return date;
	}
	
	public static Date setTime00(Date date)  {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		
		Date rd=new Date(cal.getTimeInMillis());
		return rd;
	}
	
	
	
	
	

	public static Date getDateForNextDate(Date date, int dayDuration) {
		if (date == null)
			return new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(((Date)(date.clone())).getTime());
		
		cal.set(Calendar.DATE, cal.get(Calendar.DATE)+dayDuration);
		
		Date returnDate=new Date(cal.getTimeInMillis());
		
		return returnDate;
	}
	
	public static Date changeHourForDate(Date date, String time) {
		if (date == null)
			return new Date();
		
		String[] timeArr=time.split(":");
		
		int hour=Integer.parseInt(timeArr[0]);
		int minute=Integer.parseInt(timeArr[1]);
		
		
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(((Date)(date.clone())).getTime());
		
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		
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
	
	public static Date getDateForNextHour(Date date, int hour) {
		if (date == null)
			return new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(((Date)(date.clone())).getTime());
		
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+hour);
		
		Date returnDate=new Date(cal.getTimeInMillis());
		
		return returnDate;
	}
	
	public static Date getDateForNextHourMinute(Date date, int hour,int minute) {
		if (date == null)
			return new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(((Date)(date.clone())).getTime());
		
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+hour);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+minute);
		
		Date returnDate=new Date(cal.getTimeInMillis());
		
		
		return returnDate;
	}
	
	public static Date getDateForNextMinute(Date date,int minute) {
		if (date == null)
			return new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(((Date)(date.clone())).getTime());
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+minute);
		
		Date returnDate=new Date(cal.getTimeInMillis());
		
		
		return returnDate;
	}
	
	public static Date sysdateForDate(){
		return new Date(System.currentTimeMillis());
		
	}
	
	public static int getDaySeqByDayNames(Date date){
		SimpleDateFormat date_format = new SimpleDateFormat("EEE");
		String nameOfDay=date_format.format(date);
		int daySeq=1;
		if(nameOfDay.equals("Mon")){
			daySeq=1;
		}else if (nameOfDay.equals("Tue")){
			daySeq=2;
		}else if (nameOfDay.equals("Wed")){
			daySeq=3;
		}else if (nameOfDay.equals("Thr")){
			daySeq=4;
		}else if (nameOfDay.equals("Fri")){
			daySeq=5;
		}else if (nameOfDay.equals("Sat")){
			daySeq=6;
		}else if (nameOfDay.equals("Sun")){
			daySeq=7;
		}
		
		return daySeq;
		
		
	}
	
	
	
	public String getDayNamesTr(int daySeq){
		String dayName="";
		switch (daySeq) {
		case 1:
			dayName = "Pazartesi";
			break;
		case 2:
			dayName = "Salı";
			break;
		case 3:
			dayName = "Çarşamba";
			break;
		case 4:
			dayName = "Perşembe";
			break;
		case 5:
			dayName = "Cuma";
			break;
		case 6:
			dayName = "Cumartesi";
			break;
		case 7:
			dayName = "Pazar";
			break;
		}
		return dayName;
		
		
	}
	
	
	public String getDayNamesEn(int daySeq){
		String dayName="";
		switch (daySeq) {
		case 1:
			dayName = "Monday";
			break;
		case 2:
			dayName = "Tuesday";
			break;
		case 3:
			dayName = "Wednesday";
			break;
		case 4:
			dayName = "Thursday";
			break;
		case 5:
			dayName = "Friday";
			break;
		case 6:
			dayName = "Saturday";
			break;
		case 7:
			dayName = "Sunday";
			break;
		}
		return dayName;
		
		
	}
	
	
	
	public static String getDayNamesStaticTr(Date day){
		Calendar cal=Calendar.getInstance();
		cal.setTime(day);
		
		int daySeq=cal.get(Calendar.DAY_OF_WEEK);
		
		String dayName="";
		switch (daySeq) {
		case 1:
			dayName = "Pazar";
			break;
		case 2:
			dayName = "Pazartesi";
			break;
		case 3:
			dayName = "Salı";
			break;
		case 4:
			dayName = "Çarşamba";
			break;
		case 5:
			dayName = "Perşembe";
			break;
		case 6:
			dayName = "Cuma";
			break;
		case 7:
			dayName = "Cumartesi";
			break;
		
		}
		return dayName;
		
		
	}
	
	public static String getDayNamesStaticEn(Date day){
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
	
	
	public static String getMonthNamesEn(int monthSeq){
		String monthName="";
		switch (monthSeq) {
		case 1:
			monthName = "January";
			break;
		case 2:
			monthName = "February";
			break;
		case 3:
			monthName = "March";
			break;
		case 4:
			monthName = "April";
			break;
		case 5:
			monthName = "May";
			break;
		case 6:
			monthName = "June";
			break;
		case 7:
			monthName = "July";
			break;
		case 8:
			monthName = "Agust";
			break;
		case 9:
			monthName = "September";
			break;
		case 10:
			monthName = "October";
			break;
		case 11:
			monthName = "November";
			break;
		case 12:
			monthName = "December";
			break;
		}
		return monthName;
		
		
	}
	public static String getMonthNamesTr(int monthSeq){
		String monthName="";
		switch (monthSeq) {
		case 1:
			monthName = "OCAK";
			break;
		case 2:
			monthName = "ŞUBAT";
			break;
		case 3:
			monthName = "MART";
			break;
		case 4:
			monthName = "NİSAN";
			break;
		case 5:
			monthName = "MAYIS";
			break;
		case 6:
			monthName = "HAZİRAN";
			break;
		case 7:
			monthName = "TEMMUZ";
			break;
		case 8:
			monthName = "AĞUSTOS";
			break;
		case 9:
			monthName = "EYLÜL";
			break;
		case 10:
			monthName = "EKİM";
			break;
		case 11:
			monthName = "KASIM";
			break;
		case 12:
			monthName = "ARALIK";
			break;
		}
		return monthName;
		
		
	}
	
	public static Date ohbeDefaultDate(){
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR, 2010);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DATE, 10);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		return new Date(cal.getTimeInMillis());
	}
	
	public static Timestamp sysdateForTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static Date getThatDayOneDayBefore(Date date) throws Exception {
		
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.DATE, cal.get(Calendar.DATE)-1);
		date.setTime(cal.getTimeInMillis());
		
		return date;
	}
	
	public static Date getThatDayOneDayAfter(Date date) throws Exception {
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
		date.setTime(cal.getTimeInMillis());
		return date;
	}
	
	public static Date getThatDateForNight(String dateString,String format)  {
		Date date = new Date();
		if(dateString!=null){
		
		try {
			if (!dateString.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				date = sdf.parse(dateString);
				
			}
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		date.setTime(cal.getTimeInMillis());
		return date;
	}
	
	
	
	public int getDaySeq(Date startDate,Date currentDate){
		
		Calendar calStartDate=Calendar.getInstance();
		calStartDate.setTime(startDate);
		calStartDate.set(Calendar.HOUR_OF_DAY, 0);
		calStartDate.set(Calendar.MINUTE, 0);
		calStartDate.set(Calendar.MILLISECOND, 0);
		startDate.setTime(calStartDate.getTimeInMillis());
		
		Calendar calCurrentDate=Calendar.getInstance();
		calCurrentDate.setTime(currentDate);
		calCurrentDate.set(Calendar.HOUR_OF_DAY, 0);
		calCurrentDate.set(Calendar.MINUTE, 0);
		calCurrentDate.set(Calendar.MILLISECOND, 0);
		currentDate.setTime(calCurrentDate.getTimeInMillis());
		
		
		
		
		return  ((int)( (currentDate.getTime()-startDate.getTime())  / (1000 * 60 * 60 * 24))); 
		
	}
	
	public static boolean controlDeleteStudioPlan(Date controlDate,int changeDuration){
		Calendar cal=Calendar.getInstance();
		cal.setTime(controlDate);
		
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.MINUTE,cal.get(Calendar.MINUTE)-changeDuration);
		
		if(calendar.before(cal)){
			return false;
		}else{
			return true;
		}
		
		
	}
	
	
    public static void main(String[] args) throws NoSuchAlgorithmException {
    	getRestrictedMd5Value(FIRM_ONE);
    	/*getRestrictedMd5Value(FIRM_TWO);
    	getRestrictedMd5Value(FIRM_MORE);
    	getRestrictedMd5Value(TEACHER_ONE);
    	getRestrictedMd5Value(TEACHER_TWO_FIVE);
    	getRestrictedMd5Value(TEACHER_FIVE_TEN);
    	getRestrictedMd5Value(TEACHER_MORE);*/
    	
    	
    	
	}
	
    /*
     * 
     * 	original:BİR FİRMA  digested(hex):eec21ebf9875e158c52bfdcabc0d90a4
		original:İKİ FİRMA  digested(hex):e445bc40ef519c3727b73d5a722965a6
		original:ÇOK FİRMA  digested(hex):b98c99eb0e161604d8a9ddf16771c61f
		original:BİR EĞİTMEN  digested(hex):4f6fd0b3b234cdf462b5ba97e647dce2
		original:İKİ-BEŞ EĞİTMEN   digested(hex):bf3ba57d6c22e17f03b87e052f7a1658
		original:BEŞ-ON EĞİTMEN  digested(hex):f7a71fc0cbd8fb41796946c056d250ba
		original:ÇOK EĞİTMEN  digested(hex):923fa5c6500ef9e356a83c4c545fae84
     * 
     */
    
	public static String FIRM_ONE="BİR FİRMA";
	public static String FIRM_TWO="İKİ FİRMA";
	public static String FIRM_MORE="ÇOK FİRMA";
	
	public static String TEACHER_ONE="BİR EĞİTMEN";
	public static String TEACHER_TWO_FIVE="İKİ-BEŞ EĞİTMEN ";
	public static String TEACHER_FIVE_TEN="BEŞ-ON EĞİTMEN";
	public static String TEACHER_MORE="ÇOK EĞİTMEN";
	
	
	public static int FIRM_ONE_INT=1;
	public static int FIRM_TWO_INT=2;
	public static int FIRM_MORE_INT=0;
	
	public static int TEACHER_ONE_INT=1;
	public static int TEACHER_TWO_FIVE_INT=5;
	public static int TEACHER_FIVE_TEN_INT=10;
	public static int TEACHER_MORE_INT=0;
	
	
	
	
	
	public static String getRestrictedMd5Value(String original)  {
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		md.update(original.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		////System.out.println("original:" + original+"  digested(hex):"+sb.toString());
		return sb.toString().trim();
	}
	
	
}

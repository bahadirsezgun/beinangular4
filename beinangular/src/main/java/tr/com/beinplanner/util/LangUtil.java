package tr.com.beinplanner.util;

public class LangUtil {

	
	public static final String SESSION_USER_NOT_FOUND  = "SESSION_EXPIRED";
	public static final String USER_NOT_FOUND  = "USER_NOT_FOUND";
	public static final String LOGIN_SUCCESS  = "LOGIN_SUCCESS";
	
	public static String LANG_AD="AD";
	public static String LANG_SOYAD="SOYAD";
	public static String LANG_CEP_TELEFON="CEP TELEFON";
	public static String LANG_IS_TELEFON="IS TELEFON";
	public static String LANG_EMAIL="EMAIL";
	public static String LANG_TC_KIMLIK="TC KIMLIK";
	public static String LANG_DOGUM_TARIH="DOGUM TARIH";
	public static String LANG_SUBE="SUBE";
	
	public static String LANG_PACKET_SALE_AUTOMATIC="Otomatik paket patışı yapılmıştır";
	public static String LANG_PACKET_SALE_SUBJECT="Paket Satışı";
	public static String LANG_BRANCH="ŞUBE ";
	
	public static String LANG_CANCEL="İPTAL ";
	
	
	
	
	public static void setTranslateEn(){
		LANG_AD="NAME";
		LANG_SOYAD="SURNAME";
		LANG_CEP_TELEFON="GSM";
		LANG_IS_TELEFON="WORK PHONE";
		LANG_EMAIL="EMAIL";
		LANG_TC_KIMLIK="SSN";
		LANG_DOGUM_TARIH="BIRTHDAY";
		LANG_SUBE="BRANCH";
		LANG_PACKET_SALE_AUTOMATIC="Packet sold created by BEINPlanner";
		LANG_PACKET_SALE_SUBJECT="Packet Sale";
		LANG_BRANCH="BRANCH ";
		LANG_CANCEL="CANCELED ";
		
	}
	
	public static void setTranslateTr(){
		LANG_AD="AD";
		LANG_SOYAD="SOYAD";
		LANG_CEP_TELEFON="CEP TELEFON";
		LANG_IS_TELEFON="IS TELEFON";
		LANG_EMAIL="EMAIL";
		LANG_TC_KIMLIK="TC KIMLIK";
		LANG_DOGUM_TARIH="DOGUM TARIH";
		LANG_SUBE="SUBE";
		LANG_PACKET_SALE_AUTOMATIC="Otomatik paket patışı yapılmıştır";
		LANG_PACKET_SALE_SUBJECT="Paket Satışı";
		LANG_BRANCH="ŞUBE ";
		LANG_CANCEL="İPTAL ";
	}
}

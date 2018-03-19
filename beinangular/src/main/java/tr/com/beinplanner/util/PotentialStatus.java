package tr.com.beinplanner.util;

public class PotentialStatus {

	
	public static final int POTENTIAL_NEW=0;
	public static final int POTENTIAL_CALLED=1;
	public static final int POTENTIAL_FAIL=2;
	public static final int POTENTIAL_TO_REAL=3;
	
	public static final String POTENTIAL_NEW_STR="newPotential";
	public static final String POTENTIAL_CALLED_STR="called";
	public static final String POTENTIAL_FAIL_STR="noWantToMember";
	public static final String POTENTIAL_TO_REAL_STR="beMember";
	
	
	public static String POTENTIAL_STATU(int ps){
		switch (ps) {
		case POTENTIAL_NEW:
			return POTENTIAL_NEW_STR;
		case POTENTIAL_CALLED:
			return POTENTIAL_CALLED_STR;
		case POTENTIAL_FAIL:
			return POTENTIAL_FAIL_STR;
		case POTENTIAL_TO_REAL:
			return POTENTIAL_TO_REAL_STR;
			
         
		default:
			return POTENTIAL_NEW_STR;
		}
	}
	
}

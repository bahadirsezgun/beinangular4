package tr.com.beinplanner.util;

public class UserTypes {

	public static final int USER_TYPE_MEMBER_INT  = 1;
	public static final int USER_TYPE_STAFF_INT = 2;
	public static final int USER_TYPE_SCHEDULAR_STAFF_INT  = 3;
	public static final int USER_TYPE_MANAGER_INT = 4;
	public static final int USER_TYPE_SUPER_MANAGER_INT = 5;
	public static final int USER_TYPE_ADMIN_INT = 6;
	public static final int USER_TYPE_PASIVE_INT = 7;
	
	
	public static final String USER_TYPE_MEMBER  = "userMember";
	public static final String USER_TYPE_STAFF = "userStaff";
	public static final String USER_TYPE_SCHEDULAR_STAFF  = "userSchedulerStaff";
	public static final String USER_TYPE_MANAGER = "userManager";
	public static final String USER_TYPE_SUPER_MANAGER = "userSuperManager";
	public static final String USER_TYPE_ADMIN = "userAdmin";
	public static final String USER_TYPE_PASIVE = "userPassive";
	
	public static final int GENDER_MALE=0;
	public static final int GENDER_FEMALE=1;
	
	
	
	public static int getUserTypeInt(String userType){
		
		if(userType.equals("userMember")){
			return 1;
		}else if(userType.equals("userStaff")){
			return 2;
		}else if(userType.equals("userSchedulerStaff")){
			return 3;
		}else if(userType.equals("userManager")){
			return 4;
		}else if(userType.equals("userSuperManager")){
			return 5;
		}else if(userType.equals("userAdmin")){
			return 6;
		}else if(userType.equals("userPassive")){
			return 7;
		}
		
		return 1;
		
	}
	

 
	
	
	/*
	
private int getUserCompletePercent(User user){
    	
    	int percent=100;
    	if(user.getStateId()>0 
    			&& user.getCityId()>0 
    			&& !user.getUserGsm().equals("")
    			&& !user.getUserAddress().equals("")
    			&& !user.getUserComment().equals("")
    			&& user.getUserGender()>0
    			){
    		percent=100;
    	}else if(!user.getUserGsm().equals("")
    			&& !user.getUserAddress().equals("")
    			&& !user.getUserComment().equals("")
    			&& user.getUserGender()>0
    			){
    		percent=80;
    	}else if(!user.getUserAddress().equals("")
    			&& !user.getUserComment().equals("")
    			&& user.getUserGender()>0
    			){
    		percent=60;
    	}else if(!user.getUserComment().equals("")
    			&& user.getUserGender()>0
    			){
    		percent=50;
    	}else if(user.getProfileUrl()!=null){
    		percent=50;
    	}
    	else if(!user.getUserComment().equals("")
    			&& user.getUserGender()>0
    			){
    		percent=40;
    	}else if( user.getUserGender()>0
    			){
    		percent=20;
    	}else{
    		percent=10;
    	}
    	
    	return percent;
    }
	*/

	
	
	
}

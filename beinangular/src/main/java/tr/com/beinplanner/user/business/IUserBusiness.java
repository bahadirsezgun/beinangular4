package tr.com.beinplanner.user.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.user.iuser.ProcessAdmin;
import tr.com.beinplanner.user.iuser.ProcessInterface;
import tr.com.beinplanner.user.iuser.ProcessManager;
import tr.com.beinplanner.user.iuser.ProcessMember;
import tr.com.beinplanner.user.iuser.ProcessSchedulerStaff;
import tr.com.beinplanner.user.iuser.ProcessStaff;
import tr.com.beinplanner.user.iuser.ProcessSuperManager;
import tr.com.beinplanner.util.UserTypes;

@Service
@Qualifier("iUserBusiness")
public class IUserBusiness {

	@Autowired
	ProcessAdmin processAdmin;
	
	@Autowired
	ProcessManager processManager;
	
	@Autowired
	ProcessMember processMember;
	
	@Autowired
	ProcessSchedulerStaff processSchedulerStaff;
	
	@Autowired
	ProcessStaff processStaff;
	
	@Autowired
	ProcessSuperManager processSuperManager;
	
	
	public String generateUserName(User user){
		String userName=(user.getUserName().substring(0,1)).toLowerCase()+user.getUserSurname().toLowerCase();
		
		userName=userName.replaceAll("ö", "o");
		userName=userName.replaceAll("ı", "i");
		userName=userName.replaceAll("ü", "u");
		userName=userName.replaceAll("ş", "s");
		userName=userName.replaceAll("ğ", "g");
		userName=userName.replaceAll("ç", "c");
		
		
		userName=userName.replaceAll("Ö", "o");
		userName=userName.replaceAll("İ", "i");
		userName=userName.replaceAll("Ü", "u");
		userName=userName.replaceAll("Ş", "s");
		userName=userName.replaceAll("Ğ", "g");
		userName=userName.replaceAll("Ç", "c");
		
		return userName;
	}
	
	public User setUserDefaults(User user) {
		
		if(user.getUserAddress()==null) {
			user.setUserAddress("");
		}
		if(user.getUserSsn()==null) {
			user.setUserSsn("");
		}
		if(user.getUserComment()==null) {
			user.setUserComment("");
		}
		if(user.getUserPhone()==null) {
			user.setUserPhone("");
		}
		
		if(user.getUserBirthday()==null) {
			user.setUserBirthday(new Date());
		}
		
		if(user.getUserEmail()==null) {
			user.setUserEmail("");
		}
		return user;
	}
	
	
	
	public HmiResultObj canUserDelete(User user) {
		ProcessInterface processInterface=null;
		
		if(user.getUserType()== UserTypes.USER_TYPE_ADMIN_INT){
			processInterface=processAdmin;
		}else if(user.getUserType()==UserTypes.USER_TYPE_MANAGER_INT){
			processInterface=processManager;
  		}else if(user.getUserType()==UserTypes.USER_TYPE_MEMBER_INT){
  			processInterface=processMember;
   		}else if(user.getUserType()==UserTypes.USER_TYPE_SCHEDULAR_STAFF_INT){
   			processInterface=processSchedulerStaff;
   		}else if(user.getUserType()==UserTypes.USER_TYPE_STAFF_INT){
   			processInterface=processStaff;
   		}else if(user.getUserType()==UserTypes.USER_TYPE_SUPER_MANAGER_INT){
   			processInterface=processSuperManager;
   		}
		
		return processInterface.canUserDelete(user);
	}
	
	
}

package tr.com.beinplanner.user.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.dashboard.businessEntity.ActiveMember;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.business.IUserBusiness;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.user.repository.UserRepository;
import tr.com.beinplanner.util.OhbeUtil;
import tr.com.beinplanner.util.ResultStatuObj;
import tr.com.beinplanner.util.UserTypes;

@Service
@Qualifier("userService")
public class UserService  {

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	IUserBusiness iUserBusiness; 
	
	public synchronized HmiResultObj create(User user){
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		user=iUserBusiness.setUserDefaults(user);
		
		User userIn=null;
		if(user.getUserId()==0){
			if(user.getUserEmail().equals("")){
				user.setUserEmail(iUserBusiness.generateUserName(user));
			}
			if(findUserByUserEmail(user.getUserEmail()).isPresent()) {
				hmiResultObj.setResultMessage("userFoundWithThisEmail");
		    	hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
		    	return hmiResultObj;
		    }
		}else{
			userIn=findUserById(user.getUserId());
		}
		
		if(userIn!=null){
			user.setUserId(userIn.getUserId());
			//user.setProfileUrl(userIn.getProfileUrl());
			//user.setUrlType(userIn.getUrlType());
			user.setPassword(userIn.getPassword());
			user=userRepository.save(user);
			hmiResultObj.setResultObj(user);
			
			return hmiResultObj;
		}else{
			if(user.getUserType()==UserTypes.USER_TYPE_MEMBER_INT || user.getUserType()==UserTypes.USER_TYPE_ADMIN_INT){
				int randomPIN = (int)(Math.random()*9000)+1000;
				String password = ""+randomPIN;
				user.setPassword(password);
				user=userRepository.save(user);
				hmiResultObj.setResultObj(user);
			}else {
				user.setPassword("0000");
				user=userRepository.save(user);
				hmiResultObj.setResultObj(user);
			}
			return hmiResultObj;
		}
		
	}
	
	
	public synchronized HmiResultObj delete(long userId){
		
		User userInDb=userRepository.findOne(userId);
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj=iUserBusiness.canUserDelete(userInDb);
		
		if(hmiResultObj.getResultStatu().equals(ResultStatuObj.RESULT_STATU_SUCCESS_STR)) {
		    userRepository.delete(userInDb);	
		}
	
		return hmiResultObj;
	}
	
	
	public synchronized Optional<User> findUserByUserEmail(String userEmail){
		return userRepository.findByUserEmail(userEmail);
	}
	
	public synchronized User findUserById(long userId){
		return userRepository.findOne(userId);
	}
	
	public synchronized List<User> findAllStaffByFirmId(int firmId){
		return userRepository.findAllStaffByFirmId(firmId);
	}
	
	public synchronized List<User> findAllByFirmId(int firmId){
		return userRepository.findAllByFirmId(firmId);
	}
	public synchronized List<User> findAllByFirmIdAndUserType(int firmId,int userType){
		return userRepository.findAllByFirmIdAndUserType(firmId, userType);
	}
	
	
	public synchronized User findUserByPersonalPayId(long payId){
		return userRepository.findUserByPersonalPayId(payId);
	}
	
	public synchronized User findUserByClassPayId(long payId){
		return userRepository.findUserByClassPayId(payId);
	}
	
	public synchronized User findUserByMembershipPayId(long payId){
		return userRepository.findUserByMembershipPayId(payId);
	}
	
	public synchronized List<User> findByUsernameAndUsersurname(String userName,String userSurname,int firmId,int userType){
		return userRepository.findByUserNameStartingWithAndUserSurnameStartingWithAndFirmIdAndUserType(userName, userSurname, firmId,userType);
	}
	
	
	
	public synchronized List<User> finsSpecialDateOfUsers(int firmId){
		Date startDate=OhbeUtil.getDateForNextDate(new Date(), -1*5);
		Date endDate=OhbeUtil.getDateForNextDate(new Date(), 2);
		List<User>	userTblsHappy=new ArrayList<User>();
		try {
			List<User>	userTbls=userRepository.findAllByFirmId(firmId);
			userTbls.forEach(users->{
				Date birthDate=users.getUserBirthday();
	            if(birthDate!=null){
	            	Calendar calToday=Calendar.getInstance();
	            	Calendar cal=Calendar.getInstance();
	            	cal.setTimeInMillis(birthDate.getTime());
	            	
	            	cal.set(Calendar.YEAR, calToday.get(Calendar.YEAR));
	            	
	            	Date newBirth=new Date(cal.getTimeInMillis());
	            	if(newBirth.after(startDate) && newBirth.before(endDate)){
	            		userTblsHappy.add(users);
	            	}
	            }
			});
			return userTblsHappy;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public synchronized ActiveMember findActiveMemberCount(int firmId) {
		List<User> userInMemberships=userRepository.findActiveMemberInMembershipPlanning(firmId);
		List<User> userInClasss=userRepository.findActiveMemberInClassPlanning(firmId);
		List<User> userInPersonals=userRepository.findActiveMemberInPersonalPlanning(firmId);
		
		ActiveMember activeMember=new ActiveMember();
		activeMember.setActiveMemberCCount(userInClasss.size());
		activeMember.setActiveMemberPCount(userInPersonals.size());
		activeMember.setActiveMemberMCount(userInMemberships.size());
		
		activeMember.setActiveMemberCount(activeMember.getActiveMemberCCount()+activeMember.getActiveMemberMCount()+activeMember.getActiveMemberPCount());;
		return activeMember;
	}
	
}

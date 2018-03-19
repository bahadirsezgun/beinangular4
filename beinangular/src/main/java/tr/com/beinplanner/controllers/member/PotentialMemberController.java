package tr.com.beinplanner.controllers.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.business.IUserBusiness;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.user.dao.UserPotential;
import tr.com.beinplanner.user.service.PotentialUserService;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.PotentialStatus;
import tr.com.beinplanner.util.ResultStatuObj;
import tr.com.beinplanner.util.StatuTypes;
@RestController
@RequestMapping("/bein/potential")
public class PotentialMemberController {

	@Autowired
	PotentialUserService potentialUserService;
	
	@Autowired
	LoginSession loginSession;
	
	@Autowired
	UserService userService;
	
	@Autowired
	IUserBusiness iUserBusiness; 
	
	@PostMapping(value="/findByName")
	public @ResponseBody List<UserPotential> findByName(@RequestBody UserPotential userPotential){
		List<UserPotential> userPotentials=potentialUserService.findByUsernameAndUsersurname(userPotential.getUserName()+"%", userPotential.getUserSurname()+"%", loginSession.getUser().getFirmId());
		userPotentials.forEach(pu->{
			pu.setpStatuStr(PotentialStatus.POTENTIAL_STATU(pu.getpStatu()));
		});
		return userPotentials;
	}
	
	
	@PostMapping(value="/findById/{userId}")
	public @ResponseBody UserPotential findUserPotentialById(@PathVariable long userId){
		
		UserPotential pu=potentialUserService.findById(userId);
		pu.setpStatuStr(PotentialStatus.POTENTIAL_STATU(pu.getpStatu()));
		return pu;
	}
	
	
	@PostMapping(value="/create")
	public @ResponseBody HmiResultObj create(@RequestBody UserPotential userPotential){
		if(userPotential.getUserId()!=0) {
			UserPotential userPotentialDb=potentialUserService.findById(userPotential.getUserId());
			if(userPotentialDb!=null) {
				if(userPotentialDb.getpStatu()==PotentialStatus.POTENTIAL_TO_REAL) {
					userPotential.setpStatu(PotentialStatus.POTENTIAL_TO_REAL);
				}
			}
		}
		
		userPotential.setFirmId(loginSession.getUser().getFirmId());
		return potentialUserService.createPotentialUser(userPotential);
	}
	
	@PostMapping(value="/convertToMember")
	public @ResponseBody HmiResultObj convertToMember(@RequestBody UserPotential userPotential){
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		
			User user=new User();
			
			user=iUserBusiness.setUserDefaults(user);
			user.setUserName(userPotential.getUserName());
			user.setUserComment(userPotential.getpComment());
			user.setUserEmail(userPotential.getUserEmail());
			user.setUserGsm(userPotential.getUserGsm());
			user.setUserSurname(userPotential.getUserSurname());
			user.setFirmId(loginSession.getUser().getFirmId());
			user.setStaffId(userPotential.getStaffId());
			user.setUserGender(userPotential.getUserGender());
			
			
			hmiResultObj= userService.create(user);
		
		
		
		
		userPotential.setFirmId(loginSession.getUser().getFirmId());
		userPotential.setpStatu(PotentialStatus.POTENTIAL_TO_REAL);
		userPotential=(UserPotential)potentialUserService.createPotentialUser(userPotential).getResultObj();
		
		hmiResultObj.setResultObj(userPotential);
		
		return hmiResultObj;
	}
}

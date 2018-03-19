package tr.com.beinplanner.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.dao.UserPotential;
import tr.com.beinplanner.user.repository.PotentialUserRepository;
import tr.com.beinplanner.user.repository.UserRepository;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("potentialUserService")
public class PotentialUserService {

	@Autowired
	PotentialUserRepository potentialUserRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public synchronized HmiResultObj createPotentialUser(UserPotential userPotential){
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		
		
		try {
			userPotential=potentialUserRepository.save(userPotential);
		} catch (Exception e) {
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			
		}
		hmiResultObj.setResultObj(userPotential);
		
		return hmiResultObj;
	}
		
	
	public synchronized List<UserPotential> findByUsernameAndUsersurname(String userName,String userSurname,int firmId){
		
		List<UserPotential> potentials=potentialUserRepository.findByUserNameStartingWithAndUserSurnameStartingWithAndFirmId(userName, userSurname, firmId);
		potentials.forEach(pu->{
			if(pu.getStaffId()>0) {
				pu.setStaff(userRepository.findOne(pu.getStaffId()));
			}
		});
		return potentials;
	}
	
	public synchronized UserPotential findById(long id){
		
		UserPotential potential=potentialUserRepository.findOne(id);
		if(potential.getStaffId()>0) {
			potential.setStaff(userRepository.findOne(potential.getStaffId()));
		}
		return potential;
	}
}

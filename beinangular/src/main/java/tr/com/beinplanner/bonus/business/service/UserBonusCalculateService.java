package tr.com.beinplanner.bonus.business.service;

import java.util.Date;

import tr.com.beinplanner.bonus.businessDao.UserBonusObj;

public interface UserBonusCalculateService {

	
	public UserBonusObj findStaffBonusObj(long schStaffId,Date startDate,Date endDate,int firmId);
	
	
}

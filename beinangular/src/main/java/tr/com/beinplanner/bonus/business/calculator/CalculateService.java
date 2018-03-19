package tr.com.beinplanner.bonus.business.calculator;

import java.util.List;

import tr.com.beinplanner.bonus.businessDao.UserBonusObj;
import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;

public interface CalculateService {

	public UserBonusObj calculateIt(List<ScheduleTimePlan> scheduleTimePlans,long staffId,int firmId);
	
}

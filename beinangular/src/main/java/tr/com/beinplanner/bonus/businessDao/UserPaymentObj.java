package tr.com.beinplanner.bonus.businessDao;

import java.util.List;

import tr.com.beinplanner.schedule.dao.ScheduleFactory;

public class UserPaymentObj {

	List<ScheduleFactory> scheduleFactories;

	public List<ScheduleFactory> getScheduleFactories() {
		return scheduleFactories;
	}

	public void setScheduleFactories(List<ScheduleFactory> scheduleFactories) {
		this.scheduleFactories = scheduleFactories;
	}
	
	
	
}

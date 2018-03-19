package tr.com.beinplanner.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.schedule.dao.ScheduleUsersClassPlan;
import tr.com.beinplanner.schedule.dao.ScheduleUsersPersonalPlan;

@Repository
public interface ScheduleUsersPersonalPlanRepository   extends CrudRepository<ScheduleUsersPersonalPlan, Long>{

	public List<ScheduleUsersPersonalPlan> findBySchtId(long schtId);
	
	public List<ScheduleUsersPersonalPlan> findBySaleId(long saleId);

	
	@Query(value="SELECT SUM(0) SUPP_ID,SUM(0) SCHT_ID,b.USER_ID,b.SALE_ID" + 
			"				  FROM 	schedule_time_plan a,  " + 
			"						schedule_users_personal_plan b, " + 
			"				        schedule_plan c  " + 
			"					 WHERE a.SCHT_ID=b.SCHT_ID " + 
			"					   AND c.SCH_ID=a.SCH_ID  " + 
			"					   AND c.SCH_ID=:schId  " + 
			"					 GROUP BY b.USER_ID,b.SALE_ID ",nativeQuery=true)
	public List<ScheduleUsersPersonalPlan> findScheduleUsersPlanBySchId(@Param("schId") long schId);
}

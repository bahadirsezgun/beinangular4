package tr.com.beinplanner.schedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;

@Repository
public interface ScheduleTimePlanRepository  extends CrudRepository<ScheduleTimePlan, Long>{

	public List<ScheduleTimePlan> findBySchId(long schId);
	
	@Query(value="SELECT a.SCHT_ID,d.PROG_ID,d.PROG_NAME,c.SCH_COUNT,a.STATUTP ,a.PLAN_START_DATE,a.PLAN_END_DATE,a.SCH_ID,a.SCHT_STAFF_ID,a.TP_COMMENT" + 
			"				  FROM 	schedule_time_plan a,  " + 
			"						schedule_users_class_plan b, " + 
			"				        schedule_plan c, " + 
			"				        program_class d  " + 
			"					 WHERE a.SCHT_STAFF_ID=:schStaffId " + 
			"					   AND a.SCHT_ID=b.SCHT_ID " + 
			"					   AND a.PLAN_START_DATE>=:startDate " + 
			"					   AND a.PLAN_START_DATE<:endDate " + 
			"					   AND c.SCH_ID=a.SCH_ID  " + 
			"					   AND c.PROG_ID=d.PROG_ID  " + 
			"					 GROUP BY a.SCHT_ID,d.PROG_ID,d.PROG_NAME,c.SCH_COUNT,a.STATUTP,a.PLAN_START_DATE,a.PLAN_END_DATE,a.SCHT_STAFF_ID,a.TP_COMMENT  " + 
			"					 ORDER BY a.PLAN_START_DATE ",nativeQuery=true)
	public List<ScheduleTimePlan> findScheduleTimePlansClassPlanByDatesForStaff(@Param("schStaffId") long schStaffId,@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	

	@Query(value="SELECT a.SCHT_ID,d.PROG_ID,d.PROG_NAME,c.SCH_COUNT,a.STATUTP,a.PLAN_START_DATE,a.PLAN_END_DATE,a.SCH_ID,a.SCHT_STAFF_ID,a.TP_COMMENT" + 
			"				  FROM 	schedule_time_plan a,  " + 
			"						schedule_users_personal_plan b, " + 
			"				        schedule_plan c, " + 
			"				        program_personal d  " + 
			"					 WHERE a.SCHT_STAFF_ID=:schStaffId " + 
			"					   AND a.SCHT_ID=b.SCHT_ID " + 
			"					   AND a.PLAN_START_DATE>=:startDate " + 
			"					   AND a.PLAN_START_DATE<:endDate " + 
			"					   AND c.SCH_ID=a.SCH_ID  " + 
			"					   AND c.PROG_ID=d.PROG_ID  " + 
			"					 GROUP BY a.SCHT_ID,d.PROG_ID,d.PROG_NAME,c.SCH_COUNT,a.STATUTP,a.PLAN_START_DATE,a.PLAN_END_DATE,a.SCHT_STAFF_ID,a.TP_COMMENT  " + 
			"					 ORDER BY a.PLAN_START_DATE ",nativeQuery=true)
	public List<ScheduleTimePlan> findScheduleTimePlansPersonalPlanByDatesForStaff(@Param("schStaffId") long schStaffId,@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	
	
	
	@Query(value="SELECT a.* "
			+ "  FROM schedule_time_plan a " + 
			"						 WHERE a.PLAN_START_DATE>=:startDate " + 
			"						and a.PLAN_START_DATE<:endDate AND a.SCHT_ID IN "
			+ "                      (SELECT SCHT_ID FROM schedule_users_class_plan "
			+ "                         WHERE USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId))",nativeQuery=true)
	public List<ScheduleTimePlan> findClassesForClass(@Param ("startDate") Date startDate,@Param ("endDate") Date endDate,@Param ("firmId") int firmId );

	@Query(value="SELECT a.* "
			+ "  FROM schedule_time_plan a " + 
			"						 WHERE a.PLAN_START_DATE>=:startDate " + 
			"						and a.PLAN_START_DATE<:endDate AND a.SCHT_ID IN "
			+ "                      (SELECT SCHT_ID FROM schedule_users_personal_plan "
			+ "                         WHERE USER_ID IN (SELECT USER_ID FROM user WHERE FIRM_ID=:firmId))",nativeQuery=true)
	public List<ScheduleTimePlan> findClassesForPersonal(@Param ("startDate") Date startDate,@Param ("endDate") Date endDate,@Param ("firmId") int firmId );

	
	
	@Query(value="SELECT a.* "
			+ "  FROM schedule_time_plan a " + 
			"						 WHERE a.SCHT_STAFF_ID=:staffId LIMIT 1 ",nativeQuery=true)
	public ScheduleTimePlan findTimePlanToControlDeleteSchStaff(@Param ("staffId") long staffId);
		
	
	@Query(value="SELECT a.* "
			+ "  FROM schedule_time_plan a "
			+ "      ,schedule_users_class_plan b " + 
			"		 WHERE a.SCHT_STAFF_ID=:staffId "
			+ "      AND a.PLAN_START_DATE=:startDate"
			+ "      AND a.SCHT_ID=b.SCHT_ID  "
			+ "      LIMIT 1 ",nativeQuery=true)
	public ScheduleTimePlan findScheduleTimePlanClassPlanByDateTimeForStaff(@Param ("staffId") long staffId,@Param("startDate") Date startDate);
		
	
	@Query(value="SELECT a.* "
			+ "  FROM schedule_time_plan a "
			+ "  ,schedule_users_personal_plan b" + 
			"		 WHERE a.SCHT_STAFF_ID=:staffId "
			+ "      AND a.PLAN_START_DATE=:startDate"
			+ "       AND a.SCHT_ID=b.SCHT_ID   "
			+ "      LIMIT 1 ",nativeQuery=true)
	public ScheduleTimePlan findScheduleTimePlanPersonalPlanByDateTimeForStaff(@Param ("staffId") long staffId,@Param("startDate") Date startDate);
		
	
	@Query(value="SELECT a.* "
			+ "  FROM schedule_time_plan a "
			+ "  WHERE a.SCHT_STAFF_ID=:staffId "
			+ "      AND a.PLAN_START_DATE=:startDate    "
			+ "      LIMIT 1 ",nativeQuery=true)
	public ScheduleTimePlan findScheduleTimePlanByDateTimeForStaff(@Param ("staffId") long staffId,@Param("startDate") Date startDate);
	
	
	
	@Query(value="SELECT a.* "
			+ "  FROM schedule_time_plan a "
			+ "      ,schedule_users_personal_plan b " + 
			"		 WHERE b.SALE_ID=:saleId "
			+ "      AND a.SCHT_ID=b.SCHT_ID  ORDER BY a.PLAN_START_DATE DESC "
			+ "      LIMIT 1 ",nativeQuery=true)
	public ScheduleTimePlan findScheduleTimePlanPersonalBySaleId(@Param ("saleId") long saleId);
		
	@Query(value="SELECT a.* "
			+ "  FROM schedule_time_plan a "
			+ "      ,schedule_users_class_plan b " + 
			"		 WHERE b.SALE_ID=:saleId "
			+ "      AND a.SCHT_ID=b.SCHT_ID  ORDER BY a.PLAN_START_DATE DESC "
			+ "      LIMIT 1 ",nativeQuery=true)
	public ScheduleTimePlan findScheduleTimePlanClassBySaleId(@Param ("saleId") long saleId);
		
	
	
}

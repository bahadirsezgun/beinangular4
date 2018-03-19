package tr.com.beinplanner.controllers.booking.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.program.service.ProgramService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.businessEntity.ScheduleMembershipFreezeObj;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipPlan;
import tr.com.beinplanner.schedule.service.ScheduleMembershipService;

@RestController
@RequestMapping("/bein/membership/booking")
public class MembershipBookingController {

	
	@Autowired
	LoginSession loginSession;
	
	@Autowired
	ScheduleMembershipService scheduleMembershipService;
	
	
	
	@Autowired
	ProgramService programService;
	
	@PostMapping(value="/createPlan")
	public HmiResultObj createPlan(@RequestBody ScheduleMembershipPlan scheduleMembershipPlan) {
	    return scheduleMembershipService.createPlan(scheduleMembershipPlan);
	}
	
	@PostMapping(value="/findScheduleFactoryPlanBySaleId/{saleId}")
	public ScheduleFactory findScheduleFactoryPlanBySaleId(@PathVariable("saleId") long saleId) {
		return scheduleMembershipService.findScheduleFactoryPlanBySaleId(saleId);
	}
	
	
	
	@PostMapping(value="/freezeSchedule") 
	public @ResponseBody HmiResultObj freezeSchedule(@RequestBody ScheduleMembershipFreezeObj smp){
		return scheduleMembershipService.freezeSchedule(smp);
	}
	
	
	@PostMapping(value="/unFreezeSchedule/{smtpId}/{smpId}") 
	public @ResponseBody HmiResultObj unFreezeSchedule(@PathVariable("smtpId") long smtpId,@PathVariable("smpId") long smpId) {
		return scheduleMembershipService.unFreezeSchedule(smtpId, smpId);
	}
	
	
}

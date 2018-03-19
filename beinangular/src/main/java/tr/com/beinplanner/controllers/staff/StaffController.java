package tr.com.beinplanner.controllers.staff;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.mail.MailObj;
import tr.com.beinplanner.mail.MailSenderThread;
import tr.com.beinplanner.program.dao.ProgramClass;
import tr.com.beinplanner.program.dao.ProgramFactory;
import tr.com.beinplanner.program.dao.ProgramPersonal;
import tr.com.beinplanner.program.service.ProgramService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.businessEntity.ScheduleSearchObj;
import tr.com.beinplanner.schedule.businessEntity.StaffClassPlans;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.dao.SchedulePlan;
import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;
import tr.com.beinplanner.schedule.service.IScheduleService;
import tr.com.beinplanner.schedule.service.ScheduleClassService;
import tr.com.beinplanner.schedule.service.SchedulePersonalService;
import tr.com.beinplanner.schedule.service.ScheduleService;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.DateTimeUtil;
import tr.com.beinplanner.util.OhbeUtil;
import tr.com.beinplanner.util.ProgramTypes;
import tr.com.beinplanner.util.ResultStatuObj;
import tr.com.beinplanner.util.UserTypes;

@RestController
@RequestMapping("/bein/staff")
public class StaffController {

	@Autowired
	UserService userService;
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	ProgramService programService;
	
	@Autowired
	LoginSession loginSession;
	
	@Autowired
	ScheduleClassService scheduleClassService;
	
	@Autowired
	SchedulePersonalService schedulePersonalService;
	
	IScheduleService iScheduleService;
	
	
	@PostMapping(value="/getSchStaffPlan") 
	public @ResponseBody List<StaffClassPlans> getSchStaffPlanByMonth(@RequestBody ScheduleSearchObj scheduleSearchObj) {
	
		
	
		String payDateStr="";
		
		Date startDate=new Date();
		Date endDate=new Date();
		
		
		if(scheduleSearchObj.getQueryType()==1) {
			String monthStr=""+scheduleSearchObj.getMonth();
			if(scheduleSearchObj.getMonth()<10)
				 monthStr="0"+scheduleSearchObj.getMonth();
			
			 payDateStr="01/"+monthStr+"/"+scheduleSearchObj.getYear()+" 00:00";
			 startDate=OhbeUtil.getThatDayFormatNotNull(payDateStr, "dd/MM/yyyy HH:mm");
			 endDate=OhbeUtil.getDateForNextMonth(startDate, 1);
				
		}else {
			 startDate=OhbeUtil.getThatDayFormatNotNull(scheduleSearchObj.getStartDateStr(), "dd/MM/yyyy HH:mm");
			 endDate=OhbeUtil.getThatDayFormatNotNull(scheduleSearchObj.getEndDateStr(), "dd/MM/yyyy HH:mm");
			
		}
		
		List<StaffClassPlans> staffClassPlans=new ArrayList<StaffClassPlans>();
		List<ScheduleTimePlan> scheduleTimePlanObjs=null;
		
		if(scheduleSearchObj.getTypeOfSchedule()==ProgramTypes.PROGRAM_PERSONAL){
			iScheduleService=schedulePersonalService;
		}else if(scheduleSearchObj.getTypeOfSchedule()==ProgramTypes.PROGRAM_CLASS){
			iScheduleService=scheduleClassService;
		}
		
		scheduleTimePlanObjs=iScheduleService.findScheduleTimePlansPlanByDatesForStaff(scheduleSearchObj.getStaffId(), startDate, endDate, loginSession.getUser().getFirmId());
		
		String color1="#62cb31";
		String color11="#ffffff";
		String color2="#fedde4";
		String color22="#000000";
		String currentColor=color1;
		String currentColorFont=color11;
		long prevSchtId=0;
		for (ScheduleTimePlan scheduleTimePlan : scheduleTimePlanObjs) {
			SchedulePlan schedulePlan=scheduleService.findSchedulePlanById(scheduleTimePlan.getSchId());
			List<ScheduleFactory> scheduleFactories=null;
			if(scheduleSearchObj.getTypeOfSchedule()==ProgramTypes.PROGRAM_PERSONAL){
				scheduleFactories=schedulePersonalService.findScheduleUsersPlanBySchtId(scheduleTimePlan.getSchtId());
				ProgramFactory programFactory=programService.findProgramPersonalById(schedulePlan.getProgId());
				scheduleTimePlan.setProgName(((ProgramPersonal)programFactory).getProgName());
			}else {
				scheduleFactories=scheduleClassService.findScheduleUsersPlanBySchtId(scheduleTimePlan.getSchtId());
				ProgramFactory programFactory=programService.findProgramClassById(schedulePlan.getProgId());
				scheduleTimePlan.setProgName(((ProgramClass)programFactory).getProgName());
			}
			
			scheduleTimePlan.setUsers(scheduleFactories);
			
			
			StaffClassPlans staffClassPlan=new StaffClassPlans();
			staffClassPlan.setProgName(scheduleTimePlan.getProgName());
			staffClassPlan.setPlanStartDate(scheduleTimePlan.getPlanStartDate());
			staffClassPlan.setPlanStartDateStr(OhbeUtil.getDateStrByFormat(scheduleTimePlan.getPlanStartDate(),loginSession.getPtGlobal().getPtScrDateFormat()));
			staffClassPlan.setPlanStartTimeStr(OhbeUtil.getTimeByFormat(new Timestamp(scheduleTimePlan.getPlanStartDate().getTime()), "HH:mm"));
			staffClassPlan.setPlanDay(DateTimeUtil.getDayNames(staffClassPlan.getPlanStartDate()));
			
			staffClassPlan.setSchf(scheduleTimePlan.getUsers());
			staffClassPlans.add(staffClassPlan);
		}
		return staffClassPlans;
		
	}
	
	
	@PostMapping(value="/changePassword")
	public  @ResponseBody HmiResultObj changePassword(@RequestBody User user ) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage("undefinedUser");
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
		
		Optional<User> userOld=userService.findUserByUserEmail(user.getUserEmail());
		
		if(userOld.isPresent()) {
		
			User u=userOld.get();
			if(u.getPassword().equals(user.getOldPassword())) {
				u.setPassword(user.getNewPassword());
				userService.create(u);
				hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
				hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			}
			
		}
		
		
		
		return hmiResultObj;
	}
	
	@PostMapping(value="/forgotPassword") 
	public @ResponseBody HmiResultObj forgotPassword(@RequestBody MailObj mailObj) throws IOException, MessagingException{
	
		HmiResultObj hmiResultObj=new HmiResultObj();
		Optional<User> u= userService.findUserByUserEmail(mailObj.getToPerson());
		if(!u.isPresent()){
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("noUserFound");
		}else{
			User user=u.get();
			
			String content=mailObj.getContent();
			content=content.replaceAll("xxxxx", user.getUserName()+" "+user.getUserSurname());
			
			String htmlContent="<strong>"+user.getPassword()+"</strong>";
			
			mailObj.setContent(content);
			mailObj.setHtmlContent(htmlContent);
			
			String[] toWho=new String[]{mailObj.getToPerson()};
			
			
			mailObj.setToWho(toWho);
			
			
			MimeMultipart mcontent = new MimeMultipart();
			MimeBodyPart mainPart = new MimeBodyPart();
			  
		    mainPart.setText(mailObj.getContent(),"UTF-8", "plain");
		    mainPart.addHeader("Content-Type", "text/plain; charset=UTF-8"); 
		    
		    mcontent.addBodyPart(mainPart);
		    
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent( mailObj.getHtmlContent(), "text/html; charset=utf-8" );
			
			mcontent.addBodyPart(htmlPart);
			
			mailObj.setMultipartMessage(mcontent);
			
			MailSenderThread mailSenderThread=new MailSenderThread(mailObj);
			Thread thr=new Thread(mailSenderThread);
			thr.start();
		
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			
		}
		
		return hmiResultObj;
	}
	
	
	public HmiResultObj sendMailForPasswordReminder(MailObj mailObj) throws IOException, MessagingException{
		
		MimeMultipart content = new MimeMultipart();
		MimeBodyPart mainPart = new MimeBodyPart();
		  
	    mainPart.setText(mailObj.getContent(),"UTF-8", "plain");
	    mainPart.addHeader("Content-Type", "text/plain; charset=UTF-8"); 
	    
	    content.addBodyPart(mainPart);
	    
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent( mailObj.getHtmlContent(), "text/html; charset=utf-8" );
		
		content.addBodyPart(htmlPart);
		
		mailObj.setMultipartMessage(content);
		
		MailSenderThread mailSenderThread=new MailSenderThread(mailObj);
		Thread thr=new Thread(mailSenderThread);
		thr.start();
	
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
	return hmiResultObj;
   }
	
	
	@PostMapping(value="/findById/{userId}")
	public  @ResponseBody HmiResultObj doLogin(@PathVariable long userId,HttpServletRequest request ) {
		User user=userService.findUserById(userId);
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(user);
		return hmiResultObj;
	}
	
	@PostMapping(value="/findAllSchedulerStaff")
	public  @ResponseBody HmiResultObj findAll(HttpServletRequest request ) {
		List<User> user=userService.findAllByFirmIdAndUserType(loginSession.getUser().getFirmId(),UserTypes.USER_TYPE_SCHEDULAR_STAFF_INT);
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(user);
		return hmiResultObj;
	}
	
	@PostMapping(value="/findAllStaff")
	public  @ResponseBody HmiResultObj findAllStaff() {
		List<User> user=userService.findAllStaffByFirmId(loginSession.getUser().getFirmId());
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(user);
		return hmiResultObj;
	}
	
	@PostMapping(value="/create")
	public  @ResponseBody HmiResultObj create(@RequestBody User user ) {
		user.setFirmId(loginSession.getUser().getFirmId());
		return userService.create(user);
	}
	
	@PostMapping(value="/delete/{userId}")
	public  @ResponseBody HmiResultObj delete(@PathVariable long userId ) {
		return userService.delete(userId);
	}
	
	
	@PostMapping(value="/setStaffToPassiveMode/{userId}") 
	public @ResponseBody HmiResultObj setStaffToPassiveMode(@PathVariable("userId") long userId){
		User user=userService.findUserById(userId);
		user.setStaffStatu(1);
		return userService.create(user);
	}
	
	@PostMapping(value="/setStaffToActiveMode/{userId}") 
	public @ResponseBody HmiResultObj setStaffToActiveMode(@PathVariable("userId") long userId){
		User user=userService.findUserById(userId);
		user.setStaffStatu(0);
		return userService.create(user);
	}
	
	@PostMapping(value="/setPersonalBonusType/{userId}/{bonusTypeP}")
	public @ResponseBody HmiResultObj setPersonalBonusType(@PathVariable("userId") long userId,@PathVariable("bonusTypeP") int bonusTypeP) {
		User user=userService.findUserById(userId);
		user.setBonusTypeP(bonusTypeP);
		return userService.create(user);
	}
	
	@PostMapping(value="/setClassBonusType/{userId}/{bonusTypeC}")
	public @ResponseBody HmiResultObj setClassBonusType(@PathVariable("userId") long userId,@PathVariable("bonusTypeC") int bonusTypeC) {
		User user=userService.findUserById(userId);
		user.setBonusTypeC(bonusTypeC);
		return userService.create(user);
		
	}
	
}

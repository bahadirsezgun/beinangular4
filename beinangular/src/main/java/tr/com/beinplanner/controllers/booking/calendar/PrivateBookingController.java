package tr.com.beinplanner.controllers.booking.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.definition.dao.DefCalendarTimes;
import tr.com.beinplanner.definition.service.DefinitionService;
import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.packetsale.dao.PacketSaleClass;
import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.packetsale.dao.PacketSalePersonal;
import tr.com.beinplanner.program.dao.ProgramClass;
import tr.com.beinplanner.program.dao.ProgramPersonal;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.businessEntity.PeriodicTimePlan;
import tr.com.beinplanner.schedule.businessEntity.ScheduleCalendarObj;
import tr.com.beinplanner.schedule.businessEntity.ScheduleTimeObj;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.dao.SchedulePlan;
import tr.com.beinplanner.schedule.dao.ScheduleTimePlan;
import tr.com.beinplanner.schedule.dao.ScheduleUsersClassPlan;
import tr.com.beinplanner.schedule.dao.ScheduleUsersPersonalPlan;
import tr.com.beinplanner.schedule.service.IScheduleService;
import tr.com.beinplanner.schedule.service.ScheduleClassService;
import tr.com.beinplanner.schedule.service.SchedulePersonalService;
import tr.com.beinplanner.schedule.service.ScheduleService;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.DateTimeUtil;
import tr.com.beinplanner.util.OhbeUtil;
import tr.com.beinplanner.util.ProgramTypes;
import tr.com.beinplanner.util.TimeTypes;
import tr.com.beinplanner.util.UserTypes;

@RestController
@RequestMapping("/bein/private/booking")
public class PrivateBookingController {


	@Autowired
	LoginSession loginSession;
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	ScheduleClassService scheduleClassService;
	
	@Autowired
	SchedulePersonalService schedulePersonalService;
	
	
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	DefinitionService definitionService;
	
	
	
	@RequestMapping(value="/findBookingByTimePlan/{schtId}", method = RequestMethod.POST) 
	public SchedulePlan	findBookingByTimePlan(@PathVariable long schtId){
		
		ScheduleTimePlan scheduleTimePlan= scheduleService.findScheduleTimePlanById(schtId);
		SchedulePlan schedulePlan=scheduleService.findSchedulePlanById(scheduleTimePlan.getSchId());
		
		User staff=userService.findUserById(schedulePlan.getSchStaffId());
		schedulePlan.setUserName(staff.getUserName());
		schedulePlan.setUserSurname(staff.getUserSurname());
		schedulePlan.setUrlType(staff.getUrlType());
		schedulePlan.setProfileUrl(staff.getProfileUrl());
		
		
		List<ScheduleTimePlan> scheduleTimePlans=scheduleService.findScheduleTimePlanBySchId(schedulePlan.getSchId());
		IScheduleService iScheduleService=null;
		if(schedulePlan.getProgType()==ProgramTypes.PROGRAM_PERSONAL) {
			iScheduleService=schedulePersonalService;
		}else {
			iScheduleService=scheduleClassService;
		}
		
		
		for (ScheduleTimePlan stp : scheduleTimePlans) {
			User staffInTP=userService.findUserById(stp.getSchtStaffId());
			stp.setStaff(staffInTP);
			stp.setPlanDayName(DateTimeUtil.getDayNames(stp.getPlanStartDate()));
			List<ScheduleFactory> scheduleFactories=iScheduleService.findScheduleUsersPlanBySchtId(stp.getSchtId());
			stp.setScheduleFactories(scheduleFactories);
		};
		
		schedulePlan.setScheduleTimePlans(scheduleTimePlans);
		
		return schedulePlan;
	}
	
	
	@RequestMapping(value="/generateScheduleTPBySaledPacket", method = RequestMethod.POST) 
	public ScheduleTimePlan	findSaledPacket(@RequestBody PacketSaleFactory psf){
		IScheduleService iScheduleService=null;
		
		if(psf instanceof PacketSalePersonal) {
			iScheduleService=schedulePersonalService;
		}else if (psf instanceof PacketSaleClass) {
			iScheduleService=scheduleClassService;
		}
		
		return iScheduleService.findScheduleTimePlanBySaleId(psf);
	
	}
	
	/**
	 *
	 * @param scheduleTimePlan
	 * @comment Creating of Schedule time plan by the calendar page. This method (createScheduleTimePlan) will control this creation is suiatbale or not.
	 *          If it is suitable than schedule time plan is going to be created by. </br>
	 *          This process control,</br>
	 *          1-Trainer has a class at the same time </br>
	 *          2-Schedule time plan is binded to Schedule plan than </br>
	 *          	2.1 Schedule Plan is able to create new plan or the plan is finished or not.
	 *              2.2 Member has saled packet by its own sales count. Control the member that she gets more time from her token</br>
	 *          3-The member has saled packet, if is not then , program automaticly sale related packet to him.</br>
	 *          Note: Plan end time set by this method. Page will send only start time of the booking.
	 * @return HmiResultObj </br>
	 *         <table >
	 *         <tr>
	 *         <td style='border:1px solid black'>ResultStatu</td>
	 *         <td style='border:1px solid black'>ResultMessage</td>
	 *         <td style='border:1px solid black'>ResultObj</td>
	 *         </tr>
	 *         <tr>
	 *         <td style='border:1px solid black'>Success/Fail</td>
	 *         <td style='border:1px solid black'>Success/Fail</td>
	 *         <td style='border:1px solid black'>ScheduleTimePlanObj</td>
	 *         </tr>
	 *         </table>
	 */
	@PostMapping(value="/createScheduleTimePlan")
	public List<HmiResultObj> createScheduleTimePlan(@RequestBody ScheduleTimePlan scheduleTimePlan) {
		IScheduleService iScheduleService=null;
		
		List<HmiResultObj> hmiResultObjs=new ArrayList<>();
		
		if(scheduleTimePlan.getProgramFactory() instanceof ProgramClass)
	    	iScheduleService=scheduleClassService;
	    else if (scheduleTimePlan.getProgramFactory() instanceof ProgramPersonal)
	    	iScheduleService=schedulePersonalService;
		
		DefCalendarTimes defCalendarTimes= definitionService.findCalendarTimes(loginSession.getUser().getFirmId());
		scheduleTimePlan.setPlanEndDate(OhbeUtil.getDateForNextMinute(((Date)scheduleTimePlan.getPlanStartDate().clone()),defCalendarTimes.getDuration()));
		
		SchedulePlan schedulePlan=null;
		if(scheduleTimePlan.getSchId()==0) {
			schedulePlan=new SchedulePlan();
			if(scheduleTimePlan.getProgramFactory() instanceof ProgramClass) {
				schedulePlan.setProgId(((ProgramClass)scheduleTimePlan.getProgramFactory()).getProgId());
				schedulePlan.setProgType(ProgramTypes.PROGRAM_CLASS);
				schedulePlan.setSchCount(((ProgramClass)scheduleTimePlan.getProgramFactory()).getProgCount());
			}else {
				schedulePlan.setProgId(((ProgramPersonal)scheduleTimePlan.getProgramFactory()).getProgId());
				schedulePlan.setProgType(ProgramTypes.PROGRAM_PERSONAL);
				schedulePlan.setSchCount(((ProgramPersonal)scheduleTimePlan.getProgramFactory()).getProgCount());
			}
			
			
			schedulePlan.setSchStaffId(scheduleTimePlan.getSchtStaffId());
			schedulePlan.setFirmId(loginSession.getUser().getFirmId());
			schedulePlan=scheduleService.createSchedulePlan(schedulePlan);
		}else {
			schedulePlan=scheduleService.findSchedulePlanById(scheduleTimePlan.getSchId());
		}
		
		if(scheduleTimePlan.getSchtId()!=0) {
			
			HmiResultObj hmiResultObj=iScheduleService.updateScheduleTimePlan(scheduleTimePlan);
			hmiResultObjs.add(hmiResultObj);
			
		}else {
			HmiResultObj hmiResultObj=iScheduleService.createPlan(scheduleTimePlan,schedulePlan);
			hmiResultObjs.add(hmiResultObj);
		}
		
		if(scheduleTimePlan.getPeriod()==1) {
			List<ScheduleTimePlan> scheduleTimePlans=generateTimePlans(scheduleTimePlan, schedulePlan, iScheduleService);
			for (ScheduleTimePlan stp : scheduleTimePlans) {
				stp.setSchtId(0);
				hmiResultObjs.add(iScheduleService.createPlan(stp,schedulePlan));
			}
		}
		
		
		
		return hmiResultObjs;
	}
	
	/**
	 *
	 * @param scheduleTimePlan
	 * @comment Schedule time plan try to change on the calendar page than updateScheduleDate method will control this change is suiatbale or not.
	 *          If it is suitable than schedule time plan is going to be change by. This method only changes time and trainer nothing else.
	 * @return
	 */
	@PostMapping(value="/updateScheduleTimePlan")
	public HmiResultObj updateScheduleTimePlan(@RequestBody ScheduleTimePlan scheduleTimePlan) {
		IScheduleService iScheduleService=null;
		if(scheduleTimePlan.getProgramFactory() instanceof ProgramClass)
	    	iScheduleService=scheduleClassService;
	    else if (scheduleTimePlan.getProgramFactory() instanceof ProgramPersonal)
	    	iScheduleService=schedulePersonalService;
		
		
		return iScheduleService.updateScheduleTimePlan(scheduleTimePlan);
		
	}
	
	
	
	
	
	/**
	 *
	 * @param scheduleTimePlan
	 * @comment Schedule time plan will be canceled. This means is the plan color is going to be red, it still view on the calendar page and It is counted when bonus payment to trainer. But The member did not participate to class.
	 *            
	 * @return {@link HmiResultObj}
	 */
	@PostMapping(value="/cancelScheduleTimePlan")
	public HmiResultObj cancelScheduleTimePlan(@RequestBody ScheduleTimePlan scheduleTimePlan) {
		IScheduleService iScheduleService=null;
		if(scheduleTimePlan.getProgramFactory() instanceof ProgramClass)
	    	iScheduleService=scheduleClassService;
	    else if (scheduleTimePlan.getProgramFactory() instanceof ProgramPersonal)
	    	iScheduleService=schedulePersonalService;
		
		return iScheduleService.cancelScheduleTimePlan(scheduleTimePlan);
		
	}
	
	/**
	 *
	 * @param scheduleTimePlan
	 * @comment Schedule time plan will be postponed. This means is the plan color is going to be yellow, it still view on the calendar page and <b> It is not counted when bonus payment to trainer.</b> But The member did not participate to class.
	 *            
	 * @return {@link HmiResultObj}
	 */
	@PostMapping(value="/postponeScheduleTimePlan")
	public HmiResultObj postponeScheduleTimePlan(@RequestBody ScheduleTimePlan scheduleTimePlan) {
		IScheduleService iScheduleService=null;
		if(scheduleTimePlan.getProgramFactory() instanceof ProgramClass)
	    	iScheduleService=scheduleClassService;
	    else if (scheduleTimePlan.getProgramFactory() instanceof ProgramPersonal)
	    	iScheduleService=schedulePersonalService;
		
		return iScheduleService.postponeScheduleTimePlan(scheduleTimePlan);
		
	}
	
	
	/**
	 *
	 * @param scheduleTimePlan
	 * @comment Schedule time plan will be delete. This means is the plan removed from calendar.
	 *            
	 * @return {@link HmiResultObj}
	 */
	@PostMapping(value="/deleteScheduleTimePlan")
	public HmiResultObj deleteScheduleTimePlan(@RequestBody ScheduleTimePlan scheduleTimePlan) {
		IScheduleService iScheduleService=null;
		if(scheduleTimePlan.getProgramFactory() instanceof ProgramClass)
	    	iScheduleService=scheduleClassService;
	    else if (scheduleTimePlan.getProgramFactory() instanceof ProgramPersonal)
	    	iScheduleService=schedulePersonalService;
		
		return iScheduleService.deleteScheduleTimePlan(scheduleTimePlan);
		
	}
	
	
	
	@PostMapping(value="/cancelUserInTimePlan")
	public HmiResultObj cancelUserInTimePlan(@RequestBody ScheduleFactory scheduleFactory) {
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		
		ScheduleTimePlan scheduleTimePlan=null;
		List<ScheduleFactory> scheduleFactories=null;
		
		if(scheduleFactory instanceof ScheduleUsersPersonalPlan) {
			
			scheduleTimePlan=scheduleService.findScheduleTimePlanById(((ScheduleUsersPersonalPlan) scheduleFactory).getSchtId());
			scheduleFactories=schedulePersonalService.findScheduleUsersPlanBySchtId(((ScheduleUsersPersonalPlan) scheduleFactory).getSchtId());
			if(scheduleFactories.size()==1) {
				hmiResultObj=schedulePersonalService.deleteScheduleTimePlan(scheduleTimePlan);
			}else {
				hmiResultObj=schedulePersonalService.deleteScheduleUsersPersonalTimePlan(((ScheduleUsersPersonalPlan) scheduleFactory), scheduleTimePlan);
			}
			
		}else if (scheduleFactory instanceof ScheduleUsersClassPlan) {
			scheduleTimePlan=scheduleService.findScheduleTimePlanById(((ScheduleUsersClassPlan) scheduleFactory).getSchtId());
			scheduleFactories=scheduleClassService.findScheduleUsersPlanBySchtId(((ScheduleUsersClassPlan) scheduleFactory).getSchtId());
			if(scheduleFactories.size()==1) {
				hmiResultObj=scheduleClassService.deleteScheduleTimePlan(scheduleTimePlan);
			}else {
				hmiResultObj=scheduleClassService.deleteScheduleUsersClassTimePlan(((ScheduleUsersClassPlan) scheduleFactory), scheduleTimePlan);
			}
		}
		
		
		
		return hmiResultObj;
	}
	
	
	
	/**
	 *
	 * @param scheduleTimePlan
	 * @comment Schedule time plan try to change on the calendar page than updateScheduleDate method will control this change is suiatbale or not.
	 *          If it is suitable than schedule time plan is going to be change by. This method only changes time and trainer nothing else.
	 * @return
	 */
	@PostMapping(value="/addUserInScheduleTimePlan")
	public HmiResultObj addUserInScheduleTimePlan(@RequestBody ScheduleFactory scheduleFactory) {
		IScheduleService iScheduleService=null;
		if(scheduleFactory instanceof ScheduleUsersPersonalPlan)
	    	iScheduleService=schedulePersonalService;
	    else if (scheduleFactory instanceof ScheduleUsersClassPlan)
	    	iScheduleService=scheduleClassService;
		
		
		return iScheduleService.addUserInScheduleTimePlan(scheduleFactory);
		
	}
	
	
	
	@PostMapping(value="/findTimes")
	public List<String> findTimes() {
		
		List<String> allDayTimes=new ArrayList<String>();
		
		DefCalendarTimes defCalendarTimes= definitionService.findCalendarTimes(loginSession.getUser().getFirmId());
		
		
		int startTimeCal=Integer.parseInt(defCalendarTimes.getStartTime().replace(":", ""));
		int endTimeCal=Integer.parseInt(defCalendarTimes.getEndTime().replace(":", ""));
		if(endTimeCal==0){
			endTimeCal=2400;
		}
		int duration=defCalendarTimes.getCalPeriod();		
		int classDuration=defCalendarTimes.getDuration();		
		int sequence=classDuration/duration;
		int loopCount=24*60/duration;
		
		Calendar startTimeCalendar=Calendar.getInstance();
		startTimeCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(defCalendarTimes.getStartTime().split(":")[0]));
		startTimeCalendar.set(Calendar.MINUTE, Integer.parseInt(defCalendarTimes.getStartTime().split(":")[1]));
		startTimeCalendar.set(Calendar.SECOND, 0);
		startTimeCalendar.set(Calendar.MILLISECOND, 0);
		
		
		for (int i = 0; i < loopCount; i++) {
			int startTime=startTimeCal+i*duration;
			int timeMinute=startTime-startTimeCal;
			
			Calendar sequenceTimeCalendar=Calendar.getInstance();
			sequenceTimeCalendar.set(Calendar.HOUR_OF_DAY, startTimeCalendar.get(Calendar.HOUR_OF_DAY));
			sequenceTimeCalendar.set(Calendar.MINUTE, (startTimeCalendar.get(Calendar.MINUTE)+timeMinute));
			sequenceTimeCalendar.set(Calendar.SECOND, 0);
			sequenceTimeCalendar.set(Calendar.MILLISECOND, 0);
			
			
			String generatedMinute=sequenceTimeCalendar.get(Calendar.MINUTE)<10?"0"+sequenceTimeCalendar.get(Calendar.MINUTE):""+sequenceTimeCalendar.get(Calendar.MINUTE);
			
			String generatedHour=sequenceTimeCalendar.get(Calendar.HOUR_OF_DAY)==0?"24":""+sequenceTimeCalendar.get(Calendar.HOUR_OF_DAY);
			int generatedStartTime=Integer.parseInt(generatedHour+""+generatedMinute);
			
			String generatedTime="";
			String saatG="";
			String dakikaG="";
			
			if(generatedStartTime<1000){
				saatG="0"+(""+generatedStartTime).substring(0,1);
				dakikaG=(""+generatedStartTime).substring(1,3);
			}else{
				saatG=(""+generatedStartTime).substring(0,2);
				dakikaG=(""+generatedStartTime).substring(2,4);
			}
			generatedTime=saatG+":"+dakikaG;
			if(generatedStartTime>=startTimeCal && generatedStartTime<=endTimeCal) 
				allDayTimes.add(generatedTime);
			
		}
		
		return allDayTimes;
	}
	
	
	
	@PostMapping(value="/findAllPlanByDate")
	public List<ScheduleTimeObj> findAllPlanByDate(@RequestBody ScheduleCalendarObj scheduleCalendarObj) {
	    
		
		List<ScheduleTimeObj> scheduleTimeObjs=generateScheduleTimeObj(scheduleCalendarObj);
		List<User> users= userService.findAllByFirmIdAndUserType(loginSession.getUser().getFirmId(), UserTypes.USER_TYPE_SCHEDULAR_STAFF_INT);
		List<String> times=findTimes();
		
		scheduleTimeObjs.forEach(sto->{
			if(sto.getStaffs()==null) {
				sto.setStaffs(new ArrayList<>());
			}
		  for (User user : users) { 
			  User userInTimePlan=null;
			    try {
				   userInTimePlan=(User)user.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			  
			   if(userInTimePlan.getScheduleTimePlans()==null)
				   userInTimePlan.setScheduleTimePlans(new ArrayList<>());
			      
				   
				for(String t : times)  { 
					Date controlDate=DateTimeUtil.setHourMinute((Date)sto.getCalendarDate().clone(),t);
					ScheduleTimePlan stpfc=scheduleClassService.findScheduleTimePlanPlanByDateTimeForStaff(userInTimePlan.getUserId(), controlDate);
				    if(stpfc!=null) {
				    	stpfc.setScheduleFactories(scheduleClassService.findScheduleUsersPlanBySchtId(stpfc.getSchtId()));
				    	stpfc.setStaff(user);
				    	
				    	userInTimePlan.getScheduleTimePlans().add(stpfc);
				    }else {
				    	 ScheduleTimePlan stpfp=schedulePersonalService.findScheduleTimePlanPlanByDateTimeForStaff(userInTimePlan.getUserId(), controlDate);
						 if(stpfp!=null) {
							   stpfp.setScheduleFactories(schedulePersonalService.findScheduleUsersPlanBySchtId(stpfp.getSchtId()));
							   stpfp.setStaff(user);
							   userInTimePlan.getScheduleTimePlans().add(stpfp);
						 }else {
							 ScheduleTimePlan scheduleTimePlan=new ScheduleTimePlan();
							 scheduleTimePlan.setPlanStartDate(controlDate);
							 scheduleTimePlan.setStaff(user);
							 userInTimePlan.getScheduleTimePlans().add(scheduleTimePlan);
							 
						 }
				    }
				}
			   
			   sto.getStaffs().add(userInTimePlan);
			}   
		});		
			
			
		return scheduleTimeObjs;
	}
	
	
	private List<ScheduleTimePlan> generateTimePlans(ScheduleTimePlan scheduleTimePlan,SchedulePlan schedulePlan,IScheduleService iScheduleService){
		List<ScheduleTimePlan> scheduleTimePlans=scheduleService.findScheduleTimePlanBySchId(schedulePlan.getSchId());
		int progCount=schedulePlan.getSchCount();
		int leftProgCount=progCount-scheduleTimePlans.size();
		int periodCount=scheduleTimePlan.getPeriodCount();
		
		List<ScheduleTimePlan> generatedSTPs=new ArrayList<>();
		
		DefCalendarTimes defCalendarTimes= definitionService.findCalendarTimes(loginSession.getUser().getFirmId());
		int progDuration=defCalendarTimes.getDuration();
		
			
			if(leftProgCount<periodCount)
				periodCount=leftProgCount;
			
			Date startDate=scheduleTimePlan.getPlanStartDate();
			List<Date> dates=getStudioPlanDayArrays(startDate, scheduleTimePlan.getPeriodicTimePlans(), periodCount);
			for (Date date : dates) {
				ScheduleTimePlan schTP=(ScheduleTimePlan)scheduleTimePlan.clone();
				List<ScheduleFactory> scheduleFactories=new ArrayList<>();
				schTP.getScheduleFactories().forEach(sf->{
					ScheduleFactory schF=(ScheduleFactory)sf.clone();
					if(schF instanceof ScheduleUsersPersonalPlan) {
						((ScheduleUsersPersonalPlan)schF).setSuppId(0);
					}else {
						((ScheduleUsersClassPlan)schF).setSucpId(0);
					}
					scheduleFactories.add(schF);
				});
				schTP.setScheduleFactories(scheduleFactories);
				schTP.setPlanStartDate(date);
				Date endDate=(Date)date.clone();
				endDate=OhbeUtil.getDateForNextMinute(endDate, progDuration);
				schTP.setPlanEndDate(endDate);
				generatedSTPs.add(schTP);
			}
			
			
			return generatedSTPs;
	}
	
	
	
	
	private List<Date> getStudioPlanDayArrays(Date startDate,List<PeriodicTimePlan> periodicTimePlans,int dpAdet){
		Calendar startPoint=Calendar.getInstance();
		startPoint.setTime((Date)startDate.clone());
		
		//String[] gunArr=gunler.split("#");
		
		boolean monday=false;
		boolean tuesday=false;
		boolean wednesday=false;
		boolean thursday=false;
		boolean friday=false;
		boolean saturday=false;
		boolean sunday=false;
		
		String mondayTime="";
		String tuesdayTime="";
		String wednesdayTime="";
		String thursdayTime="";
		String fridayTime="";
		String saturdayTime="";
		String sundayTime="";
	
		
		for (PeriodicTimePlan scheduleTimeObj : periodicTimePlans) {
	        String gun=""+scheduleTimeObj.getProgDay();
	        String times=scheduleTimeObj.getProgStartTime();
	        if(gun.equals("1")){
	        	monday=true;
	        	mondayTime=times;
	        }else if(gun.equals("2")){
	        	tuesday=true;
	        	tuesdayTime=times;
	        }else if(gun.equals("3")){
	        	wednesday=true;
	        	wednesdayTime=times;
	        }else if(gun.equals("4")){
	        	thursday=true;
	        	thursdayTime=times;
	        }else if(gun.equals("5")){
	        	friday=true;
	        	fridayTime=times;
	        }else if(gun.equals("6")){
	        	saturday=true;
	        	saturdayTime=times;
	        }else if(gun.equals("7")){
	        	sunday=true;
	        	sundayTime=times;
	        }
	        
	        
	        
        }
		
		
		List<Date> gunlerList=new ArrayList<Date>();
		int dersSayisi=dpAdet;
		int dateCounter=0;
		for (int i = 0; i < dpAdet; i++) {
	        
			   while(true){
				   if(dateCounter>0)
					   startPoint.add(Calendar.DATE, 1);
				   
				   String day=TimeTypes.getDateStrByFormatEEEByTurkish(new Date(startPoint.getTimeInMillis()));
				   if(day.equals(TimeTypes.TIME_Pzt) && monday){
					   Date gunDate=new Date(startPoint.getTimeInMillis());
					   if(!mondayTime.equals("0")){
						   gunDate= OhbeUtil.changeHourForDate(gunDate, mondayTime);
					   }
             		   gunlerList.add(gunDate);
             		   dateCounter=dateCounter+1;
             		   break;  
				   }else if(day.equals(TimeTypes.TIME_Sal) && tuesday){
					   Date gunDate=new Date(startPoint.getTimeInMillis());
             		  
             		  if(!tuesdayTime.equals("0")){
						   gunDate= OhbeUtil.changeHourForDate(gunDate,tuesdayTime);
					   }
             		 gunlerList.add(gunDate);
             		   dateCounter=dateCounter+1;
             		   break;  
				   }else if(day.equals(TimeTypes.TIME_Car) && wednesday){
					   Date gunDate=new Date(startPoint.getTimeInMillis());
             		  
             		  if(!wednesdayTime.equals("0")){
						   gunDate= OhbeUtil.changeHourForDate(gunDate,wednesdayTime);
					   }
             		 gunlerList.add(gunDate);
             		   dateCounter=dateCounter+1;
             		   break;  
				   }else if(day.equals(TimeTypes.TIME_Per) && thursday){
					   Date gunDate=new Date(startPoint.getTimeInMillis());
             		  
             		  if(!thursdayTime.equals("0")){
						   gunDate= OhbeUtil.changeHourForDate(gunDate,thursdayTime);
					   }
             		 gunlerList.add(gunDate);
             		   dateCounter=dateCounter+1;
             		   break;  
				   }else if(day.equals(TimeTypes.TIME_Cum) && friday){
					   Date gunDate=new Date(startPoint.getTimeInMillis());
             		  
             		  if(!fridayTime.equals("0")){
						   gunDate= OhbeUtil.changeHourForDate(gunDate,fridayTime);
					   }
             		 gunlerList.add(gunDate);
             		   dateCounter=dateCounter+1;
             		   break;  
				   }else if(day.equals(TimeTypes.TIME_Cmt) && saturday){
					   Date gunDate=new Date(startPoint.getTimeInMillis());
             		   
             		  if(!saturdayTime.equals("0")){
						   gunDate= OhbeUtil.changeHourForDate(gunDate,saturdayTime);
					   }
             		 gunlerList.add(gunDate);
             		   dateCounter=dateCounter+1;
             		   break;  
				   }else if(day.equals(TimeTypes.TIME_Paz) && sunday){
					   Date gunDate=new Date(startPoint.getTimeInMillis());
             		  
             		  if(!sundayTime.equals("0")){
						   gunDate= OhbeUtil.changeHourForDate(gunDate,sundayTime);
					   }
             		 gunlerList.add(gunDate);
             		   dateCounter=dateCounter+1;
             		   break;  
				   }
				   
				   dateCounter=dateCounter+1;
				   
			   }
			   dersSayisi--;
      		   if(dersSayisi==0)
      			  break;
		}
		return gunlerList;
	}
	
	
	/**
	 * 
	 * @param scheduleCalendarObj
	 * @comment this private method generate calendar dates. It can be daily, weekly and monthly. ScheduleCalendarObj abject attribute of duration is 1 for daily, 7 for weekly and 30 for monthly.
	 *          In shortly, duration attributes sets day count. 
	 * @return List<ScheduleTimeObj>
	 */
	private List<ScheduleTimeObj> generateScheduleTimeObj(ScheduleCalendarObj scheduleCalendarObj){
		int duration=scheduleCalendarObj.getDayDuration();
		List<ScheduleTimeObj> scheduleTimeObjs=new ArrayList<ScheduleTimeObj>();
		for (int i = 0; i < duration; i++) {
			Date startDate=(Date)OhbeUtil.getDateForNextDate(scheduleCalendarObj.getCalendarDate(), i).clone();
			ScheduleTimeObj scheduleTimeObj=new ScheduleTimeObj();
			scheduleTimeObj.setCalendarDate(startDate);
			scheduleTimeObj.setCalendarDateName(DateTimeUtil.getDayNames(startDate));
			scheduleTimeObjs.add(scheduleTimeObj);
		}
		return scheduleTimeObjs;
	}
	
	
}

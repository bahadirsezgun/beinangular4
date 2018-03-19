package tr.com.beinplanner.bonus.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.bonus.businessDao.UserBonusSearchObj;
import tr.com.beinplanner.bonus.businessDao.UserPaymentObj;
import tr.com.beinplanner.bonus.comparator.UserBonusPaymentComparator;
import tr.com.beinplanner.bonus.dao.UserBonusPaymentClass;
import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;
import tr.com.beinplanner.bonus.repository.UserBonusPaymentClassRepository;
import tr.com.beinplanner.packetpayment.business.PacketPaymentClassBusiness;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.dao.ScheduleUsersClassPlan;
import tr.com.beinplanner.schedule.service.ScheduleClassService;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("userBonusPaymentClassService")
public class UserBonusPaymentClassService implements IUserBonusPayment {

	
	@Autowired
	UserBonusPaymentClassRepository userBonusPaymentClassRepository;
	
	
	@Autowired
	PacketPaymentClassBusiness packetPaymentClassBusiness;
	
	@Autowired
	ScheduleClassService scheduleClassService;
	
  
	@Autowired
	UserService userService;
	
  
	
	@Override
	public UserPaymentObj findUserPayment(long schtId) {
	  UserPaymentObj userPaymentObj=new UserPaymentObj();
		 List<ScheduleFactory> scheduleFactories=scheduleClassService.findScheduleUsersPlanBySchtId(schtId);
		 for (ScheduleFactory scheduleFactory : scheduleFactories) {
			PacketPaymentFactory packetPaymentFactory=packetPaymentClassBusiness.findPacketPaymentBySaleId(scheduleFactory.getSaleId());
	    	if(packetPaymentFactory!=null){
	    		((ScheduleUsersClassPlan)scheduleFactory).setPacketPaymentFactory(packetPaymentFactory);
	    	}
	    }
	    userPaymentObj.setScheduleFactories(scheduleFactories);
		return userPaymentObj;
	}

	@Override
	public HmiResultObj saveBonusPayment(UserBonusPaymentFactory userBonusPaymentFactory) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		try {
			UserBonusPaymentClass userBonusPaymentClass=userBonusPaymentClassRepository.save((UserBonusPaymentClass)userBonusPaymentFactory);
			hmiResultObj.setResultObj(userBonusPaymentClass);
		} catch (Exception e) {
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
		}
		
		 return hmiResultObj;
	}

	@Override
	public HmiResultObj deleteBonusPayment(UserBonusPaymentFactory userBonusPaymentFactory) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		userBonusPaymentClassRepository.delete((UserBonusPaymentClass)userBonusPaymentFactory);
		return hmiResultObj;
	}

	@Override
	public List<UserBonusPaymentFactory> findStaffBonusPayment(UserBonusSearchObj userBonusSearchObj) {
		if(userBonusSearchObj.getQueryType()==1){
			List<UserBonusPaymentClass> userBonusPaymentClasss=userBonusPaymentClassRepository.findByUserIdAndBonMonthAndBonYear(userBonusSearchObj.getSchStaffId(), userBonusSearchObj.getMonth(), userBonusSearchObj.getYear());
			List<UserBonusPaymentFactory> userBonusPaymentFactories=new ArrayList<>();
			userBonusPaymentFactories.addAll(userBonusPaymentClasss);
			
			return userBonusPaymentFactories;
		}else{
			List<UserBonusPaymentClass> userBonusPaymentClasss=userBonusPaymentClassRepository.findUserBonusPaymentClassByDate(userBonusSearchObj.getSchStaffId(), userBonusSearchObj.getStartDate(), userBonusSearchObj.getEndDate());
			List<UserBonusPaymentFactory> userBonusPaymentFactories=new ArrayList<>();
			userBonusPaymentFactories.addAll(userBonusPaymentClasss);
			
			return userBonusPaymentFactories;
		}
	
	}


@Override
	public List<UserBonusPaymentFactory> controlUserBonusPaymentByDate(long schStaffId, Date startDate) {
	  List<UserBonusPaymentClass> userBonusPaymentClasses=userBonusPaymentClassRepository.controlUserBonusPaymentClassByDate(schStaffId, startDate);
	  List<UserBonusPaymentFactory> bonusPaymentFactories=new ArrayList<>();
	  bonusPaymentFactories.addAll(userBonusPaymentClasses); 
	  Collections.sort(bonusPaymentFactories, new UserBonusPaymentComparator());
	  return bonusPaymentFactories;
	}


  public List<UserBonusPaymentFactory>	findUserBonusPaymentByDate(long schStaffId, Date startDate, Date endDate){
	   List<UserBonusPaymentClass> userBonusPaymentClasses=userBonusPaymentClassRepository.findUserBonusPaymentClassByDate(schStaffId, startDate, endDate);
	  List<UserBonusPaymentFactory> bonusPaymentFactories=new ArrayList<>();
	  bonusPaymentFactories.addAll(userBonusPaymentClasses); 
	  Collections.sort(bonusPaymentFactories, new UserBonusPaymentComparator());
	  return bonusPaymentFactories;
  }

  
  public List<UserBonusPaymentFactory>	controlUserBonusPaymentClassByDate(long schStaffId, Date startDate){
	 
	  List<UserBonusPaymentClass> userBonusPaymentClasses=userBonusPaymentClassRepository.controlUserBonusPaymentClassByDate(schStaffId, startDate);
	  List<UserBonusPaymentFactory> bonusPaymentFactories=new ArrayList<>();
	  bonusPaymentFactories.addAll(userBonusPaymentClasses); 
	  Collections.sort(bonusPaymentFactories, new UserBonusPaymentComparator());
	  return bonusPaymentFactories;
  }
  
  public  List<UserBonusPaymentFactory> findAllBonusPaymentsByMonthAndYear(int firmId, int month ,int year) {
	  List<UserBonusPaymentClass> userBonusPaymentClasses=userBonusPaymentClassRepository.findByFirmIdAndBonMonthAndBonYear(firmId, month, year);
	  userBonusPaymentClasses.forEach(ubpc->{
		  ubpc.setUser(userService.findUserById(ubpc.getUserId()));
	  });
	  
	  List<UserBonusPaymentFactory> bonusPaymentFactories=new ArrayList<UserBonusPaymentFactory>();
	  bonusPaymentFactories.addAll(userBonusPaymentClasses);
	  Collections.sort(bonusPaymentFactories, new UserBonusPaymentComparator());
	  return bonusPaymentFactories;
  }
  
  
  public double findTotalOfMonthBonusPayment(int firmId, int month ,int year) {
	  List<UserBonusPaymentClass> userBonusPaymentClasses=userBonusPaymentClassRepository.findByFirmIdAndBonMonthAndBonYear(firmId, month, year);
	  double totalPayment=userBonusPaymentClasses.stream().mapToDouble(ubpc->ubpc.getBonAmount()).sum();
	  return totalPayment;
  }
  
  
  public double findTotalOfDateBonusPayment(int firmId, Date startDate ,Date endDate) {
	  List<UserBonusPaymentClass> userBonusPaymentClasses=userBonusPaymentClassRepository.findTotalOfDateBonusPaymentClass(firmId, startDate, endDate);
	  double totalPayment=userBonusPaymentClasses.stream().mapToDouble(ubpc->ubpc.getBonAmount()).sum();
	  return totalPayment;
  }
}

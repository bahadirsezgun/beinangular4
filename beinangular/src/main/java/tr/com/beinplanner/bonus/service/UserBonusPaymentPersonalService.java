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
import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;
import tr.com.beinplanner.bonus.dao.UserBonusPaymentPersonal;
import tr.com.beinplanner.bonus.repository.UserBonusPaymentPersonalRepository;
import tr.com.beinplanner.packetpayment.business.PacketPaymentPersonalBusiness;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetpayment.service.PacketPaymentService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.dao.ScheduleUsersPersonalPlan;
import tr.com.beinplanner.schedule.service.SchedulePersonalService;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("userBonusPaymentPersonalService")
public class UserBonusPaymentPersonalService implements IUserBonusPayment {

	
	
	@Autowired
	@Qualifier("userBonusPaymentClassService")
	IUserBonusPayment iUserBonusPayment;
	
	@Autowired
	UserBonusPaymentPersonalRepository userBonusPaymentPersonalRepository;
	
	
	
	@Autowired
	PacketPaymentPersonalBusiness packetPaymentPersonalBusiness;
	
	@Autowired
	SchedulePersonalService schedulePersonalService;
	

	@Autowired
	UserService userService;
	
  
	

  @Override
	public UserPaymentObj findUserPayment(long schtId) {
	  UserPaymentObj userPaymentObj=new UserPaymentObj();
		 List<ScheduleFactory> scheduleFactories=schedulePersonalService.findScheduleUsersPlanBySchtId(schtId);
		 for (ScheduleFactory scheduleFactory : scheduleFactories) {
			PacketPaymentFactory packetPaymentFactory=packetPaymentPersonalBusiness.findPacketPaymentBySaleId(scheduleFactory.getSaleId());
	    	if(packetPaymentFactory!=null){
	    		((ScheduleUsersPersonalPlan)scheduleFactory).setPacketPaymentFactory(packetPaymentFactory);
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
			UserBonusPaymentPersonal userBonusPaymentPersonal=userBonusPaymentPersonalRepository.save((UserBonusPaymentPersonal)userBonusPaymentFactory);
			hmiResultObj.setResultObj(userBonusPaymentPersonal);
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
		userBonusPaymentPersonalRepository.delete(((UserBonusPaymentPersonal)userBonusPaymentFactory).getBonId());
		return hmiResultObj;
	}

	@Override
	public List<UserBonusPaymentFactory> findStaffBonusPayment(UserBonusSearchObj userBonusSearchObj) {
		if(userBonusSearchObj.getQueryType()==1){
			List<UserBonusPaymentPersonal> userBonusPaymentPersonals=userBonusPaymentPersonalRepository.findByUserIdAndBonMonthAndBonYear(userBonusSearchObj.getSchStaffId(), userBonusSearchObj.getMonth(), userBonusSearchObj.getYear());
			List<UserBonusPaymentFactory> userBonusPaymentFactories=new ArrayList<>();
			userBonusPaymentFactories.addAll(userBonusPaymentPersonals);
			
			return userBonusPaymentFactories;
		}else{
			List<UserBonusPaymentPersonal> userBonusPaymentPersonals=userBonusPaymentPersonalRepository.findUserBonusPaymentPersonalByDate(userBonusSearchObj.getSchStaffId(), userBonusSearchObj.getStartDate(), userBonusSearchObj.getEndDate());
			List<UserBonusPaymentFactory> userBonusPaymentFactories=new ArrayList<>();
			userBonusPaymentFactories.addAll(userBonusPaymentPersonals);
			
			return userBonusPaymentFactories;
		}
	
	}

	
	
	
  public List<UserBonusPaymentFactory>	findUserBonusPaymentByDate(long schStaffId, Date startDate, Date endDate){
	  
	  
	  List<UserBonusPaymentPersonal> userBonusPaymentPersonals=userBonusPaymentPersonalRepository.findUserBonusPaymentPersonalByDate(schStaffId, startDate, endDate);
	  List<UserBonusPaymentFactory> bonusPaymentFactories=new ArrayList<>();
	  bonusPaymentFactories.addAll(userBonusPaymentPersonals); 
	  Collections.sort(bonusPaymentFactories, new UserBonusPaymentComparator());
	  return bonusPaymentFactories;
  }
  
  public List<UserBonusPaymentFactory>	controlUserBonusPaymentByDate(long schStaffId, Date startDate){
	  
	  List<UserBonusPaymentPersonal> userBonusPaymentPersonals=userBonusPaymentPersonalRepository.controlUserBonusPaymentPersonalByDate(schStaffId, startDate);
	  List<UserBonusPaymentFactory> bonusPaymentFactories=new ArrayList<>();
	  bonusPaymentFactories.addAll(userBonusPaymentPersonals); 
	  Collections.sort(bonusPaymentFactories, new UserBonusPaymentComparator());
	  return bonusPaymentFactories;
	  
	 
  }
  
 
  
  public  List<UserBonusPaymentFactory> findAllBonusPaymentsByMonthAndYear(int firmId, int month ,int year) {
	  List<UserBonusPaymentFactory> userBonusPaymentFactories=iUserBonusPayment.findAllBonusPaymentsByMonthAndYear(firmId, month, year);
		 
	  List<UserBonusPaymentPersonal> userBonusPaymentPersonals=userBonusPaymentPersonalRepository.findByFirmIdAndBonMonthAndBonYear(firmId, month, year);
	  
	  userBonusPaymentPersonals.forEach(ubpp->{
		  ubpp.setUser(userService.findUserById(ubpp.getUserId()));
	  });
	  
	  userBonusPaymentFactories.addAll(userBonusPaymentPersonals);
	  
	  Collections.sort(userBonusPaymentFactories, new UserBonusPaymentComparator());
	  return userBonusPaymentFactories;
  }
  
  public double findTotalOfMonthBonusPayment(int firmId, int month ,int year) {
	  List<UserBonusPaymentPersonal> userBonusPaymentPersonals=userBonusPaymentPersonalRepository.findByFirmIdAndBonMonthAndBonYear(firmId, month, year);
	  double totalPayment=userBonusPaymentPersonals.stream().mapToDouble(ubpp->ubpp.getBonAmount()).sum();
	  return totalPayment;
  }
  
  
  
  public double findTotalOfDateBonusPayment(int firmId, Date startDate ,Date endDate) {
	  List<UserBonusPaymentPersonal> userBonusPaymentPersonals=userBonusPaymentPersonalRepository.findTotalOfDateBonusPaymentPersonal(firmId, startDate, endDate);
	  double totalPayment=userBonusPaymentPersonals.stream().mapToDouble(ubpp->ubpp.getBonAmount()).sum();
	  return totalPayment;
  }
  
  
}

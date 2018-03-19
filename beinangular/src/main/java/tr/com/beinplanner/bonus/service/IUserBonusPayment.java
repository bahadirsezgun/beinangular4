package tr.com.beinplanner.bonus.service;

import java.util.Date;
import java.util.List;

import tr.com.beinplanner.bonus.businessDao.UserBonusSearchObj;
import tr.com.beinplanner.bonus.businessDao.UserPaymentObj;
import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;
import tr.com.beinplanner.result.HmiResultObj;

public interface IUserBonusPayment {

	
	  public List<UserBonusPaymentFactory>	findUserBonusPaymentByDate(long schStaffId, Date startDate, Date endDate);
	  
	  public List<UserBonusPaymentFactory>	controlUserBonusPaymentByDate(long schStaffId, Date startDate);
	  
	  public  List<UserBonusPaymentFactory> findAllBonusPaymentsByMonthAndYear(int firmId, int month ,int year) ;
	  
	  public double findTotalOfMonthBonusPayment(int firmId, int month ,int year) ;
	  
	  public double findTotalOfDateBonusPayment(int firmId, Date startDate ,Date endDate);
	  
	  
	  
	  public UserPaymentObj findUserPayment(long schtId);
	  
	  public HmiResultObj saveBonusPayment(UserBonusPaymentFactory userBonusPaymentFactory);
	  
	  public HmiResultObj deleteBonusPayment(UserBonusPaymentFactory userBonusPaymentFactory);
	  
	  public List<UserBonusPaymentFactory> findStaffBonusPayment(UserBonusSearchObj userBonusSearchObj);
	  
}

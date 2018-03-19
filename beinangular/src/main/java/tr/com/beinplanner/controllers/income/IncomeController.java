package tr.com.beinplanner.controllers.income;

import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;
import tr.com.beinplanner.bonus.service.UserBonusPaymentClassService;
import tr.com.beinplanner.bonus.service.UserBonusPaymentPersonalService;
import tr.com.beinplanner.income.dao.PtExpenses;
import tr.com.beinplanner.income.dao.PtMonthlyInOutObj;
import tr.com.beinplanner.income.service.PtExpensesService;
import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;
import tr.com.beinplanner.packetpayment.service.PacketPaymentService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.OhbeUtil;

@RestController
@RequestMapping("/bein/income")
public class IncomeController {

	
	@Autowired
	PtExpensesService ptExpensesService;
	
	@Autowired
	PacketPaymentService packetPaymentService;
	
	@Autowired
	UserBonusPaymentClassService userBonusPaymentClassService;
	
	@Autowired
	UserBonusPaymentPersonalService userBonusPaymentPersonalService;
	
	
	@Autowired
	LoginSession loginSession;
	
	@PostMapping(value="/createPtExpenses") 
	public @ResponseBody HmiResultObj createPtExpenses(@RequestBody PtExpenses ptExpenses){
		ptExpenses.setFirmId(loginSession.getUser().getFirmId());
		return ptExpensesService.createPtExpenses(ptExpenses);
	}
		
	@PostMapping(value="/deletePtExpenses") 
	public @ResponseBody HmiResultObj deletePtExpenses(@RequestBody PtExpenses ptExpenses ){
		ptExpenses.setFirmId(loginSession.getUser().getFirmId());
		return ptExpensesService.deletePtExpenses(ptExpenses);
	}
	
	
	@PostMapping(value="/findPtExpensesForMonth/{year}/{month}") 
	public @ResponseBody List<PtExpenses> deletePtExpenses(@PathVariable int year,@PathVariable int month ){
	  return ptExpensesService.findPtExpensesForMonth(year, month, loginSession.getUser().getFirmId());
	}
	
	
	@PostMapping(value="/findPtInOutDetailForMonth/{year}/{month}") 
	public @ResponseBody PtMonthlyInOutObj findPtInOutDetailForMonth(@PathVariable("year") int year,@PathVariable("month") int month ){
	
		
		PtMonthlyInOutObj ptMonthlyInOutObj=new PtMonthlyInOutObj();
		
		List<PtExpenses> ptExp= ptExpensesService.findPtExpensesForMonth(year, month, loginSession.getUser().getFirmId());
		ptMonthlyInOutObj.setPtExp(ptExp);
		
		String dateString="01/"+((month<10)?"0"+month:month)+"/"+year;
		
		Date startDate=OhbeUtil.getThatDateForNight(dateString, "dd/MM/yyyy");
		Date endDate=OhbeUtil.getDateForNextMonth(OhbeUtil.getThatDateForNight(dateString, "dd/MM/yyyy"), 1);
		
		
		
		List<PacketPaymentDetailFactory> ppf=packetPaymentService.findIncomePaymentInMonth(startDate, endDate, loginSession.getUser().getFirmId());
		ptMonthlyInOutObj.setPpf(ppf);
		
		List<UserBonusPaymentFactory> userBonusPaymentFactories=userBonusPaymentPersonalService.findAllBonusPaymentsByMonthAndYear(loginSession.getUser().getFirmId(), month, year);
		ptMonthlyInOutObj.setUbpf(userBonusPaymentFactories);
		
		
		return ptMonthlyInOutObj;
		
	}
	
	
}

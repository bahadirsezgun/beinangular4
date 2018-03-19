package tr.com.beinplanner.controllers.staff;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.definition.dao.DefBonus;
import tr.com.beinplanner.definition.service.DefinitionService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.util.BonusTypes;
import tr.com.beinplanner.util.SessionUtil;
import tr.com.beinplanner.util.UserTypes;
@RestController
@RequestMapping("/bein/staff/bonus")
public class StaffBonusController {

	@Autowired
	DefinitionService definitionService;
	
	
	@RequestMapping(value="/create", method = RequestMethod.POST) 
	public @ResponseBody HmiResultObj create(@RequestBody DefBonus defBonus, HttpServletRequest request ){
		return definitionService.createDefBonus(defBonus);
	}
	
	@RequestMapping(value="/delete/{bonusId}", method = RequestMethod.POST) 
	public @ResponseBody HmiResultObj delete(@PathVariable long bonusId, HttpServletRequest request ){
		DefBonus defBonus=new DefBonus();
		defBonus.setBonusId(bonusId);
		return definitionService.deleteDefBonus(defBonus);
	}
	
	@RequestMapping(value="/findClassRateBonus/{userId}", method = RequestMethod.POST) 
	public @ResponseBody List<DefBonus> findClassRateBonus(@PathVariable("userId") int userId , HttpServletRequest request ){
		return definitionService.findByUserIdAndBonusTypeAndBonusIsType(userId, BonusTypes.BONUS_TYPE_CLASS, BonusTypes.BONUS_IS_TYPE_RATE);
	}
	
	@RequestMapping(value="/findClassStaticBonus/{userId}", method = RequestMethod.POST) 
	public @ResponseBody List<DefBonus> findClassStaticBonus(@PathVariable("userId") int userId , HttpServletRequest request ){
		return definitionService.findByUserIdAndBonusTypeAndBonusIsType(userId, BonusTypes.BONUS_TYPE_CLASS, BonusTypes.BONUS_IS_TYPE_STATIC);
	}
	
	@RequestMapping(value="/findClassStaticRateBonus/{userId}", method = RequestMethod.POST) 
	public @ResponseBody List<DefBonus> findClassStaticRateBonus(@PathVariable("userId") int userId , HttpServletRequest request ){
		return definitionService.findByUserIdAndBonusTypeAndBonusIsType(userId, BonusTypes.BONUS_TYPE_CLASS, BonusTypes.BONUS_IS_TYPE_STATIC_RATE);
	}
	
	
	
	
	@RequestMapping(value="/findPersonalRateBonus/{userId}", method = RequestMethod.POST) 
	public @ResponseBody List<DefBonus> findPersonalRateBonus(@PathVariable("userId") int userId , HttpServletRequest request ){
		return definitionService.findByUserIdAndBonusTypeAndBonusIsType(userId, BonusTypes.BONUS_TYPE_PERSONAL, BonusTypes.BONUS_IS_TYPE_RATE);
	}
	
	@RequestMapping(value="/findPersonalStaticBonus/{userId}", method = RequestMethod.POST) 
	public @ResponseBody List<DefBonus> findPersonalStaticBonus(@PathVariable("userId") int userId , HttpServletRequest request ){
		return definitionService.findByUserIdAndBonusTypeAndBonusIsType(userId, BonusTypes.BONUS_TYPE_PERSONAL, BonusTypes.BONUS_IS_TYPE_STATIC);
	}
	
	@RequestMapping(value="/findPersonalStaticRateBonus/{userId}", method = RequestMethod.POST) 
	public @ResponseBody List<DefBonus> findPersonalStaticRateBonus(@PathVariable("userId") int userId , HttpServletRequest request ){
		return definitionService.findByUserIdAndBonusTypeAndBonusIsType(userId, BonusTypes.BONUS_TYPE_PERSONAL, BonusTypes.BONUS_IS_TYPE_STATIC_RATE);
	}
	
	
	
	
}

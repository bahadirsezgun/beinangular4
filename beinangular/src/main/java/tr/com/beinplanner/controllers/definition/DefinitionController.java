package tr.com.beinplanner.controllers.definition;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.definition.dao.DefCalendarTimes;
import tr.com.beinplanner.definition.dao.DefFirm;
import tr.com.beinplanner.definition.service.DefinitionService;
import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.ResultStatuObj;

@RestController
@RequestMapping("/bein/definition")
public class DefinitionController {

	@Autowired
	DefinitionService definitionService;
	
	@Autowired
	LoginSession loginSession;
	
	@PostMapping(value="/defCalendarTimes/create")
	public  @ResponseBody HmiResultObj createDefCalendarTimes(@RequestBody DefCalendarTimes defCalendarTimes) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		defCalendarTimes.setFirmId(loginSession.getUser().getFirmId());
		defCalendarTimes=definitionService.createDefCalendarTimes(defCalendarTimes);
		return hmiResultObj;
	}
	
	@PostMapping(value="/defCalendarTimes/find")
	public  @ResponseBody DefCalendarTimes findDefCalendarTimes() {
		return definitionService.findCalendarTimes(loginSession.getUser().getFirmId());
	}
	
	@PostMapping(value="/firm/find")
	public  @ResponseBody DefFirm findDefFirm() {
		return definitionService.findFirm(loginSession.getUser().getFirmId());
	}
	
	@PostMapping(value="/firm/create")
	public  @ResponseBody HmiResultObj createDefFirm(@RequestBody DefFirm defFirm) {
		
		DefFirm defF=definitionService.findFirm(loginSession.getUser().getFirmId());
		
		defFirm.setFirmId(loginSession.getUser().getFirmId());
		defFirm.setCreateTime(new Date());
		defFirm.setFirmRestriction(defF.getFirmRestriction());
		defFirm.setFirmGroupId(defF.getFirmGroupId());
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		
		try {
			definitionService.createFirm(defFirm);
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			
		} catch (Exception e) {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_FAIL_STR);
			
		}
		
		
		return hmiResultObj;
		
		
		
	}
}

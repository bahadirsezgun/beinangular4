package tr.com.beinplanner.controllers.settings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.settings.dao.PtGlobal;
import tr.com.beinplanner.settings.dao.PtRules;
import tr.com.beinplanner.settings.service.SettingsService;
import tr.com.beinplanner.util.ResultStatuObj;

@RestController
@RequestMapping("/bein/settings")
public class SettingsController {

	
	@Autowired
	LoginSession loginSession;
	
	@Autowired
	SettingsService settingsService;
	
	@PostMapping(value="/global/create")
	public  @ResponseBody HmiResultObj createPtGlobal(@RequestBody PtGlobal ptGlobal) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		PtGlobal ptgInDb=settingsService.findPtGlobalByFirmId(loginSession.getUser().getFirmId());
		if(ptgInDb!=null) {
			ptGlobal.setGlbId(ptgInDb.getGlbId());
		}
		
		ptGlobal.setFirmId(loginSession.getUser().getFirmId());
		ptGlobal=settingsService.createPtGlobal(ptGlobal);
		
		hmiResultObj.setResultObj(ptGlobal);
		
		return hmiResultObj;
	}
	
	
	@PostMapping(value="/rule/create")
	public  @ResponseBody HmiResultObj createRules(@RequestBody PtRules ptRules) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		PtRules ptr=settingsService.findByRuleIdAndFirmId(ptRules.getRuleId(), loginSession.getUser().getFirmId());
		
		if(ptr!=null) {
			ptRules.setPtrId(ptr.getPtrId());
		}
		
		ptRules.setFirmId(loginSession.getUser().getFirmId());
		ptRules=settingsService.createPtRules(ptRules);
		
		hmiResultObj.setResultObj(ptRules);
		
		return hmiResultObj;
	}
	
	@PostMapping(value="/rule/findAll")
	public  @ResponseBody List<PtRules> findAllRules() {
		return settingsService.findPtRulesByFirmId( loginSession.getUser().getFirmId());
	}
	
}

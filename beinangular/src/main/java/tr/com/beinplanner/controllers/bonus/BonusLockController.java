package tr.com.beinplanner.controllers.bonus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.settings.dao.PtLock;
import tr.com.beinplanner.settings.service.SettingsService;
import tr.com.beinplanner.util.BonusLockUtil;
import tr.com.beinplanner.util.ResultStatuObj;

@RestController
@RequestMapping("/bein/bonuslock")
public class BonusLockController {

	@Autowired
	SettingsService settingsService;
	
	@Autowired
	LoginSession loginSession;
	
	
	@PostMapping(value="/findBonusLock")
	public HmiResultObj findBonusLock() {
		HmiResultObj hmiResultObj=new HmiResultObj();
		PtLock ptLock=settingsService.findPtLock(loginSession.getUser().getFirmId());
		
		if(ptLock==null) {
			ptLock=new PtLock();
			ptLock.setFirmId(loginSession.getUser().getFirmId());
			ptLock.setBonusLock(BonusLockUtil.BONUS_LOCK_FLAG);
			settingsService.createPtLock(ptLock);
		}
		
		if(ptLock.getBonusLock()==BonusLockUtil.BONUS_LOCK_FLAG) {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
		}else {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		}
		return hmiResultObj;
	}
	
	@PostMapping(value="/lock")
	public HmiResultObj lock() {
		HmiResultObj hmiResultObj=new HmiResultObj();
		PtLock ptLock=settingsService.findPtLock(loginSession.getUser().getFirmId());
		if(ptLock.getBonusLock()==BonusLockUtil.BONUS_LOCK_FLAG) {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			ptLock.setBonusLock(BonusLockUtil.BONUS_UNLOCK_FLAG);
			settingsService.createPtLock(ptLock);
		}else {
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			ptLock.setBonusLock(BonusLockUtil.BONUS_LOCK_FLAG);
			settingsService.createPtLock(ptLock);
		}
		return hmiResultObj;
	}
	
}

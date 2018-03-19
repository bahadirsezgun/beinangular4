package tr.com.beinplanner.controllers.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.settings.dao.PacketRestriction;
import tr.com.beinplanner.settings.dao.PtGlobal;
import tr.com.beinplanner.settings.service.SettingsService;
import tr.com.beinplanner.user.dao.User;
/**
 * 
 * @author Bahadır Sezgun
 * @comment Her firmanın kendine ait ayarları (Dil,Para birimi, Tarih formatı) bulunmaktadır. Firma bazında bu ayarlar 
 * veritabanından alınır ve AngularJs ile CommonService.setPtGlobal() function aracılığı ile @rootScope javascript objesi 
 * icerisine yerleştirilir.
 *
 */
@RestController
@RequestMapping("/bein/global")
public class GlobalController {

	@Autowired
	LoginSession loginSession;
	
	@Autowired
	SettingsService settingsService;
	
	
	@PostMapping(value="/getGlobals")
	public  @ResponseBody HmiResultObj getGlobals() {
	  PtGlobal ptGlobal=	 settingsService.findPtGlobalByFirmId(loginSession.getUser().getFirmId());
	  HmiResultObj hmiResultObj=new HmiResultObj();
	  hmiResultObj.setResultObj(ptGlobal);
	  return hmiResultObj;
	}
	
	@PostMapping(value="/getRestrictions")
	public  @ResponseBody HmiResultObj getRestrictions() {
	  PacketRestriction packetRestriction=	 loginSession.getPacketRestriction();
	  HmiResultObj hmiResultObj=new HmiResultObj();
	  hmiResultObj.setResultObj(packetRestriction);
	  return hmiResultObj;
	}
	
	@PostMapping(value="/getSessionUser")
	public  @ResponseBody HmiResultObj getSessionUser() {
	  User user=	 loginSession.getUser();
	  HmiResultObj hmiResultObj=new HmiResultObj();
	  hmiResultObj.setResultObj(user);
	  return hmiResultObj;
	}
	
}

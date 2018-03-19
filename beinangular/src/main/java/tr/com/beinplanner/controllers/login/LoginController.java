package tr.com.beinplanner.controllers.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.dao.User;
@RestController
@RequestMapping("/bein/login")
public class LoginController {
	@Autowired
	LoginSession loginSession;
	
	@PostMapping(value="/getLoginUser")
	public  @ResponseBody HmiResultObj getLoginUser() throws CloneNotSupportedException {
	   User user=(User)(loginSession.getUser()).clone() ;
	   user.setPassword(null);
	   HmiResultObj hmiResultObj=new HmiResultObj();
	   hmiResultObj.setResultObj(user);
	  return hmiResultObj;
	}
	
	
	
}

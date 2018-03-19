package tr.com.beinplanner.controllers.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.menu.dao.MenuRoleTbl;
import tr.com.beinplanner.menu.dao.MenuTbl;
import tr.com.beinplanner.menu.service.MenuService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.UserTypes;

@RestController
@RequestMapping("/bein/menu")
public class MenuController {

	@Autowired
	MenuService menuService;
	
	
	
	@Autowired
	LoginSession loginSession;
	
	@PostMapping(value="/getMenuLeft")
	public  @ResponseBody HmiResultObj getMenuLeft() {
		List<MenuTbl> menuTbl= menuService.findSideUpperMenuByUserType(loginSession.getUser().getUserType(), loginSession.getUser().getFirmId());
		menuTbl.forEach(m->{
			 m.setMenuSubTbls(menuService.findSideSubMenuByUserType(loginSession.getUser().getUserType(), m.getMenuId() , loginSession.getUser().getFirmId()));
		   });
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(menuTbl);
		return hmiResultObj;
	}
	
	@PostMapping(value="/getTopMenu")
	public  @ResponseBody HmiResultObj getTopMenu() {
		List<MenuTbl> menuTbl= menuService.findTopMenuByUserType(loginSession.getUser().getUserType(), loginSession.getUser().getFirmId());
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(menuTbl);
		return hmiResultObj;
	}
	
	@PostMapping(value="/getDashboardMenu")
	public  @ResponseBody HmiResultObj getDashboardMenu(HttpServletRequest request ) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		
		
		MenuTbl menuTbl=menuService.findMenuDashboardByUserTypeAndFirmId(loginSession.getUser().getUserType(),loginSession.getUser().getFirmId());
		hmiResultObj.setResultObj(menuTbl);
		return hmiResultObj;
	}
	
	
	
	@PostMapping(value="/findMenuLeft/{userType}")
	public  @ResponseBody HmiResultObj findMenuLeft(@PathVariable ("userType") int userType) {
		List<MenuTbl> menuTbl= menuService.findUserAuthorizedMenus(userType, loginSession.getUser().getFirmId());
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(menuTbl);
		return hmiResultObj;
	}
	
	@PostMapping(value="/findTopMenu/{userType}")
	public  @ResponseBody HmiResultObj findTopMenu(@PathVariable ("userType") int userType) {
		List<MenuTbl> menuTbl= menuService.findAllTopMenu(userType, loginSession.getUser().getFirmId());
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(menuTbl);
		return hmiResultObj;
	}
	
	@PostMapping(value="/findDashboardMenu/{userType}")
	public  @ResponseBody HmiResultObj findDashboardMenu(@PathVariable ("userType") int userType) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		MenuTbl menuTbl=menuService.findMenuDashboardByUserTypeAndFirmId(userType,loginSession.getUser().getFirmId());
		hmiResultObj.setResultObj(menuTbl);
		return hmiResultObj;
	}
	
	
	@PostMapping(value="/createMenuRole")
	public  @ResponseBody HmiResultObj createMenuRole(@RequestBody MenuRoleTbl menuRoleTbl) {
		menuRoleTbl.getPk().setFirmId(loginSession.getUser().getFirmId());
		return menuService.createMenuRoleTbl(menuRoleTbl);
	}
	
	@PostMapping(value="/deleteMenuRole")
	public  @ResponseBody HmiResultObj deleteMenuRole(@RequestBody MenuRoleTbl menuRoleTbl) {
		menuRoleTbl.getPk().setFirmId(loginSession.getUser().getFirmId());
		return menuService.deleteMenuRoleTbl(menuRoleTbl);
	}
	
	@PostMapping(value="/changeDashboardMenuRole")
	public  @ResponseBody HmiResultObj changeDashboardMenuRole(@RequestBody MenuRoleTbl menuRoleTbl) {
		menuRoleTbl.getPk().setFirmId(loginSession.getUser().getFirmId());
		MenuRoleTbl merTbl=menuService.findMenuRoleTblForDashboard(menuRoleTbl.getPk().getRoleId(), loginSession.getUser().getFirmId());
		menuService.deleteMenuRoleTbl(merTbl);
		return menuService.createMenuRoleTbl(menuRoleTbl);
	}
}

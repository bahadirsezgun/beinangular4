package tr.com.beinplanner.user.iuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tr.com.beinplanner.menu.dao.MenuSubTbl;
import tr.com.beinplanner.menu.dao.MenuTbl;
import tr.com.beinplanner.menu.service.MenuService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.ResultStatuObj;
import tr.com.beinplanner.util.UserTypes;

@Component(value="processSuperManager")
@Scope("prototype")
public class ProcessSuperManager implements ProcessInterface {

	@Autowired
	MenuService menuService;

	@Autowired
	UserService userService;

	
	
	
	@Override
	public List<MenuTbl> getMenuSide(int firmId) {
		List<MenuTbl> menuTbls=menuService.findSideUpperMenuByUserType(UserTypes.USER_TYPE_MANAGER_INT,firmId);
		
		for (MenuTbl menuTbl : menuTbls) {
			List<MenuSubTbl> menuSubTbls=menuService.findSideSubMenuByUserType(UserTypes.USER_TYPE_MANAGER_INT, menuTbl.getMenuId(),firmId);
			menuTbl.setMenuSubTbls(menuSubTbls);
		}
		
		
		return menuTbls;
		
	}

	@Override
	public List<MenuTbl> getMenuTop(int firmId) {
		List<MenuTbl> menuTopTbls=menuService.findTopMenuByUserType(UserTypes.USER_TYPE_MANAGER_INT,firmId);
		return menuTopTbls;
	}

	

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public HmiResultObj canUserDelete(User user) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		
		return hmiResultObj;
	}
}

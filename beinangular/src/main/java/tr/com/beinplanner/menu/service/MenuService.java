package tr.com.beinplanner.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.menu.dao.MenuRoleEmbededId;
import tr.com.beinplanner.menu.dao.MenuRoleTbl;
import tr.com.beinplanner.menu.dao.MenuSubTbl;
import tr.com.beinplanner.menu.dao.MenuTbl;
import tr.com.beinplanner.menu.repository.MenuRepository;
import tr.com.beinplanner.menu.repository.MenuRoleRepository;
import tr.com.beinplanner.menu.repository.MenuSubRepository;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.MenuUtil;
import tr.com.beinplanner.util.ResultStatuObj;
import tr.com.beinplanner.util.UserTypes;


@Service
@Qualifier("menuService")
public class MenuService {

	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	MenuSubRepository menuSubRepository;
	
	@Autowired
	MenuRoleRepository menuRoleRepository;
	
	
	public HmiResultObj createMenuRoleTbl(MenuRoleTbl menuRoleTbl) {
		menuRoleTbl=menuRoleRepository.save(menuRoleTbl);
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultObj(menuRoleTbl);
		return hmiResultObj;
	}
	
	public HmiResultObj deleteMenuRoleTbl(MenuRoleTbl menuRoleTbl) {
		menuRoleRepository.delete(menuRoleTbl);
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		return hmiResultObj;
	}
	
	
	public MenuRoleTbl findMenuRoleTblForDashboard(int roleId,int firmId) {
		return menuRoleRepository.findDashboardMenuRole(roleId, firmId);
	}
	
	public List<MenuTbl> findTopMenuByUserType(int userType,long firmId){
		if(userType==UserTypes.USER_TYPE_ADMIN_INT)
			firmId=MenuUtil.ADMIN_MENU_DEFINITION_FIRM_ID;
		return menuRepository.findTopMenuByUserType(userType, firmId);
	}

	public List<MenuTbl> findSideUpperMenuByUserType(int userType,long firmId){
		if(userType==UserTypes.USER_TYPE_ADMIN_INT)
			firmId=MenuUtil.ADMIN_MENU_DEFINITION_FIRM_ID;
		return menuRepository.findSideUpperMenuByUserType(userType, firmId);
	}
	
	public List<MenuSubTbl> findSideSubMenuByUserType(int userType, long upperMenu,long firmId){
		if(userType==UserTypes.USER_TYPE_ADMIN_INT)
			firmId=MenuUtil.ADMIN_MENU_DEFINITION_FIRM_ID;
		return menuSubRepository.findSideSubMenuByUserType(userType, upperMenu, firmId);
	}
	
	
	public MenuTbl findMenuDashboardByUserTypeAndFirmId(int userType,int firmId){
		if(userType==UserTypes.USER_TYPE_ADMIN_INT)
			firmId=MenuUtil.ADMIN_MENU_DEFINITION_FIRM_ID;
		return menuRepository.findMenuDashboardByUserTypeAndFirmId(userType, firmId);
	}
	
	
	public List<MenuTbl> findUserAuthorizedMenus(int authUserType,long firmId){
		
		
		
		List<MenuTbl> menuAllTbls=menuRepository.findSideUpperMenuByUserType(UserTypes.getUserTypeInt(UserTypes.USER_TYPE_ADMIN), MenuUtil.ADMIN_MENU_DEFINITION_FIRM_ID);
		for (MenuTbl menuAllTbl : menuAllTbls) {
			List<MenuSubTbl> menuLevel2s=menuSubRepository.findSideSubMenuByUserType(UserTypes.getUserTypeInt(UserTypes.USER_TYPE_ADMIN), menuAllTbl.getMenuId(),MenuUtil.ADMIN_MENU_DEFINITION_FIRM_ID);
			menuAllTbl.setMenuSubTbls(menuLevel2s);
		}
		
		List<MenuTbl> menuAuthTbls=menuRepository.findSideUpperMenuByUserType(authUserType,firmId);
		
		
		for (MenuTbl menuTbl : menuAllTbls) {
			menuTbl.setAuthority(0);
			for (MenuTbl menuAuthTbl : menuAuthTbls) {
				if(menuAuthTbl.getMenuId()==menuTbl.getMenuId()){
					menuTbl.setAuthority(1);
					break;
				}
			}
			if(menuTbl.getAuthority()==1){
				List<MenuSubTbl> menuAuthLevelTbls=menuSubRepository.findSideSubMenuByUserType(authUserType, menuTbl.getMenuId(),firmId);
				List<MenuSubTbl> menuAllLevel2s=menuTbl.getMenuSubTbls();
				for (MenuSubTbl menuAllLevel2 : menuAllLevel2s) {
					menuAllLevel2.setAuthority(0);
					for (MenuSubTbl menuAuthLevel2 : menuAuthLevelTbls) {
						if(menuAllLevel2.getMenuId()==menuAuthLevel2.getMenuId()){
							menuAllLevel2.setAuthority(1);
							break;
						}
					}
				}
			}
			
			
		}
		return menuAllTbls;
	}
	
	public List<MenuTbl> findAllTopMenu(int userType,int firmId) {
		List<MenuTbl> menuAllTbls=menuRepository.findTopMenuByUserType(UserTypes.getUserTypeInt(UserTypes.USER_TYPE_ADMIN), MenuUtil.ADMIN_MENU_DEFINITION_FIRM_ID);
		List<MenuTbl> menuAuthTbls=menuRepository.findTopMenuByUserType(userType, firmId);
		
		for (MenuTbl menuTbl : menuAllTbls) {
			menuTbl.setAuthority(0);
			for (MenuTbl menuAuthTbl : menuAuthTbls) {
				if(menuAuthTbl.getMenuId()==menuTbl.getMenuId()){
					menuTbl.setAuthority(1);
					break;
				}
			}
			
		}
		return menuAllTbls;
	}
	
}

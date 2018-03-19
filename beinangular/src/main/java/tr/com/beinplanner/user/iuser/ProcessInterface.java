package tr.com.beinplanner.user.iuser;

import java.util.List;

import tr.com.beinplanner.menu.dao.MenuTbl;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.dao.User;

public interface ProcessInterface {

	
	public HmiResultObj canUserDelete(User user);

	public List<MenuTbl> getMenuSide(int firmId);
	
	public List<MenuTbl> getMenuTop(int firmId);
}

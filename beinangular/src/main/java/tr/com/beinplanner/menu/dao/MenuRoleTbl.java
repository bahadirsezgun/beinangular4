package tr.com.beinplanner.menu.dao;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "menu_role_tbl") 
public class MenuRoleTbl {

	@EmbeddedId
	private MenuRoleEmbededId pk;
	
	@Column(name="ROLE_NAME")
	private String roleName;

	public MenuRoleEmbededId getPk() {
		return pk;
	}

	public void setPk(MenuRoleEmbededId pk) {
		this.pk = pk;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	
}

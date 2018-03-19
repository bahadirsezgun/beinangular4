package tr.com.beinplanner.menu.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "menu_tbl") 
public class MenuSubTbl {

	@Id
	@Column(name="MENU_ID")
	private long menuId;
	
	@Column(name="MENU_NAME")
	private String menuName;
	
	@Column(name="UPPER_MENU_ID")
	private long upperMenuId;
	
	@Column(name="MENU_LINK")
	private String menuLink;
	
	@Column(name="MENU_COMMENT")
	private String menuComment;
	
	@Column(name="MENU_SIDE")
	private long menuSide;
	
	@Column(name="MENU_CLASS")
	private String menuClass;

	@Transient
	private int authority;

	
	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public long getUpperMenuId() {
		return upperMenuId;
	}

	public void setUpperMenuId(long upperMenuId) {
		this.upperMenuId = upperMenuId;
	}

	public String getMenuLink() {
		return menuLink;
	}

	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}

	public String getMenuComment() {
		return menuComment;
	}

	public void setMenuComment(String menuComment) {
		this.menuComment = menuComment;
	}

	public long getMenuSide() {
		return menuSide;
	}

	public void setMenuSide(long menuSide) {
		this.menuSide = menuSide;
	}

	public String getMenuClass() {
		return menuClass;
	}

	public void setMenuClass(String menuClass) {
		this.menuClass = menuClass;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}
	
	
}

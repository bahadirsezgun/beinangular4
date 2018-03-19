package tr.com.beinplanner.settings.dao;
/**
 * 
 * @author Bahadır Sezgun
 * @comment If the customer did not define any of programs than not need to 
 *          <br>search at at the database. So, this is for performance matter.
 *  
 */
public class PacketRestriction {
    
	private int personalRestriction;
	private int groupRestriction;
	private int membershipRestriction;
	private int multiRestriction;
	
	public int getMultiRestriction() {
		return multiRestriction;
	}
	public void setMultiRestriction(int multiRestriction) {
		this.multiRestriction = multiRestriction;
	}
	public int getPersonalRestriction() {
		return personalRestriction;
	}
	public void setPersonalRestriction(int personalRestriction) {
		this.personalRestriction = personalRestriction;
	}
	public int getGroupRestriction() {
		return groupRestriction;
	}
	public void setGroupRestriction(int groupRestriction) {
		this.groupRestriction = groupRestriction;
	}
	public int getMembershipRestriction() {
		return membershipRestriction;
	}
	public void setMembershipRestriction(int membershipRestriction) {
		this.membershipRestriction = membershipRestriction;
	}
	
	
}

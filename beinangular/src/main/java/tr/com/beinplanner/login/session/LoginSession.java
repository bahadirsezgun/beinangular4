package tr.com.beinplanner.login.session;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import tr.com.beinplanner.settings.dao.PacketRestriction;
import tr.com.beinplanner.settings.dao.PtGlobal;
import tr.com.beinplanner.settings.dao.PtRules;
import tr.com.beinplanner.user.dao.User;
/**
 * 
 * @author Bahadir Sezgun
 * @category Session
 * @comment Kullanıcı sisteme giris yaptıktan sonra sistemde kaldığı <br> 
 *          sürece session obje firma için tutulur.
 * @see UserSecurityService <br>
 *      SecurityConfig (Web Project Security Config) 
 */
@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class LoginSession implements Serializable {

	public List<PtRules> getPtRules() {
		return ptRules;
	}

	public void setPtRules(List<PtRules> ptRules) {
		this.ptRules = ptRules;
	}

	private static final long serialVersionUID = 1L;

	private User user;

	private PtGlobal ptGlobal;
	
	List<PtRules> ptRules;
	
	PacketRestriction packetRestriction;
	
	public PtGlobal getPtGlobal() {
		return ptGlobal;
	}

	public void setPtGlobal(PtGlobal ptGlobal) {
		this.ptGlobal = ptGlobal;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PacketRestriction getPacketRestriction() {
		return packetRestriction;
	}

	public void setPacketRestriction(PacketRestriction packetRestriction) {
		this.packetRestriction = packetRestriction;
	}
	
	
	
	
}

package tr.com.beinplanner.security.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import tr.com.beinplanner.user.dao.User;

public class CustomUserDetails extends User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomUserDetails(final User user) {
        super(user);
    }

  

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}



	@Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

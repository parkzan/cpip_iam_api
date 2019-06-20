package co.prior.iam.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = -2609017211284130218L;

	@JsonIgnore
	private long userId;
	
	private String userCode;

	@JsonIgnore
	private String userPassword;
	
	private String localFirstName;
	
	private String localMiddleName;
	
	private String localLastName;
	
	private String engFirstName;
	
	private String engMiddleName;
	
	private String engLastName;
	
	@JsonIgnore
	private boolean isAccountNonLocked;

	@JsonIgnore
	private boolean isEnabled;
	
	@JsonIgnore
	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return userPassword;
	}

	@Override
	public String getUsername() {
		return userCode;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}

package org.iesalixar.poriad.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserMain implements UserDetails{
	
	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private String email;

	private String address;

	private boolean enabled;

	private boolean newsletter;

	private String phone;

	private String urlImages;
	
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public boolean getNewsletter() {
		return newsletter;
	}

	public String getPhone() {
		return phone;
	}

	public String getUrlImages() {
		return urlImages;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setUrlImages(String urlImages) {
		this.urlImages = urlImages;
	}

	public UserMain(String firstName, String lastName, String username, String password, String email, String address,
			boolean newsletter, String phone, String urlImages,
			Collection<? extends GrantedAuthority> authorities) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.newsletter = newsletter;
		this.phone = phone;
		this.urlImages = urlImages;
		this.authorities = authorities;
	}
	
	public static UserMain build(UserSnowy user) {
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role
				.getRoleName().name())).collect(Collectors.toList());
		
		return new UserMain(user.getFirstName(), user.getLastName(),
				user.getUsername(), user.getPassword(), user.getEmail(),
				user.getAddress(), user.isNewsletter(), user.getPhone(),
				user.getUrlImages(), authorities);
	}
}

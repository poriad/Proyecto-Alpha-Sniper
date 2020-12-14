package org.iesalixar.poriad.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class JwtDto {

	private String token;

	private String bearer = "Bearer";

	private String userName;

	private Collection<? extends GrantedAuthority> authorities;

	public JwtDto(String token, String userName, Collection<? extends GrantedAuthority> authorities) {
		this.token = token;
		this.userName = userName;
		this.authorities = authorities;
	}

}

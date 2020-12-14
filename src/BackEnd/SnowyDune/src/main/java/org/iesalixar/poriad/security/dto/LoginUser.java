package org.iesalixar.poriad.security.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUser {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}

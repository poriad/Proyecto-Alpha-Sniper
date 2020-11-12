package org.iesalixar.poriad.security.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {
	
//	@NotBlank
//	private String nombre;
	
	@NotBlank
	private String username;
	
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	private Set<String> roles = new HashSet<>();
}

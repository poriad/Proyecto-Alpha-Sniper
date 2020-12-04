package org.iesalixar.poriad.security.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.iesalixar.poriad.entity.Cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {
	
	private String firstName;
	
	private String lastName;
	
	@NotBlank
	private String username;
	
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	private String address;
	
	private String phone;
	
	private boolean newsletter;
	
	private Set<String> roles = new HashSet<>();
	
	
	
}

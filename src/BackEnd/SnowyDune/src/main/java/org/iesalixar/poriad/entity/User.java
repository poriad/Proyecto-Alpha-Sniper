package org.iesalixar.poriad.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table
public class User {
	
	@Id 
	@GeneratedValue
	private int idUser;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String username;
	private String password;
	private int enabled;
	private int newsletter;
	private String phone;
	private String urlImages;
	

}

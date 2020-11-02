package org.iesalixar.poriad.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserSnowy {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String address;
	
	private String username;
	
	@JsonIgnore
	private String password;
	
	private boolean enabled;
	
	private int newsletter;
	
	private String phone;
	
	private String urlImages;
	
	@OneToMany(mappedBy = "user")
	private Set<Payment> payment;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
	private Cart cart;
	
	@OneToMany(mappedBy = "user")
	private Set<Authorities> authorities;
	
	@OneToOne(mappedBy = "user")
	private Trip trip;
	
	@OneToMany(mappedBy = "user")
	private Set<Comment> comments;

}

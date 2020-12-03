package org.iesalixar.poriad.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.iesalixar.poriad.security.entity.UserSnowy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double priceDay;
	
	private String description;
	
	private String name;
	
	private String phone;
	
	private String email;
	
	private String location;
	
	private String country;
	
	private String ulrImages;
	
	private Integer activated;
	
	private int starts;
	
	private int stars;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=true)
	private UserSnowy user; 
	
	@ManyToOne
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;
	
	@JsonIgnore
	@OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL)
	private Set<Comment> comments;
	
	
}

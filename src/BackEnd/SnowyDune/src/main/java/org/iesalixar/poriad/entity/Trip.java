package org.iesalixar.poriad.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class Trip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double totalPrice;
	
	private int totalDays;
	
	private Date entryDate;
	
	@ManyToMany
	@JoinTable(
			  name = "Cart_items", 
			  joinColumns = @JoinColumn(name = "trip_id"), 
			  inverseJoinColumns = @JoinColumn(name = "cart_id"))
	private Set<Cart> cart;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "hotel_id", nullable = true)
	private Hotel hotel;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "skiMaterial_id", nullable = true)
	private SkiMaterial skiMaterial;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "carRental_id", nullable = true)
	private CarRental carRental;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "classes_id", nullable = true)
	private Classes classes;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserSnowy user;
	
	
}

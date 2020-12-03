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
public class Trip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double totalPrice;
	
	private int totalDays;
	
	private Date entryDate;
	
	private int totalPersons;
	
	private int checkout;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = true)
	private Cart cart;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "payment_id", nullable = true)
	private Payment payment;
	
	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "hotel_id",referencedColumnName = "id", nullable = true)
	private Hotel hotel;
	
	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "skiMaterial_id",referencedColumnName = "id", nullable = true)
	private SkiMaterial skiMaterial;
	
	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "carRental_id", referencedColumnName = "id",nullable = true)
	private CarRental carRental;
	
	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "classes_id",referencedColumnName = "id", nullable = true)
	private Classes classes;
	
	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "station_id",referencedColumnName = "id", nullable = true)
	private Station station;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserSnowy user;
	
	
}

package org.iesalixar.poriad.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.iesalixar.poriad.security.entity.UserSnowy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String comment;
	
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
	
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	private UserSnowy user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;
	
}

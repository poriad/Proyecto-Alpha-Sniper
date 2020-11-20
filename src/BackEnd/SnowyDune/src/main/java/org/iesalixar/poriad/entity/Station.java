package org.iesalixar.poriad.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NAME")
	private String name;

	private String location;

	private String country;

	private Date openingDate;

	private Date closingDate;

	private String description;

	private String urlImages;
	
	private Integer activated;
	
	@JsonIgnore
	@OneToMany(mappedBy = "station")
	private Set<Trip> trip;
	
	@JsonIgnore
	@OneToMany(mappedBy = "station")
	private Set<SkiMaterial> skiMaterial;
	
	@JsonIgnore
	@OneToMany(mappedBy = "station")
	private Set<Hotel> hotels;
	
	@JsonIgnore
	@OneToMany(mappedBy = "station")
	private Set<CarRental> carRental;
	
	@JsonIgnore
	@OneToMany(mappedBy = "station")
	private Set<Classes> classes;
	
	@JsonIgnore
	@OneToMany(mappedBy = "station")
	private Set<Forfait> forfait;
	
	@JsonIgnore
	@OneToMany(mappedBy = "station")
	private Set<Comment> comments;
	
	
}

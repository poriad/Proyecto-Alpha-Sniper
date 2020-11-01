package org.iesalixar.poriad.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

	private String name;

	private String location;

	private String country;

	private Date openingDate;

	private Date closingDate;

	private String description;

	private String urlImages;
	
	@OneToOne(mappedBy = "station")
	private Trip trip;

	@OneToMany(mappedBy = "station")
	private Set<SkiMaterial> skiMaterial;
	
	@OneToMany(mappedBy = "station")
	private Set<Hotel> hotels;
	
	@OneToMany(mappedBy = "station")
	private Set<CarRental> carRental;
	
	@OneToMany(mappedBy = "station")
	private Set<Classes> classes;
	
	@OneToMany(mappedBy = "station")
	private Set<Forfait> forfait;
	
	@OneToMany(mappedBy = "station")
	private Set<Comment> comments;
	
}

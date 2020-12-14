package org.iesalixar.poriad.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	private String location;

	private String country;

	private Date openingDate;

	private Date closingDate;

	@Column(columnDefinition = "varchar(1400)")
	private String description;

	private String urlImages;

	private Integer activated;

	private Integer forfaitPrice;

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

	@OneToOne(mappedBy = "station")
	private Forfait forfait;

	@JsonIgnore
	@OneToMany(mappedBy = "station")
	private Set<Comment> comments;

	public Station(String name, String location, String country, Date openingDate, Date closingDate, String description,
			String urlImages, Integer activated) {
		this.name = name;
		this.location = location;
		this.country = country;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.description = description;
		this.urlImages = urlImages;
		this.activated = activated;
	}

}

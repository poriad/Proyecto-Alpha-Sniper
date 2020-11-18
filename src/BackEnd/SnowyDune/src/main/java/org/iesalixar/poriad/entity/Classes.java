package org.iesalixar.poriad.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Classes {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Double priceHour;
	
	private String description;
	
	private String name;
	
	private String phone;
	
	private String location;
	
	private String urlImages;
	
	private int activated;
	
	@ManyToOne
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;
	
	@JsonIgnore
	@OneToMany(mappedBy = "classes")
	private Set<Comment> comments;
	
	@OneToMany(mappedBy = "classes")
	private Set<Trip> trip;

}

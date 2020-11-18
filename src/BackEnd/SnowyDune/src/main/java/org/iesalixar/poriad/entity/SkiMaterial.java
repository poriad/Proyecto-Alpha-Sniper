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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SkiMaterial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String location;
	
	private String phone;
	
	private String description;
	
	private Double priceDay;
	
	private String name;
	
	private Integer activated;
	
	private String urlImages;
	
	
	@ManyToOne
	@JoinColumn(name="station_id", nullable=true)
	private Station station;
	
	@JsonIgnore
	@OneToMany(mappedBy = "skiMaterial")
	private Set<Comment> comments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "skiMaterial")
	private Set<Trip> trip;
	
}

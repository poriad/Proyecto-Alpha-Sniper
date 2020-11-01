package org.iesalixar.poriad.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	private int numberDays;
	
	private int numberPerson;
	
	private String location;
	
	private String phone;
	
	private String description;
	
	private Double priceDay;
	
	private String name;
	
	private String urlImages;
	
	@ManyToOne
	@JoinColumn(name="station_id", nullable=true)
	private Station station;
	
	@OneToMany(mappedBy = "skiMaterial")
	private Set<Comment> comments;
	
}

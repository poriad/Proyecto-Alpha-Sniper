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
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double priceDay;
	
	private String description;
	
	private String name;
	
	private String location;
	
	private int numberDays;
	
	private String phone;
	
	private String ulrImages;
	
	@ManyToOne
	@JoinColumn(name = "id_hotel", nullable = true)
	private HotelCategory hotelCategory;
	
	@ManyToOne
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;
	
	@OneToMany(mappedBy = "hotel")
	private Set<Comment> comments;
	
	
}

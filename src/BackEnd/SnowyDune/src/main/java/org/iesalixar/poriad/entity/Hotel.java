package org.iesalixar.poriad.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	@JoinColumn(name = "id_hotel_category", nullable = false)
	private HotelCategory hotelCategory;
	
}

package org.iesalixar.poriad.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
public class Hotel {

	@Id
	@GeneratedValue
	private int idHotel;
	private Double priceDay;
	private String description;
	private String name;
	private String location;
	private int numberDays;
	private String phone;
	private String ulrImages;
	
}

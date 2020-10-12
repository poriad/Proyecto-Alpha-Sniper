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
public class CarRental {
	
	@Id
	@GeneratedValue
	private int idCarRental;
	private String name;
	private Double price;
	private String description;
	private String location;
	private int numberDays;
	
}

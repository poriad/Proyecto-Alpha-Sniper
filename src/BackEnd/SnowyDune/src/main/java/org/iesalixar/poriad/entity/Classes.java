package org.iesalixar.poriad.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Classes {
	
	@Id
	@GeneratedValue
	private int idClasses;
	
	private Double priceHour;
	private String description;
	private String name;
	private String phone;
	private String location;
	private Date date;
	private int hours;
	private String urlImages;

}

package org.iesalixar.poriad.entity;

import java.sql.Date;

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
public class Forfait {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int numberForfait;
	
	private Double price;
	
	private String urlImage;
	
	private Date inicialDate;
	
	private int numberDays;
	
	
	@ManyToOne
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;
	
	
	
}

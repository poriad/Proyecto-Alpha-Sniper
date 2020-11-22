package org.iesalixar.poriad.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class Forfait {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double price;
	
	
	@OneToOne
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;

	public Forfait() {
	}


	public Forfait(Long id, Double price, Station station) {
		this.id = id;
		this.price = price;
		this.station = station;
	}


	public Long getId() {
		return id;
	}


	public Double getPrice() {
		return price;
	}

	@JsonIgnore
	public Station getStation() {
		return station;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public void setStation(Station station) {
		this.station = station;
	}
	
	
	
}

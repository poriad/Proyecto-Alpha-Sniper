package org.iesalixar.poriad.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double totalPrice;
	
	private int totalDays;
	
	private Date entryDate;
	
	@ManyToMany
	@JoinTable(
			  name = "Cart_items", 
			  joinColumns = @JoinColumn(name = "trip_id"), 
			  inverseJoinColumns = @JoinColumn(name = "cart_id"))
	private Set<Cart> cart;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserSnowy user;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "station_id", referencedColumnName = "id")
	private Station station;
}

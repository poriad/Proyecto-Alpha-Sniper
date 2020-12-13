package org.iesalixar.poriad.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.iesalixar.poriad.security.entity.UserSnowy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double paymentAmount;
	
	@CreationTimestamp
	private Date paymentDate;
	
	private String paymentType;
	
	private String name;
	
	private String lastName;
	
	private String enterpriseName;
	
	private String nif;
	
	private String address;
	
	private String addressAditional;
	
	private String country;
	
	private String zipCode;
	
	private String province;
	
	private String phone;
	
	private String email;
	
	@OneToMany(mappedBy = "payment")
	private Set<Trip> trip;
	
	@ManyToOne
	@JoinColumn(name="payment_id", nullable=true)
	private PaymentHistory paymentHistory;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=true)
	private UserSnowy user; 
	
}

package org.iesalixar.poriad.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

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
	
	private Date paymentDate;
	
	private String otherDetails;
	
	private String paymentType;
	
	@ManyToOne
	@JoinColumn(name="payment_id", nullable=true)
	private PaymentHistory paymentHistory;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=true)
	private User user; 
	
}

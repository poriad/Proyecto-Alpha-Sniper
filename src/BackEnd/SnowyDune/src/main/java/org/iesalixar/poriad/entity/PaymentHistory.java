package org.iesalixar.poriad.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double paymentAmount;
	
	@CreationTimestamp
	private Date paymentDate;
	
	private String otherDetails;
	
	private String paymentType;
	
	@OneToMany(mappedBy = "paymentHistory")
	private Set<Payment> payments;
}

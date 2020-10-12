package org.iesalixar.poriad.entity;

import java.sql.Date;

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
public class Payment {

	@Id
	@GeneratedValue
	private int idPayment;
	private Double paymentAmount;
	private Date paymentDate;
	private String otherDetails;
	private String paymentType;
	
}

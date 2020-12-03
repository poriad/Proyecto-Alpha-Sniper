package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "payment", path = "payment")
@CrossOrigin
public interface PaymentRepository extends JpaRepository<Payment, Long>{
	
	@Modifying
	@Query(value="UPDATE Payment p SET p.user.id = :userId where p.id = :paymentId")
	void updatePayment(@Param("userId") Long userId,@Param("paymentId") Long paymentId);
	
}

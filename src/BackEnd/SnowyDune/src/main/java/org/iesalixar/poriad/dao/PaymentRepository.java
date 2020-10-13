package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "payment", path = "payment")
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}

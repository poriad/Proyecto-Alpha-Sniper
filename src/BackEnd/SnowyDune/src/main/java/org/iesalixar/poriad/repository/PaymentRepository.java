package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "payment", path = "payment")
@CrossOrigin("http:/localhost:8081")
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}

package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "paymentHistory", path = "payment-history")
@CrossOrigin("http:/localhost:8081")
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long>{

}

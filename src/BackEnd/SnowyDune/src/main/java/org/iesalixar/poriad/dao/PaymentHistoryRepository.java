package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "paymentHistory", path = "payment-history")
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long>{

}

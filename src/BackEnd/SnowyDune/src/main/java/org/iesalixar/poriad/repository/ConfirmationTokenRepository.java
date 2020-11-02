package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{
	
	ConfirmationToken findByConfirmationToken(String confirmationToken);
	
}

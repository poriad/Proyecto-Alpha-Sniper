package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "cart", path = "cart")
@CrossOrigin
public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query(value = "SELECT u.cart.id FROM UserSnowy u where u.id = :id")
	Long getCartByUserId(@Param("id") Long userId);

}

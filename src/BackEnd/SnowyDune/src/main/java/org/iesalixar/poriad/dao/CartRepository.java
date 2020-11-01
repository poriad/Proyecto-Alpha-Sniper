package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{

}

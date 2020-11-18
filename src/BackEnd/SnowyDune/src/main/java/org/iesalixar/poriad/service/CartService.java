package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Cart;
import org.iesalixar.poriad.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {
	
	@Autowired
	CartRepository cartRepository;
	
	public List<Cart> listCart(){
		return cartRepository.findAll();
	}
	
	public void saveCart(Cart cart) {
		cartRepository.save(cart);
	}
	
	public void deleteCart(Long id) {
		cartRepository.deleteById(id);
	}
	
	public boolean existById(Long id) {
		return cartRepository.existsById(id);
		
	}
	
	public Cart findById(Long id) {
		return cartRepository.getOne(id);
	}
}

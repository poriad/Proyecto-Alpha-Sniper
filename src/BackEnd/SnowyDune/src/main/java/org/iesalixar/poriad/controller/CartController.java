package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.CarRental;
import org.iesalixar.poriad.entity.Cart;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.service.CarRentalService;
import org.iesalixar.poriad.service.CartService;
import org.iesalixar.poriad.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car-rental")
@CrossOrigin
public class CartController {

	@Autowired
	CartService cartService;
	
	@Autowired
	CommentService commentService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Cart>> listCart() {
		
		List<Cart> listCart = cartService.listCart();
		
		return new ResponseEntity(listCart,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createCart(@RequestBody Cart cart){
		
		cartService.saveCart(cart);
		
		return new ResponseEntity(cart, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCart(@PathVariable Long id, @RequestBody Cart cartDto){
		
		if(!cartService.existById(id)) {
			return new ResponseEntity(new Mensaje("El comentario no existe"),HttpStatus.NOT_FOUND);
		}
		
		Cart cart = cartService.findById(id);
		
		
		cartService.saveCart(cart);
		
		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable Long id){
		
		if(!cartService.existById(id)) {
			return new ResponseEntity(new Mensaje("El comentario no existe"), HttpStatus.NOT_FOUND);
		}
		
		commentService.deleteCommentsCarRental(id);
		
		cartService.deleteCart(id);
		
		return new ResponseEntity(new Mensaje("Comentario eliminada"), HttpStatus.OK);
		
	}
	
}

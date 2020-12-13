package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.CarRental;
import org.iesalixar.poriad.entity.Cart;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.service.CarRentalService;
import org.iesalixar.poriad.service.CartService;
import org.iesalixar.poriad.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {
	
	final static Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	CartService cartService;
	
	@Autowired
	CommentService commentService;
	
	// Servicio que devuelve la lista de los carritos existentes
	@GetMapping("/list")
	public ResponseEntity<List<Cart>> listCart() {
		
		List<Cart> listCart = cartService.listCart();
		
		logger.info("Servicio consumido /cart/list");
		
		return new ResponseEntity(listCart,HttpStatus.OK);
		
	}
	
	// Servicio que crea un carrito
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createCart(@RequestBody Cart cart){
		
		cartService.saveCart(cart);
		
		logger.info("Servicio consumido /cart/create, " + cart);
		
		return new ResponseEntity(cart, HttpStatus.OK);
	}
	
	// Servicio que actualiza un carrito
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCart(@PathVariable Long id, @RequestBody Cart cartDto){
		
		if(!cartService.existById(id)) {
			
			logger.error("El servicio ha dado error /cart/update/" + id);
			
			return new ResponseEntity(new Mensaje("El Carrito no existe"),HttpStatus.NOT_FOUND);
		}
		
		Cart cart = cartService.findById(id);
		
		cartService.saveCart(cart);
		
		logger.info("Servicio consumido /cart/update/" + id);
		
		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"),HttpStatus.OK);
		
	}
	
	// Servicio que borra un carrito
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable Long id){
		
		if(!cartService.existById(id)) {
			
			logger.error("El servicio ha dado error /cart/delete/" + id);
			
			return new ResponseEntity(new Mensaje("El comentario no existe"), HttpStatus.NOT_FOUND);
		}
		
		commentService.deleteCommentsCarRental(id);
		
		cartService.deleteCart(id);
		
		logger.info("Servicio consumido /cart/delete/" + id);
		
		return new ResponseEntity(new Mensaje("Comentario eliminada"), HttpStatus.OK);
		
	}
	
}

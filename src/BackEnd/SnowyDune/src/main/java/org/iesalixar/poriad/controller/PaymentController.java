package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Cart;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.Payment;
import org.iesalixar.poriad.service.CartService;
import org.iesalixar.poriad.service.CommentService;
import org.iesalixar.poriad.service.PaymentService;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Payment>> listPayment() {
		
		List<Payment> listCart = paymentService.listPayment();
		
		return new ResponseEntity(listCart,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updatePayment")
	public ResponseEntity<?> updatePayment(@RequestParam Long userId, @RequestParam Long paymentId){
		
		paymentService.updatePayment(userId, paymentId);
		
		return new ResponseEntity(new Mensaje("Pago actualizado correctamente"), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createPayment(@RequestBody Payment payment){
		
		paymentService.savePayment(payment);
		
		return new ResponseEntity(payment, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updatePayment(@PathVariable Long id, @RequestBody Payment cartDto){
		
		if(!paymentService.existById(id)) {
			return new ResponseEntity(new Mensaje("El pago no existe"),HttpStatus.NOT_FOUND);
		}
		
		Payment payment = paymentService.findById(id);
		
		
		paymentService.savePayment(payment);
		
		return new ResponseEntity(new Mensaje("Pago actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deletePayment(@PathVariable Long id){
		
		if(!paymentService.existById(id)) {
			return new ResponseEntity(new Mensaje("El pago no existe"), HttpStatus.NOT_FOUND);
		}
		
		paymentService.deletePayment(id);
		
		return new ResponseEntity(new Mensaje("Pago Eliminado"), HttpStatus.OK);
		
	}

}

package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.dto.PaymentIntentDTO;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.Payment;
import org.iesalixar.poriad.service.PaymentService;
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
import org.springframework.web.bind.annotation.RequestParam;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Controller
@RequestMapping("/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

	final static Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	PaymentService paymentService;

	// Servicio que devuelve una lista de pagos
	@GetMapping("/list")
	public ResponseEntity<List<Payment>> listPayment() {

		List<Payment> listCart = paymentService.listPayment();

		logger.info("Servicio consumido /payment/list");

		return new ResponseEntity(listCart, HttpStatus.OK);

	}

	// Servicio que actualiza un pago, relacionandolo con un usuario
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updatePayment")
	public ResponseEntity<?> updatePayment(@RequestParam Long userId, @RequestParam Long paymentId) {

		paymentService.updatePayment(userId, paymentId);

		logger.info("Servicio consumido /payment/updatePayment, identificador de pago: " + paymentId
				+ ", identificador de usuario: " + userId);

		return new ResponseEntity(new Mensaje("Pago actualizado correctamente"), HttpStatus.OK);
	}

	// Servicio que crea un pago
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public ResponseEntity<?> createPayment(@RequestBody Payment payment) {

		paymentService.savePayment(payment);

		logger.info("Servicio consumido /payment/create, pago creado con identificador: " + payment.getId());

		return new ResponseEntity(payment, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/createPayment")
	public ResponseEntity<?> createPaymentStripe() {
		Payment payment = new Payment();

		paymentService.savePayment(payment);

		logger.info("Servicio consumido /payment/createPayment, pago creado con identificador: " + payment.getId());

		return new ResponseEntity(payment, HttpStatus.OK);
	}

	// Servicio que actualiza un pago
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updatePayment(@PathVariable Long id, @RequestBody Payment cartDto) {

		if (!paymentService.existById(id)) {

			logger.error("Error en el servicio /payment/update/" + id);

			return new ResponseEntity(new Mensaje("El pago no existe"), HttpStatus.NOT_FOUND);
		}

		Payment payment = paymentService.findById(id);

		paymentService.savePayment(payment);

		logger.info("Servicio consumido /payment/update/" + id);

		return new ResponseEntity(new Mensaje("Pago actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que borra un pago
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deletePayment(@PathVariable Long id) {

		if (!paymentService.existById(id)) {

			logger.error("Error en el servicio /payment/delete/" + id);

			return new ResponseEntity(new Mensaje("El pago no existe"), HttpStatus.NOT_FOUND);
		}

		paymentService.deletePayment(id);

		logger.info("Servicio consumido /payment/delete/" + id);

		return new ResponseEntity(new Mensaje("Pago Eliminado"), HttpStatus.OK);

	}

	@PostMapping("/paymentIntent")
	public ResponseEntity<?> payment(@RequestBody PaymentIntentDTO paymentIntentDto) throws StripeException {
		PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDto);
		String paymentStr = paymentIntent.toJson();

		return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
	}

	@PostMapping("/confirm/{id}")
	public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException {
		PaymentIntent paymentIntent = paymentService.confirm(id);
		String paymentStr = paymentIntent.toJson();

		return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
	}

	@PostMapping("/cancel/{id}")
	public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException {
		PaymentIntent paymentIntent = paymentService.cancel(id);
		String paymentStr = paymentIntent.toJson();

		return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
	}

}

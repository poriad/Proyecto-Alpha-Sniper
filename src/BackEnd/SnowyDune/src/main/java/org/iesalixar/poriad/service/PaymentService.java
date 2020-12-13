package org.iesalixar.poriad.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iesalixar.poriad.dto.PaymentIntentDTO;
import org.iesalixar.poriad.entity.Cart;
import org.iesalixar.poriad.entity.Payment;
import org.iesalixar.poriad.repository.CartRepository;
import org.iesalixar.poriad.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
@Transactional
public class PaymentService {

	@Autowired
	PaymentRepository paymentRepository;
	
	@Value("${stripe.key.secret}")
	String secretKey;
	
	public List<Payment> listPayment(){
		return paymentRepository.findAll();
	}
	
	public void savePayment(Payment payment) {
		paymentRepository.save(payment);
	}
	
	public void updatePayment(Long userId,Long paymentId) {
		paymentRepository.updatePayment(userId, paymentId);
	}
	
	public void deletePayment(Long id) {
		paymentRepository.deleteById(id);
	}
	
	public boolean existById(Long id) {
		return paymentRepository.existsById(id);
	}
	
	public Payment findById(Long id) {
		return paymentRepository.getOne(id);
	}
	
	
	// Stripe Service
	public PaymentIntent paymentIntent (PaymentIntentDTO paymentIntentDTO) throws StripeException {
		Stripe.apiKey = secretKey;
		
		Map<String,Object> params = new HashMap<>();
		params.put("amount", paymentIntentDTO.getAmount());
		params.put("currency", paymentIntentDTO.getCurrency());
		params.put("description", paymentIntentDTO.getDescription());
		ArrayList payment_method_types = new ArrayList<>();
		payment_method_types.add("card");
		params.put("payment_method_types", payment_method_types);
		
		return PaymentIntent.create(params);
		
	}
	
	public PaymentIntent confirm(String id) throws StripeException {
		Stripe.apiKey = secretKey;
		
		PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
		Map<String,Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		paymentIntent.confirm(params);
		
		return paymentIntent;
		
		
	}
	
	public PaymentIntent cancel(String id) throws StripeException {
		Stripe.apiKey = secretKey;
		
		PaymentIntent paymentIntent = PaymentIntent.retrieve(id);

		paymentIntent.cancel();
		
		return paymentIntent;
		
		
	}
	
}

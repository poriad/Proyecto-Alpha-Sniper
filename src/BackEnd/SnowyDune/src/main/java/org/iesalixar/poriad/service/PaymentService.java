package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Cart;
import org.iesalixar.poriad.entity.Payment;
import org.iesalixar.poriad.repository.CartRepository;
import org.iesalixar.poriad.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {

	@Autowired
	PaymentRepository paymentRepository;
	
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
}

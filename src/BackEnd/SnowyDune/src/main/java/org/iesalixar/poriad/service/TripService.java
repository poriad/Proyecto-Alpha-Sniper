package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Cart;
import org.iesalixar.poriad.entity.Trip;
import org.iesalixar.poriad.repository.CartRepository;
import org.iesalixar.poriad.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripService {

	@Autowired
	TripRepository tripRepository;
	
	public List<Trip> listTrip(){
		return tripRepository.findAll();
	}
	
	public void saveTrip(Trip trip) {
		tripRepository.save(trip);
	}
	
	public void deleteTrip(Long id) {
		tripRepository.deleteById(id);
	}
	
	public boolean existById(Long id) {
		return tripRepository.existsById(id);
		
	}
	public Trip findById(Long id) {
		return tripRepository.getOne(id);
	}
	
}

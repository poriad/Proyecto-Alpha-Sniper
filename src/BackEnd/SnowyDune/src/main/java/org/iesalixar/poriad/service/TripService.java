package org.iesalixar.poriad.service;

import java.util.List;
import java.util.Optional;

import org.iesalixar.poriad.entity.Trip;
import org.iesalixar.poriad.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripService {

	@Autowired
	TripRepository tripRepository;

	public List<Trip> listTrip() {
		return tripRepository.findAll();
	}

	public List<Trip> getTripsInCart(Long id) {
		return tripRepository.getTripsInCart(id);
	}

	public List<Trip> getTripsDoneLastYear(Integer id) {
		return tripRepository.getTripsDoneLastYear(id);
	}

	public List<Trip> getTripsDoneThisYear(Integer id) {
		return tripRepository.getTripsDoneThisYear(id);
	}

	public List<Trip> getTripsInCartForCheckout(Long id) {
		return tripRepository.getTripsInCartForCheckout(id);
	}

	public void saveTrip(Trip trip) {
		tripRepository.save(trip);
	}

	public void deleteTrip(Long id) {
		tripRepository.deleteTrip(id);
	}

	public boolean existById(Long id) {
		return tripRepository.existsById(id);

	}

	public Trip findById(Long id) {
		return tripRepository.getOne(id);
	}

	public Optional<Trip> getById(Long id) {
		return tripRepository.findById(id);
	}

	public void updateTrip(Long id, Long userId, Long stationId, Long hotelId, Long classesId, Long skiMaterialId,
			Long carRentalId, Long cartId) {
		tripRepository.updateTrip(id, userId, stationId, hotelId, classesId, skiMaterialId, carRentalId, cartId);
	}

	public void updateTripToCheckout(Long tripId) {
		tripRepository.updateTripToCheckout(tripId);
	}

	public void updateTripPaymentDone(Long paymentId, Long tripId) {
		tripRepository.updateTripPaymentDone(paymentId, tripId);
	}

}

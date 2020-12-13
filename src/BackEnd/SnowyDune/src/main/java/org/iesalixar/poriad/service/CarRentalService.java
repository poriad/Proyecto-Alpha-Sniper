package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.CarRental;
import org.iesalixar.poriad.repository.CarRentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarRentalService {
	
	@Autowired
	CarRentalRepository carRentalRepository;
	
	public List<CarRental> listCarRental(){
		return carRentalRepository.findAll();
	}
	
	public List<CarRental> listCarRentalStatus(Integer status){
		return carRentalRepository.listCarRentalStatus(status);
	}
	
	public void saveCarRental(CarRental classes) {
		carRentalRepository.save(classes);
	}
	
	public void updateCarRentalStatus(Long id ,Integer status) {
		carRentalRepository.updateCarRentalStatus(id, status);
	}
	
	public void updateStationIdCarRental(Long id ,Long station) {
		carRentalRepository.updateStationIdCarRental(id, station);
	}
	
	public void updateUserIdCarRental(Long id ,Long userid) {
		carRentalRepository.updateUserIdCarRental(id, userid);
	}
	
	public void deleteCarRental(Long id) {
		carRentalRepository.deleteById(id);
	}
	
	public boolean existById(Long id) {
		return carRentalRepository.existsById(id);	
	}
	
	public CarRental findById(Long id) {
		return carRentalRepository.getOne(id);
	}
}

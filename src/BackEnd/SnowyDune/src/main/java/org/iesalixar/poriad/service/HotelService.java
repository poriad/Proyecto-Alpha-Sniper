package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Hotel;
import org.iesalixar.poriad.repository.CommentRepository;
import org.iesalixar.poriad.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HotelService {

	@Autowired
	HotelRepository hotelRepository;
	
	public List<Hotel> listHotel(){
		return hotelRepository.findAll();
	}
	
	public List<Hotel> listHotelsStatus(Integer status){
		return hotelRepository.listHotelsStatus(status);
	}
	
	public void updateHotelStatus(Long id ,Integer status) {
		hotelRepository.updateHotelStatus(id, status);
	}
	
	public void saveHotel(Hotel hotel) {
		hotelRepository.save(hotel);
	}
	
	public void deleteHotel(Long id) {
		hotelRepository.deleteById(id);
	}
	
	public boolean existById(Long id) {
		return hotelRepository.existsById(id);	
	}
	
	public Hotel findById(Long id) {
		return hotelRepository.getOne(id);
	}
	
}

package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Hotel;
import org.iesalixar.poriad.entity.SkiMaterial;
import org.iesalixar.poriad.repository.SkiMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SkiMaterialService {

	@Autowired
	SkiMaterialRepository skiMaterialRepository;
	
	public List<SkiMaterial> listSkiMaterial(){
		return skiMaterialRepository.findAll();
	}
	
	public List<SkiMaterial> listHotelsStatus(Integer status){
		return skiMaterialRepository.listSkiMaterialStatus(status);
	}
	
	public void updateSkiMaterialStatus(Long id ,Integer status) {
		skiMaterialRepository.updateSkiMaterialStatus(id, status);
	}
	
	public void updateStationIdSkiMaterial(Long id ,Long station) {
		skiMaterialRepository.updateStationIdSkiMaterial(id, station);
	}
	
	public void updateUserIdSkiMaterial(Long id ,Long userid) {
		skiMaterialRepository.updateUserIdSkiMaterial(id, userid);
	}
	
	public void saveSkiMaterial(SkiMaterial skiMaterial) {
		skiMaterialRepository.save(skiMaterial);
	}
	
	public void deleteSkiMaterial(Long id) {
		skiMaterialRepository.deleteById(id);
	}
	
	public boolean existById(Long id) {
		return skiMaterialRepository.existsById(id);	
	}
	
	public SkiMaterial findById(Long id) {
		return skiMaterialRepository.getOne(id);
	}
	
}

package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Classes;
import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Hotel;
import org.iesalixar.poriad.repository.ClassesRepository;
import org.iesalixar.poriad.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClassesService {
	
	@Autowired
	ClassesRepository classesRepository;
	
	public List<Classes> listComment(){
		return classesRepository.findAll();
	}
	
	public List<Classes> listClassesStatus(Integer status){
		return classesRepository.listClassesStatus(status);
	}
	
	public List<Classes> getClassesFromUser(Long id){
		return classesRepository.getClassesFromUser(id);
	}
	
	public void updateClassesStatus(Long id ,Integer status) {
		classesRepository.updateClassesStatus(id, status);
	}
	
	public void updateStationIdClasses(Long id ,Long station) {
		classesRepository.updateStationIdClasses(id, station);
	}
	
	public void updateUserIdClasses(Long id ,Long userid) {
		classesRepository.updateUserIdClasses(id, userid);
	}
	
	public void saveClasses(Classes classes) {
		classesRepository.save(classes);
	}
	
	public void deleteClasses(Long id) {
		classesRepository.deleteById(id);
	}
	
	public boolean existById(Long id) {
		return classesRepository.existsById(id);	
	}
	
	public Classes findById(Long id) {
		return classesRepository.getOne(id);
	}

}

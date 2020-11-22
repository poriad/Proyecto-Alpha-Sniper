package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Forfait;
import org.iesalixar.poriad.repository.CommentRepository;
import org.iesalixar.poriad.repository.ForfaitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ForfaitService {
	
	@Autowired
	ForfaitRepository forfaitRepository;
	
	public List<Forfait> listForfait(){
		return forfaitRepository.findAll();
	}
	
	public void save(Forfait forfait) {
		forfaitRepository.save(forfait);
	}
	
	public void deleteForfait(Long id) {
		forfaitRepository.deleteById(id);
	}
	
	public boolean existById(Long id) {
		return forfaitRepository.existsById(id);	
	}
	
	public Forfait findById(Long id) {
		return forfaitRepository.getOne(id);
	}

}

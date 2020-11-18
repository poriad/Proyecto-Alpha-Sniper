package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.repository.CommentRepository;
import org.iesalixar.poriad.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	
	public List<Comment> listComment(){
		return commentRepository.findAll();
	}
	
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}
	
	public boolean existById(Long id) {
		return commentRepository.existsById(id);	
	}
	
	public Comment findById(Long id) {
		return commentRepository.getOne(id);
	}
	
	public void deleteCommentsHotel(Long id) {
		commentRepository.deleteCommentsHotel(id);
	}
		
	
}

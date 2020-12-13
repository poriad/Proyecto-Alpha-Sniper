package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.repository.CommentRepository;
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
	
	public void deleteCommentsStation(Long id) {
		commentRepository.deleteCommentsStation(id);
	}
	
	public void deleteCommentsHotel(Long id) {
		commentRepository.deleteCommentsHotel(id);
	}
	
	public void deleteCommentsSkiMaterial(Long id) {
		commentRepository.deleteCommentsSkiMaterial(id);
	}
	
	public void deleteCommentsClasses(Long id) {
		commentRepository.deleteCommentsClasses(id);
	}
	
	public void deleteCommentsCarRental(Long id) {
		commentRepository.deleteCommentsCarRental(id);
	}
	
	public void updateCommentStation(Long stationId, Long userId, Long commentId) {
		commentRepository.updateCommentsStation(stationId, userId, commentId);
	}
	
	public void updateCommentHotel(Long hotelId, Long userId, Long commentId) {
		commentRepository.updateCommentsHotel(hotelId, userId, commentId);
	}
	
	public void updateCommentClasses(Long classesId, Long userId, Long commentId) {
		commentRepository.updateCommentsClasses(classesId, userId, commentId);
	}
	
	public void updateCommentSkiMaterial(Long skiMaterialId, Long userId, Long commentId) {
		commentRepository.updateCommentsSkiMaterial(skiMaterialId, userId, commentId);
	}
	
	public void updateCommentCarRental(Long carRentalId, Long userId, Long commentId) {
		commentRepository.updateCommentsCarRental(carRentalId, userId, commentId);
	}
	
		
	
}

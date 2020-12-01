package org.iesalixar.poriad.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.service.CommentService;
import org.iesalixar.poriad.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Comment>> listComment() {
		
		List<Comment> listComment = commentService.listComment();
		
		return new ResponseEntity(listComment,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createComment(@RequestBody Comment comment){
		
		commentService.saveComment(comment);
		
		
		return new ResponseEntity(comment, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Comment commentDto){
		
		if(!commentService.existById(id)) {
			return new ResponseEntity(new Mensaje("El comentario no existe"),HttpStatus.NOT_FOUND);
		}
		
		Comment comment = commentService.findById(id);
		
		comment.setComment(commentDto.getComment());;

		commentService.saveComment(comment);
		
		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentStation")
	public ResponseEntity<?> updateCommentStation(@RequestParam Long userId, @RequestParam Long stationId,@RequestParam Long commentId){
		
		commentService.updateCommentStation(stationId, userId, commentId);
		
		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"),HttpStatus.OK);
		
	}

	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentHotel")
	public ResponseEntity<?> updateCommentHotel(@RequestParam Long userId, @RequestParam Long hotelId,@RequestParam Long commentId){
		
		commentService.updateCommentHotel(hotelId, userId, commentId);
		
		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentClasses")
	public ResponseEntity<?> updateCommentClasses(@RequestParam Long userId, @RequestParam Long classesId,@RequestParam Long commentId){
		
		commentService.updateCommentClasses(classesId, userId, commentId);
		
		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentSkiMaterial")
	public ResponseEntity<?> updateCommentSkiMaterial(@RequestParam Long userId, @RequestParam Long skiMaterialId,@RequestParam Long commentId){
		
		commentService.updateCommentSkiMaterial(skiMaterialId, userId, commentId);
		
		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentCarRental")
	public ResponseEntity<?> updateCommentCarRental(@RequestParam Long userId, @RequestParam Long carRentalId,@RequestParam Long commentId){
		
		commentService.updateCommentCarRental(carRentalId, userId, commentId);
		
		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable Long id){
		
		if(!commentService.existById(id)) {
			return new ResponseEntity(new Mensaje("El comentario no existe"), HttpStatus.NOT_FOUND);
		}
		
		commentService.deleteComment(id);
		
		return new ResponseEntity(new Mensaje("Comentario eliminada"), HttpStatus.OK);
		
	}
	

}

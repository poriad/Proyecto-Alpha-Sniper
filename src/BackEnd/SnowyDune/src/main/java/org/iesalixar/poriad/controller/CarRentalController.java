package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.service.CarRentalService;
import org.iesalixar.poriad.service.CommentService;
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

@Controller
@RequestMapping("/carRental")
@CrossOrigin
public class CarRentalController {
	
	@Autowired
	CarRentalService carRentalService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Comment>> listComment() {
		
		List<Comment> listComment = commentService.listComment();
		
		return new ResponseEntity(listComment,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createStation(@RequestBody Comment comment){
		
		carRentalService.saveComment(comment);
		
		return new ResponseEntity(comment, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody Comment commentDto){
		
		if(!carRentalService.existById(id)) {
			return new ResponseEntity(new Mensaje("El comentario no existe"),HttpStatus.NOT_FOUND);
		}
		
		Comment comment = carRentalService.findById(id);
		
		comment.setComment(commentDto.getComment());;

		
		carRentalService.saveComment(comment);
		
		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"),HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id){
		
		if(!carRentalService.existById(id)) {
			return new ResponseEntity(new Mensaje("El comentario no existe"), HttpStatus.NOT_FOUND);
		}
		
		carRentalService.deleteStation(id);
		
		return new ResponseEntity(new Mensaje("Comentario eliminada"), HttpStatus.OK);
		
	}
	
	
}

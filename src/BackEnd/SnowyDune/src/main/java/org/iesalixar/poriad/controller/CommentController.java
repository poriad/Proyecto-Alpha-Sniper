package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@CrossOrigin(origins = "*")
public class CommentController {

	final static Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentService commentService;

	// Servicio que devuelve una lista de los comentarios
	@GetMapping("/list")
	public ResponseEntity<List<Comment>> listComment() {

		List<Comment> listComment = commentService.listComment();

		logger.info("Servicio consumido /comment/list");

		return new ResponseEntity(listComment, HttpStatus.OK);
	}

	// Servicio que crea un comentario
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createComment(@RequestBody Comment comment) {

		commentService.saveComment(comment);

		logger.info("Servicio consumido /comment/create, con body de respuesta: " + comment);

		return new ResponseEntity(comment, HttpStatus.OK);
	}

	// Servicio que actualiza un comentario a partir de su identificador
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Comment commentDto) {

		if (!commentService.existById(id)) {

			logger.error("Se ha producido un error al consumir el servicio /comment/update/" + id);

			return new ResponseEntity(new Mensaje("El comentario no existe"), HttpStatus.NOT_FOUND);
		}

		Comment comment = commentService.findById(id);

		comment.setComment(commentDto.getComment());

		commentService.saveComment(comment);

		logger.info("Servicio consumido /comment/update/" + id + ", con body de respuesta: " + comment);

		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que relaciona un comentario con una estación y con un usuario
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentStation")
	public ResponseEntity<?> updateCommentStation(@RequestParam Long userId, @RequestParam Long stationId,
			@RequestParam Long commentId) {

		commentService.updateCommentStation(stationId, userId, commentId);

		logger.info("Servicio consumido /comment/update" + ", con la estación: " + stationId + ", el usuario: " + userId
				+ " y el comentario: " + commentId);

		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que relaciona un comentario con un hotel y con un usuario
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentHotel")
	public ResponseEntity<?> updateCommentHotel(@RequestParam Long userId, @RequestParam Long hotelId,
			@RequestParam Long commentId) {

		commentService.updateCommentHotel(hotelId, userId, commentId);

		logger.info("Servicio consumido /comment/update" + ", con el hotel: " + hotelId + ", el usuario: " + userId
				+ " y el comentario: " + commentId);

		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que relaciona un comentario con un negocio de clases y con un
	// usuario
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentClasses")
	public ResponseEntity<?> updateCommentClasses(@RequestParam Long userId, @RequestParam Long classesId,
			@RequestParam Long commentId) {

		commentService.updateCommentClasses(classesId, userId, commentId);

		logger.info("Servicio consumido /comment/update" + ", con el negocio de clases: " + classesId + ", el usuario: "
				+ userId + " y el comentario: " + commentId);

		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que relaciona un comentario con un negocio de material de ski y con
	// un usuario
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentSkiMaterial")
	public ResponseEntity<?> updateCommentSkiMaterial(@RequestParam Long userId, @RequestParam Long skiMaterialId,
			@RequestParam Long commentId) {

		commentService.updateCommentSkiMaterial(skiMaterialId, userId, commentId);

		logger.info("Servicio consumido /comment/update" + ", con el negocio de material de ski: " + skiMaterialId
				+ ", el usuario: " + userId + " y el comentario: " + commentId);

		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que relaciona un comentario con un negocio de alquiler de vehículos
	// y con un usuario
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCommentCarRental")
	public ResponseEntity<?> updateCommentCarRental(@RequestParam Long userId, @RequestParam Long carRentalId,
			@RequestParam Long commentId) {

		commentService.updateCommentCarRental(carRentalId, userId, commentId);

		logger.info("Servicio consumido /comment/update" + ", con el negocio de alquiler de vehículos: " + carRentalId
				+ ", el usuario: " + userId + " y el comentario: " + commentId);

		return new ResponseEntity(new Mensaje("Comentario actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que borra un comentario
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable Long id) {

		if (!commentService.existById(id)) {
			return new ResponseEntity(new Mensaje("El comentario no existe"), HttpStatus.NOT_FOUND);
		}

		commentService.deleteComment(id);

		logger.info("Servicio consumido /comment/delete/" + id);

		return new ResponseEntity(new Mensaje("Comentario eliminada"), HttpStatus.OK);

	}

}

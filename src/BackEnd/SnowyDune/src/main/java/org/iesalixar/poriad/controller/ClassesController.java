package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Classes;
import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.SkiMaterial;
import org.iesalixar.poriad.service.ClassesService;
import org.iesalixar.poriad.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
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
@RequestMapping("/classes")
@CrossOrigin(origins = "*")
public class ClassesController {
	
	final static Logger logger = LoggerFactory.getLogger(ClassesController.class);
	
	@Autowired
	ClassesService classesService;
	
	@Autowired
	CommentService commentService;
	
	// Servicio que devuelve una lista de negocios de clases
	@GetMapping("/list")
	public ResponseEntity<List<Classes>> listClasses() {
		
		List<Classes> listClasses = classesService.listComment();
		
		logger.info("Se ha consumido el servicio classes/list");
		
		return new ResponseEntity(listClasses,HttpStatus.OK);
	}
	
	// Servicio que devuelve un negocio de clases en función del id
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/classesId/{id}")
	public ResponseEntity<Classes> findClassesById(@PathVariable Long id) {
	
		Classes classes = classesService.findById(id);
		
		logger.info("Se ha consumido el servicio classes/classesId/" + id);
		
		return new ResponseEntity(classes,HttpStatus.OK);
	}
	
	// Servicio que devuelve un listado de negocios de clases que pertenezcan a un usuario
	@GetMapping("/listClasses/{id}")
	public ResponseEntity<List<Classes>> getClassesFromUser(@PathVariable Long id) {
		
		List<Classes> listClasses = classesService.getClassesFromUser(id);
		
		logger.info("Se ha consumido el servicio classes/listClasses/" + id);
		
		return new ResponseEntity(listClasses,HttpStatus.OK);
	}
	
	// Servicio que devuelve una lista de negocios de clases en función del status
	@GetMapping("/listStatus/{status}")
	public ResponseEntity<List<Classes>> listClassesStatus(@PathVariable Integer status) {
		
		List<Classes> listClasses = classesService.listClassesStatus(status);
		
		logger.info("Se ha consumido el servicio classes/listStatus/" + status);
		
		return new ResponseEntity(listClasses,HttpStatus.OK);
	}
	
	// Servicio que crea un negocio de clases
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PostMapping("/create")
	public ResponseEntity<?> createClasses(@RequestBody Classes classes){
		
		classesService.saveClasses(classes);
		
		logger.info("Se ha consumido el servicio classes/create, con el nombre: " + classes.getName());
		
		return new ResponseEntity(classes, HttpStatus.OK);
	}
	
	// Servicio que actualiza un negocio de clases
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateClasses(@PathVariable Long id, @RequestBody Classes classesDto){
		
		if(!classesService.existById(id)) {
			
			logger.error("Respuesta de error en el servicio classes/update/" + id);
			
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		Classes classes = classesService.findById(id);
		
		classes.setDescription(classesDto.getDescription());
		classes.setLocation(classesDto.getLocation());
		classes.setName(classesDto.getName());
		classes.setPhone(classesDto.getName());
		classes.setPriceHour(classesDto.getPriceHour());
		classes.setUrlImages(classesDto.getUrlImages());

		
		classesService.saveClasses(classes);
		
		logger.info("Se ha consumido el servicio classes/update/ " + id + ", con body de respuesta" + classesDto);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	// Servicio que actualiza el status de un negocio de clases
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateClassesStatus(@PathVariable Long id, @RequestParam Integer status){
		
		if(!classesService.existById(id)) {
			
			logger.error("Respuesta de error en el servicio classes/updateStatus/" + id);
			
			return new ResponseEntity(new Mensaje("El hotel no existe"),HttpStatus.NOT_FOUND);
		}
		
		classesService.updateClassesStatus(id, status);
		
		logger.info("Se ha consumido el servicio classes/updateStatus/ " + id + ", con status" + status);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	// Servicio que actualiza la estación a la que pertenece el negocio de clases
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateStatusClassesStation/{id}")
	public ResponseEntity<?> updateStationIdClasses(@PathVariable Long id, @RequestParam Long station){
		
		if(!classesService.existById(id)) {
			
			logger.error("Respuesta de error en el servicio classes/updateStatusClassesStation/" + id);
			
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		classesService.updateStationIdClasses(id, station);
		
		logger.info("Se ha consumido el servicio classes/updateStatus/ " + id + ", con estación " + station);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	// Servicio que actualiza el usuario al que le pertenece el negocio de clases
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateUserIdClasses/{id}")
	public ResponseEntity<?> updateUserIdClasses(@PathVariable Long id, @RequestParam Long userid){
		
		if(!classesService.existById(id)) {
			
			logger.error("Respuesta de error en el servicio classes/updateUserIdClasses/" + id);
			
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		classesService.updateUserIdClasses(id, userid);
		
		logger.info("Se ha consumido el servicio classes/updateStatus/ " + id + ", con usuario " + userid);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	// Servicio que borra un negocio de clases y los comentarios asociados
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteClasses(@PathVariable Long id){
		
		if(!classesService.existById(id)) {
			
			logger.error("Respuesta de error en el servicio classes/delete/" + id);
			
			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}
		
		commentService.deleteCommentsClasses(id);
		
		classesService.deleteClasses(id);
		
		logger.info("Se ha consumido el servicio classes/delete/ " + id);
		
		return new ResponseEntity(new Mensaje("Servicio eliminado"), HttpStatus.OK);
		
	}

}

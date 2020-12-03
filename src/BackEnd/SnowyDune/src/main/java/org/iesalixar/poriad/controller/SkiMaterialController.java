package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Classes;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.SkiMaterial;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.service.CommentService;
import org.iesalixar.poriad.service.SkiMaterialService;
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
@RequestMapping("/skiMaterial")
@CrossOrigin
public class SkiMaterialController {

	@Autowired
	SkiMaterialService skiMaterialService;
	
	@Autowired
	CommentService commentService;
	
	@GetMapping("/list")
	public ResponseEntity<List<SkiMaterial>> listSkiMaterial() {
		
		List<SkiMaterial> listSkiMaterial = skiMaterialService.listSkiMaterial();
		
		return new ResponseEntity(listSkiMaterial,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/skiMaterialId/{id}")
	public ResponseEntity<SkiMaterial> findSkiMaterialById(@PathVariable Long id) {
	
		SkiMaterial skiMaterial = skiMaterialService.findById(id);
		
		return new ResponseEntity(skiMaterial,HttpStatus.OK);
	}
	
	@GetMapping("/listStatus/{status}")
	public ResponseEntity<List<SkiMaterial>> listSkiMaterialStatus(@PathVariable Integer status) {
		
		List<SkiMaterial> listSkiMaterial = skiMaterialService.listHotelsStatus(status);
		
		return new ResponseEntity(listSkiMaterial,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createStation(@RequestBody SkiMaterial skiMaterial){
		
		skiMaterialService.saveSkiMaterial(skiMaterial);
		
		return new ResponseEntity(skiMaterial, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody SkiMaterial skiMaterialDto){
		
		if(!skiMaterialService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		SkiMaterial skiMaterial = skiMaterialService.findById(id);
		
		skiMaterial.setDescription(skiMaterialDto.getDescription());
		skiMaterial.setLocation(skiMaterialDto.getLocation());
		skiMaterial.setName(skiMaterialDto.getName());
		skiMaterial.setPhone(skiMaterialDto.getPhone());
		skiMaterial.setPriceDay(skiMaterialDto.getPriceDay());
		skiMaterial.setUrlImages(skiMaterialDto.getUrlImages());
		
		skiMaterialService.saveSkiMaterial(skiMaterial);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateSkiMaterialStatus(@PathVariable Long id, @RequestParam Integer status){
		
		if(!skiMaterialService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		skiMaterialService.updateSkiMaterialStatus(id, status);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateStatusSkiMaterialStation/{id}")
	public ResponseEntity<?> updateStationIdClasses(@PathVariable Long id, @RequestParam Long station){
		
		if(!skiMaterialService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		skiMaterialService.updateStationIdSkiMaterial(id, station);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateUserIdSkiMaterial/{id}")
	public ResponseEntity<?> updateUserIdSkiMaterial(@PathVariable Long id, @RequestParam Long userid){
		
		if(!skiMaterialService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		skiMaterialService.updateUserIdSkiMaterial(id, userid);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id){
		
		if(!skiMaterialService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}
		
		commentService.deleteCommentsSkiMaterial(id);
		
		skiMaterialService.deleteSkiMaterial(id);
		
		return new ResponseEntity(new Mensaje("Servicio eliminado"), HttpStatus.OK);
		
	}
	
}

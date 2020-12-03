package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.SkiMaterial;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.entity.Trip;
import org.iesalixar.poriad.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip")
@CrossOrigin
public class TripController {
	
	
	
	@Autowired
	private TripService tripService;
	
	
	@GetMapping("/list")
	public ResponseEntity<List<Trip>> listTrips() {
		
		List<Trip> listTrips = tripService.listTrip();
		
		return new ResponseEntity(listTrips,HttpStatus.OK);
	}
	
	@GetMapping("/listCart")
	public ResponseEntity<List<Trip>> listTripsCart(@RequestParam Long id) {
		
		List<Trip> listTrips = tripService.getTripsInCart(id);
		
		return new ResponseEntity(listTrips,HttpStatus.OK);
	}
	
	@GetMapping("/listCheckout")
	public ResponseEntity<List<Trip>> listTripsCartCheckout(@RequestParam Long id) {
		
		List<Trip> listTrips = tripService.getTripsInCartForCheckout(id);
		
		return new ResponseEntity(listTrips,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public ResponseEntity<?> createTrip(@RequestBody Trip trip){
		
		tripService.saveTrip(trip);
		
		return new ResponseEntity(trip, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateTrip")
	public ResponseEntity<?> updateTrip(@RequestParam Long id,@RequestParam Long userId,@RequestParam Long stationId,@RequestParam Long hotelId,@RequestParam Long classesId, @RequestParam Long skiMaterialId,@RequestParam Long carRentalId ,@RequestParam Long cartId){
		
		tripService.updateTrip(id, userId, stationId, hotelId, classesId, skiMaterialId, carRentalId, cartId);
		
		return new ResponseEntity(new Mensaje("Viaje actualizado correctamente"),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateTripToCheckout/{id}")
	public ResponseEntity<?> updateTrip(@PathVariable Long id){
		
		tripService.updateTripToCheckout(id);
		
		return new ResponseEntity(new Mensaje("Viaje actualizado correctamente"),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateTripPaymentDone")
	public ResponseEntity<?> updateTripPaymentDone(@RequestParam Long paymentId, @RequestParam Long tripId){
		
		tripService.updateTripPaymentDone(paymentId, tripId);
		
		return new ResponseEntity(new Mensaje("Viaje actualizado correctamente"),HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id){
		
		if(!tripService.existById(id)) {
			return new ResponseEntity(new Mensaje("El viaje no existe"), HttpStatus.NOT_FOUND);
		}
		
		tripService.deleteTrip(id);
		
		return new ResponseEntity(new Mensaje("Viaje eliminado"), HttpStatus.OK);
		
	}
	
}

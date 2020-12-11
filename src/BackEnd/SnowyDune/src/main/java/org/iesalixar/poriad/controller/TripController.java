package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.SkiMaterial;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.entity.Trip;
import org.iesalixar.poriad.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@CrossOrigin(origins = "*")
public class TripController {
	
	final static Logger logger = LoggerFactory.getLogger(TripController.class);
	
	@Autowired
	private TripService tripService;
	
	// Servicio que devuelve un listado de los viajes
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/list")
	public ResponseEntity<List<Trip>> listTrips() {
		
		List<Trip> listTrips = tripService.listTrip();
		
		logger.info("Servicio consumido /trip/list");
		
		return new ResponseEntity(listTrips,HttpStatus.OK);
	}
	
	// Servicio que devuelve el listado de viajes que un usuario tiene en el carrito
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/listCart")
	public ResponseEntity<List<Trip>> listTripsCart(@RequestParam Long id) {
		
		List<Trip> listTrips = tripService.getTripsInCart(id);
		
		logger.info("Servicio consumido /trip/listCart");
		
		return new ResponseEntity(listTrips,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/listLastYearTrip")
	public ResponseEntity<List<Trip>> getTripsDoneLastYear(@RequestParam Integer id) {
		
		List<Trip> listTrips = tripService.getTripsDoneLastYear(id);
		
		logger.info("Servicio consumido /trip/listCart");
		
		return new ResponseEntity(listTrips,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/listThisYearTrips")
	public ResponseEntity<List<Trip>> getTripsDoneThisYear(@RequestParam Integer id) {
		
		List<Trip> listTrips = tripService.getTripsDoneThisYear(id);
		
		logger.info("Servicio consumido /trip/listThisYearTrips");
		
		return new ResponseEntity(listTrips,HttpStatus.OK);
	}
	// Servicio que devuelve el listado de viajes que un usuario tiene para realizar el checkout
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/listCheckout")
	public ResponseEntity<List<Trip>> listTripsCartCheckout(@RequestParam Long id) {
		
		List<Trip> listTrips = tripService.getTripsInCartForCheckout(id);
		
		logger.info("Servicio consumido /trip/listCheckout");
		
		return new ResponseEntity(listTrips,HttpStatus.OK);
	}
	
	// Servicio que crea un viaje
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public ResponseEntity<?> createTrip(@RequestBody Trip trip){
		
		tripService.saveTrip(trip);
		
		logger.info("Servicio consumido /trip/create, con body de respuesta: " + trip);
		
		return new ResponseEntity(trip, HttpStatus.OK);
	}
	
	// Servicio que actualiza un viaje, asignandole los servicios seleccionados y el carrito del usuario
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateTrip")
	public ResponseEntity<?> updateTrip(@RequestParam Long id,@RequestParam Long userId,@RequestParam Long stationId,@RequestParam Long hotelId,@RequestParam Long classesId, @RequestParam Long skiMaterialId,@RequestParam Long carRentalId ,@RequestParam Long cartId){
		
		tripService.updateTrip(id, userId, stationId, hotelId, classesId, skiMaterialId, carRentalId, cartId);
		
		logger.info("Servicio consumido /trip/updateTrip");
		
		return new ResponseEntity(new Mensaje("Viaje actualizado correctamente"),HttpStatus.OK);
	}
	
	// Servicio que actualiza el estado del viaje, pasandolo a checkout
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateTripToCheckout/{id}")
	public ResponseEntity<?> updateTrip(@PathVariable Long id){
		
		tripService.updateTripToCheckout(id);
		
		logger.info("Servicio consumido /trip/updateTripToCheckout/" + id);
		
		return new ResponseEntity(new Mensaje("Viaje actualizado correctamente"),HttpStatus.OK);
	}
	
	// Servicio que actualiza el viaje a estado pagado
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateTripPaymentDone")
	public ResponseEntity<?> updateTripPaymentDone(@RequestParam Long paymentId, @RequestParam Long tripId){
		
		tripService.updateTripPaymentDone(paymentId, tripId);
		
		logger.info("Servicio consumido /trip/updateTripPaymentDone");
		
		return new ResponseEntity(new Mensaje("Viaje actualizado correctamente"),HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id){
		
		if(!tripService.existById(id)) {
			
			logger.info("Error en servicio /trip/delete/" + id);
			
			return new ResponseEntity(new Mensaje("El viaje no existe"), HttpStatus.NOT_FOUND);
		}
		
		tripService.deleteTrip(id);
		
		logger.info("Servicio consumido /trip/delete/" + id);
		
		return new ResponseEntity(new Mensaje("Viaje eliminado"), HttpStatus.OK);
		
	}
	
}

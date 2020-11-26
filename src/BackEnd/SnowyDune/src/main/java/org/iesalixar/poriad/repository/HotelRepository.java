package org.iesalixar.poriad.repository;

import java.util.List;

import org.iesalixar.poriad.entity.Classes;
import org.iesalixar.poriad.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "hotel", path = "hotel")
@CrossOrigin
public interface HotelRepository extends JpaRepository<Hotel, Long>{
	
	@Query(value="SELECT s FROM Hotel s WHERE s.activated = :status")
	List<Hotel> listHotelsStatus(@Param("status") Integer status);
	
	@Query("SELECT s FROM Hotel s WHERE s.activated = :status")
	Page<Hotel> listHotelStatusPageable(Pageable pageable,@Param("status") Integer status);
	
	@Query(value="Select s FROM Hotel s WHERE s.user.id = :id and s.activated = 1")
	List<Hotel> getHotelFromUser(@Param("id") Long userId);
	
	// activated = 2 -> borrado
	// = 1 activado
	// = 0 pendiente confirmacion
	// ACABAR
	@Modifying
	@Query(value="UPDATE Hotel h SET h.activated= :status WHERE h.id = :id")
	void updateHotelStatus(@Param("id") Long id , @Param("status") Integer status);
	
	// añadimos el station_id al hotel
	@Modifying
	@Query(value="UPDATE Hotel h SET h.station.id = :station WHERE h.id = :id")
	void updateStationIdHotel(@Param("id") Long id , @Param("station") Long station);
	
	// añadimos el user_id del hotel
	@Modifying
	@Query(value="UPDATE Hotel h SET h.user.id = :user WHERE h.id = :id")
	void updateUserIdHotel(@Param("id") Long id , @Param("user") Long userid);
	
}

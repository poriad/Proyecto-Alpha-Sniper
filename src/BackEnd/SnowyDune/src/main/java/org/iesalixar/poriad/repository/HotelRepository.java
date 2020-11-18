package org.iesalixar.poriad.repository;

import java.util.List;

import org.iesalixar.poriad.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(collectionResourceRel = "hotel", path = "hotel")
@CrossOrigin
public interface HotelRepository extends JpaRepository<Hotel, Long>{
	
	Page<Hotel> findByHotelCategoryId(@RequestParam("id") Long id, Pageable pageable);
	
	@Query("SELECT s FROM Hotel s WHERE s.activated = :status")
	List<Hotel> listHotelsStatus(@Param("status") Integer status);
	
}

package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(collectionResourceRel = "hotel", path = "hotel")
@CrossOrigin("http:/localhost:8081")
public interface HotelRepository extends JpaRepository<Hotel, Long>{
	
	Page<Hotel> findByHotelCategoryId(@RequestParam("id") Long id, Pageable pageable);
	

}

package org.iesalixar.poriad.repository;

import java.util.List;

import org.iesalixar.poriad.entity.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
public interface StationRepository extends JpaRepository<Station, Long>{
	
	Page<Station> findByCountry(@RequestParam("country") String country, Pageable pageable);
	
	Page<Station> findByNameContaining(@RequestParam("name") String name, Pageable pageable);
	
	@Query("SELECT s FROM Station s WHERE s.activated = :status")
	List<Station> listStationStatus(@Param("status") Integer status);

}

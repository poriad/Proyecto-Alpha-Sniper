package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
public interface StationRepository extends JpaRepository<Station, Long>{
	
	Page<Station> findByCountry(@RequestParam("country") String country, Pageable pageable);
	
	Page<Station> findByNameContaining(@RequestParam("name") String name, Pageable pageable);

}

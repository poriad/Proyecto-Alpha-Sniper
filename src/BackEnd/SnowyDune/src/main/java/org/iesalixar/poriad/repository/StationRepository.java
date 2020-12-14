package org.iesalixar.poriad.repository;

import java.util.List;
import org.iesalixar.poriad.entity.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(collectionResourceRel = "station", path = "stations")
@CrossOrigin
public interface StationRepository extends JpaRepository<Station, Long> {

	boolean existsByName(String name);

	Page<Station> findByCountry(@RequestParam("country") String country, Pageable pageable);

	Page<Station> findByNameContaining(@RequestParam("name") String name, Pageable pageable);

	@Query("SELECT s FROM Station s WHERE s.activated = :status")
	Page<Station> listStationStatusPageable(Pageable pageable, @Param("status") Integer status);

	@Query("SELECT s FROM Station s WHERE s.activated = :status")
	List<Station> listStationStatus(@Param("status") Integer status);

	@Modifying
	@Query(value = "UPDATE Station h SET h.activated= :status WHERE h.id = :id")
	void updateStationStatus(@Param("id") Long id, @Param("status") Integer status);

}

package org.iesalixar.poriad.repository;

import java.sql.Date;
import java.util.List;

import org.iesalixar.poriad.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "trip", path = "trip")
@CrossOrigin
public interface TripRepository extends JpaRepository<Trip, Long>{
	
	//@Query(value="SELECT t FROM Trip t WHERE t.entryDate > current_date ORDER BY t.entryDate ASC Limit 0, 25",nativeQuery=true)
	@Query(value="SELECT * FROM trip where entry_date > SYSDATE() ORDER BY entry_date ASC Limit 0, 5",nativeQuery=true)
	List<Trip> getTripsSysdate();
	
	//@Query(value="SELECT t FROM Trip t WHERE t.entryDate < current_date and t.entryDate >")
	@Query(value="SELECT * FROM trip where user_id = :userId and (entry_date BETWEEN '2020-01-01' AND SYSDATE()) ORDER BY entry_date ASC Limit 0, 3",nativeQuery=true)
	List<Trip> getTripsDoneThisYear(@Param("userId") Integer userId);
	
	@Query(value="SELECT * FROM trip where user_id = :userId and entry_date BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() ORDER BY entry_date",nativeQuery=true)
	List<Trip> getTripsDoneLastMonth(@Param("userId") Integer userId);
	
	//@Query(value="SELECT t FROM Trip t WHERE t.entryDate < 2019-12-31")
	@Query(value="SELECT * FROM trip where entry_date BETWEEN '2019-01-30' AND '2020-01-01' ORDER BY entry_date ASC Limit 0, 3",nativeQuery=true)
	List<Trip> getTripsDoneLastYear();
	
}

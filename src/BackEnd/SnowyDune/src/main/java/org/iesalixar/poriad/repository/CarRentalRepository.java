package org.iesalixar.poriad.repository;

import java.util.List;

import org.iesalixar.poriad.entity.CarRental;
import org.iesalixar.poriad.entity.Classes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "carRental", path = "car-rental")
@CrossOrigin
public interface CarRentalRepository extends JpaRepository<CarRental, Long>{

	@Query("SELECT s FROM CarRental s WHERE s.activated = :status")
	List<CarRental> listCarRentalStatus(@Param("status") Integer status);
	
	@Query("SELECT s FROM CarRental s WHERE s.activated = :status")
	Page<CarRental> listCarRentalStatusPageable(Pageable pageable,@Param("status") Integer status);
	
	// activated = 2 -> borrado
	// = 1 activado
	// = 0 pendiente confirmacion
	@Modifying
	@Query(value="UPDATE CarRental h SET h.activated= :status WHERE h.id = :id")
	void updateCarRentalStatus(@Param("id") Long id , @Param("status") Integer status);
}

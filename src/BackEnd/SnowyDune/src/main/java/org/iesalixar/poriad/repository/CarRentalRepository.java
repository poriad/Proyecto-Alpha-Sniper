package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "carRental", path = "car-rental")
@CrossOrigin("http:/localhost:8081")
public interface CarRentalRepository extends JpaRepository<CarRental, Long>{

}

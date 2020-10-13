package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "carRental", path = "car-rental")
public interface CarRentalRepository extends JpaRepository<CarRental, Long>{

}

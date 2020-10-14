package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "trip", path = "trip")
@CrossOrigin("http:/localhost:8081")
public interface TripRepository extends JpaRepository<Trip, Long>{

}

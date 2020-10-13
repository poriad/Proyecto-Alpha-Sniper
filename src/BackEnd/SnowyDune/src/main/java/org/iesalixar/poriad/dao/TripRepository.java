package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trip", path = "trip")
public interface TripRepository extends JpaRepository<Trip, Long>{

}

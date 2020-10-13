package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "classes", path = "classes")
public interface ClassesRepository extends JpaRepository<Classes, Long>{

}

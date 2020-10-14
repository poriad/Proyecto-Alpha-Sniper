package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "classes", path = "classes")
@CrossOrigin("http:/localhost:8081")
public interface ClassesRepository extends JpaRepository<Classes, Long>{

}

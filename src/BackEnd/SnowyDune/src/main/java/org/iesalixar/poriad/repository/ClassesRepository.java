package org.iesalixar.poriad.repository;

import java.util.List;

import org.iesalixar.poriad.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "classes", path = "classes")
@CrossOrigin("http:/localhost:8081")
public interface ClassesRepository extends JpaRepository<Classes, Long>{

	@Query("SELECT s FROM Classes s WHERE s.activated = :status")
	List<Classes> listClassesStatus(@Param("status") Integer status);
	
}

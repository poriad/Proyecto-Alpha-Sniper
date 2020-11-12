package org.iesalixar.poriad.security.repository;

import java.util.Optional;

import org.iesalixar.poriad.security.entity.UserSnowy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
@CrossOrigin("http:/localhost:8081")
public interface UserRepository extends JpaRepository<UserSnowy, Integer>{
	
	Optional<UserSnowy> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	//UserSnowy findByUsername(String username);
	
	UserSnowy findByEmailIgnoreCase(String email);

}

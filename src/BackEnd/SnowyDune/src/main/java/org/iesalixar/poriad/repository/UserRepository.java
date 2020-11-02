package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.UserDTO;
import org.iesalixar.poriad.entity.UserSnowy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
@CrossOrigin("http:/localhost:8081")
public interface UserRepository extends JpaRepository<UserSnowy, Long>{

	UserSnowy findByUsername(String username);
	
	UserSnowy findByEmailIgnoreCase(String email);

}

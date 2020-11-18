package org.iesalixar.poriad.security.repository;

import java.util.Optional;

import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.security.entity.UserSnowy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
@CrossOrigin
public interface UserRepository extends JpaRepository<UserSnowy, Long>{
	
	Optional<UserSnowy> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	//UserSnowy findByUsername(String username);
	
	UserSnowy findByEmailIgnoreCase(String email);
	
	@Query(value="SELECT u from UserSnowy u WHERE u.username= :username")
	Page<UserSnowy> findByUser(@Param("username") String name, Pageable pageable);
	
	@Query(value="SELECT u from UserSnowy u WHERE u.isEnterprise = 0")
	Page<UserSnowy> findByIsEnterprise(Pageable pageable);
	
	@Query(value="SELECT u from UserSnowy u")
	Page<UserSnowy> findAllUser(Pageable pageable);


}

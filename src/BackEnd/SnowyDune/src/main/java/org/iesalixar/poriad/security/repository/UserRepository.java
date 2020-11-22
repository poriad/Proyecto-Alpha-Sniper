package org.iesalixar.poriad.security.repository;

import java.util.Optional;

import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.security.entity.UserSnowy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	// isEnterprise = 2 -> Pendiente
	// = 1 es usuario empresa
	// = 0 no es usuario empresa
	@Query(value="SELECT u from UserSnowy u WHERE u.isEnterprise = 2")
	Page<UserSnowy> findByIsEnterprise(Pageable pageable);
	
	// isActive = 2 -> borrado
	// = 1 es usuario
	// = 0 no es usuario confirmado
	@Query(value="SELECT u from UserSnowy u WHERE u.isActive = 1")
	Page<UserSnowy> findAllUser(Pageable pageable);
	
	// ORDER METHODS
	// http://localhost:8082/api/user/search/findAllUserOderByUsernamepage=0&size=8
	@Query(value="SELECT u from UserSnowy u ORDER BY u.email ASC")
	Page<UserSnowy> findAllUserOderByEmail(Pageable pageable);
	
	@Query(value="SELECT u from UserSnowy u ORDER BY u.username ASC")
	Page<UserSnowy> findAllUserOderByUsername(Pageable pageable);

	@Query(value="SELECT u from UserSnowy u ORDER BY u.firstName ASC")
	Page<UserSnowy> findAllUserOderByFirstName(Pageable pageable);
	
	Page<UserSnowy> findAllByOrderByIdAsc(Pageable pageable);
	
	// ACTUALIZADO DE USUARIO A TIPO EMPRESA
	// Controller
	// http://localhost:8082/user/updateStatus/1?status=1
	@Modifying
	@Query(value="UPDATE UserSnowy u SET u.nomComercial = null, u.NIF = null, u.CNAE = null, u.activity = null,u.location = null, u.enterprisePhone = null, u.enterpriseEmail = null  WHERE u.id = :id")
	void deleteUserEnterprise(@Param("id") Long id);
	
	@Modifying
	@Query(value="UPDATE UserSnowy u SET u.isEnterprise= :status WHERE u.id = :id")
	void updateUserEnterprise(@Param("id") Long id , @Param("status") int status);
	
	// ACTUALIZADO DE USUARIO A TIPO EMPRESA
	// Controller
	// http://localhost:8082/user/updateUserStatus/1?status=1
	@Modifying
	@Query(value="UPDATE UserSnowy u SET u.isActive= :status WHERE u.id = :id")
	void updateUserStatus(@Param("id") Long id , @Param("status") int status);
	
}

package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "comment", path = "comment")
@CrossOrigin
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	@Query(value="SELECT c from Comment c INNER JOIN Hotel h ON h.id = c.hotel WHERE h.id = :id")
	Page<Comment> findAllCommentsHotels(@Param("id") Long id ,Pageable pageable);
	
	@Modifying
	@Query(value="DELETE FROM Comment c WHERE c.station.id = :id")
	void deleteCommentsStation(@Param("id") Long id);
	
	@Modifying
	@Query(value="DELETE FROM Comment c WHERE c.hotel.id = :id")
	void deleteCommentsHotel(@Param("id") Long id);
	
	@Modifying
	@Query(value="DELETE FROM Comment c WHERE c.skiMaterial.id = :id")
	void deleteCommentsSkiMaterial(@Param("id") Long id);
	
	@Modifying
	@Query(value="DELETE FROM Comment c WHERE c.classes.id = :id")
	void deleteCommentsClasses(@Param("id") Long id);
	
	@Modifying
	@Query(value="DELETE FROM Comment c WHERE c.carRental.id = :id")
	void deleteCommentsCarRental(@Param("id") Long id);
	
}

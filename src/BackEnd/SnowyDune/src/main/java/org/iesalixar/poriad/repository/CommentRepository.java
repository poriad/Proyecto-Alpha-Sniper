package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "comment", path = "comment")
@CrossOrigin
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "SELECT c FROM Comment c WHERE c.station.id = :id")
	Page<Comment> listCommentsStation(@Param("id") Long id, Pageable pageable);

	@Query(value = "SELECT c FROM Comment c WHERE c.hotel.id = :id")
	Page<Comment> listCommentsHotel(@Param("id") Long id, Pageable pageable);

	@Query(value = "SELECT c FROM Comment c WHERE c.skiMaterial.id = :id")
	Page<Comment> listCommentsSkiMaterial(@Param("id") Long id, Pageable pageable);

	@Query(value = "SELECT c FROM Comment c WHERE c.carRental.id = :id")
	Page<Comment> listCommentsCarRental(@Param("id") Long id, Pageable pageable);

	@Query(value = "SELECT c FROM Comment c WHERE c.classes.id = :id")
	Page<Comment> listCommentsClasses(@Param("id") Long id, Pageable pageable);

	@Query(value = "select c FROM Comment c")
	Page<Comment> listComments(Pageable pageable);

	@Modifying
	@Query(value = "UPDATE Comment c SET c.station.id = :stationId, c.user.id = :userId, c.active = 1 WHERE c.id = :commentId")
	void updateCommentsStation(@Param("stationId") Long stationId, @Param("userId") Long userId,
			@Param("commentId") Long commentId);

	@Modifying
	@Query(value = "UPDATE Comment c SET c.hotel.id = :hotelId, c.user.id = :userId, c.active = 1 WHERE c.id = :commentId")
	void updateCommentsHotel(@Param("hotelId") Long stationId, @Param("userId") Long userId,
			@Param("commentId") Long commentId);

	@Modifying
	@Query(value = "UPDATE Comment c SET c.classes.id = :classesId, c.user.id = :userId, c.active = 1 WHERE c.id = :commentId")
	void updateCommentsClasses(@Param("classesId") Long stationId, @Param("userId") Long userId,
			@Param("commentId") Long commentId);

	@Modifying
	@Query(value = "UPDATE Comment c SET c.skiMaterial.id = :skiMaterialId, c.user.id = :userId, c.active = 1 WHERE c.id = :commentId")
	void updateCommentsSkiMaterial(@Param("skiMaterialId") Long stationId, @Param("userId") Long userId,
			@Param("commentId") Long commentId);

	@Modifying
	@Query(value = "UPDATE Comment c SET c.carRental.id = :carRentalId, c.user.id = :userId, c.active = 1 WHERE c.id = :commentId")
	void updateCommentsCarRental(@Param("carRentalId") Long stationId, @Param("userId") Long userId,
			@Param("commentId") Long commentId);

	@Modifying
	@Query(value = "DELETE FROM Comment c WHERE c.station.id = :id")
	void deleteCommentsStation(@Param("id") Long id);

	@Modifying
	@Query(value = "DELETE FROM Comment c WHERE c.hotel.id = :id")
	void deleteCommentsHotel(@Param("id") Long id);

	@Modifying
	@Query(value = "DELETE FROM Comment c WHERE c.skiMaterial.id = :id")
	void deleteCommentsSkiMaterial(@Param("id") Long id);

	@Modifying
	@Query(value = "DELETE FROM Comment c WHERE c.classes.id = :id")
	void deleteCommentsClasses(@Param("id") Long id);

	@Modifying
	@Query(value = "DELETE FROM Comment c WHERE c.carRental.id = :id")
	void deleteCommentsCarRental(@Param("id") Long id);

}

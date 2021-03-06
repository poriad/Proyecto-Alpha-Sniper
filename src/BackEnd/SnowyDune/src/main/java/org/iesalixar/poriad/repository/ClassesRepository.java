package org.iesalixar.poriad.repository;

import java.util.List;
import org.iesalixar.poriad.entity.Classes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "classes", path = "classes")
@CrossOrigin
public interface ClassesRepository extends JpaRepository<Classes, Long> {

	@Query("SELECT s FROM Classes s WHERE s.activated = :status")
	List<Classes> listClassesStatus(@Param("status") Integer status);

	@Query("SELECT s FROM Classes s WHERE s.activated = :status")
	Page<Classes> listClassesStatusPageable(Pageable pageable, @Param("status") Integer status);

	@Query(value = "SELECT s FROM Classes s WHERE s.activated = :status and s.location = :location")
	Page<Classes> listClassesByLocationStatusPageable(Pageable pageable, @Param("status") Integer status,
			@Param("location") String location);

	@Query("SELECT s FROM Classes s WHERE s.activated = :status and s.location = :location and(:minPrice is null or s.priceHour >= :minPrice) and (:maxPrice is null or s.priceHour <= :maxPrice)")
	Page<Classes> listClassesByLocationAndPriceStatusPageable(Pageable pageable, @Param("status") Integer status,
			@Param("location") String location, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

	@Query(value = "Select s FROM Classes s WHERE s.user.id = :id and s.activated = 1")
	List<Classes> getClassesFromUser(@Param("id") Long userId);

	// activated = 2 -> borrado
	// = 1 activado
	// = 0 pendiente confirmacion
	@Modifying
	@Query(value = "UPDATE Classes h SET h.activated= :status WHERE h.id = :id")
	void updateClassesStatus(@Param("id") Long id, @Param("status") Integer status);

	// añadimos el station_id al servicio de clases
	@Modifying
	@Query(value = "UPDATE Classes h SET h.station.id = :station WHERE h.id = :id")
	void updateStationIdClasses(@Param("id") Long id, @Param("station") Long station);

	// añadimos el user_id al servicio de clases
	@Modifying
	@Query(value = "UPDATE Classes h SET h.user.id = :user WHERE h.id = :id")
	void updateUserIdClasses(@Param("id") Long id, @Param("user") Long userid);
}

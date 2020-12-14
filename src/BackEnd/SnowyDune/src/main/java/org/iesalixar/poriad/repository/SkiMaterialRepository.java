package org.iesalixar.poriad.repository;

import java.util.List;
import org.iesalixar.poriad.entity.SkiMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "skiMaterial", path = "ski-material")
@CrossOrigin
public interface SkiMaterialRepository extends JpaRepository<SkiMaterial, Long> {

	@Query("SELECT s FROM SkiMaterial s WHERE s.activated = :status")
	List<SkiMaterial> listSkiMaterialStatus(@Param("status") Integer status);

	@Query("SELECT s FROM SkiMaterial s WHERE s.activated = :status")
	Page<SkiMaterial> listSkiMaterialStatusPageable(Pageable pageable, @Param("status") Integer status);

	@Query(value = "SELECT s FROM SkiMaterial s WHERE s.activated = :status and s.location = :location")
	Page<SkiMaterial> listSkiMaterialByLocationStatusPageable(Pageable pageable, @Param("status") Integer status,
			@Param("location") String location);

	@Query("SELECT s FROM SkiMaterial s WHERE s.activated = :status and s.location = :location and(:minPrice is null or s.priceDay >= :minPrice) and (:maxPrice is null or s.priceDay <= :maxPrice)")
	Page<SkiMaterial> listSkiMaterialByLocationAndPriceStatusPageable(Pageable pageable,
			@Param("status") Integer status, @Param("location") String location, @Param("minPrice") Double minPrice,
			@Param("maxPrice") Double maxPrice);

	@Query(value = "Select s FROM SkiMaterial s WHERE s.user.id = :id and s.activated = 1")
	List<SkiMaterial> getSkiMaterialFromUser(@Param("id") Long userId);

	// activated = 2 -> borrado
	// = 1 activado
	// = 0 pendiente confirmacion
	@Modifying
	@Query(value = "UPDATE SkiMaterial h SET h.activated= :status WHERE h.id = :id")
	void updateSkiMaterialStatus(@Param("id") Long id, @Param("status") Integer status);

	// añadimos el station_id al servicio de vehiculos
	@Modifying
	@Query(value = "UPDATE SkiMaterial h SET h.station.id = :station WHERE h.id = :id")
	void updateStationIdSkiMaterial(@Param("id") Long id, @Param("station") Long station);

	// añadimos el user_id al servicio de vehiculos
	@Modifying
	@Query(value = "UPDATE SkiMaterial h SET h.user.id = :user WHERE h.id = :id")
	void updateUserIdSkiMaterial(@Param("id") Long id, @Param("user") Long userid);

}

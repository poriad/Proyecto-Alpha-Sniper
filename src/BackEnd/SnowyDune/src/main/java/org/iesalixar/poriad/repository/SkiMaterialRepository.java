package org.iesalixar.poriad.repository;

import java.util.List;

import org.iesalixar.poriad.entity.SkiMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkiMaterialRepository extends JpaRepository<SkiMaterial, Long>{

	@Query("SELECT s FROM SkiMaterial s WHERE s.activated = :status")
	List<SkiMaterial> listSkiMaterialStatus(@Param("status") Integer status);
	
	@Modifying
	@Query(value="UPDATE SkiMaterial h SET h.activated= :status WHERE h.id = :id")
	void updateSkiMaterialStatus(@Param("id") Long id , @Param("status") Integer status);
	
}

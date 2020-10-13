package org.iesalixar.poriad.dao;

import org.iesalixar.poriad.entity.Forfait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "forfait", path = "forfait")
public interface ForfaitRepository extends JpaRepository<Forfait, Long>{

}

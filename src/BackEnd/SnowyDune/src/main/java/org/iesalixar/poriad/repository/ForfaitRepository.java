package org.iesalixar.poriad.repository;

import org.iesalixar.poriad.entity.Forfait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "forfait", path = "forfait")
@CrossOrigin
public interface ForfaitRepository extends JpaRepository<Forfait, Long> {

}

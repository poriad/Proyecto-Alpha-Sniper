package org.iesalixar.poriad.security.repository;

import java.util.Optional;

import org.iesalixar.poriad.security.entity.Role;
import org.iesalixar.poriad.security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(RoleName roleNombre);
}

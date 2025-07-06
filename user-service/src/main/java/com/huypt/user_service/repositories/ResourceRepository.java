package com.huypt.user_service.repositories;

import com.huypt.user_service.models.Resource;
import com.huypt.user_service.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query(
            value = "SELECT rs.* FROM resource as rs " +
                    "JOIN role_resource as rr ON rs.id = rr.resource_id " +
                    "JOIN role as r ON r.id = rr.role_id " +
                    "WHERE r.name IN :roles",
            nativeQuery = true
    )
    List<Resource> findAllByRoles(@Param("roles") List<String> roles);
}

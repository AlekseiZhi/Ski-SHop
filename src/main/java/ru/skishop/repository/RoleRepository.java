package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skishop.entities.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT r " +
            "FROM Role r" +
            " WHERE r.id IN :ids")
    List<Role> findRolesByIdList(@Param("ids") List<Long> ids);
}
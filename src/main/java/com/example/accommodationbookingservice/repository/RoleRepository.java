package com.example.accommodationbookingservice.repository;

import com.example.accommodationbookingservice.model.Role;
import com.example.accommodationbookingservice.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(RoleName name);
}

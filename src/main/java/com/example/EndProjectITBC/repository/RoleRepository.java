package com.example.EndProjectITBC.repository;

import com.example.EndProjectITBC.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String name);
}

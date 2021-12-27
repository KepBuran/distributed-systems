package com.tem.userservice.repo;

import com.tem.userservice.repo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}

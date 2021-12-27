package com.tem.userservice.repo;


import com.tem.userservice.repo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<User, Long> {
}

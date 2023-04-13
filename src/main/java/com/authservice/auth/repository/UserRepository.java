package com.authservice.auth.repository;

import com.authservice.auth.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData,Long> {

    Optional<UserData> findByEmail(String email);
}

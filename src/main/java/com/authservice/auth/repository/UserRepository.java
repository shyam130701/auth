package com.authservice.auth.repository;

import com.authservice.auth.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserData,Long> {

    Optional<UserData> findByEmail(String email);
}

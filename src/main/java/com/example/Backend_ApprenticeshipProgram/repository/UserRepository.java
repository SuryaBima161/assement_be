package com.example.Backend_ApprenticeshipProgram.repository;

import com.example.Backend_ApprenticeshipProgram.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {
    UserModel findByUsername(String username);
}

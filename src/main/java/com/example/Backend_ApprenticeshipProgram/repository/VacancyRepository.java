package com.example.Backend_ApprenticeshipProgram.repository;

import com.example.Backend_ApprenticeshipProgram.model.VacancyModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VacancyRepository extends MongoRepository<VacancyModel, String> {
}

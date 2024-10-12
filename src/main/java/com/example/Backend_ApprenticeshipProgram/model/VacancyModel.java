package com.example.Backend_ApprenticeshipProgram.model;

import java.time.LocalDate;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@NoArgsConstructor
@Document(collection = "vacancies")
public class VacancyModel {
    @Id
    private String id;
    private String vacancyName;
    private String description;
    private Integer maximumAge;
    private Integer minimumExperience;
    private Double salary;
    private LocalDate publishDate;
    private LocalDate expiryDate;

    public boolean isActive() {
        return expiryDate == null || !expiryDate.isBefore(LocalDate.now());
    }
}

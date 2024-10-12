package com.example.Backend_ApprenticeshipProgram.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Future;

@Data
public class VacancyDto {
    @NotBlank(message = "Vacancy name is required")
    private String vacancyName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Maximum age is required")
    private Integer maximumAge;

    @NotNull(message = "Minimum experience is required")
    private Integer minimumExperience;

    @NotNull(message = "Salary is required")
    private Double salary;

    @NotNull(message = "Publish Date is required")
    @Future
    private LocalDate publishDate;

    @NotNull(message = "ExpiryDate is required")
    @Future
    private LocalDate expiryDate;
}

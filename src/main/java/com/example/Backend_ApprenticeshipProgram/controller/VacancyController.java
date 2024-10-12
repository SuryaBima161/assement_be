package com.example.Backend_ApprenticeshipProgram.controller;

import com.example.Backend_ApprenticeshipProgram.dto.VacancyDto;
import com.example.Backend_ApprenticeshipProgram.dto.respone.ResponeDto;
import com.example.Backend_ApprenticeshipProgram.model.VacancyModel;
import org.springframework.http.HttpStatus;
import com.example.Backend_ApprenticeshipProgram.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponeDto> createVacancy(@RequestBody VacancyDto vacancyDTO) {
        VacancyModel vacancyModel = vacancyService.convertDtoToModel(vacancyDTO);
        VacancyModel createVacancy = vacancyService.createVacancy(vacancyModel);
        ResponeDto response = new ResponeDto("Vacancy created successfully", createVacancy);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponeDto> updateVacancy(@PathVariable String id, @RequestBody VacancyDto vacancyDTO) {
        VacancyModel vacancyModel = vacancyService.convertDtoToModel(vacancyDTO);
        VacancyModel updatedVacancy = vacancyService.updateVacancy(id, vacancyModel);
        ResponeDto response = new ResponeDto("Vacancy updated successfully", updatedVacancy);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponeDto> deleteVacancy(@PathVariable String id) {
        vacancyService.deleteVacancy(id);
        ResponeDto response = new ResponeDto("Vacancy deleted successfully",null);
        return ResponseEntity.ok(response);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponeDto> getVacanciesByRole() {
        List<VacancyModel> vacancies;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            vacancies = vacancyService.getAllVacancies();
            ResponeDto response = new ResponeDto("All vacancies retrieved successfully", vacancies);
            return ResponseEntity.ok(response);
        } else {
            vacancies = vacancyService.getAllActiveVacancies();
            ResponeDto response = new ResponeDto("Active vacancies retrieved successfully", vacancies);
            return ResponseEntity.ok(response);
        }
    }
}

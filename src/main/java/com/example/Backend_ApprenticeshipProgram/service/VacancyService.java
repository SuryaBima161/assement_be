package com.example.Backend_ApprenticeshipProgram.service;

import com.example.Backend_ApprenticeshipProgram.dto.VacancyDto;
import com.example.Backend_ApprenticeshipProgram.model.VacancyModel;
import com.example.Backend_ApprenticeshipProgram.exception.ResourceNotFoundException;
import com.example.Backend_ApprenticeshipProgram.repository.VacancyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VacancyService {
    private static final String VACANCY_NOT_FOUND = "Vacancy not found with id: ";

    @Autowired
    private VacancyRepository vacancyRepository;

    public List<VacancyModel> getAllVacancies() {
        return vacancyRepository.findAll();
    }

    public List<VacancyModel> getAllActiveVacancies() {
        List<VacancyModel> allVacancies = vacancyRepository.findAll();
        return allVacancies.stream()
                .filter(VacancyModel::isActive)
                .collect(Collectors.toList());
    }


    public VacancyModel createVacancy(VacancyModel vacancy) {
        log.info("Creating vacancy: {}", vacancy.getVacancyName());
        return vacancyRepository.save(vacancy);
    }

    public VacancyModel updateVacancy(String id, VacancyModel vacancy) {
        return vacancyRepository.findById(id)
                .map(existingVacancy -> {
                    vacancy.setId(id);
                    return vacancyRepository.save(vacancy);
                })
                .orElseThrow(() -> new ResourceNotFoundException(VACANCY_NOT_FOUND + id));
    }

    public void deleteVacancy(String id) {
        if (!vacancyRepository.existsById(id)) {
            throw new ResourceNotFoundException(VACANCY_NOT_FOUND + id);
        }
        vacancyRepository.deleteById(id);
        log.info("Deleted vacancy with id: {}", id);
    }

    public VacancyModel convertDtoToModel(VacancyDto vacancyDTO) {
        VacancyModel vacancyModel = new VacancyModel();
        vacancyModel.setVacancyName(vacancyDTO.getVacancyName());
        vacancyModel.setDescription(vacancyDTO.getDescription());
        vacancyModel.setMaximumAge(vacancyDTO.getMaximumAge());
        vacancyModel.setMinimumExperience(vacancyDTO.getMinimumExperience());
        vacancyModel.setSalary(vacancyDTO.getSalary());
        vacancyModel.setPublishDate(vacancyDTO.getPublishDate());
        vacancyModel.setExpiryDate(vacancyDTO.getExpiryDate());
        return vacancyModel;
    }
}

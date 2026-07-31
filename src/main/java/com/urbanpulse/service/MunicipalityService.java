package com.urbanpulse.service;

import com.urbanpulse.exception.ResourceNotFoundException;
import com.urbanpulse.model.Municipality;
import com.urbanpulse.repository.MunicipalityRepository;

import java.util.List;

public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    public MunicipalityService(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
    }

    public Municipality create(String name, String region, String contactEmail) {
        Municipality municipality = new Municipality(name, region, contactEmail);
        return municipalityRepository.save(municipality);
    }

    public Municipality getById(Long id) {
        return municipalityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Municipality not found: " + id));
    }

    public List<Municipality> getAll() {
        return municipalityRepository.findAll();
    }
}
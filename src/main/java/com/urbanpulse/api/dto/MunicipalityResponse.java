package com.urbanpulse.api.dto;

import com.urbanpulse.model.Municipality;

public class MunicipalityResponse {
    private final Long id;
    private final String name;

    public MunicipalityResponse(Municipality municipality) {
        this.id = municipality.getId();
        this.name = municipality.getName();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}
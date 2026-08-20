package com.urbanpulse.api.dto;

import com.urbanpulse.model.Category;

public class CategoryResponse {
    private final Long id;
    private final String name;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}
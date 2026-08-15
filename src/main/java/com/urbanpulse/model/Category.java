package com.urbanpulse.model;

import com.urbanpulse.model.enums.IssueStatus;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Issue> issues = new ArrayList<>();

    protected Category() {}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Issue> getIssues() { return issues; }
    @Override
    public String toString() {
        return name;
    }
}
package com.urbanpulse.model;

import com.urbanpulse.model.enums.IssueStatus;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "municipalities")
public class Municipality extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String region;

    @Column(name = "contact_email")
    private String contactEmail;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    private List<Issue> issues = new ArrayList<>();

    protected Municipality() {}

    public Municipality(String name, String region, String contactEmail) {
        this.name = name;
        this.region = region;
        this.contactEmail = contactEmail;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public List<Issue> getIssues() { return issues; }
}
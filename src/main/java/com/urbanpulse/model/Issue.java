package com.urbanpulse.model;

import com.urbanpulse.model.enums.IssueStatus;
import com.urbanpulse.model.enums.Priority;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "issues")
public class Issue extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    private Double latitude;
    private Double longitude;
    private String address;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "municipality_id", nullable = false)
    private Municipality municipality;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private List<IssuePhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    protected Issue() {}

    public Issue(String title, String description, IssueStatus status, Priority priority,
                 User reporter, Municipality municipality, Category category) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.reporter = reporter;
        this.municipality = municipality;
        this.category = category;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public IssueStatus getStatus() { return status; }
    public void setStatus(IssueStatus status) { this.status = status; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public User getReporter() { return reporter; }
    public Municipality getMunicipality() { return municipality; }
    public Category getCategory() { return category; }
    public List<IssuePhoto> getPhotos() { return photos; }
    public List<Comment> getComments() { return comments; }
}
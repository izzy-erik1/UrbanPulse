package com.urbanpulse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Column(length = 500, nullable = false)
    private String message;

    @Column(name ="is_read", nullable = false )
    private boolean read = false;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    protected Notification() {}

    public Notification(String message, User recipient) {
        this.message = message;
        this.recipient = recipient;
    }

    public String getMessage() { return message; }
    public boolean isRead() { return read; }
    public void markAsRead() { this.read = true; }
    public User getRecipient() { return recipient; }
}
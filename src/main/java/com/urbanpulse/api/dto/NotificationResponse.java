package com.urbanpulse.api.dto;

import com.urbanpulse.model.Notification;

public class NotificationResponse {

    private final Long id;
    private final String message;
    private final boolean read;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.read = notification.isRead();
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public boolean isRead() { return read; }
}
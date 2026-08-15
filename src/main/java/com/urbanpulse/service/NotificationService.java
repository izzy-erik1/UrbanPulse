package com.urbanpulse.service;

import com.urbanpulse.model.Notification;
import com.urbanpulse.model.User;
import com.urbanpulse.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification notify(String message, User recipient) {
        Notification notification = new Notification(message, recipient);
        return notificationRepository.save(notification);
    }

    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId)
                .ifPresent(n -> {
                    n.markAsRead();
                    notificationRepository.update(n);
                });
    }

    public List<Notification> getUnreadForUser(Long userId) {
        return notificationRepository.findByUser(userId).stream()
                .filter(n -> !n.isRead())
                .collect(Collectors.toList());
    }
}
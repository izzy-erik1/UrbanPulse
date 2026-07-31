package com.urbanpulse.repository;

import com.urbanpulse.model.Notification;
import java.util.List;

public interface NotificationRepository extends Repository<Notification, Long> {

    List<Notification> findByUser(Long userId);
}
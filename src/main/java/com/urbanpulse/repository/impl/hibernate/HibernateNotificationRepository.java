package com.urbanpulse.repository.impl.hibernate;

import com.urbanpulse.model.Notification;
import com.urbanpulse.repository.NotificationRepository;
import com.urbanpulse.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class HibernateNotificationRepository implements NotificationRepository {

    @Override
    public Optional<Notification> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Notification.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Notification> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT n FROM Notification n", Notification.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Notification save(Notification entity) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    public Notification update(Notification entity) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Notification merged = em.merge(entity);
            em.getTransaction().commit();
            return merged;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Notification notification = em.find(Notification.class, id);
            if (notification != null) {
                em.remove(notification);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Notification> findByUser(Long userId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT n FROM Notification n WHERE n.recipient.id = :userId", Notification.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
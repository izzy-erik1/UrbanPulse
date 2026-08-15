package com.urbanpulse.repository.impl.hibernate;

import com.urbanpulse.model.Municipality;
import com.urbanpulse.repository.MunicipalityRepository;
import com.urbanpulse.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateMunicipalityRepository implements MunicipalityRepository {

    @Override
    public Optional<Municipality> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Municipality.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Municipality> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT m FROM Municipality m", Municipality.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Municipality save(Municipality entity) {
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
    public Municipality update(Municipality entity) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Municipality merged = em.merge(entity);
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
            Municipality municipality = em.find(Municipality.class, id);
            if (municipality != null) {
                em.remove(municipality);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
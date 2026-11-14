package com.urbanpulse.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

// The single place every repository gets a database session from.
// Connection details come from environment variables, never from source code.
public class HibernateUtil {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = build();

    private HibernateUtil() {}

    private static EntityManagerFactory build() {
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("DB_PASSWORD environment variable is not set");
        }

        Map<String, String> overrides = new HashMap<>();
        overrides.put("jakarta.persistence.jdbc.url", envOrDefault("DB_URL",
                "jdbc:sqlserver://localhost:1433;databaseName=urbanpulse;encrypt=true;trustServerCertificate=true"));
        overrides.put("jakarta.persistence.jdbc.user", envOrDefault("DB_USERNAME", "sa"));
        overrides.put("jakarta.persistence.jdbc.password", password);

        return Persistence.createEntityManagerFactory("urbanpulse-pu", overrides);
    }

    private static String envOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? fallback : value;
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void shutdown() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }
}
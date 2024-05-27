package org.arso.utils;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EntityManagerHelper {
    private static EntityManagerFactory entityManagerFactory;

    private static final ThreadLocal<EntityManager> entityManagerHolder;


    static {

        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        properties.put("javax.persistence.jdbc.url", System.getenv("DATABASE_URL"));
        properties.put("javax.persistence.jdbc.user", System.getenv("DATABASE_USER"));
        properties.put("javax.persistence.jdbc.password", System.getenv("DATABASE_PASSWORD"));
        properties.put("eclipselink.ddl-generation", "create-or-extend-tables");
        properties.put("eclipselink.query-results-cache", "false");
        properties.put("eclipselink.cache.shared.default", "false");
        properties.put("eclipselink.cache.size.default", "0");
        properties.put("eclipselink.refresh", "true");

        entityManagerFactory = Persistence.createEntityManagerFactory("alquileres", properties);

        entityManagerHolder = new ThreadLocal<EntityManager>();

    }


    public static EntityManager getEntityManager() {

        EntityManager entityManager = entityManagerHolder.get();

        if (entityManager == null || !entityManager.isOpen()) {

            entityManager = entityManagerFactory.createEntityManager();

            entityManagerHolder.set(entityManager);

        }

        return entityManager;

    }


    public static void closeEntityManager() {

        EntityManager entityManager = entityManagerHolder.get();

        if (entityManager != null) {

            entityManagerHolder.set(null);

            entityManager.close();

        }

    }
}

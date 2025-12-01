package org.util;
import jakarta.persistence.*;

public class JPAUtil {
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("livrariaPU");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}
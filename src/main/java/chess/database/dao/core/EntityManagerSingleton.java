package chess.database.dao.core;

import chess.database.configuration.PersistanceSecureConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by aleksanderr on 08/07/17.
 */
class EntityManagerSingleton {
    private  EntityManagerFactory emf;
    private static EntityManagerSingleton ourInstance = new EntityManagerSingleton();

    public static EntityManagerSingleton getInstance() {
        return ourInstance;
    }

    private EntityManagerSingleton() {
        emf = Persistence.createEntityManagerFactory(
                "NewPersistenceUnit",
                new PersistanceSecureConfiguration().getHibernateCredential()
        );
    }

    EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}

package chess.database.dao.core;

import chess.database.configuration.PersistanceSecureConfiguration;
import org.postgresql.Driver;
import org.postgresql.ds.PGPoolingDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

/**
 * Created by aleksanderr on 08/07/17.
 */
class EntityManagerSingleton {
    private EntityManagerFactory emf;
    private static EntityManagerSingleton ourInstance = new EntityManagerSingleton();

    public static EntityManagerSingleton getInstance() {
        return ourInstance;
    }

    private EntityManagerSingleton() {
        Properties properties = new PersistanceSecureConfiguration().getHibernateCredential();

        emf = Persistence.createEntityManagerFactory("NewPersistenceUnit",properties);
    }

    EntityManager getEntityManager(){
        if(!emf.isOpen()){
            emf = Persistence.createEntityManagerFactory(
                    "NewPersistenceUnit",
                    new PersistanceSecureConfiguration().getHibernateCredential()
            );
        }
        return emf.createEntityManager();
    }
}

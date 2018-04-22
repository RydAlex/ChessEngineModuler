package chess.database.dao.core;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by aleksanderr on 25/06/17.
 */
public class CoreDAO<Type> {

    protected EntityManager em;

    protected void startConnection(){
        em = EntityManagerSingleton.getInstance().getEntityManager();
    }

    @Transactional
    public void save(Type object){
        startConnection();
        em.getTransaction().begin();
        em.persist(object);
        em.flush();
        em.getTransaction().commit();
        closeConnection();
    }

    @Transactional
    public void edit(Type object){
        startConnection();
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
        closeConnection();
    }

    @Transactional
    public void remove(Type object){
        startConnection();
        em.getTransaction().begin();
        em.remove(object);
        em.getTransaction().commit();
        closeConnection();
    }

    @Transactional
    protected void closeConnection(){
        em.close();
    }
}

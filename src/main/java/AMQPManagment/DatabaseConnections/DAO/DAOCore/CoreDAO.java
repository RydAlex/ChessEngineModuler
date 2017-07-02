package AMQPManagment.DatabaseConnections.DAO.DAOCore;

import AMQPManagment.DatabaseConnections.Configuration.HibernateConfiguration;
import AMQPManagment.DatabaseConnections.Entities.CurrentElo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by aleksanderr on 25/06/17.
 */
public class CoreDAO<Type> {
    private EntityManagerFactory emf;
    protected EntityManager em;

    public CoreDAO(){
        startConnection();
    }

    private void startConnection(){
        emf = Persistence.createEntityManagerFactory(
                "NewPersistenceUnit",
                new HibernateConfiguration().getHibernateCredential()
        );
        em = emf.createEntityManager();
    }

    public void save(Type object){
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    public void edit(Type object){
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
    }

    public void remove(Type object){
        em.getTransaction().begin();
        em.remove(object);
        em.getTransaction().commit();
    }

    public void closeConnection(){
        em.close();
        emf.close();
    }
}

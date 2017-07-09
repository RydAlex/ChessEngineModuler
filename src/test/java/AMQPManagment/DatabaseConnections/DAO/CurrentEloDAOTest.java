package AMQPManagment.DatabaseConnections.DAO;

import AMQPManagment.DatabaseConnections.Entities.CurrentElo;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aleksanderr on 08/07/17.
 */
public class CurrentEloDAOTest {
    @Test
    public void canSearchEloDependsOnEngineName(){
        CurrentEloDAO currentEloDAO = new CurrentEloDAO();
        CurrentElo currElo = currentEloDAO.findByEngineName("daydreamer").get(0);
    }
}
package AMQPManagment.DatabaseConnections.Service;

import AMQPManagment.DatabaseConnections.DAO.CurrentEloDAO;
import AMQPManagment.DatabaseConnections.DAO.EngineNameDAO;
import AMQPManagment.DatabaseConnections.Entities.EngineName;

/**
 * Created by aleksanderr on 02/07/17.
 */
public class EloService {
    public void getElo(String engineName){
        CurrentEloDAO currElo = new CurrentEloDAO();

    }
}

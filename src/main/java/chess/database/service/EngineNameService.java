package chess.database.service;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.dao.EngineNameDAO;
import chess.database.entities.EngineName;

import javax.transaction.Transactional;
import java.util.List;

public class EngineNameService {

    @Transactional
    public EngineName saveEngineNameEntity(EngineName engineName){
        EngineNameDAO engineNameDAO = new EngineNameDAO();
        engineNameDAO.edit(engineName);
        return engineName;
    }

    @Transactional
    public EngineName saveEngineNameEntity(String engineName,
                                       TypeOfMessageExtraction type,
                                       Integer elo) {
        EngineNameDAO dao = new EngineNameDAO();
        EngineName engineNameElem = new EngineName();
        engineNameElem.setEngineName(engineName);
        engineNameElem.setCurrentElo(elo);
        engineNameElem.setTypeOfGame(type.getTypeOfGame());
        dao.save(engineNameElem);
        return engineNameElem;
    }

    @Transactional
    public List<EngineName> getEngineNameEntity(String engineName){
        EngineNameDAO dao = new EngineNameDAO();
        return dao.findByName(engineName);
    }



    @Transactional
    public List<EngineName> getEngineNameEntityWithTypeOfGameFilter
                            (String engineName, TypeOfMessageExtraction type){
        EngineNameDAO dao = new EngineNameDAO();
        return dao.findByNameAndType(engineName, type.getTypeOfGame());
    }
}

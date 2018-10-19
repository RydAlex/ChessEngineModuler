package chess.database.service;

import chess.database.dao.EngineDAO;
import chess.database.entities.Engine;

public class EngineService {

    public Engine findByNameOrCreate(String name){
        EngineDAO engineDAO = new EngineDAO();
        Engine engine = engineDAO.getEngineByName(name);
        if(engine == null){
            engine = new Engine();
            engine.setEngineName(name);
            engineDAO.save(engine);
        }
        return engine;
    }
}

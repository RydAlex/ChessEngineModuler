package chess.ui.rest.endpoints;

import chess.amqp.message.EngineEloPair;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.service.EloService;
import chess.database.service.EngineNameService;
import chess.engine.processor.interfaces.EngineRunner;
import chess.engine.processor.interfaces.EngineRunnerImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 21/07/17.
 */
@RestController
@RequestMapping("/engine/names/")
public class EngineNameEndpoint {
    EngineNameService engineNameService = new EngineNameService();

    @RequestMapping("/")
    public List<String> fetchEnginesNames(){
        EngineRunner runner = new EngineRunnerImpl();
        return runner.getEngineNames();
    }

    @RequestMapping("/timeout/{timeoutValue}")
    public List<EngineEloPair> fetchEnginesNamesWithEloByTimeout(@PathVariable(value="timeoutValue") Integer timeoutValue){
        return engineNameService.findEloValuesByTimeoutValue(timeoutValue);
    }

    @RequestMapping("/depth/{depthValue}")
    public List<EngineEloPair> fetchEnginesNamesWithEloByDepth(@PathVariable(value="depthValue") Integer depthValue){
        return engineNameService.findEloValuesByDepthValue(depthValue);
    }
}

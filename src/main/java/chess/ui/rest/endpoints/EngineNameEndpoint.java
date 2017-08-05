package chess.ui.rest.endpoints;

import chess.engine.processor.interfaces.EngineRunner;
import chess.engine.processor.interfaces.EngineRunnerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by aleksanderr on 21/07/17.
 */
@RestController
@RequestMapping("/engine")
public class EngineNameEndpoint {

    @RequestMapping("/names")
    public List<String> fetchEnginesNames(){
        EngineRunner runner = new EngineRunnerImpl();
        return runner.getEngineNames();
    }
}

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
@RequestMapping("/play")
public class PlayGameEndpoint {

	@RequestMapping("/simple")
	public List<String> eloSimpleGame(){
		EngineRunner runner = new EngineRunnerImpl();
		return runner.getEngineNames();
	}

	@RequestMapping("/eloVotes")
	public List<String> eloVotesGame(){
		EngineRunner runner = new EngineRunnerImpl();
		return runner.getEngineNames();
	}

	@RequestMapping("/eloDist")
	public List<String> eloDistGame(){
		EngineRunner runner = new EngineRunnerImpl();
		return runner.getEngineNames();
	}

	@RequestMapping("/random")
	public List<String> randomGame(){
		EngineRunner runner = new EngineRunnerImpl();
		return runner.getEngineNames();
	}
}

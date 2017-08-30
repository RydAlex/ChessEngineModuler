package chess.ui.rest.endpoints;

import chess.engine.processor.interfaces.EngineRunner;
import chess.engine.processor.interfaces.EngineRunnerImpl;
import chess.manager.game.definitions.SingleMoveGameDefiner;
import chess.ui.data.GameSpecification;
import chess.ui.data.NameWithRuleHolder;
import chess.utils.parsing.objects.EngineNameUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by aleksanderr on 21/07/17.
 */
@RestController
@CrossOrigin(origins = "*")
public class PlayGameEndpoint {

	@RequestMapping(value = "/play", method = RequestMethod.POST)
	public String eloSimpleGame(@RequestBody GameSpecification gameSpecification){
		SingleMoveGameDefiner singleMoveGameDefiner = new SingleMoveGameDefiner();
		String answer = "";
		fillEnginesListWithDummies(gameSpecification);
		if(EngineNameUtil.fetchRuleTypeFromRule(gameSpecification.getRule()).equals("timeout")){
			answer = singleMoveGameDefiner.sendSimpleTimeoutMoveRequestWithDefinedNames(
					gameSpecification.getGameEngines(),
					Integer.parseInt(
							EngineNameUtil.fetchNumberFromRule(
									gameSpecification.getRule()
							)
					),
					gameSpecification.getType(),
					gameSpecification.getChessboardFen()
			);
		} else {
			answer = singleMoveGameDefiner.sendSimpleDepthMoveRequestWithDefinedNames(
					gameSpecification.getGameEngines(),
					Integer.parseInt(
							EngineNameUtil.fetchNumberFromRule(
									gameSpecification.getRule()
							)
					),
					gameSpecification.getType(),
					gameSpecification.getChessboardFen()
			);
		}
		return "{ \"answer\" : \"" +  answer + "\"}";
	}

	private void fillEnginesListWithDummies(GameSpecification gameSpecification) {
		List<String> engines = gameSpecification.getGameEngines();
		for(int i=0 ; i<4; i++){
			engines.add("DUMMY");
		}
		gameSpecification.setGameEngines(engines);

	}
}

package chess.ui.rest.endpoints;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.ui.data.LevelEloPair;
import chess.ui.data.NameWithRuleHolder;
import chess.ui.helpers.ClusterTypeAssociationSearch;
import chess.ui.rest.services.GameApproachService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/approach")
public class GameApproachEndpoint {
	GameApproachService gameApproachService = new GameApproachService();

	@RequestMapping(value = "/best", method = RequestMethod.POST)
	public Map<TypeOfMessageExtraction, Integer> fetchBest(@RequestBody NameWithRuleHolder namesWithRules){
		List<LevelEloPair> levelEloPairs = gameApproachService.fetchBestCalculationOptionForCluster(namesWithRules);
		Map<TypeOfMessageExtraction, Integer> map = ClusterTypeAssociationSearch.findAssociationTo(levelEloPairs, namesWithRules.getRule());
		map.put(TypeOfMessageExtraction.ELO_SIMPLE, gameApproachService.findBestEngineInSingleEloGames(levelEloPairs));
		return map;
	}

	@RequestMapping("/all")
	public List<String> fetchAll(){
		return gameApproachService.fetchAllEngineApproaches();
	}
}

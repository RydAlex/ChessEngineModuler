package chess.ui.rest.endpoints;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.ui.data.LevelEloPair;
import chess.ui.data.NameWithRuleHolder;
import chess.ui.helpers.ClusterTypeAssociationSearch;
import chess.ui.rest.services.GameApproachService;
import chess.utils.parsing.objects.EngineNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/approach")
public class GameApproachEndpoint {
	GameApproachService gameApproachService = new GameApproachService();

	@RequestMapping(value = "/best", method = RequestMethod.POST)
	public Map<TypeOfMessageExtraction, Integer> fetchBest(@RequestBody NameWithRuleHolder namesWithRules){
		log.info(EngineNameUtil.constructClusterNameFromEngineName(namesWithRules.getNames()));
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

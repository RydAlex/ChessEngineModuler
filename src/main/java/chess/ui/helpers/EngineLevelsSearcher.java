package chess.ui.helpers;

import chess.amqp.message.EngineEloPair;
import chess.database.service.EloPairService;
import chess.ui.data.EngineLevel;
import chess.utils.parsing.objects.EngineNameUtil;

import java.util.List;

public class EngineLevelsSearcher {
	EloPairService engineNameService = new EloPairService();

	public EngineLevel setLevelOfEngine(EngineEloPair eloPairOfSearchEngine, String rule){
		List<EngineEloPair> eloPairs =
				rule.contains("depth") ?
					engineNameService.findEloValuesByDepthValue(
							EngineNameUtil.changeRuleToNumber(rule)) :
					engineNameService.findEloValuesByTimeoutValue(
							EngineNameUtil.changeRuleToNumber(rule)
				);

		sortEloPairsList(eloPairs);

		return discoverEngineLevel(eloPairOfSearchEngine, eloPairs);
	}

	private EngineLevel discoverEngineLevel(EngineEloPair eloPairOfSearchEngine, List<EngineEloPair> eloPairs) {
		int indexOfEngine = findIndexInArray(eloPairOfSearchEngine, eloPairs);
		int levelIntervalIndex = eloPairs.size()/3;
		if(indexOfEngine < levelIntervalIndex){
			return EngineLevel.WEAK;
		} else if(levelIntervalIndex > (eloPairs.size() - indexOfEngine)){
			return EngineLevel.STRONG;
		} else {
			return EngineLevel.AVERAGE;
		}
	}

	private void sortEloPairsList(List<EngineEloPair> eloPairs) {
		eloPairs.sort((lhs, rhs) ->
				lhs.getEloValue() < rhs.getEloValue() 	? -1 :
						(lhs.getEloValue() > rhs.getEloValue()) ? 1 :
								0
		);
	}

	private int findIndexInArray(EngineEloPair eloPairOfSearchEngine, List<EngineEloPair> eloPairs) {
		for(int i=0; i<eloPairs.size(); i++){
			if(eloPairOfSearchEngine.getEngineName().equals(eloPairs.get(i).getEngineName())){
				return i;
			}
		}
		return -1;
	}
}

package chess.ui.rest.services;

import chess.amqp.message.EngineEloPair;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.service.EloPairService;
import chess.ui.data.LevelEloPair;
import chess.ui.data.NameWithRuleHolder;
import chess.ui.helpers.EngineLevelsSearcher;

import java.util.LinkedList;
import java.util.List;

public class GameApproachService {
	EloPairService engineNameService = new EloPairService();

	public List<String> fetchAllEngineApproaches(){
		List<String> listOfTypes = new LinkedList<>();
		for(TypeOfMessageExtraction type : TypeOfMessageExtraction.values()){
			listOfTypes.add(type.getTypeOfGame());
		}
		return listOfTypes;
	}

	public List<LevelEloPair> fetchBestCalculationOptionForCluster(NameWithRuleHolder namesWithRules){
		List<LevelEloPair> enginesToReturn= new LinkedList<>();
		EngineLevelsSearcher engineLevelsSearcher = new EngineLevelsSearcher();
		String rule = namesWithRules.getRule();

		for(String engineName : namesWithRules.getNames()){
			LevelEloPair levelEloPair = new LevelEloPair();

			EngineEloPair eloPair = engineNameService.findEloValuesByNameAndRule(engineName, rule);

			if(eloPair != null){
				levelEloPair.setEloPair(eloPair);
				levelEloPair.setEngineLevel(engineLevelsSearcher.setLevelOfEngine(eloPair, rule));
				enginesToReturn.add(levelEloPair);
			}
		}
		return enginesToReturn;
	}

	public Integer findBestEngineInSingleEloGames(List<LevelEloPair> levelEloPairs) {
		EngineEloPair eloPair = new EngineEloPair("",0);
		for(LevelEloPair levelEloPair : levelEloPairs){
			if(levelEloPair.getEloPair().getEloValue() > eloPair.getEloValue()){
				eloPair = levelEloPair.getEloPair();
			}
		}
		return eloPair.getEloValue();
	}
}

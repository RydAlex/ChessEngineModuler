package chess.database.service;

import chess.amqp.message.EngineEloPair;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.dao.CurrentEloDAO;
import chess.database.dao.EngineNameDAO;
import chess.database.entities.CurrentElo;
import chess.engine.processor.interfaces.EngineRunner;
import chess.engine.processor.interfaces.EngineRunnerImpl;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EngineNameService {
	EngineNameDAO engineNameDAO = new EngineNameDAO();
	CurrentEloDAO currentEloDAO = new CurrentEloDAO();

	@Transactional
	public List<EngineEloPair> findEloValuesByDepthValue(int value) {
		String depthRule = "_depth_"  + value;
		return fetchEngineNamesWithRules(depthRule);
	}

	@Transactional
	public List<EngineEloPair> findEloValuesByTimeoutValue(int value) {
		String timeoutRule = "_timeout_"  + value;
		return fetchEngineNamesWithRules(timeoutRule);
	}

	@Transactional
	public EngineEloPair findEloValuesByNameAndRule(String name, String rule) {
		String fullEngineName;
		if(rule.contains("_")){
			fullEngineName = name + "_" + rule;
		} else {
			if(Integer.parseInt(rule)>100){
				fullEngineName = name + "_timeout_" + rule;
			} else {
				fullEngineName = name + "_depth_" + rule;
			}
		}
		List<CurrentElo> dbElements = currentEloDAO.findByEngineNameAndType(fullEngineName);
		return !dbElements.isEmpty() ? new EngineEloPair(name, dbElements.get(0).getEloValue()) : null;
	}

	@Transactional
	public Map<EngineEloPair, TypeOfMessageExtraction> findEloValuesByName(String name) {
		Map<EngineEloPair, TypeOfMessageExtraction> eloPairList = new HashMap<>();
		for(TypeOfMessageExtraction type :  TypeOfMessageExtraction.values()){
			List<CurrentElo> eloList = currentEloDAO.findByEngineNameAndType(name, type);
			if(!eloList.isEmpty()){
				eloPairList.put(new EngineEloPair(name, eloList.get(0).getEloValue()), type);
			}
		}
		return eloPairList;
	}

	private List<EngineEloPair> fetchEngineNamesWithRules(String ruleToSearch){
		EngineRunner runner = new EngineRunnerImpl();
		List<EngineEloPair> enginesWithValues = new LinkedList<>();
		List<String> names = runner.getEngineNames();
		for(String name : names){
			List<CurrentElo> currentEloList = currentEloDAO.findByEngineNameAndType(
					name + ruleToSearch,
					TypeOfMessageExtraction.ELO_SIMPLE
			);
			if(! currentEloList.isEmpty()){
				EngineEloPair eloPair = new EngineEloPair();
				eloPair.setEloValue(currentEloList.get(0).getEloValue());
				eloPair.setEngineName(name);
				enginesWithValues.add(eloPair);
			}
		}
		return enginesWithValues;
	}
}

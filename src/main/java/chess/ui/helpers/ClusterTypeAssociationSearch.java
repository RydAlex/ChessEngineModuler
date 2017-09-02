package chess.ui.helpers;

import chess.amqp.message.EngineEloPair;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.service.EloPairService;
import chess.ui.data.EngineClustersType;
import chess.ui.data.EngineLevel;
import chess.ui.data.LevelEloPair;
import chess.utils.parsing.objects.EngineNameUtil;
import chess.utils.parsing.objects.EnginesCluster;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ClusterTypeAssociationSearch {
	/**
	 *
	 * @param levelEloPairs
	 * @param rule
	 * @return
	 */
	public static Map<TypeOfMessageExtraction, Integer> findAssociationTo(List<LevelEloPair> levelEloPairs, String rule){
		List<EngineLevel> engineLevels = new LinkedList<>();
		for(LevelEloPair levelEloPair : levelEloPairs){
			engineLevels.add(levelEloPair.getEngineLevel());
		}
		Map<EngineClustersType, Double> similarity = new HashMap<>();
		for(EngineClustersType clustersType : EngineClustersType.values()){
			Double value = compatibilityCheck(clustersType, engineLevels);
			similarity.put(clustersType, value);
			log.info("Similarity to " + clustersType.name() + " in " + value*100 + "%");
		}
		return findEloValuesForBestSimaliritiesAndReturnBestOfIt(similarity, rule);
	}

	/**
	 *
	 * @param clusterType
	 * @param engineLevels
	 * @return
	 */
	private static double compatibilityCheck(EngineClustersType clusterType, List<EngineLevel> engineLevels){
		List<Boolean> compatibiltyMatrix = new LinkedList<>();
		for(int i=0; i<clusterType.getEnginesLevels().size(); i++){
			compatibiltyMatrix.add(false);
		}
		for(EngineLevel clusterEngineLevel : clusterType.getEnginesLevels()){
			for(int i=0; i<engineLevels.size(); i++){
				if(engineLevels.get(i).equals(clusterEngineLevel)
						&& compatibiltyMatrix.get(i).equals(false)) {
					compatibiltyMatrix.set(i, true);
					break;
				}
			}
		}
		return calculatePercentageCompatibilty(compatibiltyMatrix);
	}

	private static double calculatePercentageCompatibilty(List<Boolean> compatibiltyMatrix) {
		Double i = 0.0;
		for(Boolean bool : compatibiltyMatrix){
			if(bool){
				i++;
			}
		}
		return i/compatibiltyMatrix.size();
	}

	private static Map<TypeOfMessageExtraction, Integer> findEloValuesForBestSimaliritiesAndReturnBestOfIt(Map<EngineClustersType, Double> similarity, String rule) {
		Map<EngineEloPair, TypeOfMessageExtraction> mapToCheck =
				fetchDatabasePosibilities(similarity, rule);

		String name = findNameWithHighestElo(mapToCheck);

		Map<TypeOfMessageExtraction, Integer> fetchedEngines =
				fetchEnginesWithThisName(mapToCheck, name);

		return fetchedEngines;
	}

	/**
	 * For given map find all engines which have name like given
	 */
	private static Map<TypeOfMessageExtraction, Integer> fetchEnginesWithThisName(Map<EngineEloPair, TypeOfMessageExtraction> mapToCheck, String name) {
		Map<TypeOfMessageExtraction, Integer> mapWithTypes = new HashMap<>();
		for(Map.Entry<EngineEloPair, TypeOfMessageExtraction> mapElement : mapToCheck.entrySet()){
			if(mapElement.getKey().getEngineName().equals(name)){
				mapWithTypes.put(mapElement.getValue(), mapElement.getKey().getEloValue());
			}
		}
		return mapWithTypes;
	}

	/**
	 * In map find name of EloPair with highest elo
	 */
	private static String findNameWithHighestElo(Map<EngineEloPair, TypeOfMessageExtraction> mapToCheck) {
		EngineEloPair eloPair = new EngineEloPair("", 0);
		for(Map.Entry<EngineEloPair, TypeOfMessageExtraction> map : mapToCheck.entrySet()){
			if(map.getKey().getEloValue() >= eloPair.getEloValue()){
				eloPair = map.getKey();
			}
		}
		return eloPair.getEngineName();
	}

	/**
	 * Method to find elos in Database for engines which are in csv files and have biggest similarity to given engine
	 */
	private static Map<EngineEloPair, TypeOfMessageExtraction> fetchDatabasePosibilities(Map<EngineClustersType, Double> similarity, String rule) {
		Map<EngineEloPair, TypeOfMessageExtraction> mapToCheck = new HashMap<>();
		EloPairService nameService = new EloPairService();
		double checkValue = 1.0;

		while(mapToCheck.isEmpty()){
			for(Map.Entry<EngineClustersType, Double> entry : similarity.entrySet()){

				if(entry.getValue().equals(checkValue)){
					EnginesCluster cluster = CSVClusterNameFetcher.lookCSVInSearchOfAskedEngineCluster(entry.getKey(),rule);
					String name = EngineNameUtil.constructClusterNameFromEloPairs(cluster.getEngineList(), rule);
					for(Map.Entry<EngineEloPair, TypeOfMessageExtraction> map : nameService.findEloValuesByName(name).entrySet()){
						mapToCheck.put(map.getKey(), map.getValue());

						log.info("This engine was fetched cause had biggest similatity " + map.getKey().getEngineName() +
									" with this ELO " + map.getKey().getEloValue() + " and this type " +
									map.getValue());
					}
				}
			}
			checkValue -= 0.25;
		}
		return mapToCheck;
	}
}

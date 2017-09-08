package chess.ui.helpers;

import chess.engine.processor.core.enginemechanism.OsCheck;
import chess.ui.data.EngineClustersType;
import chess.utils.name.spy.EngineSearcher;
import chess.utils.parsing.objects.EnginesCluster;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

@Slf4j
public class CSVClusterNameFetcher {
	public static EnginesCluster lookCSVInSearchOfAskedEngineCluster(EngineClustersType clustersType, String ruleValue){

		EnginesCluster cluster = new EnginesCluster();

		String csvFileName = "PreDefinedClusters" + (OsCheck.isMac() ? "Mac.csv" : "Linux.csv");
		String csvFile = EngineSearcher.class.getClassLoader().getResource("csvClustersDefinitions/" + csvFileName).getPath();
		try{
			Boolean isValid = false;
			String line, cvsSplitBy = ",";
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if(line.contains("//")) {
					isValid = isValidSection(clustersType, line);
				}
				if(line.contains(ruleValue.split("_")[1]) && isValid){
					return extractNameAndRuleOfClusterFromCSV(cluster, line, cvsSplitBy);
				}
			}
		} catch(Exception ex){
			log.info(ex.getMessage());
			throw new RuntimeException(ex);
		}
		return cluster;
	}

	private static EnginesCluster extractNameAndRuleOfClusterFromCSV(EnginesCluster cluster, String line, String cvsSplitBy) {
		String[] lineItem = line.split(cvsSplitBy);
		for(int i=0 ; i<4 ; i++){
			cluster.addEngineToCluster(lineItem[i]);
		}
		Integer rule_value = Integer.parseInt(lineItem[4]);
		cluster.setPlayRule(rule_value);
		return cluster;
	}

	private static Boolean isValidSection(EngineClustersType clustersType, String line) {
		String csvRule = line.replace("//","");
		if(EngineClustersType.findByName(csvRule) != null && Objects.equals(EngineClustersType.findByName(csvRule), clustersType)){
			return true;
		} else {
			return false;
		}
	}
}

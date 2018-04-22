package chess.utils.name.spy;

import chess.engine.processor.core.enginemechanism.OsCheck;
import chess.engine.processor.interfaces.EngineRunnerImpl;
import chess.utils.parsing.objects.EnginesCluster;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by aleksanderr on 30/05/17.
 */
@Slf4j
public class EngineSearcher {

    public static List<List<String>> createPairsOfGames() {
        int engineClusterSize = 2;

        List<List<String>> listOfEnginesPairsToReturn = new LinkedList<>();
        List<String> engineList = new EngineRunnerImpl().getEngineNames();

        while(engineList.size() >= engineClusterSize){
            List<String> pairList = new LinkedList<>();
            for(int i=0; i<engineClusterSize; i++){
                pairList = takeOneElementFromFirstAndPassToSecondList(engineList, pairList);
            }
            listOfEnginesPairsToReturn.add(pairList);
        }
        return listOfEnginesPairsToReturn;
    }

    private static List<String> takeOneElementFromFirstAndPassToSecondList(List<String> engineList, List<String> pairList) {
        int chooseNumber = new Random().nextInt(engineList.size());
        pairList.add(engineList.remove(chooseNumber));
        return pairList;

    }

    public static List<EnginesCluster> createClustersFromPredefindedCSV(String csv_name){
        String csvFileExtensionSufix = ".csv";
        List<EnginesCluster> listOfEnginesPairsToReturn = new LinkedList<>();
        String csvFileName = csv_name + OsCheck.takeOsName() + csvFileExtensionSufix;
        fetchEnginesFromThisCSV(listOfEnginesPairsToReturn, csvFileName);
        return listOfEnginesPairsToReturn;
    }

    private static void fetchEnginesFromThisCSV(List<EnginesCluster> listOfEnginesPairsToReturn, String csvFileName) {
        String csvFile = EngineSearcher.class.getClassLoader().getResource("csvClustersDefinitions/" + csvFileName).getPath();
        try{
            String line;
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                parseCSVLine(listOfEnginesPairsToReturn, line);
            }
        } catch(Exception ex){
            log.info(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private static void parseCSVLine(List<EnginesCluster> listOfEnginesPairsToReturn, String line) {
        String cvsSplitBy = ",";

        if(!line.contains("//") && !line.isEmpty()){
            EnginesCluster cluster = new EnginesCluster();
            String[] lineItem = line.split(cvsSplitBy);
            for(int i=0 ; i<4 ; i++){
                cluster.addEngineToCluster(lineItem[i]);
            }
            Integer rule_value = Integer.parseInt(lineItem[4]);
            cluster.setPlayRule(rule_value);
            listOfEnginesPairsToReturn.add(cluster);
        }
    }
}

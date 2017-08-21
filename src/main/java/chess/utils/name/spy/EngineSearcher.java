package chess.utils.name.spy;

import chess.engine.processor.core.enginemechanism.OsCheck;
import chess.engine.processor.interfaces.EngineRunnerImpl;
import chess.ui.data.EngineClustersType;
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
    public static List<String> searchFewRandomEngineNames(int numberOfEngines){
        LinkedList<String> listOfNames = new LinkedList<>();
        List<String> engineList = new EngineRunnerImpl().getEngineNames();
        for(int i=0 ; i<numberOfEngines ; i++){
            int randomEngineNumber = new Random().nextInt(engineList.size());
            listOfNames.add(engineList.get(randomEngineNumber));
            engineList.remove(randomEngineNumber);
        }
        return listOfNames;
    }


    public static List<List<String>> rematchBestEnginesForDepthOrTimeout(){
        List<List<String>> listOfEnginesPairsToReturn = new LinkedList<>();

        String[] enginesToChoose = new String[]{
                "komodo",
                "gull",
                "senpai",
                "stockfish"
        };

        List<String> firstPair = new LinkedList<>();
        firstPair.add(enginesToChoose[0]);
        int choosenOpponentIndex = new Random().nextInt(enginesToChoose.length-1)+1;
        firstPair.add(enginesToChoose[choosenOpponentIndex]);

        List<String> secPair = new LinkedList<>();
        for(int i=1 ; i<enginesToChoose.length ; i++){
            if(i != choosenOpponentIndex){
                secPair.add(enginesToChoose[i]);
            }
        }
        listOfEnginesPairsToReturn.add(firstPair);
        listOfEnginesPairsToReturn.add(secPair);
        return listOfEnginesPairsToReturn;
    }

    public static List<List<String>> createPairsOfGames() {
        List<List<String>> listOfEnginesPairsToReturn = new LinkedList<>();
        List<String> engineList = new EngineRunnerImpl().getEngineNames();
        String valuesAlreadyTaken = " ";
        Random random = new Random();
        for(int i=0 ; i<engineList.size()/2 ; i++){
            LinkedList<String> engineNames = new LinkedList<>();
            int engineOneListNumber = -1;
            int engineTwoListNumber = -1;
            boolean engineFreeFound = false;
            while(!engineFreeFound) {
                engineOneListNumber = random.nextInt(engineList.size());
                if(!valuesAlreadyTaken.contains(" " + engineOneListNumber + " ")){
                    engineFreeFound = true;
                }
            }
            valuesAlreadyTaken += engineOneListNumber+" ";
            engineFreeFound = false;
            while(!engineFreeFound) {
                engineTwoListNumber = random.nextInt(engineList.size());
                if (!valuesAlreadyTaken.contains(" " + engineTwoListNumber + " ")) {
                    engineFreeFound = true;
                }
            }
            valuesAlreadyTaken += engineTwoListNumber + " ";
            engineNames.add(engineList.get(engineOneListNumber));
            engineNames.add(engineList.get(engineTwoListNumber));
            listOfEnginesPairsToReturn.add(engineNames);
        }
        return listOfEnginesPairsToReturn;
    }


    public static List<EnginesCluster> createPreDefinedClusters(){
        List<EnginesCluster> listOfEnginesPairsToReturn = new LinkedList<>();
        String csvFileName = "PreDefinedClusters" + (OsCheck.isMac() ? "Mac.csv" : "Linux.csv");
        String csvFile = EngineSearcher.class.getClassLoader().getResource("csvClustersDefinitions/" + csvFileName).getPath();
        try{
            String line = "";
            String cvsSplitBy = ",";
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
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
        } catch(Exception ex){
            log.info(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return listOfEnginesPairsToReturn;
    }
}

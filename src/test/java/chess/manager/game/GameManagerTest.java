package chess.manager.game;

import chess.geneticAlgorithm.CrossoverMechanism;
import chess.geneticAlgorithm.MutationMechanism;
import chess.geneticAlgorithm.SelectionMechanism;
import chess.utils.ChessCluster;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GameManagerTest {
    @Test
    public void testCreatingGame(){
        for(int i=0 ; i<100; i++) {
            List<ChessCluster> chessEnginesClusters = SelectionMechanism.madeSelection();
            validateList(chessEnginesClusters);
            chessEnginesClusters = CrossoverMechanism.madeCrossover(chessEnginesClusters);
            Assert.assertTrue(chessEnginesClusters.size() < 15 || chessEnginesClusters.size() >= 10);
            validateList(chessEnginesClusters);
            chessEnginesClusters = MutationMechanism.mutate(chessEnginesClusters);
            Assert.assertEquals(20,chessEnginesClusters.size());
            validateList(chessEnginesClusters);
        }
    }

    private void validateList(List<ChessCluster> chessEnginesClusters){
        for(int i=0; i<chessEnginesClusters.size(); i++){
            for(int j=0; j<chessEnginesClusters.size(); j++){
                if(i != j) {
                    ChessCluster clusterOne = chessEnginesClusters.get(i);
                    ChessCluster clusterTwo = chessEnginesClusters.get(j);
                    if(clusterOne.equals(clusterTwo)){
                        Assert.fail();
                    }
                }
            }
        }
    }
}
package chess.geneticAlgorithm;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.utils.ChessCluster;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class CrossoverMechanismTest {

    private LinkedList<ChessCluster> clusters = new LinkedList<>();

    @Before
    public void prepare(){
        for(int i=0; i<5 ; i++){
            ChessCluster chessCluster = null;
            while(!clusters.contains(chessCluster) && chessCluster == null){
                chessCluster = createChessCluster(i);
            }
            clusters.add(chessCluster);
        }
    }

    @Test
    public void isEngines_AreNotDuplicate(){
        for(int k=0; k<100000; k++) {
            List<ChessCluster> cluster = CrossoverMechanism.madeCrossover(clusters);
            for(int i=0; i<cluster.size(); i++){
                for(int j=0; j<cluster.size(); j++){
                    if(i != j) {
                        if(cluster.get(i).equals(cluster.get(j))){
                            Assert.fail();
                        }
                    }
                }
            }
            System.out.println(k + " Phase is finished.");
        }
    }

    private ChessCluster createChessCluster(int i) {
        LinkedList<Engine> engines = new LinkedList<>();
        for(int j=0; j<30; j++){
            if(new Random().nextDouble()<0.5){
                engines.add(new Engine(j,"stockfish9"));
            } else {
                engines.add(new Engine(j,"komodo8"));
            }
        }
        return new ChessCluster(new Cluster(i, 1, 1500), engines);
    }
}
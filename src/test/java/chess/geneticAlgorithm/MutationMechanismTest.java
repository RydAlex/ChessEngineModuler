package chess.geneticAlgorithm;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.utils.ChessCluster;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class MutationMechanismTest {

    @Test
    public void createNewClusterByMutation() {
        for(int k=0 ; k<100000; k++) {
            LinkedList<ChessCluster> clusters = new LinkedList<>();

            clusters.add(createChessCluster("stockfish9"));
            clusters.add(createChessCluster("komodo8"));
            clusters.add(createChessCluster("komodo7"));
            clusters.add(createChessCluster("stockfish8"));
            clusters.add(createChessCluster("stockfish7"));

            List<ChessCluster> newClusters = MutationMechanism.mutate(clusters);
            for (int i = 0; i < newClusters.size(); i++) {
                for (int j = 0; j < newClusters.size(); j++) {
                    if (i != j) {
                        if (newClusters.get(i).equals(newClusters.get(j))) {
                            Assert.fail();
                        }
                    }
                }
            }
            System.out.println(k + "is finished");
        }
    }


    private ChessCluster createChessCluster(String chessEngine) {
        Cluster cluster = new Cluster(1,1500, 1);

        LinkedList<Engine> chessEngines = new LinkedList<>();
        for(int i=0; i<30; i++){
            chessEngines.add(new Engine(1,chessEngine));
        }

        return new ChessCluster(cluster, chessEngines);
    }
}
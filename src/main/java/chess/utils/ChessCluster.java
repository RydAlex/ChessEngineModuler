package chess.utils;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@EqualsAndHashCode(of="engineList")
@AllArgsConstructor
public class ChessCluster {
    Cluster cluster;
    List<Engine> engineList;

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public List<Engine> getEngineList() {
        return engineList;
    }

    public void setEngineList(List<Engine> engineList) {
        this.engineList = engineList;
    }
}

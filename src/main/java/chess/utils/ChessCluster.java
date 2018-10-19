package chess.utils;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChessCluster {
    Cluster cluster;
    List<Engine> engineList;
}

package chess.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ChessClusterBattle {
    ChessCluster chessClusterOne;
    ChessCluster chessClusterTwo;

    public ChessCluster getChessClusterOne() {
        return chessClusterOne;
    }

    public void setChessClusterOne(ChessCluster chessClusterOne) {
        this.chessClusterOne = chessClusterOne;
    }

    public ChessCluster getChessClusterTwo() {
        return chessClusterTwo;
    }

    public void setChessClusterTwo(ChessCluster chessClusterTwo) {
        this.chessClusterTwo = chessClusterTwo;
    }
}

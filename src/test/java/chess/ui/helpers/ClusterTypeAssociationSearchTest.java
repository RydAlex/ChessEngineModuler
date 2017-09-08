package chess.ui.helpers;

import chess.ui.data.EngineLevel;
import chess.ui.data.LevelEloPair;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class ClusterTypeAssociationSearchTest {

	@Test
	public void findAssociationTo() throws Exception {
		List<LevelEloPair> engineLevels = new LinkedList<>();
		engineLevels.add(engineWithLevel(EngineLevel.AVERAGE));
		engineLevels.add(engineWithLevel(EngineLevel.AVERAGE));
		engineLevels.add(engineWithLevel(EngineLevel.AVERAGE));
		engineLevels.add(engineWithLevel(EngineLevel.WEAK));
		ClusterTypeAssociationSearch.findAssociationTo(engineLevels, null);
	}

	private LevelEloPair engineWithLevel(EngineLevel engineLevel){
		LevelEloPair levelEloPair = new LevelEloPair();
		levelEloPair.setEngineLevel(engineLevel);
		return levelEloPair;
	}
}
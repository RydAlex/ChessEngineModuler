package chess.ui.helpers;

import chess.ui.data.EngineClustersType;
import chess.utils.parsing.objects.EnginesCluster;
import org.junit.Assert;
import org.junit.Test;

public class CSVClusterNameFetcherTest {
	@Test
	public void clusterFromNameAndClusterType() throws Exception {
		EnginesCluster cluster = CSVClusterNameFetcher.lookCSVInSearchOfAskedEngineCluster(EngineClustersType.BlackSheep, "timeout_3000");
		Assert.assertTrue(cluster != null);
	}
}
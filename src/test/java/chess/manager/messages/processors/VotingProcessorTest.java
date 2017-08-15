package chess.manager.messages.processors;

import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.message.GameVotingStats;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class VotingProcessorTest {

	@Test
	public void applyVotesToDatabase() throws Exception {
		ChessJSONObject obj = new ChessJSONObject();

		List<String> engineInGame = new LinkedList<>();
		engineInGame.add("cinnamon");
		engineInGame.add("daydreamer");
		engineInGame.add("fruit");
		engineInGame.add("glaurung");
		engineInGame.add("arasanx");
		engineInGame.add("blackmamba");
		engineInGame.add("cheng");
		engineInGame.add("cinnamon");
		obj.setChessGameName(engineInGame);

		List<GameVotingStats> votesTakenInGame = new LinkedList<>();
		votesTakenInGame.add(new GameVotingStats("fruit_daydreamer", 18));
		votesTakenInGame.add(new GameVotingStats("cinnamon", 12));
		votesTakenInGame.add(new GameVotingStats("cinnamon_glaurung", 24));
		votesTakenInGame.add(new GameVotingStats("cheng", 12));
		votesTakenInGame.add(new GameVotingStats("blackmamba_cheng", 24));
		obj.setEngineNamesVotesMap(votesTakenInGame);

		obj.setTimeout(3000);
		obj.setTypeOfGame(TypeOfMessageExtraction.ELO_VOTE_WITH_DISTRIBUTION);

		VotingProcessor votingProcessor = new VotingProcessor();
		votingProcessor.applyVotesToDatabase(obj);

	}


	@Test
	public void removeSuffixWorksFine() throws Exception {
		VotingProcessor votingProcessor = new VotingProcessor();
		String answer = votingProcessor.removeSuffix("cinnamon_glaurung_goddamn_timeout_3000");
		Assert.assertEquals("cinnamon_glaurung_goddamn", answer);
		answer = votingProcessor.removeSuffix("cinnamon_glaurung_goddamn_depth_3");
		Assert.assertEquals("cinnamon_glaurung_goddamn", answer);
	}
}
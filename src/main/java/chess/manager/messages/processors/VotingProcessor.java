package chess.manager.messages.processors;

import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.GameVotingStats;
import chess.database.dao.EngineNameDAO;
import chess.database.dao.VotingStatsDAO;
import chess.database.entities.EngineName;
import chess.database.entities.VotingStats;

import java.util.List;

public class VotingProcessor {
	EngineNameDAO engineNameDAO = new EngineNameDAO();
	VotingStatsDAO votingStatsDAO = new VotingStatsDAO();

	public void applyVotesToDatabase(ChessJSONObject answer) {
		String firstClusterName = "";
		String secondClusterName = "";

		List<String> enginesNames =  answer.getChessGameName();

		for(int i=0 ;i<enginesNames.size() ; i++){
			String name = enginesNames.get(i);
			if(i<enginesNames.size()/2){
				firstClusterName = joinNamesIntoClusterName(firstClusterName, name);
			} else {
				secondClusterName = joinNamesIntoClusterName(secondClusterName, name);
			}
		}

		String suffix = extractSufix(answer);
		firstClusterName += suffix;
		secondClusterName += suffix;

		EngineName engineNameEntityForFirstCluster = engineNameDAO.findByNameAndType(firstClusterName, answer.getTypeOfGame().getTypeOfGame()).get(0);
		EngineName engineNameEntityForSecondCluster = engineNameDAO.findByNameAndType(secondClusterName, answer.getTypeOfGame().getTypeOfGame()).get(0);

		for(GameVotingStats voteStatsFromGame: answer.getEngineNamesVotesMap()){

			if(isFirstEngine(voteStatsFromGame)){
				voteStatsFromGame.setEngineName(removeSuffix(voteStatsFromGame.getEngineName()));
				findElementAndUpdateOrCreateNew(answer, engineNameEntityForFirstCluster, voteStatsFromGame);
			} else {
				voteStatsFromGame.setEngineName(removeSuffix(voteStatsFromGame.getEngineName()));
				findElementAndUpdateOrCreateNew(answer, engineNameEntityForSecondCluster, voteStatsFromGame);
			}
		}
	}

	private void findElementAndUpdateOrCreateNew(ChessJSONObject answer, EngineName clusterEntityName, GameVotingStats voteStatsFromGame) {
		List<VotingStats> votingStatsList = votingStatsDAO.findByEngineName(
				voteStatsFromGame.getEngineName(),
				clusterEntityName.getEngineName(),
				answer.getTypeOfGame()
		);

		if(votingStatsList.isEmpty()){
			// Create
			VotingStats voteStat = new VotingStats();
			voteStat.setVotePackName(voteStatsFromGame.getEngineName());
			voteStat.setVoteAmount(voteStatsFromGame.getVoteCounter());
			voteStat.setEngineNameId(clusterEntityName);
			votingStatsDAO.save(voteStat);
		} else {
			// Edit
			VotingStats voteStat = votingStatsList.get(0);
			voteStat.setVoteAmount(voteStat.getVoteAmount() + voteStatsFromGame.getVoteCounter());
			votingStatsDAO.edit(voteStat);
		}
	}

	String removeSuffix(String engineName) {
		String nameToReturn = engineName;
		int position = nameToReturn.lastIndexOf('_');
		nameToReturn = nameToReturn.substring(0, position);
		return nameToReturn;
	}

	private boolean isFirstEngine(GameVotingStats voteStatsFromGame) {
		return voteStatsFromGame.getEngineName().contains("1");
	}

	private String joinNamesIntoClusterName(String clusterName, String name) {
		if(clusterName.isEmpty()){
			clusterName = name;
		} else {
			clusterName = clusterName.concat("_" + name);
		}
		return clusterName;
	}

	private String extractSufix(ChessJSONObject answer) {
		if(answer.getDepth() != null){
			return "_depth_"  + answer.getDepth();
		} else {
			return "_timeout_" + answer.getTimeout();
		}
	}
}

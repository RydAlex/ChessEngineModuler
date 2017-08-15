package chess.amqp.message;

public class GameVotingStats {

	String engineName;
	Integer voteCounter;

	public GameVotingStats(String engineName, int voteCounter){
		this.engineName = engineName;
		this.voteCounter = voteCounter;
	}

	public GameVotingStats(){ }

	public int getVoteCounter() {
		return voteCounter;
	}

	public void setVoteCounter(Integer voteCounter) {
		this.voteCounter = voteCounter;
	}

	public String getEngineName() {
		return engineName;
	}

	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}
}

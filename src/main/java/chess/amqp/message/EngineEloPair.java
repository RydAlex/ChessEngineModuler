package chess.amqp.message;

public class EngineEloPair {
    private String engineName;
    private Integer eloValue;

    public EngineEloPair(){}

    public EngineEloPair(String engineName, Integer eloValue){
        this.engineName = engineName;
        this.eloValue = eloValue;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public Integer getEloValue() {
        return eloValue;
    }

    public void setEloValue(Integer eloValue) {
        this.eloValue = eloValue;
    }
}

package chess.amqp.message;

import lombok.NoArgsConstructor;

/**
 * Created by aleksanderr on 09/07/17.
 */

public class EngineEloPair {
    String engineName;
    Integer eloValue;

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

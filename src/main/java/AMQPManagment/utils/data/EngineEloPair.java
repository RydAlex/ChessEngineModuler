package AMQPManagment.utils.data;

/**
 * Created by aleksanderr on 09/07/17.
 */

public class EngineEloPair {
    String engineName;
    Integer eloValue;
    String typeOfGame;


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

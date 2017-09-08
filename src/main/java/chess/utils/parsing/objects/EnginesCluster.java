package chess.utils.parsing.objects;

import chess.amqp.message.EngineEloPair;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

/**
 * Created by aleksanderr on 24/07/17.
 */
@Data
@NoArgsConstructor
public class EnginesCluster {
    private LinkedList<EngineEloPair> engineList = new LinkedList<>();
    private String playRule;

    EnginesCluster(EnginesCluster enginesCluster){
        this.engineList = enginesCluster.getEngineList();
        this.playRule = enginesCluster.getPlayRule();
    }

    public void addEngineToCluster(String name){
        EngineEloPair eloPair = new EngineEloPair();
        eloPair.setEngineName(name);
        eloPair.setEloValue(0);
        engineList.add(eloPair);
    }

    public void setPlayRule(Integer value){
        playRule = value > 200 ?
                "timeout_" + value :
                "depth_"   + value ;
    }

    public Integer getRuleValue() {
        return Integer.parseInt(playRule.split("_")[1]);
    }

    public String getRuleType() {
        return playRule.split("_")[0];
    }
}

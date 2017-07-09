package AMQPManagment.utils.data;

/**
 * Created by aleksanderr on 09/07/17.
 */
public enum GameResult {
    WIN(1),
    DRAW(0.5),
    LOSE(0);

    Double value;

    GameResult(double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }


    public Boolean isWin() {
        if(value.equals(1.0)){
            return true;
        }
        if(value.equals(0.0)){
            return false;
        }
        return null;
    }

}

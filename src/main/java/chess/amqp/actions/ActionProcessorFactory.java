package chess.amqp.actions;

public class ActionProcessorFactory {

    public static Action create(String queueName){
        if(queueName.contains("Save")){
            return new ChessGameSaverAction();
        }
        if(queueName.contains("Process")){
            return new ChessGameProcessingAction();
        }
        return null;
    }
}

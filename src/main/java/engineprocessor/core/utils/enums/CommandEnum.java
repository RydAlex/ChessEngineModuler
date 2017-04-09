package engineprocessor.core.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aleksanderr on 19/03/17.
 */
@AllArgsConstructor
@Getter
public enum CommandEnum {

    GO("go"),
    DEBUG("d"),
    QUIT("quit"),
    NEW_GAME("ucinewgame"),
    IS_ENGINE_READY("isready"),
    SET_POSITION("position fen"),
    GET_CHESS_INFORMATION("uci");


    private String command;

    public static CommandEnum fromString(String text) {
        if (text != null) {
            for (CommandEnum commandEnum : CommandEnum.values()) {
                if (text.equalsIgnoreCase(commandEnum.command)) {
                    return commandEnum;
                }
            }
        }
        return null;
    }
}

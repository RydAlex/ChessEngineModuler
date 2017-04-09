package engineprocessor.core.utils.enums;

import lombok.AllArgsConstructor;

/**
 * Created by aleksanderr on 10/07/16.
 */
@AllArgsConstructor
public enum ChessEnum {

    Queen       ("Q"),
    King        ("K"),
    Tower       ("R"),
    Bishop      ("B"),
    Knight      ("N"),
    Pawn        ("P");


    private String text;

    public String getBlackFigure() {
        return this.text.toLowerCase();
    }

    public String getWhiteFigure() {
        return this.text.toUpperCase();
    }

    public static ChessEnum fromString(String text) {
        if (text != null) {
            for (ChessEnum goOption : ChessEnum.values()) {
                if (text.equalsIgnoreCase(goOption.text)) {
                    return goOption;
                }
            }
        }
        return null;
    }
}

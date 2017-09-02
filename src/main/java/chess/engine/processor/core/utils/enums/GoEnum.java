package chess.engine.processor.core.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aleksanderr on 11/05/16.
 */
@Getter
@AllArgsConstructor
public enum GoEnum {

    searchDepth         ("depth"),
    searchInTime        ("movetime");

    private String text;
}

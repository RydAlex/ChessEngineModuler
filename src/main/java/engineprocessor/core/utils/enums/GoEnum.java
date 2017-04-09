package engineprocessor.core.utils.enums;

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

    public static GoEnum fromString(String text) {
        if (text != null) {
            for (GoEnum goOption : GoEnum.values()) {
                if (text.equalsIgnoreCase(goOption.text)) {
                    return goOption;
                }
            }
        }
        return null;
    }

}

package AMQPManagment.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aleksanderr on 11/06/17.
 */
@AllArgsConstructor
@Getter
public enum TypeOfMessageExtraction {

    RANDOM                  ("RANDOM"),
    ELO_SIMPLE              ("ELO_SIMPLE"),
    ELO                     ("ELO_VOTE"),
    POSITION_WEIGHT         ("POSITION_WEIGHT"),
    DEPTH_2_SIMPLE          ("DEPTH_WITH_2_MORE_SIMPLE"),
    DEPTH_2                 ("DEPTH_WITH_2_MORE");

    private String typeOfGame;
}

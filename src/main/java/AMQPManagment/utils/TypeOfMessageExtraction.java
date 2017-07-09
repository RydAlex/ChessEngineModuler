package AMQPManagment.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aleksanderr on 11/06/17.
 */
@AllArgsConstructor
@Getter
public enum TypeOfMessageExtraction {

    RANDOM                      ("RANDOM"),
    ELO_SIMPLE                  ("ELO_SIMPLE"),
    ELO_VOTE_WITH_ELO           ("ELO_VOTE_WITH_ELO"),
    ELO_VOTE_WITH_DISTRIBUTION  ("ELO_VOTE_WITH_DISTRIBUTION");

    private String typeOfGame;
}

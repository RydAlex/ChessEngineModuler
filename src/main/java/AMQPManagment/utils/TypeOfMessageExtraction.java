package AMQPManagment.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aleksanderr on 11/06/17.
 */
@AllArgsConstructor
@Getter
public enum TypeOfMessageExtraction {

    RANDOM       ("RANDOM"),
    ELO          ("ELO"),
    POSITION     ("POSITION");

    private String typeOfGame;
}

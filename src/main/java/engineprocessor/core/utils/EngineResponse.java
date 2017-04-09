package engineprocessor.core.utils;

import lombok.Getter;
import org.joda.time.DateTime;

import java.sql.Timestamp;


/**
 * Created by aleksanderr on 10/05/16.
 */
@Getter
public class EngineResponse {

    public EngineResponse(String commandName, String responseFromEngine ){

        this.commandName = commandName;
        this.responseFromEngine = responseFromEngine;
        this.timeStamp = new Timestamp(new DateTime().getMillis());
    }

    private String commandName;
    private String responseFromEngine;
    private Timestamp timeStamp;
}

package engineprocessor.interfaces;

import engineprocessor.core.utils.enums.CommandEnum;

import java.util.List;

/**
 * Created by aleksanderr on 18/03/17.
 */
public interface EngineRunner {
    List<String> getEngineNames();
    String RunEngineWithCommand(String engineName, CommandEnum command, String... attributes);
    String RunEngineWithGoTimeoutCommand(String engineName, String fenPosition, int timeout);
    String RunEngineWithGoDepthCommand(String engineName, String fenPosition, int depth);

}

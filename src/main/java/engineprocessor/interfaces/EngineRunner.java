package engineprocessor.interfaces;

import engineprocessor.core.utils.enums.CommandEnum;

import java.util.List;

/**
 * Created by aleksanderr on 18/03/17.
 */
public interface EngineRunner {
    public List<String> getEngineNames();
    public String RunEngineWithCommand(String engineName, CommandEnum command, String... attributes);
    public String RunEngineWithGoTimeoutCommand(String engineName, String fenPosition, int timeout);
    public String RunEngineWithGoDepthCommand(String engineName, String fenPosition, int depth);

}

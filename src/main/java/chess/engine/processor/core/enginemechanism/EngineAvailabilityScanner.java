package chess.engine.processor.core.enginemechanism;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by aleksanderr on 18/03/17.
 */
public class EngineAvailabilityScanner {
    private HashMap<String, String> enginePathsMap = new HashMap<>();

    private static EngineAvailabilityScanner engineAvaliabilityInstance = new EngineAvailabilityScanner();

    public static EngineAvailabilityScanner getInstance() {
        return engineAvaliabilityInstance;
    }

    private EngineAvailabilityScanner() {
        try {
            String systemSufix = OsCheck.isMac() ? "mac" : "linux";
            String path = EngineAvailabilityScanner.class.getClassLoader().getResource("engines/").getPath();
            try (Stream<Path> paths = Files.walk(Paths.get(path))) {
                paths.filter(filePath -> Files.isDirectory(filePath))
                    .filter(filePath -> filePath.toString().contains(systemSufix))
                    .forEach(filePath ->
                            enginePathsMap.put(
                                filePath.getFileName().toString(),
                                filePath.toString()));
            }
            enginePathsMap.remove("engines");
            enginePathsMap.remove("mac");
            enginePathsMap.remove("linux");
            for (Map.Entry<String, String> enginePathMap: enginePathsMap.entrySet()){
                String[] linkElements = enginePathMap.getValue().split("/");
                enginePathMap.setValue(enginePathMap.getValue()+"/"+linkElements[linkElements.length-1]);
                FilePermissionHelper.setFileFullPermisionForMeAndMyGroup(enginePathMap.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> returnMapOfEnginePaths(){
        return enginePathsMap;
    }

    public List<String> returnListOfNames(){
        ArrayList<String> list = new ArrayList<>();

        for(Map.Entry<String, String> entry: enginePathsMap.entrySet()) {
            list.add(entry.getKey());
        }

        return list;
    }


}

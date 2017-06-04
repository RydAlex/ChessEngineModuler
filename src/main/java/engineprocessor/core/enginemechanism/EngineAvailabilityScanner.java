package engineprocessor.core.enginemechanism;

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
    HashMap<String, String> enginePathsMap = new HashMap<>();

    private static EngineAvailabilityScanner engineAvaliabilityInstance = new EngineAvailabilityScanner();

    public static EngineAvailabilityScanner getInstance() {
        return engineAvaliabilityInstance;
    }

    private EngineAvailabilityScanner() {
        try {
            String path = EngineAvailabilityScanner.class.getProtectionDomain().getCodeSource().getLocation().getFile()+ "/engineprocessor/core/engines/";
            try (Stream<Path> paths = Files.walk(Paths.get(path))) {
                paths.filter(filePath -> Files.isDirectory(filePath))
                        .forEach(filePath -> { enginePathsMap.put(filePath.getFileName().toString(), filePath.toString()); });
            }
            enginePathsMap.remove("engines");
            for (Map.Entry<String, String> link: enginePathsMap.entrySet()){
                String[] linkElements = link.getValue().split("/");
                link.setValue(link.getValue()+"/"+linkElements[linkElements.length-1]);
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

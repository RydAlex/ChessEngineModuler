package engineprocessor.core.enginemechanism;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;
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
            String systemSufix = OsCheck.isMac() ? "mac" : "linux";
            String path = EngineAvailabilityScanner.class.getProtectionDomain().getCodeSource().getLocation().getFile()+ "/engineprocessor/core/engines/";
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
            for (Map.Entry<String, String> link: enginePathsMap.entrySet()){
                String[] linkElements = link.getValue().split("/");
                link.setValue(link.getValue()+"/"+linkElements[linkElements.length-1]);
                if(systemSufix.equals("linux")){
                    setRightPermision(link.getValue());
                }
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

    private void setRightPermision(String name){
        File file = new File(name);

        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);

        try {
            Files.setPosixFilePermissions(file.toPath(), perms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

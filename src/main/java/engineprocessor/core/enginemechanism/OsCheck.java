package engineprocessor.core.enginemechanism;

import org.apache.commons.lang3.SystemUtils;

/**
 * Created by aleksanderr on 02/07/17.
 */
public class OsCheck {

    public static boolean isMac(){
        return SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX;
    }
}

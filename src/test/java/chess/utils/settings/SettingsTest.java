package chess.utils.settings;

import net.jodah.lyra.internal.util.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsTest {

    @Test
    public void isSettingsDataLoadToRedis() {
        Assert.notNull(Settings.getAMQPString());
        Assert.notNull(Settings.getPostgresDBPassword());
        Assert.notNull(Settings.getPostgresDBUrl());
        Assert.notNull(Settings.getPostgresDBUsername());
    }
}
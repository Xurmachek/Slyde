package dev.jfronny.slyde;

import dev.jfronny.libjf.config.api.v2.Entry;
import dev.jfronny.libjf.config.api.v2.JfConfig;

@JfConfig
public class Cfg {
    @Entry public static boolean sodiumCompat = true;
    @Entry public static boolean forceSodiumCompat = false;
    @Entry public static boolean holdKeyToActivate = false;
    @Entry public static boolean mouseLock = true;

    static {
        JFC_Cfg.ensureInitialized();
    }
}

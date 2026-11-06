package dev.jfronny.slyde;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

public class Slyde {
    public static boolean isActive() {
        boolean down = InputConstants.isKeyDown(InputConstants.KEY_LSHIFT)
                || InputConstants.isKeyDown(InputConstants.KEY_RSHIFT)
                || InputConstants.isKeyDown(InputConstants.KEY_LCONTROL)
                || InputConstants.isKeyDown(InputConstants.KEY_RCONTROL);
        boolean result = down == Cfg.holdKeyToActivate;
        setLocked(isScrolling && Cfg.mouseLock && result);
        return result;
    }

    private static boolean isScrolling = false;
    public static void setScrolling(boolean value) {
        if (isScrolling == value) return;
        isScrolling = value;
        setLocked(isScrolling && Cfg.mouseLock && isActive());
    }

    private static boolean isLocked = false;
    private static void setLocked(boolean value) {
        if (isLocked == value) return;
        isLocked = value;
        var window = Minecraft.getInstance().getWindow();
        if (isLocked) {
            InputConstants.grabMouse(window, window.getScreenWidth() / 2.0, window.getScreenHeight() / 2.0);
        } else {
            InputConstants.releaseMouse(window, window.getScreenWidth() / 2.0, window.getScreenHeight() / 2.0);
        }
    }
}

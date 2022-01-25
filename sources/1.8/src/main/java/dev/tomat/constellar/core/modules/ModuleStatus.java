package dev.tomat.constellar.core.modules;

import dev.tomat.common.utils.ColorUtils;

// note: what the fuck is force disabled for
// note: shut up intellij i can say fuck all i want
public enum ModuleStatus {
    ENABLED(ColorUtils.colorToInt(144, 238, 144, 255)),
    DISABLED(ColorUtils.colorToInt(255, 114, 118, 255)),
    FORCE_DISABLED(ColorUtils.colorToInt(255, 0, 0, 255));

    private final int Color;

    ModuleStatus(int color) {
        Color = color;
    }

    public int getColor() {
        return Color;
    }
}

package dev.tomat.common.utils;

public class MathUtils {
    public static float lerp(float start, float end, float step) {
        return (start + (end - start) * step);
    }
}

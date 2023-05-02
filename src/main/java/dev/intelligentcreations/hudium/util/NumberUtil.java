package dev.intelligentcreations.hudium.util;

public final class NumberUtil {
    public static int requireNonNegative(int input) {
        if (input < 0)
            throw new IllegalArgumentException("The input is negative!");
        return input;
    }
}

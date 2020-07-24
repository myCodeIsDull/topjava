package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

public class Util {
    private Util() {
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static String substring(String url, int beginIndex, int endIndex) {
        return url.substring(beginIndex,endIndex);
    }

    public static int length(String url) {
        return url.length();
    }
}
package com.lyhoangvinh.simple.utils.rx.rxlifecycle;

public final class Preconditions {
    public static <T> T checkNotNull(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }

    private Preconditions() {
        throw new AssertionError("No instances.");
    }
}

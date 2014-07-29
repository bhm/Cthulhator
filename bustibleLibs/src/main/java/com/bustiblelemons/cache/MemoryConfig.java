package com.bustiblelemons.cache;

/**
 * Created by bhm on 29.07.14.
 */
public class MemoryConfig {
    public static final int DEFAULT_DIVIDER = 1024 * 8;
    private             int divider         = DEFAULT_DIVIDER;

    public int getDivider() {
        return divider;
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    public int getSize() {
        return (int) Runtime.getRuntime().maxMemory() / getDivider();
    }
}

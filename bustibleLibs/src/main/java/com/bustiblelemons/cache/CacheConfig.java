package com.bustiblelemons.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 29.07.14.
 */
public class CacheConfig {
    private String path;
    private String  cachePath;
    private boolean isExternal;
    private MemoryConfig memoryConfig;
    private List<CacheFileDescriptor> filePaths = new ArrayList<CacheFileDescriptor>();

    private List<CacheFileDescriptor> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<CacheFileDescriptor> filePaths) {
        this.filePaths = filePaths;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MemoryConfig getMemoryConfig() {
        return memoryConfig;
    }

    public void setMemoryConfig(MemoryConfig memoryConfig) {
        this.memoryConfig = memoryConfig;
    }

    public boolean isExternal() {
        return isExternal;
    }

    public void setExternal(boolean isExternal) {
        this.isExternal = isExternal;
    }

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }
}

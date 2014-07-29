package com.bustiblelemons.cache;

import android.support.v4.util.LruCache;

import com.bustiblelemons.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bhm on 29.07.14.
 */
public abstract class AbsCache {
    public static final Logger log = new Logger(AbsCache.class);

    private final int                                   memoryDivider = 1024 * 8;
    private       int                                   cacheSize     = (int) (Runtime.getRuntime().maxMemory() / memoryDivider);
    private       LruCache<CacheFileDescriptor, byte[]> sCache        = new LruCache<CacheFileDescriptor, byte[]>(
            cacheSize);
    private CacheConfig confg;

    public int init(CacheConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("Cache config wrong");
        }
        int r = 0;
        MemoryConfig memoryConfig = config.getMemoryConfig();
        if (memoryConfig != null) {
            sCache = new LruCache<CacheFileDescriptor, byte[]>(memoryConfig.getSize());
        }
        return r;
    }

    public void saveConfig(CacheConfig config) throws IOException {
        ObjectMapper configMapper = new ObjectMapper();
        configMapper.writeValue(new FileOutputStream(config.getPath()), config);
    }

    public int save() {
        return 0;
    }
}
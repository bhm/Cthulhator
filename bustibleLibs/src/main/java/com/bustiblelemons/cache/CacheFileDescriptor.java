package com.bustiblelemons.cache;

/**
 * Created by bhm on 29.07.14.
 */
public class CacheFileDescriptor {
    private String name;
    private long size;
    private String path;
    private Class<?> mapperClass;

    public Class<?> getMapperClass() {
        return mapperClass;
    }

    public void setMapperClass(Class<?> mapperClass) {
        this.mapperClass = mapperClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

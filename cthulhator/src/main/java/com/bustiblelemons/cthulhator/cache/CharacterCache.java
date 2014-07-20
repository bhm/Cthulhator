package com.bustiblelemons.cthulhator.cache;

import android.support.v4.util.LruCache;

/**
 * Created by bhm on 20.07.14.
 */
public class CharacterCache {
    int size = 2048;
    private LruCache<String, Object> characters = new LruCache<String, Object>(size);
}

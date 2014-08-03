package com.bustiblelemons;

import android.app.Application;

import com.bustiblelemons.bustiblelibs.R;
import com.bustiblelemons.storage.Storage;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by bhm on 18.07.14.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration.Builder configBuilder = new ImageLoaderConfiguration.Builder(this);
        configBuilder.denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiscCache(Storage.getStorage(this)))
                .memoryCache(getLruCache())
                .defaultDisplayImageOptions(getDisplayOptions())
                .build();
        ImageLoader.getInstance().init(configBuilder.build());
    }

    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions.Builder b = new DisplayImageOptions.Builder();
        b.showImageOnFail(R.drawable.lemons).cacheInMemory(true).cacheOnDisk(true);
        return b.build();
    }

    private MemoryCache getLruCache() {
        return new LruMemoryCache(2 * 1024 * 1024);
    }
}

package com.bustiblelemons.utils;

import android.os.Bundle;

/**
 * Created by bhm on 02.08.14.
 */
public class BundleTools {
    public static boolean contains(Bundle bundle, String key) {
        return bundle != null && bundle.containsKey(key);
    }
}

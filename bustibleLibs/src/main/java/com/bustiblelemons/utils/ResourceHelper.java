package com.bustiblelemons.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by bhm on 17.09.14.
 */
public class ResourceHelper {

    private final Context mContext;
    private       String  mPackageName;

    public ResourceHelper(Context context) {
        mContext = context;
        mPackageName = mContext.getPackageName();
    }

    public static ResourceHelper from(Context context) {
        return new ResourceHelper(context);
    }

    public int getIdentifierForString(String resName) {
        int resId = 0;
        try {
            return mContext.getResources().getIdentifier(resName, "string", mPackageName);
        } catch (Resources.NotFoundException e) {

        }
        return resId;
    }
}

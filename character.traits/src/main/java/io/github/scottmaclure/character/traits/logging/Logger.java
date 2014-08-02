package io.github.scottmaclure.character.traits.logging;

import android.util.Log;

import java.util.Locale;

/**
 * Created by bhm on 18.07.14.
 */
public class Logger {
    private static String mTag;

    public Logger(Class<?> cls) {
        this.mTag = cls.getSimpleName();
    }

    public static int d(Throwable throwable, String format, Object... args) {
        return Log.d(mTag, String.format(Locale.ENGLISH, format, args), throwable);
    }

    public static int e(Throwable throwable, String format, Object... args) {
        return Log.e(mTag, String.format(Locale.ENGLISH, format), throwable);
    }

    public static int i(Throwable throwable, String format, Object... args) {
        return Log.i(mTag, String.format(Locale.ENGLISH, format, args), throwable);
    }

    public static int v(Throwable throwable, String format, Object... args) {
        return Log.v(mTag, String.format(Locale.ENGLISH, format, args), throwable);
    }

    public static int wtf(Throwable throwable, String format, Object... args) {
        return Log.wtf(mTag, String.format(Locale.ENGLISH, format, args), throwable);
    }

    public static int w(Throwable throwable, String format, Object... args) {
        return Log.w(mTag, String.format(Locale.ENGLISH, format, args), throwable);
    }


    public static int d(String format, Object... args) {
        return Log.d(mTag, String.format(Locale.ENGLISH, format, args));
    }

    public static int e(String format, Object... args) {
        return Log.e(mTag, String.format(Locale.ENGLISH, format, args));
    }

    public static int i(String format, Object... args) {
        return Log.i(mTag, String.format(Locale.ENGLISH, format, args));
    }

    public static int v(String format, Object... args) {
        return Log.v(mTag, String.format(Locale.ENGLISH, format, args));
    }

    public static int wtf(String format, Object... args) {
        return Log.wtf(mTag, String.format(Locale.ENGLISH, format, args));
    }

    public static int w(String format, Object... args) {
        return Log.w(mTag, String.format(Locale.ENGLISH, format, args));
    }


}

package com.bustiblelemons.typeface;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.SparseArray;

import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.utils.CaseFormat;


public class TypefaceCache {
    private static final Logger log = new Logger(TypefaceCache.class);

    private static SparseArray<Typeface> typefaces = new SparseArray<Typeface>();

    public static int init(Context context) {
        int r = 0;
        for (TypeFaceFamily attribute : TypeFaceFamily.values()) {
            if (getTypefaceByFamilyAttribute(context, attribute) != null) {
                r++;
            }
        }
        return r;
    }

    public static Typeface getTypefaceByFamilyAttribute(Context context, TypeFaceFamily attribute) {
        int id = attribute.getId();
        if (typefaces.get(id) == null) {
            loadTypeFaceByFamilyAttribute(context, attribute);
        }
        return typefaces.get(id);
    }

    public static void loadTypeFaceByFamilyAttribute(Context context, TypeFaceFamily attribute) {
        String cameled = attribute.name();
        String underScored = CaseFormat.CamelToUnderScore.convert(attribute.name());
        int filenameId = getStringNameId(context, cameled, underScored);
        if (filenameId > 0) {
            try {
                String fileName = context.getResources().getString(filenameId);
                Typeface typeface = getTypefaceByFilename(context, fileName);
                typefaces.put(attribute.getId(), typeface);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getStringNameId(Context context, String... names) {
        Resources res = context.getResources();
        String pkgName = context.getPackageName();
        for (String name : names) {
            int stringFilenameId = res.getIdentifier(name, "string", pkgName);
            if (stringFilenameId > 0) {
                return stringFilenameId;
            }
        }
        return 0;
    }

    public static Typeface getTypefaceByFilename(Context context, String filename) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), filename);
        return face;
    }
}
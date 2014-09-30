package com.bustiblelemons.typeface;

public enum TypeFaceFamily {
    ExtraLight(2),
    ExtraLightItalic(3),
    Light(5),
    LightItalic(7),
    Regular(13),
    Italic(17),
    Semibold(19),
    SemiboldItalic(23),
    Bold(29),
    BoldItalic(31),
    Black(37),
    BlackItalic(41);

    private final int id;

    TypeFaceFamily(int i) {
        this.id = i;
    }

    static TypeFaceFamily fromId(int id) {
        for (TypeFaceFamily f : values()) {
            if (f.id == id) { return f; }
        }
        throw new IllegalArgumentException();
    }

    public int getId() {
        return id;
    }
}
package com.bustiblelemons.utils;

public enum CaseFormat {
    CamelToUnderScore {
        @Override
        public String convert(String arg) {
            String regex = "([a-z])([A-Z])";
            String replacement = "$1_$2";
            return arg.replaceAll(regex, replacement);
        }
    };

    public abstract String convert(String arg);
}

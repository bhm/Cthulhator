package com.bustiblelemons;

/**
 * Created by bhm on 24.07.14.
 */
public class Test {

    private static int maxValue  = 100;
    private static int minValue  = 0;
    private static int jumpValue = 10;

    public static void main(String[] args) {
        System.out.println("Min" + minValue + "\nMax" + maxValue + "\nJump" + jumpValue);
        setMinValue(minValue);
        System.out.println("Min" + minValue + "\nMax" + maxValue + "\nJump" + jumpValue);
        setMaxValue(maxValue);
        System.out.println("Min" + minValue + "\nMax" + maxValue + "\nJump" + jumpValue);
        setJumpValue(jumpValue);
        System.out.println("Min" + minValue + "\nMax" + maxValue + "\nJump" + jumpValue);

    }

    public static void setMinValue(int nMinValue) {
        int newMax = calculateNewMax(jumpValue, nMinValue, maxValue);
        setMaxValue(newMax);
        minValue = nMinValue;
    }

    public static void setJumpValue(int nJumpValue) {
        int newMax = calculateNewMax(nJumpValue, minValue, maxValue);
        setMaxValue(newMax);
        jumpValue = nJumpValue;
    }


    public static void setMaxValue(int nMaxValue) {
        maxValue = calculateNewMax(jumpValue, minValue, nMaxValue);
    }

    private static int calculateNewMax(int jumpValue, int minValue, int maxValue) {
        int difference = (maxValue - minValue);
        if (difference <= 0) {
            throw new IllegalArgumentException();
        }
        int newMax = difference / jumpValue;
        if (newMax <= 0) {
            throw new IllegalArgumentException();
        }
        return newMax;
    }
}

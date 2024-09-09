package me.danny125.byteutilitymod.util;

public class MathUtil{
    public static boolean isBetween(double x1,double y1, double x2,double y2, double x3,double y3){
        return x1 >= x2 && x1 <= x3 && y1 >= y2 && y1 <= y3;
    }
}

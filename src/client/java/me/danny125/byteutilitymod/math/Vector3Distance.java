package me.danny125.byteutilitymod.math;

import static java.lang.Math.sqrt;

public class Vector3Distance {
    public static double getDistance(Vector3 a, Vector3 b) {
        return sqrt((a.x - b.x) * (a.x - b.x) +
                (a.y - b.y) * (a.y - b.y) +
                (a.z - b.z) * (a.z - b.z));
    }
}

package me.danny125.byteutilitymod.util;

import java.util.Random;

public class RandomUtil {
    public static int randomNumber(int min, int max) {
        Random random = new Random();

        int pick = random.nextInt(max) + min;

        return pick;
    }
}

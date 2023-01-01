package com.example.demo.utils;


import java.util.Random;

public class GenerateUtils {
    public static String getRandomCode4Digits() {
        Random random = new Random();
//        [0,9999], format int -> string
        return String.format("%4d", random.nextInt(9000) + 1000);
    }
}

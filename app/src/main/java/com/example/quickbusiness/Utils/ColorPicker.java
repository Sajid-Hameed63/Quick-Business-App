package com.example.quickbusiness.Utils;

public class ColorPicker {
    static String[] colors = {
//            "#3EB9DF",
//            "#3685BC",
//            "#D36280",
//            "#E44F55"
                "#000000",
                "#18206F"

    };
    static int currentColorIndex = 0;
    public static String getColor(){
        currentColorIndex = (currentColorIndex + 1) % colors.length;
        return colors[currentColorIndex];
    }
}

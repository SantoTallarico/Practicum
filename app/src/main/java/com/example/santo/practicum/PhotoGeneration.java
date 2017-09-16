package com.example.santo.practicum;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import com.example.santo.practicum.GameObjects.Character;
import com.example.santo.practicum.GameObjects.Equipment;
import com.example.santo.practicum.GameObjects.GameObject;

public class PhotoGeneration {
    public static GameObject Generate(Bitmap bitmap) {
        int[] pixels = new int[32 * 32];
        int red = 0;
        int blue = 0;
        int green = 0;

        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 32, 32, false);

        scaled.getPixels(pixels, 0, 32, 0, 0, 32, 32);

        for (int i = 0; i < pixels.length; i++) {
            red += Color.red(pixels[i]);
            green += Color.green(pixels[i]);
            blue += Color.blue(pixels[i]);
        }

        GameObject object;

        if (red % 2 == 0) {

            object = new Character(new Rect(-300, -300, 300, 300), "drawable/painting", 0, red / (32 * 32), green / (32 * 32), blue / (32 * 32));
        }
        else {

            object = new Equipment(new Rect(-300, -300, 300, 300), "drawable/painting", 0, red / (32 * 32), green / (32 * 32), blue / (32 * 32));
        }

        return object;
    }
}

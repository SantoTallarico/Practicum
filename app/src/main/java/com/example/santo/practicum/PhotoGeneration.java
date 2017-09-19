package com.example.santo.practicum;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PhotoGeneration {
    public static int[] Generate(Bitmap bitmap) {
        int[] pixels = new int[32 * 32];
        //bands of colour for palette generation
        int[] buckets = new int[8 * 8 * 8];

        int red = 0;
        int blue = 0;
        int green = 0;

        //using a scaled bitmap to not have to deal with actual photo dimensions, reduce number of
        //computations whenever photo is larger than 32x32 (which would be most cases)
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 32, 32, false);

        scaled.getPixels(pixels, 0, 32, 0, 0, 32, 32);

        for (int i = 0; i < pixels.length; i++) {
            red += Color.red(pixels[i]);
            green += Color.green(pixels[i]);
            blue += Color.blue(pixels[i]);

            buckets[((Color.red(pixels[i]) / 32) << 6) | ((Color.green(pixels[i]) / 32) << 3) | (Color.blue(pixels[i]) / 32)]++;
        }

        //amount of three most used colours
        int max1 = 0, max2 = 0, max3 = 0;
        //index of three most used colours (corresponds with actual colour)
        int max1i = 0, max2i = 0, max3i = 0;

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] > max1) {
                max3 = max2;
                max2 = max1;
                max1 = buckets[i];

                max3i = max2i;
                max2i = max1i;
                max1i = i;
            }
            else if (buckets[i] > max2) {
                max3 = max2;
                max2 = buckets[i];

                max3i = max2i;
                max2i = i;
            }
            else if (buckets[i] > max3) {
                max3 = buckets[i];

                max3i = i;
            }
        }

        int p1 = Color.rgb(((max1i >> 6)  * 32 + 16) & 0xFF, ((max1i >> 3) * 32 + 16) & 0xFF, (max1i * 32 + 16) & 0xFF);
        int p2 = Color.rgb(((max2i >> 6)  * 32 + 16) & 0xFF, ((max2i >> 3) * 32 + 16) & 0xFF, (max2i * 32 + 16) & 0xFF);
        int p3 = Color.rgb(((max3i >> 6)  * 32 + 16) & 0xFF, ((max3i >> 3) * 32 + 16) & 0xFF, (max3i * 32 + 16) & 0xFF);

        return new int[] {red / (32 * 32), green / (32 * 32), blue / (32 * 32), p1, p2, p3};
    }
}

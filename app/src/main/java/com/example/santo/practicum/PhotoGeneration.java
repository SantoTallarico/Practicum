package com.example.santo.practicum;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PhotoGeneration {
    public static int[] Generate(Bitmap bitmap) {
        int[] pixels = new int[64 * 64];
        //bands of colour for palette generation
        int[] bands = new int[8 * 8 * 8];

        //for storing total amounts of colour in image
        int red = 0;
        int blue = 0;
        int green = 0;

        //using a scaled bitmap to not have to deal with actual photo dimensions, reduce number of
        //computations whenever photo is larger than 64x64 (which would be most cases)
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 64, 64, false);

        scaled.getPixels(pixels, 0, 64, 0, 0, 64, 64);

        for (int i = 0; i < pixels.length; i++) {
            red += Color.red(pixels[i]);
            green += Color.green(pixels[i]);
            blue += Color.blue(pixels[i]);

            bands[((Color.red(pixels[i]) / 32) << 6) | ((Color.green(pixels[i]) / 32) << 3) | (Color.blue(pixels[i]) / 32)]++;
        }

        //amount of three most used colours
        int max1 = 0, max2 = 0, max3 = 0;
        //index of three most used colours (corresponds with actual colour)
        int max1i = 0, max2i = 0, max3i = 0;

        for (int i = 0; i < bands.length; i++) {
            if (bands[i] > max1) {
                max3 = max2;
                max2 = max1;
                max1 = bands[i];

                max3i = max2i;
                max2i = max1i;
                max1i = i;
            }
            else if (bands[i] > max2) {
                max3 = max2;
                max2 = bands[i];

                max3i = max2i;
                max2i = i;
            }
            else if (bands[i] > max3) {
                max3 = bands[i];

                max3i = i;
            }
        }

        int p1 = Color.rgb(((max1i >> 6)  * 32 + 16) & 0xFF, ((max1i >> 3) * 32 + 16) & 0xFF, (max1i * 32 + 16) & 0xFF);
        int p2 = 0;
        int p3 = 0;

        if (max2 == 0) {
            int tempRed = (((max1i >> 6)  * 32 + 16) & 0xFF) - 32;
            int tempGreen = (((max1i >> 3) * 32 + 16) & 0xFF) - 32;
            int tempBlue = ((max1i * 32 + 16) & 0xFF) - 32;
            int tempRed2 = (((max1i >> 6)  * 32 + 16) & 0xFF) + 32;
            int tempGreen2 = (((max1i >> 3) * 32 + 16) & 0xFF) + 32;
            int tempBlue2 = ((max1i * 32 + 16) & 0xFF) + 32;
            p2 = Color.rgb(tempRed < 0 ? 0 : tempRed, tempGreen < 0 ? 0 : tempGreen, tempBlue < 0 ? 0 : tempBlue);
            p3 = Color.rgb(tempRed2 > 255 ? 255 : tempRed2, tempGreen2 > 255 ? 255 : tempGreen2, tempBlue2 > 255 ? 255 : tempBlue2);
        }
        else if (max3 == 0) {
            int tempRed = (((max1i >> 6)  * 32 + 16) & 0xFF) + 32;
            int tempGreen = (((max1i >> 3) * 32 + 16) & 0xFF) + 32;
            int tempBlue = ((max1i * 32 + 16) & 0xFF) + 32;
            p3 = Color.rgb(tempRed > 255 ? 255 : tempRed, tempGreen > 255 ? 255 : tempGreen, tempBlue > 255 ? 255 : tempBlue);
        }
        else {
            p2 = Color.rgb(((max2i >> 6)  * 32 + 16) & 0xFF, ((max2i >> 3) * 32 + 16) & 0xFF, (max2i * 32 + 16) & 0xFF);
            p3 = Color.rgb(((max3i >> 6)  * 32 + 16) & 0xFF, ((max3i >> 3) * 32 + 16) & 0xFF, (max3i * 32 + 16) & 0xFF);
        }


        return new int[] {red / (64 * 64), green / (64 * 64), blue / (64 * 64), p1, p2, p3};
    }
}

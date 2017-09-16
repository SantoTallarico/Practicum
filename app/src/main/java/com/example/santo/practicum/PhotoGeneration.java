package santotallarico.photobattler;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PhotoGeneration {
    public static Fighter Generate(Bitmap bitmap) {
        int[] pixels = new int[32 * 32];
        int red = 0;
        int blue = 0;
        int green = 0;

        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 32, 32, false);

        scaled.getPixels(pixels, 0, 32, 0, 0, 32, 32);

        for (int i = 0; i < pixels.length; i++) {
            red += Color.red(pixels[i]);
            blue += Color.blue(pixels[i]);
            green += Color.green(pixels[i]);
        }


        Fighter fighter = new Fighter(new Character(scaled, red / (32 * 32), blue / (32 * 32), green / (32 * 32)));
        fighter.abilities.add(new Ability(scaled, red / (32 * 32), blue / (32 * 32), green / (32 * 32)));
        fighter.equipment.add(new Equipment(scaled, red / (32 * 32), blue / (32 * 32), green / (32 * 32)));

        /*Asset asset;

        if (red > blue && red > green) {
            asset = new Character(scaled, red / (32 * 32), blue / (32 * 32), green / (32 * 32));
        } else if (blue > green) {
            asset = new Ability(scaled, red / (32 * 32), blue / (32 * 32), green / (32 * 32));
        } else {
            asset = new Equipment(scaled, red / (32 * 32), blue / (32 * 32), green / (32 * 32));
        }*/

        return fighter;
    }
}

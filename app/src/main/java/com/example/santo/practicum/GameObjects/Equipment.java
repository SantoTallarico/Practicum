package com.example.santo.practicum.GameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.santo.practicum.Enums.EquipmentType;

import java.io.Serializable;

/**
 * Created by Santo on 7/20/2017.
 */

public class Equipment extends GameObject implements Serializable {
    String name;
    public EquipmentType type;
    public int modHitPoints = 0;
    public int modAttack = 0;
    public int modDefence = 0;
    public int modMagicDefence = 0;
    public int modSpeed = 0;

    public boolean inUse = false;

    public transient Bitmap tileIcon;

    public int palette1, palette2, palette3;

    public Equipment(Rect d, Bitmap generatedSprite, int layer, int red, int green, int blue, int p1, int p2, int p3) {
        super(d, generatedSprite, layer);
        tileIcon = generatedSprite;
        palette1 = p1;
        palette2 = p2;
        palette3 = p3;

        boolean isWeapon = true;
        switch (red % 4) {
            case 1:
                isWeapon = true;
                break;
            case 3:
                isWeapon = false;
                break;
            default:
                isWeapon = true;
                break;
        }

        switch (green % 4) {
            case 0:
                if (isWeapon) {
                    type = EquipmentType.sword;
                }
                else {
                    type = EquipmentType.armor;
                }
                break;
            case 1:
                if (isWeapon) {
                    type = EquipmentType.knife;
                }
                else {
                    type = EquipmentType.armor;
                }
                break;
            case 2:
                if (isWeapon) {
                    type = EquipmentType.staff;
                }
                else {
                    type = EquipmentType.robe;
                }
                break;
            case 3:
                if (isWeapon) {
                    type = EquipmentType.mace;
                }
                else {
                    type = EquipmentType.robe;
                }
                break;
        }

        switch (type) {
            case sword:
                modAttack = 5 + (blue % 5 + 1);
                modDefence = 5 - (blue % 5 + 1);
                break;
            case knife:
                modAttack = 5 + (blue % 5 + 1);
                modSpeed = 5 - (blue % 5 + 1);
                break;
            case staff:
                modAttack = 5 + (blue % 5 + 1);
                modSpeed = 5 - (blue % 5 + 1);
                break;
            case mace:
                modAttack = 5 + (blue % 5 + 1);
                modMagicDefence = 5 - (blue % 5 + 1);
                break;
            case armor:
                modDefence = 4 + (blue % 5 + 1);
                modHitPoints = 5 - (blue % 5 + 1);
                break;
            case robe:
                modDefence = 2 + (blue % 5 + 1);
                modMagicDefence = 2 + 5 - (blue % 5 + 1);
                break;
        }
    }

    public void Init(Context context) {
        String filePath = "";
        switch (type) {
            case sword:
                filePath = "drawable/swordpalette";
                break;
            case knife:
                filePath = "drawable/knifepalette";
                break;
            case staff:
                filePath = "drawable/staffpalette";
                break;
            case mace:
                filePath = "drawable/macepalette";
                break;
            case armor:
                filePath = "drawable/armorpalette";
                break;
            case robe:
                filePath = "drawable/robepalette";
                break;
        }
        generatedSprites = new Bitmap[1];
        generatedSprites[0] = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier(filePath, null, context.getPackageName()));
        generatedSprites[0] = ApplyPalette();
        tileIcon = generatedSprites[0];
    }

    public Bitmap ApplyPalette() {
        Bitmap temp = generatedSprites[0].copy(generatedSprites[0].getConfig(), true);

        int w = temp.getWidth();
        int h = temp.getHeight();
        int[] pixels = new int[w * h];
        temp.getPixels(pixels, 0, w, 0, 0, w, h);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int colour = pixels[i + j * w];

                switch (colour) {
                    case 0xff7f7f7f:
                        pixels[i + j * w] = palette1;
                        break;
                    case 0xff3f3f3f:
                        pixels[i + j * w] = palette2;
                        break;
                    case 0xffbfbfbf:
                        pixels[i + j * w] = palette3;
                        break;
                }
            }
        }

        temp.setPixels(pixels, 0, w, 0, 0, w, h);

        return temp;
    }
}

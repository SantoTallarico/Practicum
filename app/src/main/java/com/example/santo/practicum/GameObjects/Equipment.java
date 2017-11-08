package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
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

    public Equipment(Rect d, Bitmap generatedSprite, int layer, int red, int green, int blue) {
        super(d, generatedSprite, layer);
        tileIcon = generatedSprite;

        boolean isWeapon = true;
        switch (red % 2) {
            case 0:
                isWeapon = true;
                break;
            case 1:
                isWeapon = false;
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
                modAttack = 5 + blue % 5;
                modDefence = 5 - blue % 5;
                break;
            case knife:
                modAttack = 5 + blue % 5;
                modSpeed = 5 - blue % 5;
                break;
            case staff:
                modAttack = 5 + blue % 5;
                modSpeed = 5 - blue % 5;
                break;
            case mace:
                modAttack = 5 + blue % 5;
                modMagicDefence = 5 - blue % 5;
                break;
            case armor:
                modDefence = 4 + blue % 5;
                modHitPoints = 5 - blue % 5;
                break;
            case robe:
                modDefence = 2 + blue % 5;
                modMagicDefence = 2 + 5 - blue % 5;
                break;
        }
    }
}

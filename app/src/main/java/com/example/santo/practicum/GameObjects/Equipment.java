package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.io.Serializable;

/**
 * Created by Santo on 7/20/2017.
 */

enum EquipmentType {
    sword,
    knife,
    staff,
    mace,
    armor,
    robe
}

public class Equipment extends GameObject implements Serializable {
    String name;
    EquipmentType type;
    int modHitPoints = 0;
    int modAttack = 0;
    int modDefence = 0;
    int modMagicDefence = 0;
    int modSpeed = 0;

    public Equipment(Rect d, Bitmap generatedSprite, int layer, int red, int green, int blue) {
        super(d, generatedSprite, layer);

        switch (red % 6) {
            case 0:
                type = EquipmentType.sword;
                break;
            case 1:
                type = EquipmentType.knife;
                break;
            case 2:
                type = EquipmentType.staff;
                break;
            case 3:
                type = EquipmentType.mace;
                break;
            case 4:
                type = EquipmentType.armor;
                break;
            case 5:
                type = EquipmentType.robe;
                break;
        }
    }
}

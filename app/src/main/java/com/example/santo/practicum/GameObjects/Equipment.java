package com.example.santo.practicum.GameObjects;

import android.graphics.Rect;

import java.io.Serializable;

/**
 * Created by Santo on 7/20/2017.
 */

public class Equipment extends GameObject implements Serializable {
    String name;
    int modHitPoints = 0;
    int modAttack = 0;
    int modDefence = 0;
    int modMagicDefence = 0;
    int modSpeed = 0;

    public Equipment(Rect d, String spriteLoc, int layer, int red, int green, int blue) {
        super(d, spriteLoc, layer);
    }
}

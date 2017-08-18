package com.example.santo.practicum.GameObjects;

import android.graphics.Rect;

/**
 * Created by Santo on 7/20/2017.
 */

public class Equipment extends GameObject {
    int modHitPoints = 0;
    int modStrength = 0;
    int modDefence = 0;
    int modIntelligence = 0;
    int modWisdom = 0;
    int modSpeed = 0;

    public Equipment(Rect d, String spriteLoc, int layer) {
        super(d, spriteLoc, layer);
    }
}

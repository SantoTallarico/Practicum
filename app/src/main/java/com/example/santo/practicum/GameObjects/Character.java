package com.example.santo.practicum.GameObjects;

import android.graphics.Rect;

/**
 * Created by Santo on 7/20/2017.
 */

public class Character extends GameObject {
    int level;
    int hitPoints;
    int strength;
    int defence;
    int intelligence;
    int wisdom;
    int speed;

    Equipment weapon;
    Equipment armor;

    public Character(Rect d, String spriteLoc, int layer) {
        super(d, spriteLoc, layer);
    }
}

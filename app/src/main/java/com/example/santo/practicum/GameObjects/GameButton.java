package com.example.santo.practicum.GameObjects;

import android.graphics.Rect;

import com.example.santo.practicum.GameObjects.GameObject;

/**
 * Created by Santo on 8/9/2017.
 */

public class GameButton extends GameObject {
    public GameButton(Rect d, String spriteLoc) {
        super(d, spriteLoc);
        touchable = true;
    }
}

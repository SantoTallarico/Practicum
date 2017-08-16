package com.example.santo.practicum;

import android.graphics.Rect;

/**
 * Created by Santo on 8/9/2017.
 */

public class GameButton extends GameObject {
    GameButton(Rect d, String spriteLoc) {
        super(d, spriteLoc);
        touchable = true;
    }
}

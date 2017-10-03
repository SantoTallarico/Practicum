package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

import com.example.santo.practicum.GameObjects.GameObject;

/**
 * Created by Santo on 8/9/2017.
 */

public class GameButton extends GameObject {
    public GameButton(Rect d, String spriteLoc, int layer) {
        super(d, spriteLoc, layer);
        touchable = true;
    }

    public GameButton(Rect d, Bitmap sprite, int layer) {
        super(d, sprite, layer);
        touchable = true;
    }
}

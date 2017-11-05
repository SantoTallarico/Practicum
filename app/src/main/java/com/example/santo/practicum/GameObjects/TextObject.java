package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.santo.practicum.Enums.TextAlign;

/**
 * Created by Santo on 11/2/2017.
 */

public class TextObject extends GameObject {
    public String text;

    public TextObject(Rect d, String t, int layer, TextAlign alignment) {
        super(d, Bitmap.createBitmap(d.width(), -d.height(), Bitmap.Config.ARGB_8888), layer);

        SetText(t, alignment);
    }

    public void SetText(String t, TextAlign alignment) {
        text = t;

        Canvas canvas = new Canvas(generatedSprite);
        generatedSprite.eraseColor(0x00ffffff);

        Paint paint = new Paint();
        paint.setTextSize(32);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        switch (alignment) {
            case right:
                canvas.drawText(text, 0, scale[1] / 2 + 8, paint);
                break;
            case center:
                canvas.drawText(text, scale[0] / 2 - paint.measureText(text) / 2, scale[1] / 2 + 8, paint);
                break;
            case left:
                canvas.drawText(text, scale[0] - paint.measureText(text) / 2, scale[1] / 2 + 8, paint);
                break;
        }
    }
}

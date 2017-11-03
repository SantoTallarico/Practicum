package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by Santo on 11/2/2017.
 */

public class TextObject extends GameObject {
    public TextObject(Rect d, String t, int layer) {
        super(d, Bitmap.createBitmap(d.width(), -d.height(), Bitmap.Config.ARGB_8888), layer);

        Canvas canvas = new Canvas(generatedSprite);
        generatedSprite.eraseColor(0x00ffffff);

        Paint paint = new Paint();
        paint.setTextSize(32);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(t, 16, -d.height() / 2 + 8, paint);
    }
}

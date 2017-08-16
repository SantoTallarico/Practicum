package com.example.santo.practicum;

import android.graphics.Rect;

/**
 * Created by Santo on 7/20/2017.
 */

public class GameObject {
    public Rect dimensions;
    public String spriteLocation;

    public float[] vertices = {-1f, -1f, 0.0f,
                                -1f, 1f, 0.0f,
                                1f, 1f, 0.0f,
                                1f, -1f, 0.0f};
    //transform parameters, 2d so only need [x, y] transform and scale, rotation in radians
    public float[] translation = {0, 0};
    public float[] scale = {1, 1};
    public float rotation = 0;

    public boolean active = true;
    public boolean touchable = false;

    GameObject(Rect d, String spriteLoc) {
        dimensions = d;
        spriteLocation = spriteLoc;

        SetVertices();
    }

    void SetVertices() {
        vertices[0] = dimensions.left;
        vertices[1] = dimensions.bottom;
        vertices[3] = dimensions.left;
        vertices[4] = dimensions.top;
        vertices[6] = dimensions.right;
        vertices[7] = dimensions.top;
        vertices[9] = dimensions.right;
        vertices[10] = dimensions.bottom;
    }
}

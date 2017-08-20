package com.example.santo.practicum.GameObjects;

import android.graphics.Rect;

/**
 * Created by Santo on 7/20/2017.
 */

public class GameObject {
    public Rect defaultDimensions;
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
    public int spriteLayer = 0;

    public boolean visible = true;
    public boolean active = true;
    public boolean touchable = false;

    public GameObject(Rect d, String spriteLoc, int layer) {
        defaultDimensions = d;
        dimensions = d;
        spriteLocation = spriteLoc;
        spriteLayer = layer;

        SetVertices();
    }

    private void SetVertices() {
        vertices[0] = dimensions.left;
        vertices[1] = dimensions.bottom;
        vertices[3] = dimensions.left;
        vertices[4] = dimensions.top;
        vertices[6] = dimensions.right;
        vertices[7] = dimensions.top;
        vertices[9] = dimensions.right;
        vertices[10] = dimensions.bottom;

        vertices[2] = spriteLayer;
        vertices[5] = spriteLayer;
        vertices[8] = spriteLayer;
        vertices[11] = spriteLayer;
    }

    public void translate(float deltaX, float deltaY) {
        translation[0] += deltaX;
        translation[1] += deltaY;
    }

    public void translateTo(float newX, float newY) {
        translation[0] = newX;
        translation[1] = newY;
    }

    public void scale(float deltaX, float deltaY) {
        scale[0] *= deltaX;
        scale[1] *= deltaY;
    }

    public void scaleTo(float newX, float newY) {
        scale[0] = newX;
        scale[1] = newY;
    }

    public void rotate(float deltaR) {
        rotation += deltaR;
    }

    public void rotateTo(float newR) {
        rotation = newR;
    }

    public void onTouch() {

    }
}

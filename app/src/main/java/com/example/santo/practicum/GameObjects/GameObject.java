package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by Santo on 7/20/2017.
 */

public class GameObject implements Serializable {
    public String spriteLocation;
    public boolean isSpriteGenerated = false;
    public transient Bitmap generatedSprite;
    public int textureID;

    public final float[] VERTICES = {-0.5f, 0.5f, 0.0f,
                                    -0.5f, -0.5f, 0.0f,
                                    0.5f, -0.5f, 0.0f,
                                    0.5f, 0.5f, 0.0f};

    public float[] uvsAnimation = new float[] {
            0.0f, 0.0f,
            0.0f, 1.0f,
            0.5f, 1.0f,
            0.5f, 0.0f
    };

    public float[] vertices = VERTICES.clone();
    //transform parameters, 2d so only need [x, y] transform and scale, rotation in radians
    public float[] translation = {0, 0};
    public float[] scale = {1, 1};
    public float rotation = 0;
    public int spriteLayer = 0;
    public boolean animates = false;
    public int currentFrame = 0;
    public int numFrames = 2;
    public float xOffset = 0.5f;
    public final int FRAME_LENGTH = 500;
    public long time = 0;

    public boolean visible = true;
    public boolean active = true;
    public boolean touchable = false;

    public transient View.OnClickListener clickListener;

    public GameObject(Rect d, String spriteLoc, int layer) {
        translation[0] = d.left + d.width() / 2;
        translation[1] = d.top + d.height() / 2;
        scale[0] = d.width();
        scale[1] = -d.height();
        spriteLocation = spriteLoc;
        spriteLayer = layer;

        SetVertices();
    }

    public GameObject(Rect d, Bitmap sprite, int layer) {
        translation[0] = d.left + d.width() / 2;
        translation[1] = d.top + d.height() / 2;
        scale[0] = d.width();
        scale[1] = -d.height();
        generatedSprite = sprite;
        isSpriteGenerated = true;
        spriteLayer = layer;

        SetVertices();
    }

    private void SetVertices() {
        ApplyScale();
        ApplyTranslation();

        vertices[2] = spriteLayer;
        vertices[5] = spriteLayer;
        vertices[8] = spriteLayer;
        vertices[11] = spriteLayer;
    }

    public boolean Contains(float x, float y) {
        return x > translation[0] - scale[0] / 2 && x < translation[0] + scale[0] / 2 &&
                y > translation[1] - scale[1] / 2 && y < translation[1] + scale[1] / 2;
    }

    public void Translate(float deltaX, float deltaY) {
        translation[0] += deltaX;
        translation[1] += deltaY;

        SetVertices();
    }

    public void TranslateTo(float newX, float newY) {
        translation[0] = newX;
        translation[1] = newY;

        SetVertices();
    }

    public void Scale(float deltaX, float deltaY) {
        scale[0] *= deltaX;
        scale[1] *= deltaY;

        SetVertices();
    }

    public void ScaleTo(float newX, float newY) {
        scale[0] = newX;
        scale[1] = newY;

        SetVertices();
    }

    public void Rotate(float deltaR) {
        rotation += deltaR;
    }

    public void RotateTo(float newR) {
        rotation = newR;
    }

    public void ApplyScale() {
        vertices[0] = VERTICES[0] * scale[0];
        vertices[1] = VERTICES[1] * scale[1];
        vertices[3] = VERTICES[3] * scale[0];
        vertices[4] = VERTICES[4] * scale[1];
        vertices[6] = VERTICES[6] * scale[0];
        vertices[7] = VERTICES[7] * scale[1];
        vertices[9] = VERTICES[9] * scale[0];
        vertices[10] = VERTICES[10] * scale[1];
    }

    public void ApplyTranslation() {
        vertices[0] += translation[0];
        vertices[1] += translation[1];
        vertices[3] += translation[0];
        vertices[4] += translation[1];
        vertices[6] += translation[0];
        vertices[7] += translation[1];
        vertices[9] += translation[0];
        vertices[10] += translation[1];
    }

    public void SetOnClickListener(View.OnClickListener listener) {
        clickListener = listener;
    }

    public void Update(long elapsed) {
        time += elapsed;
        if (time > FRAME_LENGTH) {
            currentFrame++;
            if (currentFrame >= numFrames) {
                currentFrame = 0;
            }
            time = 0;
        }
    }
}

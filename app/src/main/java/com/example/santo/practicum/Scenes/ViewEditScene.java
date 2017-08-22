package com.example.santo.practicum.Scenes;

import android.graphics.Rect;
import android.os.Bundle;

import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.PB_GLSurfaceView;

public class ViewEditScene extends GameScene {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        GameObject painting = new GameObject(new Rect(-300, -300, 300, 300), "drawable/painting", 0);
        gameObjects.add(painting);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.GameObjects.Equipment;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.PB_Character;
import com.example.santo.practicum.PB_GLSurfaceView;

public class EditScene extends GameScene {
    PB_Character selectedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        selectedCharacter = ViewEditScene.selectedCharacter;

        gameObjects.add(selectedCharacter);
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

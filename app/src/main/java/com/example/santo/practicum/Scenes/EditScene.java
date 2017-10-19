package com.example.santo.practicum.Scenes;

import android.os.Bundle;

import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.PB_GLSurfaceView;

public class EditScene extends GameScene {
    Fighter selectedCharacter;

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

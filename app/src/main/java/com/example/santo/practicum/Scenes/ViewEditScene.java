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

import java.util.ArrayList;
import java.util.List;

public class ViewEditScene extends GameScene {
    static PB_Character selectedCharacter;
    List<GameButton> characterTiles = new ArrayList<GameButton>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        final GameButton btnEdit = new GameButton(new Rect(-150, -550, 150, -450), "drawable/btnfight", 100);
        btnEdit.touchable = false;
        gameObjects.add(btnEdit);


        btnEdit.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditScene.class);
                startActivity(i);
            }
        });

        if (MainMenuScene.generatedCharacters.size() == 0) {

        }
        else {
            int i = 0;
            for (final PB_Character character : MainMenuScene.generatedCharacters) {
                GameButton button = new GameButton(new Rect(-450 + i * 150, 250, -350 + i * 150, 350), character.tileIcon, 100);

                button.SetOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        selectedCharacter = character;
                        btnEdit.touchable = true;
                    }
                });

                characterTiles.add(button);
                gameObjects.add(button);

                i++;
            }
        }
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

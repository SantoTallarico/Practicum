package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.GameObjects.Equipment;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.PB_GLSurfaceView;

import java.util.ArrayList;
import java.util.List;

import static com.example.santo.practicum.Scenes.MainMenuScene.team;


public class ViewEditScene extends GameScene {
    static Fighter selectedCharacter;
    List<GameButton> characterTiles = new ArrayList<GameButton>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        final GameButton btnTeam1 = new GameButton(new Rect(-350, 450, -250, 550), team.Get(0).tileIcon, 100);
        final GameButton btnTeam2 = new GameButton(new Rect(-150, 450, -50, 550), team.Get(1).tileIcon, 100);
        final GameButton btnTeam3 = new GameButton(new Rect(50, 450, 150, 550), team.Get(2).tileIcon, 100);
        final GameButton btnTeam4 = new GameButton(new Rect(250, 450, 350, 550), team.Get(3).tileIcon, 100);
        gameObjects.add(btnTeam1);
        gameObjects.add(btnTeam2);
        gameObjects.add(btnTeam3);
        gameObjects.add(btnTeam4);

        btnTeam1.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedCharacter != null) {
                    team.Add(selectedCharacter, 0);
                    btnTeam1.isSpriteGenerated = true;
                    btnTeam1.generatedSprite = selectedCharacter.tileIcon;
                    glView.AddTexture(btnTeam1);
                }
            }
        });
        btnTeam2.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedCharacter != null) {
                    team.Add(selectedCharacter, 1);
                    btnTeam2.isSpriteGenerated = true;
                    btnTeam2.generatedSprite = selectedCharacter.tileIcon;
                    glView.AddTexture(btnTeam2);
                }
            }
        });
        btnTeam3.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedCharacter != null) {
                    team.Add(selectedCharacter, 2);
                    btnTeam3.isSpriteGenerated = true;
                    btnTeam3.generatedSprite = selectedCharacter.tileIcon;
                    glView.AddTexture(btnTeam3);
                }
            }
        });
        btnTeam4.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedCharacter != null) {
                    team.Add(selectedCharacter, 3);
                    btnTeam4.isSpriteGenerated = true;
                    btnTeam4.generatedSprite = selectedCharacter.tileIcon;
                    glView.AddTexture(btnTeam4);
                }
            }
        });

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
            for (final Fighter character : MainMenuScene.generatedCharacters) {
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

        if (MainMenuScene.generatedEquipment.size() == 0) {

        }
        else {
            int i = 0;
            for (final Equipment equipment : MainMenuScene.generatedEquipment) {
                GameObject object = new GameObject(new Rect(-450 + i * 150, -350, -350 + i * 150, -250), equipment.tileIcon, 100);

                gameObjects.add(object);

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

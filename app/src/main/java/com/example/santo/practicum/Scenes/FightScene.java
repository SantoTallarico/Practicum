package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.GameObjects.FightController;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.Team;
import com.example.santo.practicum.PB_GLSurfaceView;

public class FightScene extends GameScene {
    FightController controller;
    Fighter activeCharacter;
    Team playerTeam = MainMenuScene.team;
    Team enemyTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        playerTeam.Get(0).TranslateTo(300, 300);
        playerTeam.Get(1).TranslateTo(300, 100);
        playerTeam.Get(2).TranslateTo(300, -100);
        playerTeam.Get(3).TranslateTo(300, -300);

        gameObjects.add(playerTeam.Get(0));
        gameObjects.add(playerTeam.Get(1));
        gameObjects.add(playerTeam.Get(2));
        gameObjects.add(playerTeam.Get(3));

        enemyTeam = new Team(4);
        enemyTeam.Init(MainMenuScene.EMPTY_CHARACTER);
        enemyTeam.Add(Fighter.RandomFighter(), 0);
        enemyTeam.Add(Fighter.RandomFighter(), 1);
        enemyTeam.Add(Fighter.RandomFighter(), 2);
        enemyTeam.Add(Fighter.RandomFighter(), 3);

        enemyTeam.Get(0).TranslateTo(-300, 300);
        enemyTeam.Get(1).TranslateTo(-300, 100);
        enemyTeam.Get(2).TranslateTo(-300, -100);
        enemyTeam.Get(3).TranslateTo(-300, -300);

        gameObjects.add(enemyTeam.Get(0));
        gameObjects.add(enemyTeam.Get(1));
        gameObjects.add(enemyTeam.Get(2));
        gameObjects.add(enemyTeam.Get(3));

        controller = new FightController(playerTeam.GetTeam());
        //gameObjects.add(controller);
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
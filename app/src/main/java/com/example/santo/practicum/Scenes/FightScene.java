package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.graphics.Rect;
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

        controller = new FightController(playerTeam.GetTeam());
        //gameObjects.add(controller);

        GameButton btnCampaign = new GameButton(new Rect(-150, 350, 150, 250), "drawable/btnfight", 100);
        gameObjects.add(btnCampaign);

        btnCampaign.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightSelectScene.class);
                startActivity(i);
            }
        });
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
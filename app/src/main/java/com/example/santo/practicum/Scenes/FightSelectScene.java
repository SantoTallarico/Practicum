package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.PB_GLSurfaceView;
import com.example.santo.practicum.PhotoAccess.PhotoAccess;

public class FightSelectScene extends GameScene {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        final GameButton btnTeam1 = new GameButton(new Rect(-350, 450, -250, 550), MainMenuScene.team.Get(0).tileIcon, 100);
        final GameButton btnTeam2 = new GameButton(new Rect(-150, 450, -50, 550), MainMenuScene.team.Get(1).tileIcon, 100);
        final GameButton btnTeam3 = new GameButton(new Rect(50, 450, 150, 550), MainMenuScene.team.Get(2).tileIcon, 100);
        final GameButton btnTeam4 = new GameButton(new Rect(250, 450, 350, 550), MainMenuScene.team.Get(3).tileIcon, 100);
        gameObjects.add(btnTeam1);
        gameObjects.add(btnTeam2);
        gameObjects.add(btnTeam3);
        gameObjects.add(btnTeam4);

        GameButton btnCampaign = new GameButton(new Rect(-150, 250, 150, 350), "drawable/btnfight", 100);
        GameButton btnRandom = new GameButton(new Rect(-150, -350, 150, -250), "drawable/btnfight", 100);
        gameObjects.add(btnCampaign);
        gameObjects.add(btnRandom);

        btnCampaign.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightCampaignSelectScene.class);
                startActivity(i);
            }
        });

        btnRandom.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightScene.class);
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

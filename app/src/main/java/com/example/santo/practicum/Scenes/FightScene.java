package com.example.santo.practicum.Scenes;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.Enums.TextAlign;
import com.example.santo.practicum.FightListener;
import com.example.santo.practicum.GameObjects.FightController;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.Team;
import com.example.santo.practicum.GameObjects.TextObject;
import com.example.santo.practicum.PB_GLSurfaceView;

public class FightScene extends GameScene implements FightListener {
    FightController controller;
    Fighter activeCharacter;
    Team playerTeam = MainMenuScene.team;
    Team enemyTeam;

    TextObject btnFightText;
    TextObject txtAction1, txtAction2, txtAction3;
    GameButton btnAction1, btnAction2, btnAction3;

    Rect action1 = new Rect(-100, -300, 100, -400);
    Rect action2 = new Rect(-100, -400, 100, -500);
    Rect action3 = new Rect(-100, -500, 100, -600);

    GameObject activeFighterHighlight = new GameObject(new Rect(0, 150, 150, 0), Bitmap.createBitmap(new int[] { 0xffffffff }, 1, 1, Bitmap.Config.ARGB_8888), 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        GameButton btnFight = new GameButton(new Rect(-150, 350, 150, 250), "drawable/btnbackground", 90);
        btnFightText = new TextObject(new Rect(-150, 350, 150, 250), "Result", 100, TextAlign.center);
        gameObjects.add(btnFight);
        gameObjects.add(btnFightText);

        btnAction1 = new GameButton(action1, "drawable/btnbackground", 90);
        txtAction1 = new TextObject(action1, "action 1", 100, TextAlign.center);
        btnAction2 = new GameButton(action2, "drawable/btnbackground", 90);
        txtAction2 = new TextObject(action2, "action 2", 100, TextAlign.center);
        btnAction3 = new GameButton(action3, "drawable/btnbackground", 90);
        txtAction3 = new TextObject(action3, "action 3", 100, TextAlign.center);
        gameObjects.add(btnAction1);
        gameObjects.add(txtAction1);
        gameObjects.add(btnAction2);
        gameObjects.add(txtAction2);
        gameObjects.add(btnAction3);
        gameObjects.add(txtAction3);

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

        controller = new FightController(playerTeam, enemyTeam);
        controller.AssignListener(this);

        gameObjects.add(activeFighterHighlight);

        controller.StartRound();
    }

    @Override
    public void ListActions() {
        activeFighterHighlight.TranslateTo(controller.activeFighter.translation[0], controller.activeFighter.translation[1]);

        txtAction1.SetText(controller.activeFighter.fightActions.get(0).name, TextAlign.center);

        btnAction1.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.AddAction(controller.activeFighter.fightActions.get(0));
                controller.NextFighter();
            }
        });

        txtAction2.SetText(controller.activeFighter.fightActions.get(1).name, TextAlign.center);

        txtAction3.SetText(controller.activeFighter.fightActions.get(2).name, TextAlign.center);
    }

    @Override
    public void EndFight(boolean isPlayerWinner) {
        if (isPlayerWinner == true) {
            btnFightText.SetText("You win!", TextAlign.center);
        }
        else {
            btnFightText.SetText("You lose...", TextAlign.center);
        }

        glView.AddTexture(btnFightText);
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
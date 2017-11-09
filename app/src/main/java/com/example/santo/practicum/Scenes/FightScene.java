package com.example.santo.practicum.Scenes;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.Enums.Aiming;
import com.example.santo.practicum.Enums.FightState;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.Enums.TextAlign;
import com.example.santo.practicum.FightListener;
import com.example.santo.practicum.FightController;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.Team;
import com.example.santo.practicum.GameObjects.TextObject;
import com.example.santo.practicum.PB_GLSurfaceView;

public class FightScene extends GameScene implements FightListener {
    FightController controller;
    Team playerTeam = MainMenuScene.team;
    Team enemyTeam;

    TextObject txtCurrentState;
    TextObject txtAction1, txtAction2, txtAction3;
    GameButton btnAction1, btnAction2, btnAction3;

    TextObject txtFighter1HP, txtFighter2HP, txtFighter3HP, txtFighter4HP;
    TextObject txtEnemy1HP, txtEnemy2HP, txtEnemy3HP, txtEnemy4HP;

    Rect action1 = new Rect(-100, -300, 100, -400);
    Rect action2 = new Rect(-100, -400, 100, -500);
    Rect action3 = new Rect(-100, -500, 100, -600);
    Rect highlight = new Rect(0, 150, 150, 0);

    GameObject activeFighterHighlight = new GameObject(highlight, Bitmap.createBitmap(new int[] { 0xffffffff }, 1, 1, Bitmap.Config.ARGB_8888), 10);
    GameButton enemy1Highlight = new GameButton(highlight, Bitmap.createBitmap(new int[] { 0xffff0000 }, 1, 1, Bitmap.Config.ARGB_8888), 10);
    GameButton enemy2Highlight = new GameButton(highlight, Bitmap.createBitmap(new int[] { 0xffff0000 }, 1, 1, Bitmap.Config.ARGB_8888), 10);
    GameButton enemy3Highlight = new GameButton(highlight, Bitmap.createBitmap(new int[] { 0xffff0000 }, 1, 1, Bitmap.Config.ARGB_8888), 10);
    GameButton enemy4Highlight = new GameButton(highlight, Bitmap.createBitmap(new int[] { 0xffff0000 }, 1, 1, Bitmap.Config.ARGB_8888), 10);
    GameButton ally1Highlight = new GameButton(highlight, Bitmap.createBitmap(new int[] { 0xff00ff00 }, 1, 1, Bitmap.Config.ARGB_8888), 10);
    GameButton ally2Highlight = new GameButton(highlight, Bitmap.createBitmap(new int[] { 0xff00ff00 }, 1, 1, Bitmap.Config.ARGB_8888), 10);
    GameButton ally3Highlight = new GameButton(highlight, Bitmap.createBitmap(new int[] { 0xff00ff00 }, 1, 1, Bitmap.Config.ARGB_8888), 10);
    GameButton ally4Highlight = new GameButton(highlight, Bitmap.createBitmap(new int[] { 0xff00ff00 }, 1, 1, Bitmap.Config.ARGB_8888), 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        GameObject btnFight = new GameObject(new Rect(-150, 350, 150, 250), "drawable/btnbackground", 90);
        txtCurrentState = new TextObject(new Rect(-150, 350, 150, 250), "Choose Action", 100, TextAlign.center);
        gameObjects.add(btnFight);
        gameObjects.add(txtCurrentState);

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
        ally1Highlight.TranslateTo(300, 300);
        ally2Highlight.TranslateTo(300, 100);
        ally3Highlight.TranslateTo(300, -100);
        ally4Highlight.TranslateTo(300, -300);
        ally1Highlight.visible = false;
        ally2Highlight.visible = false;
        ally3Highlight.visible = false;
        ally4Highlight.visible = false;

        ally1Highlight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.AddAction(controller.activeAction, playerTeam.Get(0));
                controller.NextFighter();
            }
        });
        ally2Highlight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.AddAction(controller.activeAction, playerTeam.Get(1));
                controller.NextFighter();
            }
        });
        ally3Highlight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.AddAction(controller.activeAction, playerTeam.Get(2));
                controller.NextFighter();
            }
        });
        ally4Highlight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.AddAction(controller.activeAction, playerTeam.Get(3));
                controller.NextFighter();
            }
        });

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
        enemy1Highlight.TranslateTo(-300, 300);
        enemy2Highlight.TranslateTo(-300, 100);
        enemy3Highlight.TranslateTo(-300, -100);
        enemy4Highlight.TranslateTo(-300, -300);
        enemy1Highlight.visible = false;
        enemy2Highlight.visible = false;
        enemy3Highlight.visible = false;
        enemy4Highlight.visible = false;

        enemy1Highlight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.AddAction(controller.activeAction, enemyTeam.Get(0));
                controller.NextFighter();
            }
        });
        enemy2Highlight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.AddAction(controller.activeAction, enemyTeam.Get(1));
                controller.NextFighter();
            }
        });
        enemy3Highlight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.AddAction(controller.activeAction, enemyTeam.Get(2));
                controller.NextFighter();
            }
        });
        enemy4Highlight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.AddAction(controller.activeAction, enemyTeam.Get(3));
                controller.NextFighter();
            }
        });

        gameObjects.add(enemyTeam.Get(0));
        gameObjects.add(enemyTeam.Get(1));
        gameObjects.add(enemyTeam.Get(2));
        gameObjects.add(enemyTeam.Get(3));

        GameObject statsBackground = new GameObject(new Rect(150, -450, 450, -850), "drawable/btnbackground", 90);
        txtFighter1HP = new TextObject(new Rect(150, -450, 450, -550), "Fighter 1 HP: " + playerTeam.Get(0).GetStat(Stats.hitPoints), 100, TextAlign.right);
        txtFighter2HP = new TextObject(new Rect(150, -550, 450, -650), "Fighter 2 HP: " + playerTeam.Get(1).GetStat(Stats.hitPoints), 100, TextAlign.right);
        txtFighter3HP = new TextObject(new Rect(150, -650, 450, -750), "Fighter 3 HP: " + playerTeam.Get(2).GetStat(Stats.hitPoints), 100, TextAlign.right);
        txtFighter4HP = new TextObject(new Rect(150, -750, 450, -850), "Fighter 4 HP: " + playerTeam.Get(3).GetStat(Stats.hitPoints), 100, TextAlign.right);
        gameObjects.add(statsBackground);
        gameObjects.add(txtFighter1HP);
        gameObjects.add(txtFighter2HP);
        gameObjects.add(txtFighter3HP);
        gameObjects.add(txtFighter4HP);

        GameObject enemyStatsBackground = new GameObject(new Rect(-450, -450, -150, -850), "drawable/btnbackground", 90);
        txtEnemy1HP = new TextObject(new Rect(-450, -450, -150, -550), "Enemy 1 HP: " + enemyTeam.Get(0).GetStat(Stats.hitPoints), 100, TextAlign.right);
        txtEnemy2HP = new TextObject(new Rect(-450, -550, -150, -650), "Enemy 2 HP: " + enemyTeam.Get(1).GetStat(Stats.hitPoints), 100, TextAlign.right);
        txtEnemy3HP = new TextObject(new Rect(-450, -650, -150, -750), "Enemy 3 HP: " + enemyTeam.Get(2).GetStat(Stats.hitPoints), 100, TextAlign.right);
        txtEnemy4HP = new TextObject(new Rect(-450, -750, -150, -850), "Enemy 4 HP: " + enemyTeam.Get(3).GetStat(Stats.hitPoints), 100, TextAlign.right);
        gameObjects.add(enemyStatsBackground);
        gameObjects.add(txtEnemy1HP);
        gameObjects.add(txtEnemy2HP);
        gameObjects.add(txtEnemy3HP);
        gameObjects.add(txtEnemy4HP);

        controller = new FightController(playerTeam, enemyTeam);
        controller.AssignListener(this);

        gameObjects.add(activeFighterHighlight);
        gameObjects.add(enemy1Highlight);
        gameObjects.add(enemy2Highlight);
        gameObjects.add(enemy3Highlight);
        gameObjects.add(enemy4Highlight);
        gameObjects.add(ally1Highlight);
        gameObjects.add(ally2Highlight);
        gameObjects.add(ally3Highlight);
        gameObjects.add(ally4Highlight);

        controller.StartRound();
    }

    @Override
    public void ListActions() {
        activeFighterHighlight.TranslateTo(controller.activeFighter.translation[0], controller.activeFighter.translation[1]);

        txtAction1.SetText(controller.activeFighter.fightActions.get(0).name, TextAlign.center);
        btnAction1.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.SetActiveAction(controller.activeFighter.fightActions.get(0));
                if (controller.activeAction.aiming == Aiming.self) {
                    controller.AddAction(controller.activeFighter.fightActions.get(0), controller.activeFighter);
                    controller.NextFighter();
                }
                else {
                    UpdateState(FightState.selectingTarget);
                }
            }
        });

        txtAction2.SetText(controller.activeFighter.fightActions.get(1).name, TextAlign.center);
        btnAction2.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.SetActiveAction(controller.activeFighter.fightActions.get(1));
                if (controller.activeAction.aiming == Aiming.self) {
                    controller.AddAction(controller.activeFighter.fightActions.get(1), controller.activeFighter);
                    controller.NextFighter();
                }
                else {
                    UpdateState(FightState.selectingTarget);
                }
            }
        });

        txtAction3.SetText(controller.activeFighter.fightActions.get(2).name, TextAlign.center);
        btnAction3.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.SetActiveAction(controller.activeFighter.fightActions.get(2));
                if (controller.activeAction.aiming == Aiming.self) {
                    controller.AddAction(controller.activeFighter.fightActions.get(2), controller.activeFighter);
                    controller.NextFighter();
                }
                else {
                    UpdateState(FightState.selectingTarget);
                }
            }
        });
    }

    @Override
    public void UpdateText() {
        txtFighter1HP.SetText("Fighter 1 HP: " + playerTeam.Get(0).GetStat(Stats.hitPoints), TextAlign.right);
        txtFighter2HP.SetText("Fighter 2 HP: " + playerTeam.Get(1).GetStat(Stats.hitPoints), TextAlign.right);
        txtFighter3HP.SetText("Fighter 3 HP: " + playerTeam.Get(2).GetStat(Stats.hitPoints), TextAlign.right);
        txtFighter4HP.SetText("Fighter 4 HP: " + playerTeam.Get(3).GetStat(Stats.hitPoints), TextAlign.right);

        glView.AddTexture(txtFighter1HP);
        glView.AddTexture(txtFighter2HP);
        glView.AddTexture(txtFighter3HP);
        glView.AddTexture(txtFighter4HP);

        txtEnemy1HP.SetText("Enemy 1 HP: " + enemyTeam.Get(0).GetStat(Stats.hitPoints), TextAlign.right);
        txtEnemy2HP.SetText("Enemy 2 HP: " + enemyTeam.Get(1).GetStat(Stats.hitPoints), TextAlign.right);
        txtEnemy3HP.SetText("Enemy 3 HP: " + enemyTeam.Get(2).GetStat(Stats.hitPoints), TextAlign.right);
        txtEnemy4HP.SetText("Enemy 4 HP: " + enemyTeam.Get(3).GetStat(Stats.hitPoints), TextAlign.right);

        glView.AddTexture(txtEnemy1HP);
        glView.AddTexture(txtEnemy2HP);
        glView.AddTexture(txtEnemy3HP);
        glView.AddTexture(txtEnemy4HP);
    }

    @Override
    public void UpdateState(FightState state) {
        txtCurrentState.SetText("" + state, TextAlign.center);

        glView.AddTexture(txtCurrentState);

        switch (state) {
            case selectingAction:
                btnAction1.touchable = true;
                btnAction2.touchable = true;
                btnAction3.touchable = true;

                enemy1Highlight.visible = false;
                enemy2Highlight.visible = false;
                enemy3Highlight.visible = false;
                enemy4Highlight.visible = false;
                ally1Highlight.visible = false;
                ally2Highlight.visible = false;
                ally3Highlight.visible = false;
                ally4Highlight.visible = false;
                enemy1Highlight.touchable = false;
                enemy2Highlight.touchable = false;
                enemy3Highlight.touchable = false;
                enemy4Highlight.touchable = false;
                ally1Highlight.touchable = false;
                ally2Highlight.touchable = false;
                ally3Highlight.touchable = false;
                ally4Highlight.touchable = false;
                break;
            case selectingTarget:
                btnAction1.touchable = false;
                btnAction2.touchable = false;
                btnAction3.touchable = false;

                switch (controller.activeAction.aiming) {
                    case enemy:
                        enemy1Highlight.visible = enemyTeam.Get(0).isAlive;
                        enemy2Highlight.visible = enemyTeam.Get(1).isAlive;
                        enemy3Highlight.visible = enemyTeam.Get(2).isAlive;
                        enemy4Highlight.visible = enemyTeam.Get(3).isAlive;
                        enemy1Highlight.touchable = enemyTeam.Get(0).isAlive;
                        enemy2Highlight.touchable = enemyTeam.Get(1).isAlive;
                        enemy3Highlight.touchable = enemyTeam.Get(2).isAlive;
                        enemy4Highlight.touchable = enemyTeam.Get(3).isAlive;
                        break;
                    case ally:
                        ally1Highlight.visible = playerTeam.Get(0).isAlive;
                        ally2Highlight.visible = playerTeam.Get(0).isAlive;
                        ally3Highlight.visible = playerTeam.Get(0).isAlive;
                        ally4Highlight.visible = playerTeam.Get(0).isAlive;
                        ally1Highlight.touchable = playerTeam.Get(0).isAlive;
                        ally2Highlight.touchable = playerTeam.Get(0).isAlive;
                        ally3Highlight.touchable = playerTeam.Get(0).isAlive;
                        ally4Highlight.touchable = playerTeam.Get(0).isAlive;
                        break;
                    case otherAlly:
                        ally1Highlight.visible = playerTeam.Get(0).isAlive;
                        ally2Highlight.visible = playerTeam.Get(1).isAlive;
                        ally3Highlight.visible = playerTeam.Get(2).isAlive;
                        ally4Highlight.visible = playerTeam.Get(3).isAlive;
                        ally1Highlight.touchable = playerTeam.Get(0).isAlive;
                        ally2Highlight.touchable = playerTeam.Get(1).isAlive;
                        ally3Highlight.touchable = playerTeam.Get(2).isAlive;
                        ally4Highlight.touchable = playerTeam.Get(3).isAlive;
                        break;
                }
                break;
            case enemyAction:
                UninteractiveState();
                break;
            case actionsRunning:
                UninteractiveState();
                break;
        }
    }

    @Override
    public void EndFight(boolean isPlayerWinner) {
        if (isPlayerWinner == true) {
            txtCurrentState.SetText("You win!", TextAlign.center);
        }
        else {
            txtCurrentState.SetText("You lose...", TextAlign.center);
        }

        glView.AddTexture(txtCurrentState);

        UninteractiveState();

        for (Fighter fighter : playerTeam.GetTeam()) {
            fighter.ResetStats();
        }
    }

    public void UninteractiveState() {
        btnAction1.touchable = false;
        btnAction2.touchable = false;
        btnAction3.touchable = false;
        enemy1Highlight.visible = false;
        enemy2Highlight.visible = false;
        enemy3Highlight.visible = false;
        enemy4Highlight.visible = false;
        ally1Highlight.visible = false;
        ally2Highlight.visible = false;
        ally3Highlight.visible = false;
        ally4Highlight.visible = false;
        enemy1Highlight.touchable = false;
        enemy2Highlight.touchable = false;
        enemy3Highlight.touchable = false;
        enemy4Highlight.touchable = false;
        ally1Highlight.touchable = false;
        ally2Highlight.touchable = false;
        ally3Highlight.touchable = false;
        ally4Highlight.touchable = false;
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
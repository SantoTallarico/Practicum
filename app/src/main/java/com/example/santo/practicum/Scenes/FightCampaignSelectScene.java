package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.Enums.TextAlign;
import com.example.santo.practicum.GameObjects.Cleric;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.Rogue;
import com.example.santo.practicum.GameObjects.TextObject;
import com.example.santo.practicum.GameObjects.Warrior;
import com.example.santo.practicum.GameObjects.Wizard;
import com.example.santo.practicum.Mongo;
import com.example.santo.practicum.PB_GLSurfaceView;
import com.example.santo.practicum.PhotoAccess.PhotoAccess;

import java.util.ArrayList;
import java.util.List;

import static com.example.santo.practicum.Scenes.MainMenuScene.palette1;

public class FightCampaignSelectScene extends GameScene {
    public static List<Fighter> campaignFighters = new ArrayList<Fighter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        final GameObject btnTeam1 = new GameObject(new Rect(-350, 550, -250, 450), MainMenuScene.team.Get(0).tileIcon, 100);
        final GameObject btnTeam2 = new GameObject(new Rect(-150, 550, -50, 450), MainMenuScene.team.Get(1).tileIcon, 100);
        final GameObject btnTeam3 = new GameObject(new Rect(50, 550, 150, 450), MainMenuScene.team.Get(2).tileIcon, 100);
        final GameObject btnTeam4 = new GameObject(new Rect(250, 550, 350, 450), MainMenuScene.team.Get(3).tileIcon, 100);
        gameObjects.add(btnTeam1);
        gameObjects.add(btnTeam2);
        gameObjects.add(btnTeam3);
        gameObjects.add(btnTeam4);

        GameButton btnBattle1 = new GameButton(new Rect(-150, 600, 150, 400), "drawable/btnbackground", 90);
        TextObject btnBattle1Text1 = new TextObject(new Rect(-150, 600, 150, 500), "Campaign Battle 1", 100, TextAlign.center);
        TextObject btnBattle1Text2 = new TextObject(new Rect(-150, 500, 150, 400), "Recommended Level: 1", 100, TextAlign.center);
        GameButton btnBattle2 = new GameButton(new Rect(-150, 350, 150, 150), "drawable/btnbackground", 90);
        TextObject btnBattle2Text1 = new TextObject(new Rect(-150, 350, 150, 250), "Campaign Battle 2", 100, TextAlign.center);
        TextObject btnBattle2Text2 = new TextObject(new Rect(-150, 250, 150, 150), "Recommended Level: 3", 100, TextAlign.center);
        GameButton btnBattle3 = new GameButton(new Rect(-150, 100, 150, -100), "drawable/btnbackground", 90);
        TextObject btnBattle3Text1 = new TextObject(new Rect(-150, 100, 150, 0), "Campaign Battle 3", 100, TextAlign.center);
        TextObject btnBattle3Text2 = new TextObject(new Rect(-150, 0, 150, -100), "Recommended Level: 5", 100, TextAlign.center);
        GameButton btnBattle4 = new GameButton(new Rect(-150, -150, 150, -350), "drawable/btnbackground", 90);
        TextObject btnBattle4Text1 = new TextObject(new Rect(-150, -150, 150, -250), "Campaign Battle 4", 100, TextAlign.center);
        TextObject btnBattle4Text2 = new TextObject(new Rect(-150, -250, 150, -350), "Recommended Level: 7", 100, TextAlign.center);
        GameButton btnBattle5 = new GameButton(new Rect(-150, -400, 150, -600), "drawable/btnbackground", 90);
        TextObject btnBattle5Text1 = new TextObject(new Rect(-150, -400, 150, -50), "Campaign Battle 5", 100, TextAlign.center);
        TextObject btnBattle5Text2 = new TextObject(new Rect(-150, -500, 150, -600), "Recommended Level: 10", 100, TextAlign.center);
        gameObjects.add(btnBattle1);
        gameObjects.add(btnBattle1Text1);
        gameObjects.add(btnBattle1Text2);
        gameObjects.add(btnBattle2);
        gameObjects.add(btnBattle2Text1);
        gameObjects.add(btnBattle2Text2);
        gameObjects.add(btnBattle3);
        gameObjects.add(btnBattle3Text1);
        gameObjects.add(btnBattle3Text2);
        gameObjects.add(btnBattle4);
        gameObjects.add(btnBattle4Text1);
        gameObjects.add(btnBattle4Text2);
        gameObjects.add(btnBattle5);
        gameObjects.add(btnBattle5Text1);
        gameObjects.add(btnBattle5Text2);

        btnBattle1.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightScene.class);
                i.putExtra("isCampaign", true);
                campaignFighters.clear();
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 1, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 1, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Rogue(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 1, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 1, 0xffffffff, 0xffffffff, 0xffffffff));

                for (Fighter fighter : campaignFighters) {
                    fighter.Init(FightCampaignSelectScene.this);
                    fighter.isPlayerControlled = false;
                }
                startActivity(i);
            }
        });

        btnBattle2.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightScene.class);
                i.putExtra("isCampaign", true);
                campaignFighters.clear();
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 3, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 3, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 3, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 3, 0xffffffff, 0xffffffff, 0xffffffff));

                for (Fighter fighter : campaignFighters) {
                    fighter.Init(FightCampaignSelectScene.this);
                    fighter.isPlayerControlled = false;
                }
                startActivity(i);
            }
        });

        btnBattle3.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightScene.class);
                i.putExtra("isCampaign", true);
                campaignFighters.clear();
                campaignFighters.add(new Rogue(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 5, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Rogue(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 5, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 5, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 5, 0xffffffff, 0xffffffff, 0xffffffff));

                for (Fighter fighter : campaignFighters) {
                    fighter.Init(FightCampaignSelectScene.this);
                    fighter.isPlayerControlled = false;
                }
                startActivity(i);
            }
        });

        btnBattle4.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightScene.class);
                i.putExtra("isCampaign", true);
                campaignFighters.clear();
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 7, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 7, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 7, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 7, 0xffffffff, 0xffffffff, 0xffffffff));

                for (Fighter fighter : campaignFighters) {
                    fighter.Init(FightCampaignSelectScene.this);
                    fighter.isPlayerControlled = false;
                }
                startActivity(i);
            }
        });

        btnBattle5.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightScene.class);
                i.putExtra("isCampaign", true);
                campaignFighters.clear();
                campaignFighters.add(new Rogue(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 10, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 10, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 10, 0xffffffff, 0xffffffff, 0xffffffff));
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 255, 255, 255, 100, 10, 0xffffffff, 0xffffffff, 0xffffffff));

                for (Fighter fighter : campaignFighters) {
                    fighter.Init(FightCampaignSelectScene.this);
                    fighter.isPlayerControlled = false;
                }
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

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

        final GameObject btnTeam1 = new GameObject(new Rect(-350, 750, -250, 650), MainMenuScene.team.Get(0).tileIcon, 100);
        final GameObject btnTeam2 = new GameObject(new Rect(-150, 750, -50, 650), MainMenuScene.team.Get(1).tileIcon, 100);
        final GameObject btnTeam3 = new GameObject(new Rect(50, 750, 150, 650), MainMenuScene.team.Get(2).tileIcon, 100);
        final GameObject btnTeam4 = new GameObject(new Rect(250, 750, 350, 650), MainMenuScene.team.Get(3).tileIcon, 100);
        gameObjects.add(btnTeam1);
        gameObjects.add(btnTeam2);
        gameObjects.add(btnTeam3);
        gameObjects.add(btnTeam4);

        GameButton btnBattle1 = new GameButton(new Rect(-250, 600, 250, 400), "drawable/btnbackground", 90);
        TextObject btnBattle1Text1 = new TextObject(new Rect(-250, 600, 250, 500), "Campaign Battle 1", 100, TextAlign.center);
        TextObject btnBattle1Text2 = new TextObject(new Rect(-250, 500, 250, 400), "Recommended Level: 1", 100, TextAlign.center);
        GameButton btnBattle2 = new GameButton(new Rect(-250, 350, 250, 150), "drawable/btnbackground", 90);
        TextObject btnBattle2Text1 = new TextObject(new Rect(-250, 350, 250, 250), "Campaign Battle 2", 100, TextAlign.center);
        TextObject btnBattle2Text2 = new TextObject(new Rect(-250, 250, 250, 150), "Recommended Level: 3", 100, TextAlign.center);
        GameButton btnBattle3 = new GameButton(new Rect(-250, 100, 250, -100), "drawable/btnbackground", 90);
        TextObject btnBattle3Text1 = new TextObject(new Rect(-250, 100, 250, 0), "Campaign Battle 3", 100, TextAlign.center);
        TextObject btnBattle3Text2 = new TextObject(new Rect(-250, 0, 250, -100), "Recommended Level: 5", 100, TextAlign.center);
        GameButton btnBattle4 = new GameButton(new Rect(-250, -150, 250, -350), "drawable/btnbackground", 90);
        TextObject btnBattle4Text1 = new TextObject(new Rect(-250, -150, 250, -250), "Campaign Battle 4", 100, TextAlign.center);
        TextObject btnBattle4Text2 = new TextObject(new Rect(-250, -250, 250, -350), "Recommended Level: 7", 100, TextAlign.center);
        GameButton btnBattle5 = new GameButton(new Rect(-250, -400, 250, -600), "drawable/btnbackground", 90);
        TextObject btnBattle5Text1 = new TextObject(new Rect(-250, -400, 250, -500), "Campaign Battle 5", 100, TextAlign.center);
        TextObject btnBattle5Text2 = new TextObject(new Rect(-250, -500, 250, -600), "Recommended Level: 10", 100, TextAlign.center);
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
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 0xffff2244, 0xff00aa44, 0xff22dddd, 100, 1, 255, 255, 255));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 0xffff2244, 0xff00aa44, 0xff22dddd, 100, 1, 255, 255, 255));
                campaignFighters.add(new Rogue(new Rect(-75, 100, 75, -100), palette1, 0xffff2244, 0xff00aa44, 0xff22dddd, 100, 1, 255, 255, 255));
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 0xffff2244, 0xff00aa44, 0xff22dddd, 100, 1, 255, 255, 255));

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
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 0xff55ff55, 0xffbb00bb, 0xff444444, 100, 3, 255, 255, 255));
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 0xff55ff55, 0xffbb00bb, 0xff444444, 100, 3, 255, 255, 255));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 0xff55ff55, 0xffbb00bb, 0xff444444, 100, 3, 255, 255, 255));
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 0xff55ff55, 0xffbb00bb, 0xff444444, 100, 3, 255, 255, 255));

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
                campaignFighters.add(new Rogue(new Rect(-75, 100, 75, -100), palette1, 0xff000000, 0xffff0000, 0xffffffff, 100, 5, 255, 255, 255));
                campaignFighters.add(new Rogue(new Rect(-75, 100, 75, -100), palette1, 0xff000000, 0xffff0000, 0xffffffff, 100, 5, 255, 255, 255));
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 0xff000000, 0xffff0000, 0xffffffff, 100, 5, 255, 255, 255));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 0xff000000, 0xffff0000, 0xffffffff, 100, 5, 255, 255, 255));

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
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 0xff0000bb, 0xffbbbbbb, 0xffffffff, 100, 7, 255, 255, 255));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 0xff0000bb, 0xffbbbbbb, 0xffffffff, 100, 7, 255, 255, 255));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 0xff0000bb, 0xffbbbbbb, 0xffffffff, 100, 7, 255, 255, 255));
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 0xff0000bb, 0xffbbbbbb, 0xffffffff, 100, 7, 255, 255, 255));

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
                campaignFighters.add(new Rogue(new Rect(-75, 100, 75, -100), palette1, 0xffaaaa00, 0xffffffff, 0xff000000, 100, 10, 255, 255, 255));
                campaignFighters.add(new Wizard(new Rect(-75, 100, 75, -100), palette1, 0xffaaaa00, 0xffffffff, 0xff000000, 100, 10, 255, 255, 255));
                campaignFighters.add(new Cleric(new Rect(-75, 100, 75, -100), palette1, 0xffaaaa00, 0xffffffff, 0xff000000, 100, 10, 255, 255, 255));
                campaignFighters.add(new Warrior(new Rect(-75, 100, 75, -100), palette1, 0xffaaaa00, 0xffffffff, 0xff000000, 100, 10, 255, 255, 255));

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

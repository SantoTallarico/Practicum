package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.Enums.TextAlign;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.TextObject;
import com.example.santo.practicum.Mongo;
import com.example.santo.practicum.MongoAdapter;
import com.example.santo.practicum.PB_GLSurfaceView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.santo.practicum.Scenes.MainMenuScene.API_KEY;
import static com.example.santo.practicum.Scenes.MainMenuScene.DATABASE_NAME;

public class FightSelectScene extends GameScene implements MongoAdapter {
    public static List<Fighter> networkedFighters = new ArrayList<>();
    public static List<Fighter> tempNetworkedFighters;

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

        GameButton btnCampaign = new GameButton(new Rect(-150, 150, 150, 50), "drawable/btnbackground", 90);
        TextObject btnCampaignText = new TextObject(new Rect(-150, 150, 150, 50), "Campaign Battle", 100, TextAlign.center);
        GameButton btnRandom = new GameButton(new Rect(-150, -250, 150, -350), "drawable/btnbackground", 90);
        TextObject btnRandomText = new TextObject(new Rect(-150, -250, 150, -350), "Random Battle", 100, TextAlign.center);
        GameButton btnNetwork = new GameButton(new Rect(-150, -650, 150, -750), "drawable/btnbackground", 90);
        TextObject btnNetworkText = new TextObject(new Rect(-150, -650, 150, -750), "Network Battle", 100, TextAlign.center);
        gameObjects.add(btnCampaign);
        gameObjects.add(btnCampaignText);
        gameObjects.add(btnRandom);
        gameObjects.add(btnRandomText);
        gameObjects.add(btnNetwork);
        gameObjects.add(btnNetworkText);


        btnCampaign.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightCampaignSelectScene.class);
                startActivity(i);
            }
        });

        btnRandom.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightScene.class);
                i.putExtra("isNetworked", false);
                startActivity(i);
            }
        });

        btnNetwork.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightScene.class);
                i.putExtra("isNetworked", true);
                Mongo.get(FightSelectScene.this, "teams", null);
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


    @Override
    public String dbName() {
        return DATABASE_NAME;
    }

    @Override
    public String apiKey() {
        return API_KEY;
    }

    @Override
    public void processResult(String result) {
        Type fightCollectionType = new TypeToken<List<Fighter>>(){}.getType();

        tempNetworkedFighters = MainMenuScene.gsonBuilder.create().fromJson(result, fightCollectionType);

        Collections.shuffle(tempNetworkedFighters);

        for (Fighter fighter : tempNetworkedFighters) {
            if (!fighter.deviceID.equals(MainMenuScene.DEVICE_ID)) {
                networkedFighters.add(fighter);
            }
            if (networkedFighters.size() == 4) {
                break;
            }
        }

        //just in case there are less than four unique fighters
        while (networkedFighters.size() < 4) {
            networkedFighters.add(Fighter.RandomFighter(this, 1));
        }

        for (Fighter fighter : networkedFighters) {
            fighter.Init(this);
            fighter.isPlayerControlled = false;
        }
    }
}
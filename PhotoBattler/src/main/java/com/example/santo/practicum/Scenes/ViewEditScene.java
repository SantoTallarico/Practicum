package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.santo.practicum.Enums.CharacterClassHelper;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.Enums.TextAlign;
import com.example.santo.practicum.GameObjects.Equipment;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.TextObject;
import com.example.santo.practicum.Mongo;
import com.example.santo.practicum.MongoAdapter;
import com.example.santo.practicum.PB_GLSurfaceView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.santo.practicum.Scenes.MainMenuScene.API_KEY;
import static com.example.santo.practicum.Scenes.MainMenuScene.DATABASE_NAME;
import static com.example.santo.practicum.Scenes.MainMenuScene.team;


public class ViewEditScene extends GameScene implements MongoAdapter {
    static Fighter selectedCharacter;

    TextObject txtClass, txtHitPoints, txtAttack, txtDefence, txtMagicDefence, txtSpeed;

    GameObject selectedCharacterHighlight = new GameObject(new Rect(10000, 10150, 10150, 10000), Bitmap.createBitmap(new int[] { 0xff000000 }, 1, 1, Bitmap.Config.ARGB_8888), 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        gameObjects.add(selectedCharacterHighlight);

        GameObject statsBackground = new GameObject(new Rect(-400, 850, 400, 550), "drawable/btnbackground", 90);
        txtClass = new TextObject(new Rect(-350, 850, -50, 750), "Class: ", 100, TextAlign.right);
        txtHitPoints = new TextObject(new Rect(-350, 750, -50, 650), "Hit Points: ", 100, TextAlign.right);
        txtAttack = new TextObject(new Rect(-350, 650, -50, 550), "Attack: ", 100, TextAlign.right);
        txtDefence = new TextObject(new Rect(50, 850, 350, 750), "Defence: ", 100, TextAlign.right);
        txtMagicDefence = new TextObject(new Rect(50, 750, 350, 650), "Magic Defence: ", 100, TextAlign.right);
        txtSpeed = new TextObject(new Rect(50, 650, 350, 550), "Speed: ", 100, TextAlign.right);
        gameObjects.add(statsBackground);
        gameObjects.add(txtClass);
        gameObjects.add(txtHitPoints);
        gameObjects.add(txtAttack);
        gameObjects.add(txtDefence);
        gameObjects.add(txtMagicDefence);
        gameObjects.add(txtSpeed);

        final GameButton btnTeam1 = new GameButton(new Rect(-475, 500, -275, 300), team.Get(0).tileIcon, 100);
        final GameButton btnTeam2 = new GameButton(new Rect(-225, 500, -25, 300), team.Get(1).tileIcon, 100);
        final GameButton btnTeam3 = new GameButton(new Rect(25, 500, 225, 300), team.Get(2).tileIcon, 100);
        final GameButton btnTeam4 = new GameButton(new Rect(275, 500, 475, 300), team.Get(3).tileIcon, 100);
        gameObjects.add(btnTeam1);
        gameObjects.add(btnTeam2);
        gameObjects.add(btnTeam3);
        gameObjects.add(btnTeam4);

        btnTeam1.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedCharacter != null) {
                    team.Add(selectedCharacter, 0);
                    btnTeam1.isSpriteGenerated = true;
                    btnTeam1.generatedSprites[0] = selectedCharacter.tileIcon;
                    glView.AddTexture(btnTeam1, 0);
                }
            }
        });
        btnTeam2.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedCharacter != null) {
                    team.Add(selectedCharacter, 1);
                    btnTeam2.isSpriteGenerated = true;
                    btnTeam2.generatedSprites[0] = selectedCharacter.tileIcon;
                    glView.AddTexture(btnTeam2, 0);
                }
            }
        });
        btnTeam3.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedCharacter != null) {
                    team.Add(selectedCharacter, 2);
                    btnTeam3.isSpriteGenerated = true;
                    btnTeam3.generatedSprites[0] = selectedCharacter.tileIcon;
                    glView.AddTexture(btnTeam3, 0);
                }
            }
        });
        btnTeam4.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedCharacter != null) {
                    team.Add(selectedCharacter, 3);
                    btnTeam4.isSpriteGenerated = true;
                    btnTeam4.generatedSprites[0] = selectedCharacter.tileIcon;
                    glView.AddTexture(btnTeam4, 0);
                }
            }
        });

        final GameButton btnEdit = new GameButton(new Rect(-150, -350, 150, -450), "drawable/btnbackground", 90);
        btnEdit.touchable = false;
        TextObject btnEditText = new TextObject(new Rect(-150, -350, 150, -450), "Edit", 100, TextAlign.center);
        gameObjects.add(btnEdit);
        gameObjects.add(btnEditText);

        btnEdit.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditScene.class);
                startActivity(i);
            }
        });


        final GameButton btnSendTeam = new GameButton(new Rect(-150, -550, 150, -650), "drawable/btnbackground", 90);
        TextObject btnSendTeamText = new TextObject(new Rect(-150, -550, 150, -650), "Send Team", 100, TextAlign.center);
        gameObjects.add(btnSendTeam);
        gameObjects.add(btnSendTeamText);

        btnSendTeam.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Gson innerGson = MainMenuScene.gsonBuilder.create();
                String genChar1 = innerGson.toJson(team.Get(0));
                String genChar2 = innerGson.toJson(team.Get(1));
                String genChar3 = innerGson.toJson(team.Get(2));
                String genChar4 = innerGson.toJson(team.Get(3));
                try {
                    Mongo.post(ViewEditScene.this, "teams", new JSONObject(genChar1));
                    Mongo.post(ViewEditScene.this, "teams", new JSONObject(genChar2));
                    Mongo.post(ViewEditScene.this, "teams", new JSONObject(genChar3));
                    Mongo.post(ViewEditScene.this, "teams", new JSONObject(genChar4));
                }
                catch (JSONException e) {
                    Log.d("InputStream", e.getLocalizedMessage());
                }
            }
        });

        if (MainMenuScene.generatedCharacters.size() == 0) {

        }
        else {
            int i = 0;
            for (final Fighter character : MainMenuScene.generatedCharacters) {
                final GameButton button = new GameButton(new Rect(-450 + (i % 5) * 150, 250 - (i / 5) * 150, -350 + (i % 5) * 150, 150 - (i / 5) * 150), character.tileIcon, 100);

                button.SetOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        selectedCharacter = character;
                        btnEdit.touchable = true;
                        UpdateText();
                        selectedCharacterHighlight.TranslateTo(button.translation[0], button.translation[1]);
                    }
                });

                gameObjects.add(button);

                i++;
            }
        }

        if (MainMenuScene.generatedEquipment.size() == 0) {

        }
        else {
            int i = 0;
            for (final Equipment equipment : MainMenuScene.generatedEquipment) {
                GameObject object = new GameObject(new Rect(-450 + i * 150, -150, -350 + i * 150, -250), equipment.tileIcon, 100);

                gameObjects.add(object);

                i++;
            }
        }
    }

    public void UpdateText() {
        txtClass.SetText("Class: " + CharacterClassHelper.ToString(selectedCharacter.charClass), TextAlign.right);
        txtHitPoints.SetText("Hit Points: " + selectedCharacter.GetStat(Stats.hitPoints), TextAlign.right);
        txtAttack.SetText("Attack: " + selectedCharacter.GetStat(Stats.attack), TextAlign.right);
        txtDefence.SetText("Defence: " + selectedCharacter.GetStat(Stats.defence), TextAlign.right);
        txtMagicDefence.SetText("Magic Defence: " + selectedCharacter.GetStat(Stats.magicDefence), TextAlign.right);
        txtSpeed.SetText("Speed: " + selectedCharacter.GetStat(Stats.speed), TextAlign.right);

        glView.AddTexture(txtClass, 0);
        glView.AddTexture(txtHitPoints, 0);
        glView.AddTexture(txtAttack, 0);
        glView.AddTexture(txtDefence, 0);
        glView.AddTexture(txtMagicDefence, 0);
        glView.AddTexture(txtSpeed, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectedCharacter != null) {
            UpdateText();
        }
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

    }
}
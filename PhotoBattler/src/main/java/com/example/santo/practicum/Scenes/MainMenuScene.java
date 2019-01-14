package com.example.santo.practicum.Scenes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.Enums.TextAlign;
import com.example.santo.practicum.GameObjects.Cleric;
import com.example.santo.practicum.GameObjects.Equipment;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.Rogue;
import com.example.santo.practicum.GameObjects.Team;
import com.example.santo.practicum.GameObjects.TextObject;
import com.example.santo.practicum.GameObjects.Warrior;
import com.example.santo.practicum.GameObjects.Wizard;
import com.example.santo.practicum.Installation;
import com.example.santo.practicum.MongoAdapter;
import com.example.santo.practicum.PB_GLSurfaceView;
import com.example.santo.practicum.PhotoAccess.PhotoAccess;
import com.example.santo.practicum.PhotoGeneration;
import com.example.santo.practicum.R;
import com.example.santo.practicum.RuntimeTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainMenuScene extends GameScene implements MongoAdapter {
    public static List<Fighter> generatedCharacters = new ArrayList<Fighter>();
    public static List<Equipment> generatedEquipment = new ArrayList<Equipment>();
    public static Bitmap bitmap;
    public static Bitmap palette1 = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_8888);
    public static Fighter EMPTY_CHARACTER;

    public static Team team = new Team(4);

    public static GameObject generatedObject;

    public static GameObject p1 = new GameObject(new Rect(-550, -250, -250, -550), palette1, 100);

    MediaPlayer musicPlayer;
    AudioAttributes attributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build();
    SoundPool soundEffects = new SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(attributes)
            .build();

    public static GsonBuilder gsonBuilder = new GsonBuilder();
    public static final String PREFERENCES = "PBPreferences";

    RuntimeTypeAdapterFactory<Fighter> typeFactory = RuntimeTypeAdapterFactory.of(Fighter.class, "type")
            .registerSubtype(Warrior.class, "Warrior")
            .registerSubtype(Rogue.class, "Rogue")
            .registerSubtype(Wizard.class, "Wizard")
            .registerSubtype(Cleric.class, "Cleric");

    SharedPreferences settings;
    SharedPreferences.Editor settingsEditor;

    public static final String API_KEY = "5nO2whQBSRciOaL4wtkLSGR9cusmjokA";
    public static final String DATABASE_NAME = "teamdata";

    public static String DEVICE_ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DEVICE_ID = Installation.id(this);

        settings = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        settingsEditor = settings.edit();

        //settingsEditor.clear();
        //settingsEditor.commit();

        gsonBuilder.registerTypeAdapterFactory(typeFactory);
        Gson gson = gsonBuilder.create();

        Type fightCollectionType = new TypeToken<List<Fighter>>(){}.getType();
        generatedCharacters = gson.fromJson(settings.getString("generatedCharacters", ""), fightCollectionType);
        Type equipCollectionType = new TypeToken<List<Equipment>>(){}.getType();
        generatedEquipment = gson.fromJson(settings.getString("generatedEquipment", ""), equipCollectionType);

        if (generatedCharacters == null) {
            generatedCharacters = new ArrayList<Fighter>();
        }
        else {
            for (Fighter fighter : generatedCharacters) {
                fighter.Init(this);
            }
        }

        if (generatedEquipment == null) {
            generatedEquipment = new ArrayList<Equipment>();
        }
        else {
            for (Equipment equipment : generatedEquipment) {
                equipment.Init(this);
            }
        }

        musicPlayer = MediaPlayer.create(this, R.raw.costadelsanto);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musicPlayer.start();

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        EMPTY_CHARACTER = new Warrior(new Rect(0, 0, 0, 0),
                BitmapFactory.decodeResource(this.getResources(), this.getResources().getIdentifier("drawable/empty", null, this.getPackageName())),
                0, 0, 0, 100, 1, 0, 0, 0);

        team.Init(EMPTY_CHARACTER);

        GameObject backgroundImage = new GameObject(new Rect(-540, 960, 540, -960), "drawable/argyle", 1);
        gameObjects.add(backgroundImage);

        GameObject titleBackground = new GameObject(new Rect(-300, 700, 300, 500), "drawable/btnbackground", 90);
        TextObject txtTitle = new TextObject(new Rect(-150, 650, 150, 550), "Photo Battler", 100, TextAlign.center);
        txtTitle.ScaleTo(600, 200);
        gameObjects.add(titleBackground);
        gameObjects.add(txtTitle);

        GameButton btnFight = new GameButton(new Rect(-150, 350, 150, 250), "drawable/btnbackground", 90);
        TextObject btnFightText = new TextObject(new Rect(-150, 350, 150, 250), "Fight!", 100, TextAlign.center);
        GameButton btnGeneratePhoto = new GameButton(new Rect(-150, 50, 150, -50), "drawable/btnbackground", 90);
        TextObject btnGenerateText = new TextObject(new Rect(-150, 50, 150, -50), "Generate Content", 100, TextAlign.center);
        GameButton btnViewEdit = new GameButton(new Rect(-150, -250, 150, -350), "drawable/btnbackground", 90);
        TextObject btnViewText = new TextObject(new Rect(-150, -250, 150, -350), "View/Edit Fighters", 100, TextAlign.center);
        gameObjects.add(btnFight);
        gameObjects.add(btnFightText);
        gameObjects.add(btnGeneratePhoto);
        gameObjects.add(btnGenerateText);
        gameObjects.add(btnViewEdit);
        gameObjects.add(btnViewText);

        btnFight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightSelectScene.class);
                startActivity(i);
            }
        });

        btnGeneratePhoto.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PhotoAccess.performFileSearch(MainMenuScene.this);
            }
        });

        btnViewEdit.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewEditScene.class);
                startActivity(i);
            }
        });


        GameButton btnSave = new GameButton(new Rect(-350, -550, -50, -650), "drawable/btnbackground", 90);
        TextObject btnSaveText = new TextObject(new Rect(-350, -550, -50, -650), "Save", 100, TextAlign.center);
        gameObjects.add(btnSave);
        gameObjects.add(btnSaveText);

        btnSave.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gsonBuilder.registerTypeAdapterFactory(typeFactory);
                Gson innerGson = gsonBuilder.create();
                String genChars = innerGson.toJson(generatedCharacters);
                String genEquip = innerGson.toJson(generatedEquipment);
                settingsEditor.putString("generatedCharacters", genChars);
                settingsEditor.putString("generatedEquipment", genEquip);
                settingsEditor.commit();
            }
        });

        GameButton btnDelete = new GameButton(new Rect(50, -550, 350, -650), "drawable/btnbackground", 90);
        TextObject btnDeleteText = new TextObject(new Rect(50, -550, 350, -650), "Delete", 100, TextAlign.center);
        gameObjects.add(btnDelete);
        gameObjects.add(btnDeleteText);

        btnDelete.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                settingsEditor.clear();
                settingsEditor.commit();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        musicPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        musicPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        musicPlayer.release();
        soundEffects.release();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == PhotoAccess.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    bitmap = PhotoAccess.getBitmapFromUri(uri, this);
                    int[] colourInfo = PhotoGeneration.Generate(bitmap);

                    palette1 = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_8888);

                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 30; j++) {
                            palette1.setPixel(i, j, colourInfo[3]);
                        }
                    }

                    for (int i = 10; i < 20; i++) {
                        for (int j = 0; j < 30; j++) {
                            palette1.setPixel(i, j, colourInfo[4]);
                        }
                    }

                    for (int i = 20; i < 30; i++) {
                        for (int j = 0; j < 30; j++) {
                            palette1.setPixel(i, j, colourInfo[5]);
                        }
                    }

                    p1.generatedSprites[0] = palette1;

                    if (colourInfo[0] % 2 == 0) {
                        Fighter character;
                        switch (colourInfo[1] % 4) {
                            case 0:
                                character = new Warrior(new Rect(-75, 100, 75, -100), palette1, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                                break;
                            case 1:
                                character = new Rogue(new Rect(-75, 100, 75, -100), palette1, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                                break;
                            case 2:
                                character = new Wizard(new Rect(-75, 100, 75, -100), palette1, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                                break;
                            case 3:
                                character = new Cleric(new Rect(-75, 100, 75, -100), palette1, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                                break;
                            default:
                                character = new Warrior(new Rect(-75, 100, 75, -100), palette1, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                                break;
                        }
                        character.Init(this);
                        generatedCharacters.add(character);

                        Intent i = new Intent(getApplicationContext(), GenerationResultsScene.class);
                        i.putExtra("typeFlag", true);
                        generatedObject = character;
                        startActivity(i);
                    }
                    else {
                        Equipment equipment = new Equipment(new Rect(-50, 50, 50, -50), palette1, 90, colourInfo[0], colourInfo[1], colourInfo[2], colourInfo[3], colourInfo[4], colourInfo[5]);
                        equipment.Init(this);
                        generatedEquipment.add(equipment);

                        Intent i = new Intent(getApplicationContext(), GenerationResultsScene.class);
                        i.putExtra("typeFlag", false);
                        generatedObject = equipment;
                        startActivity(i);
                    }
                }
                catch (IOException e) {

                }
            }
        }
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
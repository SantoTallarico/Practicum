package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.Enums.CharacterClassHelper;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.Enums.TextAlign;
import com.example.santo.practicum.GameObjects.Equipment;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.TextObject;
import com.example.santo.practicum.PB_GLSurfaceView;

public class GenerationResultsScene extends GameScene {
    TextObject txtClass, txtHitPoints, txtAttack, txtDefence, txtMagicDefence, txtSpeed;
    GameObject displayedObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        GameObject gotBackground = new GameObject(new Rect(-100, 800, 100, 700), "drawable/btnbackground", 90);
        TextObject txtGot = new TextObject(new Rect(-100, 800, 100, 700), "You Got...", 100, TextAlign.center);
        gameObjects.add(gotBackground);
        gameObjects.add(txtGot);

        GameObject statsBackground = new GameObject(new Rect(100, 700, 500, -600), "drawable/btnbackground", 10);
        gameObjects.add(statsBackground);

        Intent i = getIntent();
        final boolean isFighter = i.getBooleanExtra("typeFlag", true);

        if (isFighter) {
            Fighter generatedCharacter = (Fighter)MainMenuScene.generatedObject;
            txtClass = new TextObject(new Rect(150, 600, 450, 500), "Class: " + CharacterClassHelper.ToString(generatedCharacter.charClass), 100, TextAlign.right);
            txtHitPoints = new TextObject(new Rect(150, 400, 450, 300), "Hit Points: " + generatedCharacter.GetStat(Stats.hitPoints), 100, TextAlign.right);
            txtAttack = new TextObject(new Rect(150, 200, 450, 100), "Attack: " + generatedCharacter.GetStat(Stats.attack), 100, TextAlign.right);
            txtDefence = new TextObject(new Rect(150, 0, 450, -100), "Defence: " + generatedCharacter.GetStat(Stats.defence), 100, TextAlign.right);
            txtMagicDefence = new TextObject(new Rect(150, -200, 450, -300), "Magic Defence: " + generatedCharacter.GetStat(Stats.magicDefence), 100, TextAlign.right);
            txtSpeed = new TextObject(new Rect(150, -400, 450, -500), "Speed: " + generatedCharacter.GetStat(Stats.speed), 100, TextAlign.right);
            displayedObject = generatedCharacter;
        }
        else {
            Equipment generatedEquipment = (Equipment)MainMenuScene.generatedObject;
            txtClass = new TextObject(new Rect(150, 600, 450, 500), "Type: " + generatedEquipment.type, 100, TextAlign.right);
            txtHitPoints = new TextObject(new Rect(150, 400, 450, 300), "Hit Points: " + generatedEquipment.modHitPoints, 100, TextAlign.right);
            txtAttack = new TextObject(new Rect(150, 200, 450, 100), "Attack: " + generatedEquipment.modAttack, 100, TextAlign.right);
            txtDefence = new TextObject(new Rect(150, 0, 450, -100), "Defence: " + generatedEquipment.modDefence, 100, TextAlign.right);
            txtMagicDefence = new TextObject(new Rect(150, -200, 450, -300), "Magic Defence: " + generatedEquipment.modMagicDefence, 100, TextAlign.right);
            txtSpeed = new TextObject(new Rect(150, -400, 450, -500), "Speed: " + generatedEquipment.modSpeed, 100, TextAlign.right);
            displayedObject = generatedEquipment;
        }

        //displayedObject.ScaleTo(300, 300);
        displayedObject.TranslateTo(-250, 0);
        gameObjects.add(displayedObject);
        gameObjects.add(txtClass);
        gameObjects.add(txtHitPoints);
        gameObjects.add(txtAttack);
        gameObjects.add(txtDefence);
        gameObjects.add(txtMagicDefence);
        gameObjects.add(txtSpeed);

        GameButton btnOK = new GameButton(new Rect(-100, -350, 100, -450), "drawable/btnbackground", 90);
        TextObject txtOK = new TextObject(new Rect(-100, -350, 100, -450), "OK", 100, TextAlign.center);
        gameObjects.add(btnOK);
        gameObjects.add(txtOK);

        btnOK.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
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

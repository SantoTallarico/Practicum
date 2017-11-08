package com.example.santo.practicum.Scenes;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.Enums.CharacterClassHelper;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.Enums.TextAlign;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.TextObject;
import com.example.santo.practicum.PB_GLSurfaceView;

public class EditScene extends GameScene {
    Fighter selectedCharacter;

    TextObject txtClass, txtHitPoints, txtAttack, txtDefence, txtMagicDefence, txtSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        selectedCharacter = ViewEditScene.selectedCharacter;

        gameObjects.add(selectedCharacter);

        GameObject statsBackground = new GameObject(new Rect(100, 700, 500, -600), "drawable/btnbackground", 10);
        gameObjects.add(statsBackground);

        txtClass = new TextObject(new Rect(100, 600, 500, 500), "Class: " + CharacterClassHelper.ToString(selectedCharacter.charClass), 100, TextAlign.right);
        txtHitPoints = new TextObject(new Rect(100, 400, 500, 300), "Hit Points: " + selectedCharacter.GetStat(Stats.hitPoints), 100, TextAlign.right);
        txtAttack = new TextObject(new Rect(100, 200, 500, 100), "Attack: " + selectedCharacter.GetStat(Stats.attack), 100, TextAlign.right);
        txtDefence = new TextObject(new Rect(100, 0, 500, -100), "Defence: " + selectedCharacter.GetStat(Stats.defence), 100, TextAlign.right);
        txtMagicDefence = new TextObject(new Rect(100, -200, 500, -300), "Magic Defence: " + selectedCharacter.GetStat(Stats.magicDefence), 100, TextAlign.right);
        txtSpeed = new TextObject(new Rect(100, -400, 500, -500), "Speed: " + selectedCharacter.GetStat(Stats.speed), 100, TextAlign.right);
        gameObjects.add(txtClass);
        gameObjects.add(txtHitPoints);
        gameObjects.add(txtAttack);
        gameObjects.add(txtDefence);
        gameObjects.add(txtMagicDefence);
        gameObjects.add(txtSpeed);

        GameButton btnEquipWeapon = new GameButton(new Rect(-450, 350, -150, 250), "drawable/btnbackground", 90);
        TextObject txtEquipWeapon = new TextObject(new Rect(-450, 350, -150, 250), "Equip Weapon", 100, TextAlign.center);
        GameButton btnEquipArmor = new GameButton(new Rect(-450, 150, -150, 50), "drawable/btnbackground", 90);
        TextObject txtEquipArmor = new TextObject(new Rect(-450, 150, -150, 50), "Equip Armor", 100, TextAlign.center);
        gameObjects.add(btnEquipWeapon);
        gameObjects.add(txtEquipWeapon);
        gameObjects.add(btnEquipArmor);
        gameObjects.add(txtEquipArmor);

        btnEquipWeapon.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EquipScene.class);
                i.putExtra("typeFlag", true);
                startActivity(i);
            }
        });

        btnEquipArmor.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EquipScene.class);
                i.putExtra("typeFlag", false);
                startActivity(i);
            }
        });
    }

    public void UpdateText() {
        txtClass.SetText("Class: " + CharacterClassHelper.ToString(selectedCharacter.charClass), TextAlign.right);
        txtHitPoints.SetText("Hit Points: " + selectedCharacter.GetStat(Stats.hitPoints), TextAlign.right);
        txtAttack.SetText("Attack: " + selectedCharacter.GetStat(Stats.attack), TextAlign.right);
        txtDefence.SetText("Defence: " + selectedCharacter.GetStat(Stats.defence), TextAlign.right);
        txtMagicDefence.SetText("Magic Defence: " + selectedCharacter.GetStat(Stats.magicDefence), TextAlign.right);
        txtSpeed.SetText("Speed: " + selectedCharacter.GetStat(Stats.speed), TextAlign.right);

        glView.AddTexture(txtClass);
        glView.AddTexture(txtHitPoints);
        glView.AddTexture(txtAttack);
        glView.AddTexture(txtDefence);
        glView.AddTexture(txtMagicDefence);
        glView.AddTexture(txtSpeed);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

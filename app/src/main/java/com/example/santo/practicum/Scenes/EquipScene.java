package com.example.santo.practicum.Scenes;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santo on 11/8/2017.
 */

public class EquipScene extends GameScene {
    Fighter selectedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        selectedCharacter = ViewEditScene.selectedCharacter;

        final boolean isWeapon = getIntent().getBooleanExtra("typeFlag", true);

        List<Equipment> displayedEquipment = new ArrayList<Equipment>();

        if (isWeapon) {
            for (Equipment equipment : MainMenuScene.generatedEquipment) {
                if (equipment.type == selectedCharacter.weaponType && equipment.inUse == false) {
                    displayedEquipment.add(equipment);
                }
            }
        }
        else {
            for (Equipment equipment : MainMenuScene.generatedEquipment) {
                if (equipment.type == selectedCharacter.armorType && equipment.inUse == false) {
                    displayedEquipment.add(equipment);
                }
            }
        }

        int i = 0;

        for (final Equipment equipment : displayedEquipment) {
            GameButton statsBackground = new GameButton(new Rect(-500, 700 - i * 300, 500, 400 - i * 300), "drawable/btnbackground", 10);
            gameObjects.add(statsBackground);

            statsBackground.SetOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (isWeapon) {
                        if (selectedCharacter.weapon != null) {
                            selectedCharacter.weapon.inUse = false;
                        }
                        equipment.inUse = true;
                        selectedCharacter.weapon = equipment;
                        selectedCharacter.ApplyEquipment();
                    }
                    else {
                        if (selectedCharacter.armor != null) {
                            selectedCharacter.armor.inUse = false;
                        }
                        equipment.inUse = true;
                        selectedCharacter.armor = equipment;
                        selectedCharacter.ApplyEquipment();
                    }

                    finish();
                }
            });

            TextObject txtType = new TextObject(new Rect(-400, 700 - i * 300, 0, 600 - i * 300), "Type: " + equipment.type, 100, TextAlign.right);
            TextObject txtHitPoints = new TextObject(new Rect(-400, 600 - i * 300, 0, 500 - i * 300), "Hit Points: " + equipment.modHitPoints, 100, TextAlign.right);
            TextObject txtAttack = new TextObject(new Rect(-400, 500 - i * 300, 0, 400 - i * 300), "Attack: " + equipment.modAttack, 100, TextAlign.right);
            TextObject txtDefence = new TextObject(new Rect(0, 700 - i * 300, 400, 600 - i * 300), "Defence: " + equipment.modDefence, 100, TextAlign.right);
            TextObject txtMagicDefence = new TextObject(new Rect(0, 600 - i * 300, 400, 500 - i * 300), "Magic Defence: " + equipment.modMagicDefence, 100, TextAlign.right);
            TextObject txtSpeed = new TextObject(new Rect(0, 500 - i * 300, 400, 400 - i * 300), "Speed: " + equipment.modSpeed, 100, TextAlign.right);
            gameObjects.add(txtType);
            gameObjects.add(txtHitPoints);
            gameObjects.add(txtAttack);
            gameObjects.add(txtDefence);
            gameObjects.add(txtMagicDefence);
            gameObjects.add(txtSpeed);

            i++;
        }
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

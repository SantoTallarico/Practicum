package com.example.santo.practicum.Scenes;

import android.graphics.Rect;
import android.os.Bundle;

import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.TextObject;
import com.example.santo.practicum.PB_GLSurfaceView;

public class EditScene extends GameScene {
    Fighter selectedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        selectedCharacter = ViewEditScene.selectedCharacter;

        gameObjects.add(selectedCharacter);

        GameObject statsBackground = new GameObject(new Rect(200, 500, 300, -600), "drawable/btnbackground", 10);
        gameObjects.add(statsBackground);

        TextObject txtHitPoints = new TextObject(new Rect(200, 400, 300, 300), "" + selectedCharacter.GetStat(Stats.hitPoints), 100);
        TextObject txtAttack = new TextObject(new Rect(200, 200, 300, 100), "" + selectedCharacter.GetStat(Stats.attack), 100);
        TextObject txtDefence = new TextObject(new Rect(200, 0, 300, -100), "" + selectedCharacter.GetStat(Stats.defence), 100);
        TextObject txtMagicDefence = new TextObject(new Rect(200, -200, 300, -300), "" + selectedCharacter.GetStat(Stats.magicDefence), 100);
        TextObject txtSpeed = new TextObject(new Rect(200, -400, 300, -500), "" + selectedCharacter.GetStat(Stats.speed), 100);
        gameObjects.add(txtHitPoints);
        gameObjects.add(txtAttack);
        gameObjects.add(txtDefence);
        gameObjects.add(txtMagicDefence);
        gameObjects.add(txtSpeed);
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

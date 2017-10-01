package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Santo on 9/28/2017.
 */

public class Warrior extends PB_Character {
    private Map<Stats, Integer> baseStats = new HashMap<Stats, Integer>() {
        {
            put(Stats.hitPoints, 100);
            put(Stats.attack, 50);
            put(Stats.defence, 50);
            put(Stats.magicDefence, 10);
            put(Stats.speed, 30);
        }
    };

    public Warrior(Rect d, Bitmap sprite, int layer, int red, int green, int blue) {
        super(d, sprite, layer);
        charClass = CharacterClass.warrior;

        InitializeStats(red, green, blue);
    }

    private void InitializeStats(int red, int green, int blue) {
        int modHitPoints;

        genStatMods.get(red % 24);

        maxHitPoints = baseStats.get(Stats.hitPoints);
        attack = baseStats.get(Stats.attack);
        defence = baseStats.get(Stats.defence);
        magicDefence = baseStats.get(Stats.magicDefence);
        speed = baseStats.get(Stats.speed);
    }

    public void Fight() {

    }

    public void LevelUp() {

    }
}

package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Santo on 7/20/2017.
 */

enum Stats {
    hitPoints,
    attack,
    defence,
    magicDefence,
    speed
}

enum CharacterClass {
    warrior,
    rogue,
    wizard,
    cleric
}

public abstract class PB_Character extends GameObject {
    String name;
    CharacterClass charClass;
    int level;
    int maxHitPoints, tempMaxHitPoints, tempHitPoints, hitPointsGrowth;
    int attack, tempAttack, attackGrowth;
    int defence, tempDefence, defenceGrowth;
    int magicDefence, tempMagicDefence, magicDefenceGrowth;
    int speed, tempSpeed, speedGrowth;

    Equipment weapon;
    Equipment armor;

    boolean isAlive = true;

    //First stat is raised, second stat is lowered. If stats are the same, no change
    protected List<Pair<Stats, Stats>> genStatMods = new ArrayList<Pair<Stats, Stats>>() {
        {
            add(new Pair<Stats, Stats>(Stats.hitPoints, Stats.hitPoints));
            add(new Pair<Stats, Stats>(Stats.hitPoints, Stats.attack));
            add(new Pair<Stats, Stats>(Stats.hitPoints, Stats.defence));
            add(new Pair<Stats, Stats>(Stats.hitPoints, Stats.magicDefence));
            add(new Pair<Stats, Stats>(Stats.hitPoints, Stats.speed));

            add(new Pair<Stats, Stats>(Stats.attack, Stats.hitPoints));
            add(new Pair<Stats, Stats>(Stats.attack, Stats.attack));
            add(new Pair<Stats, Stats>(Stats.attack, Stats.defence));
            add(new Pair<Stats, Stats>(Stats.attack, Stats.magicDefence));
            add(new Pair<Stats, Stats>(Stats.attack, Stats.speed));

            add(new Pair<Stats, Stats>(Stats.defence, Stats.hitPoints));
            add(new Pair<Stats, Stats>(Stats.defence, Stats.attack));
            add(new Pair<Stats, Stats>(Stats.defence, Stats.defence));
            add(new Pair<Stats, Stats>(Stats.defence, Stats.magicDefence));
            add(new Pair<Stats, Stats>(Stats.defence, Stats.speed));

            add(new Pair<Stats, Stats>(Stats.magicDefence, Stats.hitPoints));
            add(new Pair<Stats, Stats>(Stats.magicDefence, Stats.attack));
            add(new Pair<Stats, Stats>(Stats.magicDefence, Stats.defence));
            add(new Pair<Stats, Stats>(Stats.magicDefence, Stats.magicDefence));
            add(new Pair<Stats, Stats>(Stats.magicDefence, Stats.speed));

            add(new Pair<Stats, Stats>(Stats.speed, Stats.hitPoints));
            add(new Pair<Stats, Stats>(Stats.speed, Stats.attack));
            add(new Pair<Stats, Stats>(Stats.speed, Stats.defence));
            add(new Pair<Stats, Stats>(Stats.speed, Stats.magicDefence));
            add(new Pair<Stats, Stats>(Stats.speed, Stats.speed));
        }
    };

    public PB_Character(Rect d, Bitmap sprite, int layer) {
        super(d, sprite, layer);
        level = 1;
    }

    public void ApplyEquipment() {
        tempMaxHitPoints = maxHitPoints + weapon.modHitPoints + armor.modHitPoints;
        tempAttack = attack + weapon.modAttack + armor.modAttack;
        tempDefence = defence + weapon.modDefence + armor.modDefence;
        tempMagicDefence = magicDefence + weapon.modMagicDefence + armor.modMagicDefence;
        tempSpeed = speed + weapon.modSpeed + armor.modSpeed;
    }

    public void ModifyStat(Stats stat, int modValue) {
        switch(stat) {
            case hitPoints:
                tempHitPoints += modValue;
                if (tempHitPoints <= 0) {
                    Death();
                }
                else if (tempHitPoints > tempMaxHitPoints) {
                    tempHitPoints = tempMaxHitPoints;
                }
                break;
            case attack:
                tempAttack += modValue;
                break;
            case defence:
                tempDefence += modValue;
                break;
            case magicDefence:
                tempMagicDefence += modValue;
                break;
            case speed:
                tempSpeed += modValue;
                break;
        }
    }

    public void Death() {
        isAlive = false;
    }

    public void Revive() {
        isAlive = true;
    }

    public abstract void LevelUp();
    public abstract void Fight();
}

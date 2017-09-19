package com.example.santo.practicum.GameObjects;

import android.graphics.Rect;

/**
 * Created by Santo on 7/20/2017.
 */

enum Stats {
    hitPoints,
    strength,
    defence,
    intelligence,
    wisdom,
    speed
}

public class PB_Character extends GameObject {
    String name;
    int level;
    int maxHitPoints, tempMaxHitPoints, tempHitPoints;
    int strength, tempStrength;
    int defence, tempDefence;
    int intelligence, tempIntelligence;
    int wisdom, tempWisdom;
    int speed, tempSpeed;

    Equipment weapon;
    Equipment armor;

    boolean isAlive = true;

    public PB_Character(Rect d, String spriteLoc, int layer, int red, int green, int blue) {
        super(d, spriteLoc, layer);
        level = 1;
    }

    public void ApplyEquipment() {
        tempMaxHitPoints = maxHitPoints + weapon.modHitPoints + armor.modHitPoints;
        tempStrength = strength + weapon.modStrength + armor.modStrength;
        tempDefence = defence + weapon.modDefence + armor.modDefence;
        tempIntelligence = intelligence + weapon.modIntelligence + armor.modIntelligence;
        tempWisdom = wisdom + weapon.modWisdom + armor.modWisdom;
        tempSpeed = speed + weapon.modSpeed + armor.modSpeed;
    }

    public void ModifyStat(Stats stat, int modValue) {
        switch(stat) {
            case hitPoints:
                tempHitPoints += modValue;
                if (tempHitPoints <= 0) {
                    Death();
                }
                break;
            case strength:
                tempStrength += modValue;
                break;
            case defence:
                tempDefence += modValue;
                break;
            case intelligence:
                tempIntelligence += modValue;
                break;
            case wisdom:
                tempWisdom += modValue;
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
}

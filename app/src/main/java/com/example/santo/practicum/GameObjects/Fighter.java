package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.graphics.ColorUtils;
import android.util.Pair;

import com.example.santo.practicum.Enums.CharacterClass;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.PhotoGeneration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Santo on 7/20/2017.
 */

public abstract class Fighter extends GameObject implements Serializable {
    String name;
    public CharacterClass charClass;
    int level;
    int experience;
    int maxHitPoints, tempMaxHitPoints, tempHitPoints, hitPointsGrowth;
    int attack, tempAttack, attackGrowth;
    int defence, tempDefence, defenceGrowth;
    int magicDefence, tempMagicDefence, magicDefenceGrowth;
    int speed, tempSpeed, speedGrowth;

    Equipment weapon;
    Equipment armor;

    boolean isPlayerControlled = true;
    boolean isAlive = true;
    public transient Bitmap tileIcon;
    public int palette1, palette2, palette3;

    public static final int MAX_LEVEL = 20;

    //First stat is raised, second stat is lowered. If stats are the same, no change
    protected transient final static List<Pair<Stats, Stats>> genStatMods = new ArrayList<Pair<Stats, Stats>>() {
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

    public static final transient int[] expTable = { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900 };

    public Fighter(Rect d, Bitmap sprite, int p1, int p2, int p3, int layer, int startingLevel) {
        super(d, sprite, layer);
        palette1 = p1;
        palette2 = p2;
        palette3 = p3;
        level = startingLevel;
        tileIcon = sprite;
    }

    public static Fighter RandomFighter() {
        Random r = new Random();
        int[] randColours = new int[32 * 32];
        for (int i = 0; i < 32 * 32; i++) {
            randColours[i] = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        }
        Bitmap b = Bitmap.createBitmap(randColours, 32, 32, Bitmap.Config.ARGB_8888);
        int[] colourInfo = PhotoGeneration.Generate(b);
        Fighter random = new Warrior(new Rect(-50, 50, 50, -50), b, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
        return random;
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

    public int GetStat(Stats stat) {
        switch(stat) {
            case hitPoints:
                return tempHitPoints;
            case attack:
                return tempAttack;
            case defence:
                return tempDefence;
            case magicDefence:
                return tempMagicDefence;
            case speed:
                return tempSpeed;
            default:
                //should not get here
                return 0;
        }
    }

    public void Death() {
        isAlive = false;
    }

    public void Revive() {
        isAlive = true;
    }

    public void LevelUp() {
        if (level < MAX_LEVEL) {
            level++;
            maxHitPoints += hitPointsGrowth;
            attack += attackGrowth;
            defence += defenceGrowth;
            magicDefence += magicDefenceGrowth;
            speed += speedGrowth;

            ApplyEquipment();
        }
    }

    public void Update(long elapsed) {

    }

    public abstract void Fight();
}
package com.example.santo.practicum.GameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Pair;

import com.example.santo.practicum.Enums.CharacterClass;
import com.example.santo.practicum.Enums.EquipmentType;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.FightActions.DamageAction;
import com.example.santo.practicum.FightActions.FightAction;
import com.example.santo.practicum.FightActions.GuardAction;
import com.example.santo.practicum.FightActions.GuardAllyAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Santo on 9/28/2017.
 */

public class Warrior extends Fighter implements Serializable {
    private final static Map<Stats, Integer> baseStats = new HashMap<Stats, Integer>() {
        {
            put(Stats.hitPoints, 50);
            put(Stats.attack, 40);
            put(Stats.defence, 20);
            put(Stats.magicDefence, 10);
            put(Stats.speed, 20);
        }
    };

    private final static Map<Stats, Integer> statGrowths = new HashMap<Stats, Integer>() {
        {
            put(Stats.hitPoints, 7);
            put(Stats.attack, 2);
            put(Stats.defence, 2);
            put(Stats.magicDefence, 1);
            put(Stats.speed, 2);
        }
    };

    public Warrior(Rect d, Bitmap sprite, int p1, int p2, int p3, int layer, int startingLevel, int red, int green, int blue) {
        super(d, sprite, p1, p2, p3, layer, startingLevel);
        charClass = CharacterClass.warrior;

        weaponType = EquipmentType.sword;
        armorType = EquipmentType.armor;

        InitializeStats(red, green, blue);

        for (int i = 1; i < startingLevel; i++) {
            LevelUp();
        }
        level = startingLevel;

        type = this.getClass().getSimpleName();
    }

    @Override
    public void Init(Context context) {
        textureIDs = new int[3];
        generatedSprites = new Bitmap[3];
        generatedSprites[0] = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("drawable/warriorwalkpalette", null, context.getPackageName()));
        generatedSprites[1] = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("drawable/warriordead", null, context.getPackageName()));
        generatedSprites[2] = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("drawable/warriorstunned", null, context.getPackageName()));
        super.Init(context);

        type = this.getClass().getSimpleName();

        weaponType = EquipmentType.sword;
        armorType = EquipmentType.armor;

        fightActions = new ArrayList<FightAction>();
        fightActions.add(new DamageAction("Attack", this));
        fightActions.add(new GuardAction("Defend", this));
        fightActions.add(new GuardAllyAction("Guard Ally", this));
    }

    private void InitializeStats(int red, int green, int blue) {
        Pair<Stats, Stats> statMods = genStatMods.get(blue % 24);

        maxHitPoints = baseStats.get(Stats.hitPoints) + (statMods.first == Stats.hitPoints ? 10 : 0) + (statMods.second == Stats.hitPoints ? -10 : 0);
        attack = baseStats.get(Stats.attack) + (statMods.first == Stats.attack ? 5 : 0) + (statMods.second == Stats.attack ? -5 : 0);
        defence = baseStats.get(Stats.defence) + (statMods.first == Stats.defence ? 5 : 0) + (statMods.second == Stats.defence ? -5 : 0);
        magicDefence = baseStats.get(Stats.magicDefence) + (statMods.first == Stats.magicDefence ? 5 : 0) + (statMods.second == Stats.magicDefence ? -5 : 0);
        speed = baseStats.get(Stats.speed) + (statMods.first == Stats.speed ? 5 : 0) + (statMods.second == Stats.speed ? -5 : 0);

        ApplyEquipment();
    }

    @Override
    public void LevelUp() {
        if (level < MAX_LEVEL) {
            level++;
            maxHitPoints += statGrowths.get(Stats.hitPoints);
            attack += statGrowths.get(Stats.attack);
            defence += statGrowths.get(Stats.defence);
            magicDefence += statGrowths.get(Stats.magicDefence);
            speed += statGrowths.get(Stats.speed);

            ApplyEquipment();
        }
    }

    @Override
    public int AIChooseAction() {
        Random r = new Random();
        switch (r.nextInt(4)) {
            case 0:
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                return 0;
        }
    }
}

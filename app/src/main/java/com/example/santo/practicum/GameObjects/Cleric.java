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
import com.example.santo.practicum.FightActions.HealAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Santo on 11/12/2017.
 */

public class Cleric extends Fighter implements Serializable{
    private transient final static Map<Stats, Integer> baseStats = new HashMap<Stats, Integer>() {
        {
            put(Stats.hitPoints, 45);
            put(Stats.attack, 25);
            put(Stats.defence, 20);
            put(Stats.magicDefence, 30);
            put(Stats.speed, 20);
        }
    };

    public Cleric(Rect d, Bitmap sprite, int p1, int p2, int p3, int layer, int startingLevel, int red, int green, int blue) {
        super(d, sprite, p1, p2, p3, layer, startingLevel);
        charClass = CharacterClass.cleric;

        weaponType = EquipmentType.mace;
        armorType = EquipmentType.robe;

        InitializeStats(red, green, blue);

        for (int i = 1; i < startingLevel; i++) {
            LevelUp();
        }

        type = this.getClass().getSimpleName();
    }

    @Override
    public void Init(Context context) {
        generatedSprite = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("drawable/clericwalkpalette", null, context.getPackageName()));
        super.Init(context);

        type = this.getClass().getSimpleName();

        fightActions = new ArrayList<FightAction>();
        fightActions.add(new DamageAction("Attack", this));
        fightActions.add(new GuardAction("Defend", this));
        fightActions.add(new HealAction("Heal Ally", this));
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
}
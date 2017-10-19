package com.example.santo.practicum.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Pair;

import com.example.santo.practicum.Enums.CharacterClass;
import com.example.santo.practicum.Enums.Stats;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Santo on 9/28/2017.
 */

public class Warrior extends Fighter implements Serializable {
    private transient final static Map<Stats, Integer> baseStats = new HashMap<Stats, Integer>() {
        {
            put(Stats.hitPoints, 100);
            put(Stats.attack, 50);
            put(Stats.defence, 50);
            put(Stats.magicDefence, 10);
            put(Stats.speed, 30);
        }
    };

    public Warrior(Rect d, Bitmap sprite, int layer, int startingLevel, int red, int green, int blue) {
        super(d, sprite, layer, startingLevel);
        charClass = CharacterClass.warrior;

        InitializeStats(red, green, blue);

        for (int i = 1; i < startingLevel; i++) {
            LevelUp();
        }
    }

    private void InitializeStats(int red, int green, int blue) {
        Pair<Stats, Stats> statMods = genStatMods.get(red % 24);

        maxHitPoints = baseStats.get(Stats.hitPoints) + (statMods.first == Stats.hitPoints ? 10 : 0) + (statMods.second == Stats.hitPoints ? -10 : 0);
        attack = baseStats.get(Stats.attack) + (statMods.first == Stats.attack ? 5 : 0) + (statMods.second == Stats.attack ? -5 : 0);
        defence = baseStats.get(Stats.defence) + (statMods.first == Stats.defence ? 5 : 0) + (statMods.second == Stats.defence ? -5 : 0);
        magicDefence = baseStats.get(Stats.magicDefence) + (statMods.first == Stats.magicDefence ? 5 : 0) + (statMods.second == Stats.magicDefence ? -5 : 0);
        speed = baseStats.get(Stats.speed) + (statMods.first == Stats.speed ? 5 : 0) + (statMods.second == Stats.speed ? -5 : 0);
    }

    public void Fight() {

    }
/*
    //Parcelable methods
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
    }

    public static final Parcelable.Creator<Warrior> CREATOR
            = new Parcelable.Creator<Warrior>() {
        public Warrior createFromParcel(Parcel in) {
            return new Warrior(in);
        }

        public Warrior[] newArray(int size) {
            return new Warrior[size];
        }
    };*/
}

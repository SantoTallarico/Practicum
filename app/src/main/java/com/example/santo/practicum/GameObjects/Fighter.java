package com.example.santo.practicum.GameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Pair;

import com.example.santo.practicum.Enums.CharacterClass;
import com.example.santo.practicum.Enums.EquipmentType;
import com.example.santo.practicum.Enums.SpriteState;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.FightActions.FightAction;
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
    transient int experience = 0;
    int maxHitPoints, attack, defence, magicDefence, speed;
    transient int tempMaxHitPoints, tempHitPoints, hitPointsGrowth,
            tempAttack, attackGrowth,
            tempDefence, defenceGrowth,
            tempMagicDefence, magicDefenceGrowth,
            tempSpeed, speedGrowth;

    public Equipment weapon;
    public Equipment armor;
    public transient EquipmentType weaponType;
    public transient EquipmentType armorType;

    public transient boolean isPlayerControlled = true;
    public transient boolean isAlive = true;
    public transient boolean isGuarding = false;
    public transient boolean isGuarded = false;
    public boolean isStunned = false;
    public transient Bitmap tileIcon;
    public int palette1, palette2, palette3;

    public transient static final int MAX_LEVEL = 20;

    public transient List<FightAction> fightActions = new ArrayList<FightAction>();

    public String type = this.getClass().getSimpleName();

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
        animates = true;
        textureIDs = new int[2];
    }

    public void Init(Context context) {
        generatedSprites[0] = ApplyPalette(generatedSprites[0]);
        generatedSprites[1] = ApplyPalette(generatedSprites[1]);
        tileIcon = Bitmap.createBitmap(generatedSprites[0], 0, 0, 84, 75);
        ApplyEquipment();

        isAlive = true;
        isGuarding = false;
        isGuarded = false;
        isPlayerControlled = true;
    }

    public static Fighter RandomFighter(Context context) {
        Random r = new Random();
        int[] randColours = new int[32 * 32];
        for (int i = 0; i < 32 * 32; i++) {
            randColours[i] = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        }
        Bitmap b = Bitmap.createBitmap(randColours, 32, 32, Bitmap.Config.ARGB_8888);
        int[] colourInfo = PhotoGeneration.Generate(b);
        Fighter random;
        switch (r.nextInt(4)) {
            case 0:
                random = new Warrior(new Rect(75, 100, -75, -100), b, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                break;
            case 1:
                random = new Rogue(new Rect(75, 100, -75, -100), b, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                break;
            case 2:
                random = new Wizard(new Rect(75, 100, -75, -100), b, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                break;
            case 3:
                random = new Cleric(new Rect(75, 100, -75, -100), b, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                break;
            default:
                random = new Warrior(new Rect(75, 100, -75, -100), b, colourInfo[3], colourInfo[4], colourInfo[5], 100, 1, colourInfo[0], colourInfo[1], colourInfo[2]);
                break;
        }
        random.Init(context);
        for (int i = 0; i < random.generatedSprites.length; i++) {
            random.generatedSprites[i] = random.ApplyPalette(random.generatedSprites[i]);
        }
        random.isPlayerControlled = false;
        return random;
    }

    public Bitmap ApplyPalette(Bitmap bitmap) {
        Bitmap temp = bitmap.copy(bitmap.getConfig(), true);

        int w = temp.getWidth();
        int h = temp.getHeight();
        int[] pixels = new int[w * h];
        temp.getPixels(pixels, 0, w, 0, 0, w, h);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int colour = pixels[i + j * w];

                switch (colour) {
                    case 0xff7f7f7f:
                        pixels[i + j * w] = palette1;
                        break;
                    case 0xff3f3f3f:
                        pixels[i + j * w] = palette2;
                        break;
                    case 0xffbfbfbf:
                        pixels[i + j * w] = palette3;
                        break;
                }
            }
        }

        temp.setPixels(pixels, 0, w, 0, 0, w, h);

        return temp;
    }

    public void ApplyEquipment() {
        tempMaxHitPoints = maxHitPoints + (weapon == null ? 0 : weapon.modHitPoints) + (armor == null ? 0 : armor.modHitPoints);
        tempHitPoints = tempMaxHitPoints;
        tempAttack = attack + (weapon == null ? 0 : weapon.modAttack) + (armor == null ? 0 : armor.modAttack);
        tempDefence = defence + (weapon == null ? 0 : weapon.modDefence) + (armor == null ? 0 : armor.modDefence);
        tempMagicDefence = magicDefence + (weapon == null ? 0 : weapon.modMagicDefence) + (armor == null ? 0 : armor.modMagicDefence);
        tempSpeed = speed + (weapon == null ? 0 : weapon.modSpeed) + (armor == null ? 0 : armor.modSpeed);
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

    public void ResetStats() {
        isAlive = true;
        isGuarding = false;
        isGuarded = false;
        isStunned = false;
        tempHitPoints = tempMaxHitPoints;
    }

    public void Death() {
        isAlive = false;
        ChangeSprite(SpriteState.dead);
    }

    public void Guard() {
        isGuarding = true;
    }

    public void Guarded(Fighter protector) {
        isGuarded = true;
    }

    public void Stun() {
        isStunned = true;
    }

    public void ChangeSprite(SpriteState state) {
        switch (state) {
            case idle:
                currentTextureID = textureIDs[0];
                animates = true;
                ScaleTo(isPlayerControlled == true ? 150 : -150, 200);
                break;
            case dead:
                currentTextureID = textureIDs[1];
                animates = false;
                ScaleTo(isPlayerControlled == true ? 150 : -150200, 150);
                break;
            case stunned:
                animates = false;
                ScaleTo(isPlayerControlled == true ? 150 : -150, 200);
                break;
        }
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
}

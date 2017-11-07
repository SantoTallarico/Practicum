package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.Enums.CharacterClass;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.Enums.Stats;

/**
 * Created by Santo on 10/16/2017.
 */

public class DamageAction extends FightAction {

    public DamageAction(String n, Fighter u, Fighter t) {
        super(n, u, t, u.GetStat(Stats.speed));
    }

    public void ApplyAction() {
        Stats defenceType;
        if (user.charClass == CharacterClass.wizard) {
            defenceType = Stats.magicDefence;
        }
        else {
            defenceType = Stats.defence;
        }

        int damage = user.GetStat(Stats.attack) - target.GetStat(defenceType);

        if (damage < 1) {
            damage = 1;
        }

        target.ModifyStat(Stats.hitPoints, -damage);
    }
}

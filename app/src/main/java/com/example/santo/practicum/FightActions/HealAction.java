package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.Enums.Aiming;
import com.example.santo.practicum.Enums.CharacterClass;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.GameObjects.Fighter;

/**
 * Created by Santo on 10/16/2017.
 */

public class HealAction extends FightAction {
    public HealAction(String n, Fighter u) {
        super(n, u, u.GetStat(Stats.speed), Aiming.ally);
    }

    public void ApplyAction() {
        if (user.isAlive && !user.isStunned) {
            int heal = user.GetStat(Stats.magicDefence) / 2;

            target.ModifyStat(Stats.hitPoints, heal);
        }
    }
}

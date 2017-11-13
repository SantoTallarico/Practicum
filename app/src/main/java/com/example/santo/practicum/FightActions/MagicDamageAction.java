package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.Enums.Aiming;
import com.example.santo.practicum.Enums.CharacterClass;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.GameObjects.Fighter;

/**
 * Created by Santo on 10/16/2017.
 */

public class MagicDamageAction extends FightAction {
    public MagicDamageAction(String n, Fighter u) {
        super(n, u, u.GetStat(Stats.speed), Aiming.enemy);
    }

    public void ApplyAction() {
        if (user.isAlive && !user.isStunned) {
            int damage = user.GetStat(Stats.attack) - target.GetStat(Stats.magicDefence) / (target.isGuarding ? 2 : 1);

            if (damage < 1) {
                damage = 1;
            }

            target.ModifyStat(Stats.hitPoints, -damage);
        }
    }
}
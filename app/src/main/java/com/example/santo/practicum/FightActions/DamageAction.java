package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.Enums.Aiming;
import com.example.santo.practicum.Enums.CharacterClass;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.Enums.Stats;

/**
 * Created by Santo on 10/16/2017.
 */

public class DamageAction extends FightAction {
    public DamageAction(String n, Fighter u) {
        super(n, u, u.GetStat(Stats.speed), Aiming.enemy);
    }

    public void ApplyAction() {
        if (user.isAlive && !user.isStunned) {
            int damage;

            if (!target.isAlive) {
                ChooseRandomTarget();
            }

            if (target.isGuarded && target.protector.isAlive) {
                damage = (user.GetStat(Stats.attack) - target.protector.GetStat(Stats.defence)) / 2;
                target = target.protector;
            }
            else {
                damage = (user.GetStat(Stats.attack) - target.GetStat(Stats.defence)) / (target.isGuarding ? 2 : 1);
            }

            if (damage < 1) {
                damage = 1;
            }

            target.ModifyStat(Stats.hitPoints, -damage);
        }
    }
}

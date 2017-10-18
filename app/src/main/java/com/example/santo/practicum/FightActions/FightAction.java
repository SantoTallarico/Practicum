package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.GameObjects.PB_Character;

/**
 * Created by Santo on 10/16/2017.
 */

public abstract class FightAction {
    public PB_Character user;
    public PB_Character target;
    int actionCount;
    int priority;

    public FightAction(PB_Character u, PB_Character t, int count, int p) {
        user = u;
        target = t;
        actionCount = count;
        priority = p;
    }

    public abstract void ApplyAction();
}

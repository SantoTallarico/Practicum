package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.GameObjects.Fighter;

/**
 * Created by Santo on 10/16/2017.
 */

public abstract class FightAction {
    public Fighter user;
    public Fighter target;
    int actionCount;
    int priority;

    public FightAction(Fighter u, Fighter t, int count, int p) {
        user = u;
        target = t;
        actionCount = count;
        priority = p;
    }

    public abstract void ApplyAction();
}

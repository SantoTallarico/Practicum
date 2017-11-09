package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.Enums.Aiming;
import com.example.santo.practicum.GameObjects.Fighter;

/**
 * Created by Santo on 10/16/2017.
 */

public abstract class FightAction implements Comparable<FightAction> {
    public Fighter user;
    public Fighter target;
    public int priority;
    public String name;
    public Aiming aiming;

    public FightAction(String n, Fighter u, int p, Aiming a) {
        name = n;
        user = u;
        priority = p;
        aiming = a;
    }

    public void SetTarget(Fighter t) {
        target = t;
    }

    @Override
    public int compareTo(FightAction other) {
        return Integer.compare(this.priority, other.priority);
    }

    public abstract void ApplyAction();
}

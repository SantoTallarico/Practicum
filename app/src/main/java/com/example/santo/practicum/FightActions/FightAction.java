package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.GameObjects.Fighter;

/**
 * Created by Santo on 10/16/2017.
 */

public abstract class FightAction implements Comparable<FightAction> {
    public Fighter user;
    public Fighter target;
    public int priority;
    public String name;

    public FightAction(String n, Fighter u, Fighter t, int p) {
        name = n;
        user = u;
        target = t;
        priority = p;
    }

    @Override
    public int compareTo(FightAction other) {
        return Integer.compare(this.priority, other.priority);
    }

    public abstract void ApplyAction();
}

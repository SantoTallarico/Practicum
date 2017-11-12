package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.Enums.Aiming;
import com.example.santo.practicum.GameObjects.Fighter;

/**
 * Created by Santo on 11/12/2017.
 */

public class GuardAllyAction extends FightAction{
    public GuardAllyAction(String n, Fighter u) {
        super(n, u, 1000, Aiming.otherAlly);
    }

    public void ApplyAction() {
        if (user.isAlive) {
            user.isGuarding = true;
            target.Guarded(user);
        }
    }
}

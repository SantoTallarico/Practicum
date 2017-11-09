package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.Enums.Aiming;
import com.example.santo.practicum.Enums.CharacterClass;
import com.example.santo.practicum.Enums.Stats;
import com.example.santo.practicum.GameObjects.Fighter;

/**
 * Created by Santo on 11/8/2017.
 */

public class GuardAction extends FightAction {

    public GuardAction(String n, Fighter u) {
        super(n, u, 1000, Aiming.self);
    }

    public void ApplyAction() {
        if (user.isAlive) {
            user.isGuarding = true;
        }
    }
}

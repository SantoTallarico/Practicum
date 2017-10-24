package com.example.santo.practicum.GameObjects;

import android.graphics.Rect;

import com.example.santo.practicum.FightActions.FightAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santo on 10/16/2017.
 */

public class FightController extends GameObject {
    public List<Fighter> playerFighters = new ArrayList<Fighter>();
    public List<FightAction> queuedActions = new ArrayList<FightAction>();

    public FightController(List<Fighter> pFighters) {
        super(new Rect(0, 0, 0, 0), "", 0);
        playerFighters = pFighters;
    }

    void RunActions() {
        for (FightAction action : queuedActions) {
            action.ApplyAction();
        }
    }

    public void Update(long elapsed) {

    }
}

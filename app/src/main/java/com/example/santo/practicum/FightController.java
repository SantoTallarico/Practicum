package com.example.santo.practicum;

import android.graphics.Rect;

import com.example.santo.practicum.Enums.FightState;
import com.example.santo.practicum.FightActions.FightAction;
import com.example.santo.practicum.FightListener;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Santo on 10/16/2017.
 */

public class FightController {
    public List<Fighter> playerFighters = new ArrayList<Fighter>();
    public List<Fighter> enemyFighters = new ArrayList<Fighter>();
    public List<FightAction> queuedActions = new ArrayList<FightAction>();
    public List<FightAction> displayedActions = new ArrayList<FightAction>();

    public Fighter activeFighter;
    public int activeIndex = 0;
    public FightAction activeAction;

    public FightListener listener;

    public FightController(Team playerTeam, Team enemyTeam) {
        playerFighters = playerTeam.GetTeam();
        enemyFighters = enemyTeam.GetTeam();
    }

    public void AssignListener(FightListener l) {
        listener = l;
    }

    public void ListActions() {
        displayedActions = activeFighter.fightActions;
        listener.ListActions();
    }

    public void RunActions() {
        Collections.sort(queuedActions);

        for (FightAction action : queuedActions) {
            action.ApplyAction();
            listener.UpdateText();
        }

        queuedActions.clear();

        listener.UpdateState(FightState.selectingAction);
    }

    public void SetActiveAction(FightAction action) {
        activeAction = action;
    }

    public void AddAction(FightAction action, Fighter target) {
        action.target = target;
        queuedActions.add(action);
    }

    public void StartRound() {
        activeFighter = playerFighters.get(0);
        activeIndex = 0;
        ListActions();
    }

    public void NextFighter() {
        activeIndex++;

        if (activeIndex == 8) {
            RunActions();

            boolean isPlayerDefeated = true;
            for (Fighter f : playerFighters) {
                if (f.isAlive) {
                    isPlayerDefeated = false;
                    break;
                }
            }

            if (isPlayerDefeated == true) {
                listener.EndFight(false);
                return;
            }

            boolean isEnemyDefeated = true;
            for (Fighter f : enemyFighters) {
                if (f.isAlive) {
                    isEnemyDefeated = false;
                    break;
                }
            }

            if (isEnemyDefeated == true) {
                listener.EndFight(true);
                return;
            }

            StartRound();
            return;
        }

        if (activeIndex < 4) {
            activeFighter = playerFighters.get(activeIndex);
        }
        else {
            activeFighter = enemyFighters.get(activeIndex - 4);
        }

        if (activeFighter.isAlive) {
            ListActions();
            listener.UpdateState(FightState.selectingAction);
        }
        else if (activeFighter.isAlive == false && activeIndex == 7) {

        }
        else {
            NextFighter();
            return;
        }
    }
}

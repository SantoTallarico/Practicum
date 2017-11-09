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
import java.util.Random;

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

    public Random r = new Random();

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
        listener.UpdateState(FightState.actionsRunning);
        Collections.sort(queuedActions);

        for (FightAction action : queuedActions) {
            action.ApplyAction();
            listener.UpdateText();

            boolean isPlayerDefeated = true;
            for (Fighter f : playerFighters) {
                if (f.isAlive) {
                    isPlayerDefeated = false;
                    break;
                }
            }

            if (isPlayerDefeated == true) {
                listener.EndFight(false);
                queuedActions.clear();
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
                queuedActions.clear();
                return;
            }
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

        for (Fighter fighter : playerFighters) {
            fighter.isGuarding = false;
            fighter.isStunned = false;
        }

        for (Fighter fighter : enemyFighters) {
            fighter.isGuarding = false;
            fighter.isStunned = false;
        }

        ListActions();
    }

    public void NextFighter() {
        activeIndex++;

        if (activeIndex == 8) {
            RunActions();

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
            if (activeFighter.isPlayerControlled) {
                listener.UpdateState(FightState.selectingAction);
                ListActions();
            }
            else {
                listener.UpdateState(FightState.enemyAction);
                int actionIndex = 0;
                switch (r.nextInt(4)) {
                    case 0:
                    case 1:
                        actionIndex = 0;
                        break;
                    case 2:
                        actionIndex = 1;
                        break;
                    case 3:
                        actionIndex = 2;
                        break;
                }
                SetActiveAction(activeFighter.fightActions.get(actionIndex));
                AddAction(activeAction, AIChooseTarget());
                NextFighter();
                return;
            }
        }
        else if (activeFighter.isAlive == false && activeIndex == 7) {

        }
        else {
            NextFighter();
            return;
        }
    }

    public Fighter AIChooseTarget() {
        List<Integer> validTargets = new ArrayList<Integer>();

        int i = 0;

        switch (activeAction.aiming) {
            case enemy:
                for (Fighter fighter : playerFighters) {
                    if (fighter.isAlive) {
                        validTargets.add(i);
                    }
                    i++;
                }

                return playerFighters.get(validTargets.get(r.nextInt(validTargets.size())));
            case ally:
                for (Fighter fighter : enemyFighters) {
                    if (fighter.isAlive) {
                        validTargets.add(i);
                    }
                    i++;
                }

                return enemyFighters.get(validTargets.get(r.nextInt(validTargets.size())));
            case otherAlly:
                for (Fighter fighter : enemyFighters) {
                    if (fighter.isAlive && fighter != activeFighter) {
                        validTargets.add(i);
                    }
                    i++;
                }

                return enemyFighters.get(validTargets.get(r.nextInt(validTargets.size())));
            case self:
                return activeFighter;
            default:
                //should not get here
                return activeFighter;
        }
    }
}

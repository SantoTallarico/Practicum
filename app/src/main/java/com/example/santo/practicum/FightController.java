package com.example.santo.practicum;

import com.example.santo.practicum.Enums.FightState;
import com.example.santo.practicum.Enums.SpriteState;
import com.example.santo.practicum.FightActions.FightAction;
import com.example.santo.practicum.GameObjects.Fighter;
import com.example.santo.practicum.GameObjects.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        FightAction.playerFighters = playerFighters;
        FightAction.enemyFighters = enemyFighters;
    }

    public void AssignListener(FightListener l) {
        listener = l;
    }

    public void ListActions() {
        displayedActions = activeFighter.fightActions;
        listener.ListActions();
    }

    public boolean RunActions() {
        listener.UpdateState(FightState.actionsRunning);
        Collections.sort(queuedActions);

        for (FightAction action : queuedActions) {
            if (action.user.isAlive && !action.user.isStunned) {
                action.user.Translate(action.user.isPlayerControlled ? -50 : 50, 0);
                action.ApplyAction();
                listener.UpdateText();
                listener.ActionText(action);

                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                }
                catch(InterruptedException e) {
                    //should never happen
                }

                action.user.Translate(action.user.isPlayerControlled ? 50 : -50, 0);
            }

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
                return true;
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
                return true;
            }
        }

        queuedActions.clear();
        listener.UpdateState(FightState.selectingAction);

        return false;
    }

    public void SetActiveAction(FightAction action) {
        activeAction = action;
    }

    public void AddAction(FightAction action, Fighter target) {
        action.target = target;
        queuedActions.add(action);
    }

    public void StartFight() {
        for (Fighter fighter : playerFighters) {
            fighter.isAlive = true;
            fighter.ChangeSprite(SpriteState.idle);
        }

        for (Fighter fighter : enemyFighters) {
            fighter.isAlive = true;
            fighter.ChangeSprite(SpriteState.idle);
        }

        StartRound();
    }

    public void StartRound() {
        activeIndex = -1;

        for (Fighter fighter : playerFighters) {
            fighter.isGuarding = false;
            fighter.isGuarded = false;
            fighter.isStunned = false;
            if (fighter.isAlive) {
                fighter.ChangeSprite(SpriteState.idle);
            }
        }

        for (Fighter fighter : enemyFighters) {
            fighter.isGuarding = false;
            fighter.isGuarded = false;
            fighter.isStunned = false;
            if (fighter.isAlive) {
                fighter.ChangeSprite(SpriteState.idle);
            }
        }

        NextFighter();
    }

    public void NextFighter() {
        activeIndex++;

        if (activeIndex == 8) {
            if (RunActions() == true) {
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
            if (activeFighter.isPlayerControlled) {
                listener.UpdateState(FightState.selectingAction);
                ListActions();
            }
            else {
                listener.UpdateState(FightState.enemyAction);
                int actionIndex = activeFighter.AIChooseAction();
                SetActiveAction(activeFighter.fightActions.get(actionIndex));
                AddAction(activeAction, AIChooseTarget());
                NextFighter();
                return;
            }
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

                if (validTargets.size() == 0) {
                    return activeFighter;
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

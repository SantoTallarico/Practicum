package com.example.santo.practicum.FightActions;

import com.example.santo.practicum.Enums.Aiming;
import com.example.santo.practicum.GameObjects.Fighter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Santo on 10/16/2017.
 */

public abstract class FightAction implements Comparable<FightAction> {
    public Fighter user;
    public Fighter target;
    public int priority;
    public String name;
    public Aiming aiming;

    public static List<Fighter> playerFighters = new ArrayList<Fighter>();
    public static List<Fighter> enemyFighters = new ArrayList<Fighter>();
    public static Random r = new Random();

    public FightAction(String n, Fighter u, int p, Aiming a) {
        name = n;
        user = u;
        priority = p;
        aiming = a;
    }

    public void ChooseRandomTarget() {
        List<Integer> validTargets = new ArrayList<Integer>();
        int i = 0;

        switch (aiming) {
            case enemy:
                if (user.isPlayerControlled) {
                    for (Fighter fighter : enemyFighters) {
                        if (fighter.isAlive) {
                            validTargets.add(i);
                        }
                        i++;
                    }

                    target = enemyFighters.get(validTargets.get(r.nextInt(validTargets.size())));
                }
                else {
                    for (Fighter fighter : playerFighters) {
                        if (fighter.isAlive) {
                            validTargets.add(i);
                        }
                        i++;
                    }

                    target = playerFighters.get(validTargets.get(r.nextInt(validTargets.size())));
                }
                break;
            case ally:
                if (user.isPlayerControlled) {
                    for (Fighter fighter : playerFighters) {
                        if (fighter.isAlive) {
                            validTargets.add(i);
                        }
                        i++;
                    }

                    target = playerFighters.get(validTargets.get(r.nextInt(validTargets.size())));
                }
                else {
                    for (Fighter fighter : enemyFighters) {
                        if (fighter.isAlive) {
                            validTargets.add(i);
                        }
                        i++;
                    }

                    target = enemyFighters.get(validTargets.get(r.nextInt(validTargets.size())));
                }
                break;
            default:
                //should not get here
                target = user;
        }
    }

    @Override
    public int compareTo(FightAction other) {
        return Integer.compare(other.priority, this.priority);
    }

    public abstract void ApplyAction();
}

package com.example.santo.practicum.GameObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santo on 10/18/2017.
 */

public class Team implements Serializable {
    List<Fighter> teamCharacters;
    final static int TEAM_SIZE = 4;

    public Team(int teamSize) {
        teamCharacters = new ArrayList<Fighter>(TEAM_SIZE);
    }

    public void Init(Fighter empty) {
        for (int i = 0; i < TEAM_SIZE; i++) {
            teamCharacters.add(empty);
        }
    }

    public void Add(Fighter fighter, int position) {
        if (position < 0 || position >= TEAM_SIZE) {
            //invalid index
            return;
        }
        if (teamCharacters.contains(fighter)) {
            int existingPosition = teamCharacters.indexOf(fighter);
            Fighter temp = teamCharacters.get(position);
            teamCharacters.set(position, fighter);
            teamCharacters.set(existingPosition, temp);
        }
        else {
            teamCharacters.set(position, fighter);
        }
    }

    public Fighter Get(int position) {
        return teamCharacters.get(position);
    }

    public List<Fighter> GetTeam() {
        return teamCharacters;
    }
}

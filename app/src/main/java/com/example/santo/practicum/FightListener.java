package com.example.santo.practicum;

import com.example.santo.practicum.Enums.FightState;

/**
 * Created by Santo on 11/5/2017.
 */

public interface FightListener {
    void ListActions();
    void EndFight(boolean isPlayerWinner);
    void UpdateText();
    void UpdateState(FightState state);
}

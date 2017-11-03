package com.example.santo.practicum.Enums;

/**
 * Created by Santo on 10/16/2017.
 */

public class CharacterClassHelper {
    public static String ToString(CharacterClass c) {
        switch (c) {
            case warrior:
                return "Warrior";
            case rogue:
                return "Rogue";
            case wizard:
                return "Wizard";
            case cleric:
                return "Cleric";
            default:
                return "";
        }
    }
}

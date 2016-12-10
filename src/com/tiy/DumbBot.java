package com.tiy;

import java.util.Random;

/**
 * Created by erronius on 12/9/2016.
 */
public class DumbBot extends Player {

    public static final String[] BOT_NAMES = {"Joe", "Sal", "Jon", "Sue", "Fred", "Bob"};

    public DumbBot (char token) {
        super(BOT_NAMES[new Random().nextInt(6)] + "Bot", token);
    }

    public int getMove (SmallBoard board) {
        return 0;
    }

    public int getMove (BigBoard board) {
        return 0;
    }
}

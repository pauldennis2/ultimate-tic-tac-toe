package com.tiy;

import java.util.Random;

/**
 * Created by erronius on 12/9/2016.
 */
public class DumbBot extends Player {
    Random random;

    public static final String[] BOT_NAMES = {"Tim", "Sal", "Jon", "Sue", "Tom", "Bob"};

    public DumbBot (char token) {
        super(BOT_NAMES[new Random().nextInt(6)] + "Bot", token, PlayerType.DUMBBOT);
        random = new Random();
    }

    public int getMove (SmallBoard smallBoard) {
        int row = 0;
        int col = 0;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (smallBoard.get(row, col) != ' ');
        int answer = row*3 + col + 1;
        return answer;
    }

    public int getMove (BigBoard bigBoard) {
        int row = 0;
        int col = 0;
        char statusTokenOfDesiredBoard;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
            statusTokenOfDesiredBoard = bigBoard.get(row, col).getStatusToken();
        } while (statusTokenOfDesiredBoard != ' '); //if the board selected is full, send them back
        int answer = row*3 + col + 1;
        return answer;
    }
}

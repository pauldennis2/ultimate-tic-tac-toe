package com.tiy;

import java.util.Random;

/**
 * Created by pauldennis on 12/9/2016.
 *
 * This is a class of Bot that simply moves randomly.
 */
public class DumbBot extends Player {

    public static final String[] BOT_NAMES = {"Tim", "Sal", "Jon", "Sue", "Tom", "Bob"};

    Random random;

    public DumbBot (char token) {
        super(BOT_NAMES[new Random().nextInt(6)] + "Bot", token, PlayerType.DUMBBOT);
        random = new Random();
    }

    /**
     *
     * @param smallBoard the SmallBoard in which we have to move
     * @return a random legal move
     */
    @Override
    public int getMove (SmallBoard smallBoard) {
        int row = 0;
        int col = 0;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (smallBoard.get(row, col) != ' ');
        int answer = row * 3 + col + 1;
        return answer;
    }

    /**
     *
     * @param bigBoard the BigBoard in which we have to select a SmallBoard
     * @return a random legal SmallBoard number in which we want to move
     */
    @Override
    public int getMove (BigBoard bigBoard) {
        int row = 0;
        int col = 0;
        char statusTokenOfDesiredBoard;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
            statusTokenOfDesiredBoard = bigBoard.get(row, col).getStatusToken();
        } while (statusTokenOfDesiredBoard != ' '); //if the board selected is full, send them back
        int answer = row * 3 + col + 1;
        return answer;
    }
}

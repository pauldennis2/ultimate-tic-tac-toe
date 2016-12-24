package com.tiy;

import java.util.ArrayList;

/**
 * Created by pauldennis on 12/9/2016.
 *
 * This class serves as a record of a game. Needs implementation of writeToFile()
 */
public class GameRecord {

    String player1Name;
    String player2Name;

    PlayerType player1Type;
    PlayerType player2Type;

    ArrayList<MoveLocation> moves;

    String result;

    public GameRecord (Player player1, Player player2) {
        player1Name = player1.getName();
        player2Name = player2.getName();

        player1Type = player1.getType();
        player2Type = player2.getType();
        moves = new ArrayList<MoveLocation>();
    }

    /**
     * Adds the given move to the record
     *
     * @param bigRow row of the SmallBoard
     * @param bigCol column of the SmallBoard
     * @param smallRow row of the square within the given SmallBoard
     * @param smallCol column of the square within the given SmallBoard
     */
    public void addTurn (int bigRow, int bigCol, int smallRow, int smallCol) {
        moves.add(new MoveLocation(bigRow, bigCol, smallRow, smallCol));
    }

    /**
     *
     * @param result the result of the game overall (win, loss, tie)
     */
    public void addResult(String result) {
        this.result = result;
    }

    /**
     * Write the record to fie
     * TODO implement
     */
    public void writeToFile () {
        System.out.println("Writing to file");
    }
}

package com.tiy;

import java.util.ArrayList;

/**
 * Created by erronius on 12/9/2016.
 */
public class GameRecord {
    String player1Name;
    String player2Name;

    //String player1Type;
    //String player2Type;

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

    public void addTurn (int bigRow, int bigCol, int smallRow, int smallCol) {
        moves.add(new MoveLocation(bigRow, bigCol, smallRow, smallCol));
    }

    public void addResult(String result) {
        this.result = result;
    }

    public void writeToFile () {
        System.out.println("Writing to file");
    }
}

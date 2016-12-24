package com.tiy;

/**
 * Created by pauldennis on 12/9/2016.
 *
 * Abstract class to represent a Player of the game. Inheriting classes presumably break down into Bots and Humans.
 * Possibly could also be a user connected over the network, a NetworkUser or similar.
 */
public abstract class Player {


    //Don't hate the Player, hate its methods!
    private String name;
    private char token;
    private PlayerType type;

    /**
     *
     * @param name
     * @param token
     * @param type
     *
     * @throws AssertionError if there's an attempt to use a reserved token ('W' or 'T')
     */
    public Player (String name, char token, PlayerType type) {
        this.name = name;
        this.token = token;
        this.type = type;

        if (token == 'W' || token == 'T') {
            System.out.println("These tokens are reserved.");
            throw new AssertionError();
        }
    }

    /**
     *
     * @param board the SmallBoard in which we need to pick a square
     * @return the number of the square
     */
    public abstract int getMove(SmallBoard board);

    /**
     *
     * @param board the BigBoard in which we have to select a SmallBoard
     * @return the number of the SmallBoard
     */
    public abstract int getMove(BigBoard board);

    public char getToken() {
        return token;
    }
    public String getName() {
        return name;
    }
    public PlayerType getType() { return type; }
}

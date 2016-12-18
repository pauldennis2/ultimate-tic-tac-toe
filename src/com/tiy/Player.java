package com.tiy;

/**
 * Created by erronius on 12/9/2016.
 */
public abstract class Player {


    //Don't hate the Player, hate its methods!
    private String name;
    private char token;
    private PlayerType type;

    public Player (String name, char token, PlayerType type) {
        this.name = name;
        this.token = token;
        this.type = type;

        if (token == 'W' || token == 'T') {
            System.out.println("These tokens are reserved.");
            throw new AssertionError();
        }
    }

    public abstract int getMove(SmallBoard board);
    public abstract int getMove(BigBoard board);

    public char getToken() {
        return token;
    }
    public String getName() {
        return name;
    }
    public PlayerType getType() { return type; }
}

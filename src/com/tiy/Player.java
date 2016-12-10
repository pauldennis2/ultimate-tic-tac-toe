package com.tiy;

/**
 * Created by erronius on 12/9/2016.
 */
public abstract class Player {


    //Don't hate the Player, hate its methods!
    private String name;
    private char token;
    private String type;

    public Player (String name, char token, String type) {
        this.name = name;
        this.token = token;
        this.type = type;
    }

    public abstract int getMove(SmallBoard board);
    public abstract int getMove(BigBoard board);

    public char getToken() {
        return token;
    }
    public String getName() {
        return name;
    }
    public String getType() { return type; }
}

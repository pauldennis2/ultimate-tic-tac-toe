package com.tiy;

/**
 * Created by pauldennis on 12/10/2016.
 *
 * This is a POJO/Bean to keep track of the location of moves, both for keeping track of where the last move was and for
 * the GameRecord class.
 */
public class MoveLocation {

    private int bigRow;
    private int bigCol;
    private int smallRow;
    private int smallCol;

    public int getBigRow() {
        return bigRow;
    }

    public void setBigRow(int bigRow) {
        this.bigRow = bigRow;
    }

    public int getBigCol() {
        return bigCol;
    }

    public void setBigCol(int bigCol) {
        this.bigCol = bigCol;
    }

    public int getSmallRow() {
        return smallRow;
    }

    public void setSmallRow(int smallRow) {
        this.smallRow = smallRow;
    }

    public int getSmallCol() {
        return smallCol;
    }

    public void setSmallCol(int smallCol) {
        this.smallCol = smallCol;
    }

    public MoveLocation (int bigRow, int bigCol, int smallRow, int smallCol) {
        this.bigRow = bigRow;
        this.bigCol = bigCol;
        this.smallRow = smallRow;
        this.smallCol = smallCol;
    }
}

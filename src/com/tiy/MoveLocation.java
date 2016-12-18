package com.tiy;

/**
 * Created by erronius on 12/10/2016.
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

package com.tiy;

/**
 * Created by erronius on 12/9/2016.
 */
public class SmallBoard {

    boolean boardIsFull;
    char winningToken = ' ';
    char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};

    public SmallBoard() {
        board = new char[][] {{' ',' ',' '}, {' ',' ', ' '}, {' ', ' ',' '}};
    }

    public char getWinningToken () {
        char rowWinningToken = rowWinner(board);
        char columnWinningToken = columnWinner(board);
        char diagonalWinningToken = diagonalWinner(board);
        if (rowWinningToken != ' ') {
            winningToken = rowWinningToken;

        }
        return winningToken;
    }

    public char get (int row, int col) {
        return board[row][col];
    }


    public void clear () {
        board[0][0] = ' ';
        board[0][1] = ' ';
        board[0][2] = ' ';
        board[1][0] = ' ';
        board[1][1] = ' ';
        board[1][2] = ' ';
        board[2][0] = ' ';
        board[2][1] = ' ';
        board[2][2] = ' ';
    }

    public boolean placeToken (int row, int col, char c) {
        if (board[row][col] == ' ') {
            board[row][col] = c;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return boardIsFull;
    }

    private static char rowWinner (char[][] thisBoard) {
        for (int row = 0; row < 3; row++) {
            char c = thisBoard[row][0];//Starting from the leftmost char in the row
            if (c == ' ') {
                continue;
            }
            if (c == thisBoard[row][1]) {
                if (c == thisBoard[row][2]) {
                    return c;
                }
            }
        }
        return ' ';
    }
    private static char columnWinner (char[][] thisBoard) {
        for (int column = 0; column < 3; column++) {
            char c = thisBoard[0][column];//Starting from the top char in the column
            if (c == ' ') {
                continue;
            }
            if (c == thisBoard[1][column]) {
                if (c == thisBoard[2][column]) {
                    return c;
                }
            }
        }
        return ' ';
    }
    private static char diagonalWinner (char[][] thisBoard) {
        char c = thisBoard[0][0];
        if (c != ' ') {
            if (c == thisBoard[1][1]) {
                if (c == thisBoard[2][2]) {
                    return c;
                }
            }
        }
        c = thisBoard[2][0];
        if (c != ' ') {
            if (c == thisBoard[1][1]) {
                if (c == thisBoard[0][2]) {
                    return c;
                }
            }
        }
        return ' ';
    }
    private boolean boardIsFull () {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (board[row][column] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /*public String toString () {
        return new String(board[0]) + "\n" + new String(board[1]) + "\n" + new String(board[2]);
    }*/

    public String toString() {
        String response = "";
        for (String s : this.toStringArray()) {
            response += s + "\n";
        }
        return response;
    }

    public String[] toStringArray () {
        String [] response = new String[5];
        response [0] = board[0][0] + "|" + board[0][1] + "|" + board[0][2];
        response [1] = "-----";
        response [2] = board[1][0] + "|" + board[1][1] + "|" + board[1][2];
        response [3] = "-----";
        response [4] = board[2][0] + "|" + board[2][1] + "|" + board[2][2];
        return response;
    }

}
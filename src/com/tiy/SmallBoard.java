package com.tiy;

import java.util.Random;

/**
 * Created by pauldennis on 12/9/2016.
 *
 * This represents an individual Tic-Tac-Toe board
 */
public class SmallBoard {

    char statusToken = ' '; //Possibilities: ' ', 'X', 'O' (or custom tokens). 'W' or 'T' possible for status boards
    char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};

    public static final char WILDCARD_TOKEN = 'W';

    public SmallBoard () {
        board = new char[][] {{' ',' ',' '}, {' ',' ', ' '}, {' ', ' ',' '}};
    }

    public SmallBoard (char[][] board) {
        this.board = board;
    }

    public char[][] getBoard () {
        return board;
    }

    /**
     * Gets the status token of the board.
     * @return the token representing the status. ' ' if no winner, the token of the winner if there is one ('X' or 'O'
     * usually). In case of a tie (the board is full and there is no winner - an unwinnable non-full board is not
     * considered a tie) 'T', or 'W' if using the wildcard rule.
     */
    public char getStatusToken () {
        char rowWinningToken = rowWinner(board);
        char columnWinningToken = columnWinner(board);
        char diagonalWinningToken = diagonalWinner(board);
        if (rowWinningToken != ' ') {
            statusToken = rowWinningToken;
        }
        if (columnWinningToken != ' ') {
            statusToken = columnWinningToken;
        }
        if (diagonalWinningToken != ' ') {
            statusToken = diagonalWinningToken;
        }
        if (this.boardIsFull()) {
            return 'T';
        }
        //This works because on a regular board following normal rules you can't have two winners
        return statusToken;
    }

    /**
     * Get the token at the given location
     * @param row row of the square (0-2)
     * @param col column of the square (0-2)
     * @return the token at the given location
     */
    public char get (int row, int col) {
        return board[row][col];
    }

    /**
     * Clears the board. Should only be used for debugging/with care.
     */
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

    /**
     * Attempts to place the given token at the given location
     * @param row row of the square (0-2)
     * @param col column of the square (0-2)
     * @param token token to be placed
     * @return true if successfully placed, false otherwise
     */
    public boolean placeToken (int row, int col, char token) {
        if (board[row][col] == ' ') {
            board[row][col] = token;
            return true;
        }
        return false;
    }

    /**
     * Helper method for getStatusToken()
     * @param thisBoard board to check
     * @return token of a row winner if one exists, ' ' otherwise
     */
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

    /**
     * Helper method for getStatusToken()
     * @param thisBoard Board to check
     * @return token of a column winner if one exists, ' ' otherwise
     */
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

    /**
     * Helper method for getStatusToken()
     * @param thisBoard Board to check
     * @return token of a diagonal winner if one exists, ' ' otherwise
     */
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

    /**
     * @TODO this needs fixing. Wildcards aren't working. not sure why
     * @param p1Token Player 1's token
     * @param p2Token Player 2's token
     * @return the status token
     *
     * Notes:
     * Does this method care about the order of the tokens? I don't think so, need to make sure
     * Also note that this method should only ever be called from a status board.
     */
    public char getStatusTokenWithWildCards (char p1Token, char p2Token) {
        char[][] boardWithFirstToken = new char[3][3];
        char[][] boardWithSecondToken = new char[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char c = board[row][col];
                if (c != WILDCARD_TOKEN) {
                    boardWithFirstToken[row][col] = board[row][col];
                    boardWithSecondToken[row][col] = board[row][col];
                } else {
                    boardWithFirstToken[row][col] = p1Token;
                    boardWithSecondToken[row][col] = p2Token;
                }
            }
        } //At the end of this loop we should have two boards that are copies of the main board except
        //Any 'W' wildcard tokens should have been replaced with the player tokens

        SmallBoard p1Board = new SmallBoard(boardWithFirstToken);
        SmallBoard p2Board = new SmallBoard(boardWithSecondToken);
        char p1BoardToken = p1Board.getStatusToken();
        char p2BoardToken = p2Board.getStatusToken();
        /*
        System.out.println("p1 board:");
        System.out.println(p1Board);
        System.out.println("Token: -" + p1BoardToken + "-");
        System.out.println("p2 board:");
        System.out.println(p2Board);
        System.out.println("Token: -" + p2BoardToken + "-");
        */



        if ((p1BoardToken == p1Token) && (p2BoardToken == p2Token)) { //If they both won
            return WILDCARD_TOKEN;
        } else if (p1BoardToken == p1Token) { //Only p1 wins
            return p1Token;
        } else if (p2BoardToken == p2Token) {
            return p2Token;
        }
        return ' '; //nobody has won yet
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

    @Override
    public String toString() {
        String response = "";
        for (String s : this.toStringArray()) {
            response += s + "\n";
        }
        return response;
    }

    /**
     * Helper method for toString()
     * @return a String[] with each line of the board
     */
    private String[] toStringArray () {
        String [] response = new String[5];
        response [0] = board[0][0] + "|" + board[0][1] + "|" + board[0][2];
        response [1] = "-----";
        response [2] = board[1][0] + "|" + board[1][1] + "|" + board[1][2];
        response [3] = "-----";
        response [4] = board[2][0] + "|" + board[2][1] + "|" + board[2][2];
        return response;
    }

    /**
     * See BigBoard.createRandomBigBoard()
     * @param numTokens the number of tokens to attempt to place
     * @return A SmallBoard with 1-numTokens tokens placed in it
     */
    public static SmallBoard createRandomSmallBoard (int numTokens) {
        SmallBoard smallBoard = new SmallBoard();
        Random random = new Random();
        for (int i = 0; i < numTokens; i++) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);
            char token;
            if (random.nextBoolean()) {
                token = 'X';
            } else {
                token = 'O';
            }
            smallBoard.placeToken(row, col, token);
        }
        return smallBoard;
    }


}

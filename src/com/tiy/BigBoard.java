package com.tiy;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pauldennis on 12/9/2016.
 *
 * Class that represents the entire board. Includes the statusBoard, a SmallBoard representing the status of each of
 * the individual SmallBoards.
 */
public class BigBoard {

    private SmallBoard[][] boardArray;
    private SmallBoard statusBoard;

    private boolean tiedSquareCountsForBoth;

    private MoveLocation mostRecentMove;

    public BigBoard () {
        mostRecentMove = null;
        tiedSquareCountsForBoth = false;
        statusBoard = new SmallBoard();
        boardArray = new SmallBoard[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardArray[row][col] = new SmallBoard();
            }
        }
    }

    /**
     * Returns the most recent move made on the board.
     * @return the most recent move - null if no moves entered yet
     */
    public MoveLocation getMostRecentMove () {
        return mostRecentMove;
    }

    /**
     * Returns the current board number.
     * @return current small board number in 1-9 format; 0 if the board is open (see rules)
     */
    public int getCurrentBoardNum () {
        if (mostRecentMove == null) {
            return 0; //If there have not been any moves, the board is "open" (0)
        }
        int row = mostRecentMove.getSmallRow();
        int col = mostRecentMove.getSmallCol();
        //If the most recent square moved in corresponds to a won or full board, they can move anywhere
        //i.e. zero. Otherwise, give them the 1-9 board number
        if (boardArray[row][col].getStatusToken() != ' ') {
            return 0;
        } else {
            return row * 3 + col + 1;
        }
    }

    /**
     * This method is ONLY for testing purposes and should not be called under any normal circumstance during gameplay.
     * @param loc the location to set
     */
    public void setMostRecentMove (MoveLocation loc) {
        System.out.println("Warning from bigBoard.setMostRecentMove():");
        System.out.println("You shouldn't be using this except for testing.");
        mostRecentMove = loc;
    }

    public SmallBoard getStatusBoard () {
        return statusBoard;
    }

    /**
     * "Edge" boards are marked with E's:
     * CEC<br>
     * EME<br>
     * CEC
     * @return All the SmallBoards that are considered edge boards.
     */
    public ArrayList<SmallBoard> getEdgeBoards () {
        ArrayList<SmallBoard> edgeBoards = new ArrayList<SmallBoard>();
        //Edge boards are: 0,1 1,0 1,2 2,1
        edgeBoards.add(boardArray[0][1]);
        edgeBoards.add(boardArray[1][0]);
        edgeBoards.add(boardArray[1][2]);
        edgeBoards.add(boardArray[2][1]);
        return edgeBoards;
    }

    /**
     * "Corner" boards are marked with C's:
     * CEC<br>
     * EME<br>
     * CEC
     * @return All the SmallBoards that are considered corner boards.
     */
    public ArrayList<SmallBoard> getCornerBoards () {
        ArrayList<SmallBoard> cornerBoards = new ArrayList<SmallBoard>();
        //Corner boards are: 0,0 0,2 2,0 2,2
        cornerBoards.add(boardArray[0][0]);
        cornerBoards.add(boardArray[0][2]);
        cornerBoards.add(boardArray[2][0]);
        cornerBoards.add(boardArray[2][2]);
        return cornerBoards;
    }

    public boolean tiedSquaresCountForBoth () {
        return tiedSquareCountsForBoth;
    }

    /**
     * Flips the wildcard rule.
     */
    public void changeTiedSquareRule () {
        tiedSquareCountsForBoth = !tiedSquareCountsForBoth;
        System.out.print("Wildcard Rule is now ");
        if (tiedSquareCountsForBoth) {
            System.out.println("ON");
        } else {
            System.out.println("OFF");
        }
    }

    /**
     * Returns the SmallBoard at row-col
     * @param row the row, 0-2
     * @param col the column, 0-2
     * @return the SmallBoard at that location
     */
    public SmallBoard get (int row, int col) {
        return boardArray[row][col];
    }

    /**
     * This method is a helper method for the toString() method.
     *
     * TODO: change this method to work with StringBuilders to be more efficient in memory
     * @return a String[] with the lines of the board
     */
    private String[] toStringArray () {
        String[] response = new String[19];

        int line = 0;
        for (; line < 5; line++) {
            response[line] = boardArray[0][0].toStringArray()[line] + "||" + boardArray[0][1].toStringArray()[line] + "||" + boardArray[0][2].toStringArray()[line];
        }
        response[line]  = "-------------------";
        line++;
        response[line]  = "-------------------";
        line++;
        for (; line < 12; line++) {
            response[line] = boardArray[1][0].toStringArray()[line-7] + "||" + boardArray[1][1].toStringArray()[line-7] + "||" + boardArray[1][2].toStringArray()[line-7];
        }
        response[line] = "-------------------";
        line++;
        response[line] = "-------------------";
        line++;
        for (; line < 19; line++) {
            response[line] = boardArray[2][0].toStringArray()[line-14] + "||" + boardArray[2][1].toStringArray()[line-14] + "||" + boardArray[2][2].toStringArray()[line-14];
        }
        response[2] += "    Key:";
        response[3] += "      123";
        response[4] += "      456";
        response[5] += "      789";

        response[8] += "    Overall Status Board";
        for (int line2 = 9; line2 < 14; line2++) {
            response[line2] += "      " + statusBoard.toStringArray()[line2-9];
        }
        return response;
    }

    /**
     *
     * @return String representation of the board including the statusBoard and key
     */
    public String toString () {
        String response = "";
        for (String line : this.toStringArray()) {
            response += line + "\n";
        }
        return response;
    }

    /**
     * Attempts to place the token at the given location (see params). Can throw an IndexArrayOutOfBoundsException with
     * bad inputs, but by the time the method has been called, that should already have been screened out.
     * @param bigRow row of the SmallBoard
     * @param bigCol column of the SmallBoard
     * @param smallRow row of the square within the given SmallBoard
     * @param smallCol column of the square within the given SmallBoard
     * @param token token to place at the location
     * @return true if the method was able to successfully place the token; false otherwise
     */
    public boolean placeToken (int bigRow, int bigCol, int smallRow, int smallCol, char token) {
        if (boardArray[bigRow][bigCol].placeToken(smallRow, smallCol, token)) { //Attempts to place the token in the corresponding SmallBoard
            mostRecentMove = new MoveLocation(bigRow, bigCol, smallRow, smallCol);
            this.statusUpdate();
            return true;
        }
        return false;
    }

    /**
     * Updates the statusBoard and passes back an int with the status.
     * TODO: Change this to work with an enum
     * @return -1 if tied; 1 if 1, 0 if still going
     */
    public int statusUpdate () {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char statusToken = boardArray[row][col].getStatusToken();
                if (statusToken == 'T' && tiedSquareCountsForBoth) {
                    statusBoard.placeToken(row, col, 'W');
                } else {
                    statusBoard.placeToken(row, col, statusToken);
                }
            }
        }
        char overallStatusToken = statusBoard.getStatusToken();
        if (overallStatusToken == 'T') {
            return -1;
        } else if (overallStatusToken != ' ') { //Not tied and not empty; must be a win
            return 1;
        }
        return 0;
    }

    /**
     *
     * @param token - the token to place
     * @return an ArrayList<BigBoard> of possibilities of what the board will look like after all possible moves
     */
    public ArrayList<BigBoard> getPossibleMoves (char token) {
        int currentSmallBoardNum = this.getCurrentBoardNum();
        ArrayList<BigBoard> possibleMoves = new ArrayList<BigBoard>();
        if (currentSmallBoardNum == 0) {
            //can move anywhere; more complicated
            for (int bigRow = 0; bigRow < 3; bigRow++) {
                for (int bigCol = 0; bigCol < 3; bigCol++) {
                    SmallBoard currentSmallBoard = this.get(bigRow, bigCol);
                    for (int smallRow = 0; smallRow < 3; smallRow++) {
                        for (int smallCol = 0; smallCol < 3; smallCol++) {
                            if (currentSmallBoard.get(smallRow, smallCol) == ' ') {
                                BigBoard newBoard = this.copy();
                                newBoard.placeToken(bigRow, bigCol, smallRow, smallCol, token);
                                possibleMoves.add(newBoard);
                                //We've created a new copy of the board with the potential move and added it to the list
                            }
                        }
                    }
                }
            }
        } //End if currentSmallBoardNum == 0
        else { //currentSmallBoardNum != 0, we are restricted to one smallBoard to move in
            int bigRow = (currentSmallBoardNum - 1) / 3;
            int bigCol = (currentSmallBoardNum - 1) % 3;
            SmallBoard currentSmallBoard = this.get(bigRow, bigCol);
            for (int smallRow = 0; smallRow < 3; smallRow++) {
                for (int smallCol = 0; smallCol < 3; smallCol++) {
                    if (currentSmallBoard.get(smallRow, smallCol) == ' ') {
                        BigBoard newBoard = this.copy();
                        newBoard.placeToken(bigRow, bigCol, smallRow, smallCol, token);
                        possibleMoves.add(newBoard);
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Copies this.
     * @return A BigBoard object that is a distinct copy of this. Used to build the AI tree.
     */
    public BigBoard copy () {
        BigBoard copy = new BigBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                SmallBoard smallBoard = this.get(row, col);
                for (int smallRow = 0; smallRow < 3; smallRow++) {
                    for (int smallCol = 0; smallCol < 3; smallCol++) {
                        copy.placeToken(row, col, smallRow, smallCol, smallBoard.get(smallRow, smallCol));
                    }
                }
            }
        }
        return copy;
    }

    /**
     * Clears the entire board. Should be used with caution/for testing purposes.
     */
    public void clear () {
        System.out.println("Board cleared.");
        mostRecentMove = null;
        for (SmallBoard[] boardRow : boardArray) {
            for (SmallBoard smallBoard : boardRow) {
                smallBoard.clear();
            }
        }
    }

    /**
     * Returns a randomly generated Ultimate TTT board. The method will attempt to place tokens numTokens times, but
     * any time it attempts to place a token on an already occupied square, it will simply skip. This means that the
     * actual number of tokens on the returned board will be somewhere between 1 and numTokens, with an average of
     * around 50-60% of numTokens. We leave the exact math on that as an exercise for the reader. Tokens will be
     * a random distribution of X's and O's - no guarantee of parity.
     * @param numTokens number of tokens to ATTEMPT to place
     * @return          A BigBoard object with between 1 and numTokens tokens placed
     */
    public static BigBoard createRandomBoard (int numTokens) {
        BigBoard bigBoard = new BigBoard();
        Random random = new Random();
        for (int i = 0; i < numTokens; i++) {
            int bigRow = random.nextInt(3);
            int bigCol = random.nextInt(3);
            int smallRow = random.nextInt(3);
            int smallCol = random.nextInt(3);
            char token;
            if (random.nextBoolean()) {
                token = 'X';
            } else {
                token = 'O';
            }
            bigBoard.placeToken(bigRow, bigCol, smallRow, smallCol, token);
        }
        bigBoard.statusUpdate();
        return bigBoard;
    }
}

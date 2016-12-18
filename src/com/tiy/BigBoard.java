package com.tiy;

import java.util.ArrayList;

/**
 * Created by erronius on 12/9/2016.
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
    //Can be null!
    public MoveLocation getMostRecentMove () {
        return mostRecentMove;
    }

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
            return (row*3 + col + 1);
        }
    }


    public void setMostRecentMove (MoveLocation loc) {
        System.out.println("Warning from bigBoard.setMostRecentMove():");
        System.out.println("You shouldn't be using this except for testing.");
        mostRecentMove = loc;
    }

    public SmallBoard getStatusBoard () {
        return statusBoard;
    }

    public ArrayList<SmallBoard> getEdgeBoards () {
        ArrayList<SmallBoard> edgeBoards = new ArrayList<SmallBoard>();
        //Edge boards are: 0,1 1,0 1,2 2,1
        edgeBoards.add(boardArray[0][1]);
        edgeBoards.add(boardArray[1][0]);
        edgeBoards.add(boardArray[1][2]);
        edgeBoards.add(boardArray[2][1]);
        return edgeBoards;
    }

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

    public void changeTiedSquareRule () {
        tiedSquareCountsForBoth = !tiedSquareCountsForBoth;
        System.out.print("Wildcard Rule is now ");
        if (tiedSquareCountsForBoth) {
            System.out.println("ON");
        } else {
            System.out.println("OFF");
        }
    }

    public SmallBoard get (int row, int col) {
        return boardArray[row][col];
    }

    public String[] toStringArray () {
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

    public String toString () {
        String response = "";
        for (String line : this.toStringArray()) {
            response += line + "\n";
        }
        return response;
    }

    public boolean placeToken (int bigRow, int bigCol, int smallRow, int smallCol, char token) {
        if (boardArray[bigRow][bigCol].placeToken(smallRow, smallCol, token)) { //Attempts to place the token in the corresponding SmallBoard
            mostRecentMove = new MoveLocation(bigRow, bigCol, smallRow, smallCol);
            this.statusUpdate();
            return true;
        }
        return false;
    }

    /**
     *
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
     * @return an ArrayList<BigBoard> of possibilities of what the board will look like after the move
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

    //Clears entire board! Use with caution.
    public void clear () {
        System.out.println("Board cleared.");
        mostRecentMove = null;
        for (SmallBoard[] boardRow : boardArray) {
            for (SmallBoard smallBoard : boardRow) {
                smallBoard.clear();
            }
        }
    }
}
